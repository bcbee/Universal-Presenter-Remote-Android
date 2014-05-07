package com.dbztech.universalpresenterremote.upr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ControlActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Intent intent = getIntent();
        String token = intent.getStringExtra(LoginActivity.TokenMessage);
        setTitle("Back");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        ServerCommunication.reset();
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void slideUp(View view) {
        ServerCommunication.slideControl(1);
    }

    public void slideDown(View view) {
        ServerCommunication.slideControl(0);
    }

    public void playMedia(View view) {
        ServerCommunication.slideControl(2);
    }

}
