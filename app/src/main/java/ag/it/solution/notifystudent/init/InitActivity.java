package ag.it.solution.notifystudent.init;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import ag.it.solution.notifystudent.R;
import ag.it.solution.notifystudent.db.MessageDB;
import ag.it.solution.notifystudent.listContats.ListContactsActivity;
import ag.it.solution.notifystudent.login.LoginActivity;

public class InitActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // this.getActionBar().hide();

        //inicio de app
        countDownTimer = createCountDownTimer();
        countDownTimer.start();
    }


    private CountDownTimer createCountDownTimer() {
        return new CountDownTimer(3000, 3000 + 1) {
            @Override public void onTick(long millisUntilFinished) { }

            @Override public void onFinish() {
                callActivity();
            }
        };
    }

    private void callActivity(){

        //llamar al activity login si no hay usuario registrado en la bd como logueado

        MessageDB db = MessageDB.getInstances(getApplicationContext());
        db.open();
        String signIn= db.isSigIn();
        db.close();
        Intent intent;
        if(signIn!=null){
             intent = new Intent(this, ListContactsActivity.class);
             intent.putExtra("USERNAME", signIn);
        }else {
            intent = new Intent(this, LoginActivity.class);
        }
        this.startActivity(intent);
    }
}
