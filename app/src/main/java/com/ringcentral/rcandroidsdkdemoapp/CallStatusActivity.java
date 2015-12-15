package com.ringcentral.rcandroidsdkdemoapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pubnub.api.PubnubError;
import com.ringcentral.rc_android_sdk.rcsdk.core.SDK;

import com.ringcentral.rc_android_sdk.rcsdk.http.APICallback;
import com.ringcentral.rc_android_sdk.rcsdk.http.APIResponse;
import com.ringcentral.rc_android_sdk.rcsdk.platform.AuthException;
import com.ringcentral.rc_android_sdk.rcsdk.platform.Platform;
import com.ringcentral.rc_android_sdk.rcsdk.subscription.Subscription;
import com.ringcentral.rc_android_sdk.rcsdk.utils.Helpers;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class CallStatusActivity extends ActionBarActivity {

   // SDK sdk;
    Platform helpers;
    Subscription subscription;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_status);
        Intent intent = getIntent();
        Log.v("Call Stat Act","True");
        final Platform platform = Singleton.getInstance().getPlatform();
        Helpers helpers = new Helpers(platform);
        textView1 = (TextView) findViewById(R.id.textView1);

        helpers.subscribe(
                new APICallback() {
                    public void onAPIFailure(Request request, IOException e) {
                        Log.v("Subscription Failed", String.valueOf(e.fillInStackTrace()));
                        e.printStackTrace();
                    }

                    public void onAPIResponse(APIResponse response) throws IOException {
                        Log.v("Subscription Response", String.valueOf(response.statusCode()));
                        if (!response.ok())
                            Log.v("Error Subscription", String.valueOf(response.statusCode()));
                        String res = response.body().string();
                        Log.v("Sub Resp String", res);
                        try {
                            JSONObject responseJson = new JSONObject(res);
                            final Subscription subscription = new Subscription(platform);
                            //subscription.updateSubscription(responseJson);
                            subscription.subscribe(responseJson,
                                    new com.pubnub.api.Callback() {
                                        @Override
                                        public void connectCallback(String channel, Object message) {
                                            System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
                                                    + " : " + message.getClass() + " : "
                                                    + message.toString());
                                        }

                                        @Override
                                        public void disconnectCallback(String channel, Object message) {
                                            String decryptedString = subscription.notify(message.toString(), subscription.deliveryMode.encryptionKey);
                                            System.out.print(decryptedString);
                                        }

                                        @Override
                                        public void reconnectCallback(String channel, Object message) {
                                            System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
                                                    + " : " + message.getClass() + " : " + message.toString());
                                        }

                                        @Override
                                        public void successCallback(String channel, Object message) {
                                            String decryptedString = subscription.notify(message.toString(), subscription.deliveryMode.encryptionKey);
                                            System.out.println(decryptedString);

                                            Message msg = handler.obtainMessage();
                                            msg.what = 1;
                                            msg.obj = decryptedString;
                                            handler.sendMessage(msg);
                                        }

                                        @Override
                                        public void errorCallback(String channel, PubnubError error) {
                                            System.out.println("SUBSCRIBE : ERROR on channel " + channel
                                                    + " : " + error.toString());
                                        }

                                    });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                RelativeLayout layout= (RelativeLayout) findViewById(R.id.callStatusLayout);
                String message = (String)msg.obj;
                try {
                    JSONObject json = new JSONObject(message);
                    JSONObject body = json.getJSONObject("body");
                    String telephonyStatus = body.getString("telephonyStatus");
                    textView1.setText(telephonyStatus);
                    if(telephonyStatus.equals("Ringing")){
                        layout.setBackgroundColor(Color.BLUE);
                    }
                    if(telephonyStatus.equals("CallConnected")){
                        layout.setBackgroundColor(Color.GREEN);
                    }
                    if(telephonyStatus.equals("NoCall")){
                        layout.setBackgroundColor(Color.RED);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_call_status, menu);
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