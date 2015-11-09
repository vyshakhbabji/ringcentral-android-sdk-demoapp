package com.ringcentral.rcandroidsdkdemoapp;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import http.APIResponse;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import core.SDK;
import platform.Platform;


public class AuthActivity extends ActionBarActivity implements View.OnClickListener {

    SDK sdk;
    Platform helpers;
    EditText editText1, editText2, editText3, editText4, editText5;
    Button button1;
    CheckBox checkPrompt;
    String hasPrompt = "SANDBOX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        addListenerOnCheckBox();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button1:

                String appKey = editText4.getText().toString();
                String appSecret = editText5.getText().toString();
                helpers = sdk.platform();
                String username = editText1.getText().toString();
                String extension = editText2.getText().toString();
                String password = editText3.getText().toString();
                helpers.login("15856234138", "101", "P@ssw0rd",
                        new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                APIResponse transaction = new APIResponse(response);
                                // If HTTP response is not successful, throw exception
                                //if (!response.isSuccessful())

                                // Create RCResponse and parse the JSON response to set Auth data
                                helpers.setAuth(transaction.response());
                                // Display options Activity
                                Intent optionsIntent = new Intent(AuthActivity.this, OptionsActivity.class);
                             //  optionsIntent.putExtra("accessToken", helpers.getAccessToken());

                                Singleton.getInstance().setPlatform(helpers);
                                startActivity(optionsIntent);
                            }
                        });
                break;
        }
    }

    public void addListenerOnCheckBox() {
        checkPrompt = (CheckBox) findViewById(R.id.checkPrompt);
        checkPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    hasPrompt = "PRODUCTION";
                }else{
                    hasPrompt = "SANDBOX";
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auth, menu);
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