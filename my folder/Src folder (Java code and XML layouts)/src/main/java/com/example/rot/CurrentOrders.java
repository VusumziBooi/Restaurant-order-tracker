package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CurrentOrders extends AppCompatActivity implements RecyclerViewClickInterfaceCurOrders{

    private TextView textView,status;
    String Email,OrderItems,TimeRequested,StaffMember,Status,text,orderNumber;
    String s1[],s2[];
    ArrayList<String>A = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);
        textView = findViewById(R.id.Username);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("username"));
        Email = intent.getStringExtra("email");
        recyclerView = findViewById(R.id.recycler);
        doQuery();
    }
    public void doQuery(){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", Email)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/viewMyOrder.php")
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

                    CurrentOrders.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseData.equals("[]")){
                                String pop = "No Current orders";
                                status.setText(pop);
                                return;
                            }
                            DisplayStatus(responseData);
                        }
                    });
                }

            }
        });
    }

    public void DisplayStatus(String json){
        StringBuffer buffer = new StringBuffer();
        String staff;

        try {
            JSONArray ja = new JSONArray(json);
            for(int  i = 0 ; i < ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);

                //OrderNumber = jo.getString("ORDER_NUMBER");
                OrderItems = jo.getString("ORDER_ITEMS");
                TimeRequested = jo.getString("TIME_REQUESTED");
                StaffMember = jo.getString("STAFF_ID");
                Status = jo.getString("STATUS");
                orderNumber = jo.getString("ORDER_NUMBER");

                if(!Status.equals("COLLECTED")){
                    String Order = "Order package  : " + OrderItems + "\n";
                    String time_ini = "Time requested : " + TimeRequested + "\n";
                    staff = "Staff member   made is  : " + StaffMember + "\n";
                    String status_Order = "Order Status : " + Status + "\n";
                    String Order_Number = "Order number is " + orderNumber+"\n";
                    if(StaffMember.equals("null")){
                        staff = "Waiting for a stuff member  \n";
                    }
                    text =  Order_Number +" "+Order+" "+time_ini+" "+status_Order +" "+ staff;

                    //SpannableString ss = new SpannableString(text);

                    //StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
                    //ss.setSpan(boldspan,0,text.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                    //buffer.append(text + "\n\n");
                    A.add(text);
                }

            }

            // String order_Number = "Order number : " + OrderNumber + "\n";

            //status.setText(buffer.toString());
            String[] s = new String[A.size()];
            for(int j = 0; j < A.size(); j++){
                s[j] = A.get(j);
            }
            s1 = s;
            MyAdapterCurOrder myAdapterCurOrder = new MyAdapterCurOrder(this,s1,this);
            recyclerView.setAdapter(myAdapterCurOrder);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
          //  status.setText(ss);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        String[] r = s1[position].split(" ");

        //Toast.makeText(CurrentOrders.this,r[3],Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CurrentOrders.this,Access_type.class);
        intent.putExtra("Order_number",r[3]);
        startActivity(intent);
    }
}