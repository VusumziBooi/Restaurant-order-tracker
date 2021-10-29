package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class RatingScreen extends AppCompatActivity {
    private TextView Customer ;
    private String timeCreated,staffMember,customer,order,email,orderNumber;
    private String S_Member,Date,Order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_screen);

        Customer = findViewById(R.id.Username);
        Intent intent = getIntent();
       customer =  intent.getStringExtra("Customer");
       timeCreated = intent.getStringExtra("TimeCreated");
       staffMember = intent.getStringExtra("Staff");
       order = intent.getStringExtra("Order");
       email = intent.getStringExtra("Email");
       orderNumber = intent.getStringExtra("orderNumber");

       Customer.setText(customer);

    }

    public void doThumbsUp(View view){
        int i = 1;
        Rate(i);

    }
    public void doThumbsDown(View view){
        int i = 0;
        Rate(i);
    }

    public void Rate(int i){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Rating", i+"")
                .add("order_number",orderNumber)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/rating.php")
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

                    RatingScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RatingScreen.this,History.class);
                            intent.putExtra("Order", order);
                            intent.putExtra("TimeCreated", timeCreated);
                            intent.putExtra("Staff", staffMember);
                            intent.putExtra("username", customer);
                            intent.putExtra("email",email);
                            intent.putExtra("orderNumber",orderNumber);
                            intent.putExtra("value", "1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                }

            }
        });

    }
}