package com.ringcentral.rcandroidsdkdemoapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ringcentral.rc_android_sdk.rcsdk.core.SDK;
import com.ringcentral.rc_android_sdk.rcsdk.platform.Platform;

public class RingOutActivity extends ActionBarActivity implements View.OnClickListener {


    SDK sdk;
    Platform helpers;
    EditText fromText, toText, callerIDText;
    CheckBox checkPrompt;
    String hasPrompt = "false";
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_out);
        Intent intent = getIntent();
        sdk = (SDK) intent.getSerializableExtra("MyRcsdk");
        helpers = sdk.platform();
        addListenerOnCheckBox();
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        fromText = (EditText) findViewById(R.id.fromMessage);
        toText = (EditText) findViewById(R.id.toMessage);
        callerIDText = (EditText) findViewById(R.id.callerIDMessage);
    }

    public void addListenerOnCheckBox() {
        checkPrompt = (CheckBox) findViewById(R.id.checkPrompt);
        checkPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    hasPrompt = "true";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button1:
                String to = toText.getText().toString();
                String from = fromText.getText().toString();
                String callerId = callerIDText.getText().toString();
//                helpers.ringOut(to, from, callerId, hasPrompt,
//                        new Callback() {
//                            @Override
//                            public void onFailure(Request request, IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(Response response) throws IOException {
//                                Transaction transaction = new Transaction(response);
//                                String responseString = transaction.getBodyString();
//                                // If HTTP response is not successful, throw exception
//                                if (!response.isSuccessful()) {
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(responseString);
//                                        String errorCode = jsonObject.getString("errorCode");
//                                        String message = jsonObject.getString("message");
//                                        throw new IOException("Error code: "+ transaction.response.code() + ". Error: " + errorCode + ": " + message);
//                                    } catch (JSONException e){
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ring_out, menu);
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