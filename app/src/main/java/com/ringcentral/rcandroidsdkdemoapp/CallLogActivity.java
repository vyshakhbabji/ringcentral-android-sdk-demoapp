package com.ringcentral.rcandroidsdkdemoapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ringcentral.rc_android_sdk.rcsdk.http.APICallback;
import com.ringcentral.rc_android_sdk.rcsdk.http.APIResponse;
import com.ringcentral.rc_android_sdk.rcsdk.platform.AuthException;
import com.ringcentral.rc_android_sdk.rcsdk.subscription.Subscription;
import com.ringcentral.rc_android_sdk.rcsdk.utils.Helpers;
//import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import com.ringcentral.rc_android_sdk.rcsdk.core.SDK;

import com.ringcentral.rc_android_sdk.rcsdk.platform.Platform;


public class CallLogActivity extends ActionBarActivity {

    SDK sdk;
    Platform helpers = Singleton.getInstance().getPlatform();
    //Subscription subscription;
    TextView textView1;
    TableLayout tableLayout;
    TableRow.LayoutParams tableRowParams;
    TableRow.LayoutParams textParams;
    TableRow.LayoutParams textParams2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                HashMap<String, String> msgMap = (HashMap) msg.obj;
                TableRow tableRow = new TableRow(CallLogActivity.this);
                TextView textView1 = new TextView(CallLogActivity.this);
                textView1.setText(msgMap.get("to"));//+ "\n" + msgMap.get("location"));
                textView1.setLayoutParams(textParams);
                TextView textView2 = new TextView(CallLogActivity.this);
                textView2.setText(msgMap.get("time"));
                textView2.setLayoutParams(textParams2);
//                TextView textView3 = new TextView(CallLogActivity.this);
//                textView3.setText(msgMap.get("location"));
//                textView3.setLayoutParams(textParams);

                View v = new View(CallLogActivity.this);
                v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 3));
                v.setBackgroundColor(Color.BLACK);


                tableRow.addView(textView1);
                tableRow.addView(textView2);
                //tableRow.addView(textView3);

                tableLayout.addView(tableRow, tableRowParams);
                tableLayout.addView(v);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        textView1 = (TextView) findViewById(R.id.textView1);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        //tableRowParams.setMargins(2,2,2,2);
        tableRowParams.weight = 3;

        textParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        textParams.setMargins(0, 0, 20, 0);
        textParams.weight = 1;

        textParams2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        textParams2.setMargins(20, 0, 0, 0);
        textParams2.weight = 2;

        textView1.setText("To:");
          Log.v("access token ", String.valueOf(helpers.auth().accessTokenValid()));

        final Helpers helper = new Helpers(helpers);

        helper.callLog(
                new APICallback() {
                    @Override
                    public void onAPIFailure(Request request, IOException e) {
                        Log.v("Failed", request.method());
                    }

                    public void onAPIResponse(APIResponse response) throws IOException {
                        Log.v("onResponse CallActivity", String.valueOf(response.ok()));
                        Log.v("onResponse CallActivity", String.valueOf(response.ok()));
                        String body = response.body().string();
                        Log.v("call-log response",body);
                        String responseString = "";
                        if (response != null && response.ok()) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                JSONArray records = jsonObject.getJSONArray("records");
                                for (int i = 0; i < records.length(); i++) {
                                    JSONObject record = records.getJSONObject(i);
                                    JSONObject to = record.getJSONObject("to");
                                    JSONObject from = record.getJSONObject("from");
                                    map.put("to", to.getString("phoneNumber"));
                                    String time = record.getString("startTime").substring(11, 19);
                                    map.put("time", time);
//                                if(to.getString("location")!=null || !to.getString("location").equals(""))
//                                map.put("location", to.getString("location"));
//                                responseString += "To: " + to.getString("phoneNumber") + "                                       ";
//                                responseString += "Duration: " + record.getString("duration");
//                                responseString += "\n";
//                                JSONObject from = record.getJSONObject("from");
//                                responseString += "From: " + from.getString("phoneNumber") + " ";
//                                responseString += "\n\n";
                                    Message msg = handler.obtainMessage();
                                    msg.what = 1;
                                    msg.obj = map;
                                    handler.sendMessage(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        Intent intent = getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_call_log, menu);
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