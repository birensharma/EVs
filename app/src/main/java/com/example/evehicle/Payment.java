package com.example.evehicle;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Payment extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    private final String mid="dyIkKt14536106260780";
    private  String orderId;
    private  String custid;
    private Button pay;
    private EditText amount;
    private String money="0";
    private RatingBar rate;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        db= FirebaseDatabase.getInstance().getReference();
        pay=findViewById(R.id.paynow);
        amount=findViewById(R.id.money);

        if (ContextCompat.checkSelfPermission(Payment.this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Payment.this,
                    new String[]{Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS}, 101);
        }
        //Toast.makeText(this, "OrderId:"+orderId+"\nCId:"+custid, Toast.LENGTH_SHORT).show();
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().equals(""))
                    Toast.makeText(Payment.this, "Please Enter the amount", Toast.LENGTH_SHORT).show();
                else
                    startPayment();
            }
        });
    }
    private void startPayment(){
        orderId="Ord"+new Random().nextInt(100000000);
        custid="Cus"+new Random().nextInt(100000000);
        money=amount.getText().toString();
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(Payment.this);
        //private String orderId , mid, custid, amt;
        //String url ="https://www.blueappsoftware.com/payment/payment_paytm/generateChecksum.php";
        String url ="https://tourpackagebhutan.com/generateChecksum.php";
        String varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH ="";
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(Payment.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" + orderId+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+money+"&WEBSITE=WEBSTAGING"+
                            "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";
            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject+"");
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {
                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            PaytmPGService Service = PaytmPGService.getStagingService();
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();
            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID",orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", money);
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(Payment.this, true, true,
                    Payment.this  );
        }
    }
    @Override
    public void onTransactionResponse(Bundle bundle) {
        Toast.makeText(this, "Transaction Success", Toast.LENGTH_SHORT).show();
        final View view=this.getLayoutInflater().inflate(R.layout.activity_feedback,null);
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Please Rate us")
                .setMessage("It helps us to improve our service")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       rate= view.findViewById(R.id.rate);
                       float n= rate.getRating();
                        db.child("Feedback/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("rating").setValue(n);
                        db.child("Feedback/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        Toast.makeText(Payment.this, "Thank you for rating us",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Payment.this, Dashboard.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Payment.this, Dashboard.class));
                    }
                });
        builder.create().show();

    }
    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network issue", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, "Client Authentication Failed "+s, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void someUIErrorOccurred(String s) {
        Log.i("checksum ", " ui fail response  "+ s );
    }
    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.i("checksum ", " error loading page response true "+ s + "  s1 " + s1);
    }
    @Override
    public void onBackPressedCancelTransaction() {
        Log.i("checksum ", " cancel call back response  " );
    }
    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.i("checksum ", "  transaction cancel " );
        Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
    }

}
