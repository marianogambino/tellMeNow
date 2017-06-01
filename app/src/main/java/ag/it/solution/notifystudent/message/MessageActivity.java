package ag.it.solution.notifystudent.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ag.it.solution.notifystudent.R;
import ag.it.solution.notifystudent.ResponseService.ContactResponse;
import ag.it.solution.notifystudent.ResponseService.MessageResponse;
import ag.it.solution.notifystudent.ResponseService.User;
import ag.it.solution.notifystudent.adapter.MessageAdapter;
import ag.it.solution.notifystudent.constants.Constants;
import ag.it.solution.notifystudent.itemList.MessageItem;
import ag.it.solution.notifystudent.itemList.ContactItem;
import ag.it.solution.notifystudent.restConnection.RestService;

public class MessageActivity extends AppCompatActivity {


    private ImageButton btnSendMessage;
    private EditText editTxt;
    private List<MessageItem> messages;

    private MessageAdapter adapter;
    private ListView listMessage;

    private MessageTask task = null;

    private ContactItem user = null;
    private String myuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        try {
            Intent intent = this.getIntent();
            String userJson = intent.getStringExtra(Constants.EXTRA_MESSAGE);
            myuser = intent.getStringExtra("USERNAME");
            user = new ObjectMapper().readValue(userJson, ContactItem.class);

            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            toolbar.setTitle( user.getUsername() );
            setSupportActionBar(toolbar);



            //LEVANTAR UN THREAD PARA LA LECTURA DE MENSAJES (lee los mensajes de la bd de tipo R con estado pendiente de lectura),
            // LEVANTAR OTRO THREAD PARA EL ENVIO DE MENSAJE (lee los mensajes de la bd de tipo E con estado pendiente de envio)
            //

            //TODO: Historial conversacion -> ir a la BD interna y obtener los ultimos 10 mensajes recibidos y enviados
            //Lista de mensajes
            //recibidos y enviados

            //mensaje, usuario, action(recibido/enviado), status(ok/pendiente)
            /**Replace for access BD**/
            messages = new ArrayList<MessageItem>();

            /*MessageItem item = new MessageItem( "Hola Mariano, como estas?" ,"Yanina" , "R", "");
            MessageItem item2 = new MessageItem( "Hola Yanina, Bien y vos?" ,"Mariano" , "E", "");
            MessageItem item3 = new MessageItem( "Todo Bien" ,"Yanina" , "R", "");

            messages.add(item);
            messages.add(item2);
            messages.add(item3);

            */


            //Create Broadcast receiver
            //refernces -> http://stackoverflow.com/questions/22252065/refreshing-activity-on-receiving-gcm-push-notification

            if(listMessage == null) {
                listMessage = (ListView) findViewById(R.id.listViewMessage);
            }

            adapter = new MessageAdapter( this , messages );
            listMessage.setAdapter(adapter);
            listMessage.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
            listMessage.setStackFromBottom(true);

            //ingreso de texto
            //boton de envio de mensaje con el evento click.. + add a la lista de mensaje enviado
            editTxt = (EditText) findViewById(R.id.chat_text);
            btnSendMessage = (ImageButton) findViewById(R.id.btn_send);
            btnSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String message = editTxt.getText().toString();
                    //send message + persist to bd + add item en listview
                    if(StringUtils.isNotEmpty(message)) {
                        adapter.add(new MessageItem(message, "", "E", ""));
                        adapter.notifyDataSetChanged();
                        ///GUARDAR MENSAJE EN LA BD DE TIPO E, ESTADO PENDIENTE DE ENVIO
                        editTxt.setText("");
                        task = new MessageTask( myuser , user.getToken(), message, MessageActivity.this );
                        task.execute((Void) null);
                    }
                }
            });

        } catch (JsonParseException e1) {
            showErrorMessage(e1.getMessage());
        } catch (JsonMappingException e1) {
            showErrorMessage(e1.getMessage());
        } catch (IOException e1) {
            showErrorMessage(e1.getMessage());
        }

    }

    private void showErrorMessage(String m){
        Toast.makeText( getApplicationContext() ,
                "ERROR: \n" + m,
                Toast.LENGTH_LONG).show();
    }

    public class MessageTask extends AsyncTask<Void, Void, MessageResponse> {

        private final String mUsername;
        private final String mtoken;
        private final String mmessage;
        private Context ctx;

        MessageTask(String username, String token, String message,Context ctx) {
            mUsername = username;
            mtoken = token;
            mmessage = message;
            this.ctx = ctx;
        }

        @Override
        protected MessageResponse doInBackground(Void... params) {
            try {
                MessageResponse response = RestService.getInstance().sendMessage(mUsername, mtoken, mmessage);
                return response;
            } catch (Exception e) {
                return new MessageResponse();
            }
        }

        @Override
        protected void onPostExecute(final MessageResponse success) {
            //contactTask = null;
           // showProgress(false);

            if (success.getStatus().equalsIgnoreCase("OK")) {

                //ver

            } else {
                //mostrar toast de error
            }
        }
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            String username = intent.getStringExtra("username");

            Log.d("BR MessageActivity" , username);

            adapter.add(new MessageItem(message, username, "R", ""));
            adapter.notifyDataSetChanged();
            //do other stuff here
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        this.getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        this.getApplicationContext().unregisterReceiver(mMessageReceiver);
    }


}
