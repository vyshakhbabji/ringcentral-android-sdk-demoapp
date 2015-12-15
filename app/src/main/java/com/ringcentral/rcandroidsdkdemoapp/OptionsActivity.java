package com.ringcentral.rcandroidsdkdemoapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ringcentral.rc_android_sdk.rcsdk.core.SDK;
import com.ringcentral.rc_android_sdk.rcsdk.platform.Platform;


public class OptionsActivity extends ActionBarActivity implements View.OnClickListener {

    Button button1, button2, button3, button4;
    SDK sdk;
    Platform helpers = Singleton.getInstance().getPlatform();
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Intent intent = getIntent();

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Intent optionsIntent = new Intent(OptionsActivity.this, CallStatusActivity.class);
                startActivity(optionsIntent);
                break;

            case R.id.button2:
                Intent logIntent = new Intent(OptionsActivity.this, CallLogActivity.class);
                startActivity(logIntent);
                break;

            case R.id.button3:
                Intent smsIntent = new Intent(OptionsActivity.this, SMSActivity.class);
                startActivity(smsIntent);
                break;

            case R.id.button4:
                Intent ringoutIntent = new Intent(OptionsActivity.this, RingOutActivity.class);
                startActivity(ringoutIntent);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}