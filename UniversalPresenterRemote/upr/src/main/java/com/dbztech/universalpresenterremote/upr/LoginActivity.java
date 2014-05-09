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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;


public class LoginActivity extends Activity {
    public final static String TokenMessage = "com.dbztech.universalpresenterremote.upr.TOKEN";
    public static boolean queryServer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get a Tracker (should auto-report)
        ((upr) getApplication()).getTracker(upr.TrackerName.APP_TRACKER);

        // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        ServerCommunication.setupUid();
        updateInterface();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Get an Analytics tracker to report app starts & uncaught exceptions etc.
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stop the analytics tracking
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        queryServer = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryServer = true;
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
            openInstructions(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openInstructions(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra(TokenMessage, "123456");
        startActivity(intent);
    }

    public void updateInterface() {
        if (queryServer) {
            ServerCommunication.checkStatus();
            new CountDownTimer(1500, 750) {

                public void onTick(long millisUntilFinished) {
                    final Button connectButton = (Button) findViewById(R.id.connectButton);
                    final TextView tokenView = (TextView) findViewById(R.id.loginToken);
                    int test = ServerCommunication.controlMode;
                    String set = Integer.toString(ServerCommunication.token);
                    switch (ServerCommunication.controlMode) {
                        case 0:
                            connectButton.setEnabled(false);
                            tokenView.setText("...");
                            break;
                        case 1:
                            connectButton.setEnabled(false);
                            connectButton.setText("Waiting...");
                            tokenView.setText(set);
                            break;
                        case 2:
                            connectButton.setEnabled(true);
                            tokenView.setText(set);
                            connectButton.setText("Begin");
                            break;
                    }
                }

                public void onFinish() {
                    //Do nothing
                    updateInterface();
                }
            }.start();
        }
    }

}
