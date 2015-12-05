package com.ringcentral.rcandroidsdkdemoapp;


public class SMSActivity {//extends ActionBarActivity implements View.OnClickListener {
//
//    SDK sdk;
//    Helpers helpers;
//    EditText fromText, toText, smsText;
//    Button button1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sms);
//        Intent intent = getIntent();
//        sdk = (SDK) intent.getSerializableExtra("MyRcsdk");
//        helpers = sdk.getHelpers();
//        button1 = (Button) findViewById(R.id.button1);
//        button1.setOnClickListener(this);
//        fromText = (EditText) findViewById(R.id.fromMessage);
//        toText = (EditText) findViewById(R.id.toMessage);
//        smsText = (EditText) findViewById(R.id.SMSMessage);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//
//            case R.id.button1:
//                String to = toText.getText().toString();
//                String from = fromText.getText().toString();
//                String message = smsText.getText().toString().replace("\n", "");
//                helpers.sendSMS(to, from, message,
//                        new Callback() {
//                            @Override
//                            public void onFailure(Request request, IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(Response response) throws IOException {
//                                APIResponse transaction = new APIResponse(response.request(), response);
//                                String responseString = transaction.toString();
//                                // If HTTP response is not successful, throw exception
//                                if (!response.isSuccessful()) {
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(responseString);
//                                        String errorCode = jsonObject.getString("errorCode");
//                                        String message = jsonObject.getString("message");
//                                        throw new IOException("Error code: " + transaction.response().code() + ". Error: " + errorCode + ": " + message);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
//                break;
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sm, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
