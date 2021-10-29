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
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StaffDoneScreen extends AppCompatActivity {

    private TextView textView;
    private String orderNumber,staffId,Username,Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_done_screen);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        orderNumber =  intent.getStringExtra("orderNumber");
        staffId = intent.getStringExtra("staffId");
        Username = intent.getStringExtra("Username");
        Email = intent.getStringExtra("email");

        //Toast.makeText(StaffDoneScreen.this,Username,Toast.LENGTH_LONG).show();


        String orderItems = "Items ordered are : " + intent.getStringExtra("orderItems");
        String timeRequested = "Time when order was placed " + intent.getStringExtra("timeRequested");
        String staffId = intent.getStringExtra("staffId");

        String text = "Order number is " +  orderNumber + "\n\n" + orderItems+"\n\n"+timeRequested+"\n\n";
        SpannableString ss = new SpannableString(text);

        StyleSpan boldspan = new StyleSpan(Typeface.BOLD);

        ss.setSpan(boldspan,0,text.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);

    }

    public void doDone(View v){


        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("orderNumber", orderNumber )
                .add("staffId", String.valueOf(staffId))
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/collect.php")
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

                    StaffDoneScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StaffDoneScreen.this,responseData,Toast.LENGTH_SHORT).show();

                             Handler handle = new Handler();

                             Runnable runnable = new Runnable() {
                                 @Override
                                 public void run() {
                                     Intent intent = new Intent(StaffDoneScreen.this,Staff_order_screen.class);
                                     intent.putExtra("ExtraEmail",Email);
                                     intent.putExtra("ExtraUsername",Username);
                                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                     startActivity(intent);
                                 }
                             };
                             handle.postDelayed(runnable,1000);



                        }
                    });
                }

            }
        });



    }
}

