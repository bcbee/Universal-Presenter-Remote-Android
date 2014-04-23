package com.dbztech.universalpresenterremote.upr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

public class LoginActivity extends Activity {
    public final static String TokenMessage = "com.dbztech.universalpresenterremote.upr.TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ServerCommunication.checkStatus();
        updateInterface();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra(TokenMessage, "123456");
        startActivity(intent);
    }

    public void updateInterface() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                final Button connectButton = (Button) findViewById(R.id.connectButton);
                if (ServerCommunication.controlMode > 0) {
                    final TextView tokenView = (TextView) findViewById(R.id.loginToken);
                    String set = Integer.toString(ServerCommunication.token);
                    tokenView.setText(set);
                    connectButton.setEnabled(true);
                } else {
                    connectButton.setEnabled(false);
                }
            }

            public void onFinish() {
                //Do nothing
            }
        }.start();
    }

}
