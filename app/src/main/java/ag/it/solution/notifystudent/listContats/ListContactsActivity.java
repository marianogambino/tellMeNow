package ag.it.solution.notifystudent.listContats;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ag.it.solution.notifystudent.R;
import ag.it.solution.notifystudent.ResponseService.ContactResponse;
import ag.it.solution.notifystudent.ResponseService.User;
import ag.it.solution.notifystudent.adapter.ListContactAdapter;
import ag.it.solution.notifystudent.constants.Constants;
import ag.it.solution.notifystudent.itemList.ContactItem;
import ag.it.solution.notifystudent.message.MessageActivity;
import ag.it.solution.notifystudent.restConnection.RestService;

public class ListContactsActivity extends AppCompatActivity {

    private ListContactAdapter adapter;
    private ContactsTask contactTask = null;
    private View mProgressView;
    private View mContatcsView;
    private final List<ContactItem> contactos = new ArrayList<ContactItem>();
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressView = findViewById(R.id.contacts_progress);
        mContatcsView = findViewById(R.id.listViewUser);

        Intent intent = this.getIntent();
        username = intent.getStringExtra("USERNAME");

        //System.out.println("IN ListContactsActivity USERNAME: " + username);

        //IR AL SERVIDOR Y OBTENER TODOS LOS CONTACTOS
        showProgress(true);
        contactTask = new ContactsTask(username, ListContactsActivity.this);
        contactTask.execute((Void) null);

        ListView list = (ListView) findViewById(R.id.listViewUser);
        adapter = new ListContactAdapter( this , contactos );

        list.setAdapter(adapter);

        // Eventos
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                try{
                    ContactItem contact = adapter.getItem(position);

                    Toast.makeText( getApplicationContext() ,
                            "Iniciar screen de detalle para: \n" + contact.getUsername(),
                            Toast.LENGTH_SHORT).show();
                    String contactJson = new ObjectMapper().writeValueAsString( contact );
                    Intent intent = new Intent( getApplicationContext() , MessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra( Constants.EXTRA_MESSAGE , contactJson );
                    getApplicationContext().startActivity(intent);

                } catch (JsonParseException e1) {
                    showErrorMessage(e1.getMessage());
                } catch (JsonMappingException e1) {
                    showErrorMessage(e1.getMessage());
                } catch (IOException e1) {
                    showErrorMessage(e1.getMessage());
                }
            }
        });

    }

    private void showErrorMessage(String m){
        Toast.makeText( getApplicationContext() ,
                "ERROR: \n" + m,
                Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

    public class ContactsTask extends AsyncTask<Void, Void, ContactResponse> {

        private final String mUsername;
        private Context ctx;

        ContactsTask(String username, Context ctx) {
            mUsername = username;
            this.ctx = ctx;
        }

        @Override
        protected ContactResponse doInBackground(Void... params) {
            try {
                ContactResponse response = RestService.getInstance().getContacts(mUsername);
                return response;
            } catch (Exception e) {
                return new ContactResponse();
            }
        }

        @Override
        protected void onPostExecute(final ContactResponse success) {
            contactTask = null;
            showProgress(false);

            if (success.getStatus().equalsIgnoreCase("OK")) {

                ContactItem item;
                for(User u : success.getUsers()){
                    item = new ContactItem( u.getUsername() ,u.getToken() , "", u.getUsername());
                    contactos.add(item);
                }

            } else {
                //mostrar toast de error
            }
        }
    }


    protected void onCancelled() {
        contactTask = null;
        showProgress(false);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mContatcsView.setVisibility(show ? View.GONE : View.VISIBLE);
            mContatcsView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mContatcsView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mContatcsView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
