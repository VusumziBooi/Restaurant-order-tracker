package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Access_type extends AppCompatActivity {
    private String text;
    private Button button;


    private TextView data,status;
    int counter = 0;
    String OrderNumber,Customer,OrderItems,TimeRequested,StaffMember,Status,Email;
    String order_Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_type);
        data = findViewById(R.id.orderInformation);
        status = findViewById(R.id.order);
        button = findViewById(R.id.collect);

        Intent intent = getIntent();
        order_Number = intent.getStringExtra("Order_number");
        data.setText("Your order number is " + order_Number);

        refreshStatus(500);

    }

    public void doCollect(View v){
        Toast.makeText(Access_type.this,"Order is successfully collected \n\n",Toast.LENGTH_LONG).show();
        Intent nintent = new Intent(this,RatingScreen.class);
        nintent.putExtra("Staff",StaffMember);
        nintent.putExtra("Customer",Customer);
        nintent.putExtra("TimeCreated",TimeRequested);
        nintent.putExtra("Order",OrderItems);
        nintent.putExtra("Email",Email);
        nintent.putExtra("orderNumber", order_Number);
        startActivity(nintent);

    }

    public void DisplayStatus(String json){
        String staff;

        try {
            JSONArray ja = new JSONArray(json);
            for(int  i = 0 ; i < ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);

                //OrderNumber = jo.getString("ORDER_NUMBER");
                Customer = jo.getString("CUSTOMER");
                OrderItems = jo.getString("ORDER_ITEMS");
                TimeRequested = jo.getString("TIME_REQUESTED");
                StaffMember = jo.getString("STAFF_ID");
                Status = jo.getString("STATUS");
                Email = jo.getString("EMAIL_ADDRESS");
            }
           // String order_Number = "Order number : " + OrderNumber + "\n";
            String customer = "Order is for   :  " + Customer + "\n";
            String Order = "Order package  : " + OrderItems + "\n";
            String time_ini = "Time requested : " + TimeRequested + "\n";
            staff = "Staff member " + StaffMember + " is preparing your order"+"\n";
            String status_Order = "Order Status : " + Status + "\n";
            if(StaffMember.equals("null")){
                staff = "Waiting for a staff member  \n";
            }
            if(Status.equals("READY")){
                //staff = "Staff member " + StaffMember + " is done preparing your order"+"\n";
                staff = "Your order is ready for collection";
                button.setEnabled(true);
            }
            text = customer + Order+time_ini+status_Order + staff;

            SpannableString ss = new SpannableString(text);

            StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(boldspan,0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            status.setText(ss);
            if(!Status.equals("READY")){
                refreshStatus(10000);
            }
            

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refreshStatus(int milliseconds){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(Access_type.this,"Refreshed " + counter,Toast.LENGTH_SHORT).show();
                counter++;
               doStatus();


            }
        };
        handler.postDelayed(runnable,milliseconds);
    }


    public void doStatus(){
        String orderNumber = order_Number;

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("orderNumber", orderNumber)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/status.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if(response.isSuccessful()){
                    final String responseData = response.body().string();

                    Access_type.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DisplayStatus(responseData);

                        }
                    });
                }

            }
        });

    }
}