package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.Buffer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Staff_ID_request extends AppCompatActivity {
    private EditText Staff_id;
    private String orderNumber,OrderItems,timeRequested,Username,Email;
    private Integer staffId;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__i_d_request);
        Staff_id = findViewById(R.id.Staff_Id_req_edtxt);

        Intent intent = getIntent();
        orderNumber = intent.getStringExtra("orderNumber");
        OrderItems = intent.getStringExtra("OrderItems");
        timeRequested = intent.getStringExtra("timeRequested");
        Username = intent.getStringExtra("Username");
        Email = intent.getStringExtra("email");
        username = findViewById(R.id.username);
        username.setText(Username);


    }

    public void  doPrepare(View v){

        if(Staff_id.getText().toString().trim().isEmpty()){
            Staff_id.setError("ID field cannot be left empty");
            return;
        }

        staffId = Integer.parseInt(Staff_id.getText().toString().trim());



        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("orderNumber", orderNumber )
                .add("staffId", String.valueOf(staffId))
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/orderStaff.php")
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

                    Staff_ID_request.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Staff_ID_request.this,StaffDoneScreen.class);
                            //intent.putExtra("staff_id",Staff_id.getText().toString().trim());

                            intent.putExtra("staffId",staffId);
                            intent.putExtra("orderNumber",orderNumber);
                            intent.putExtra("orderItems",OrderItems);
                            intent.putExtra("timeRequested",timeRequested);
                            intent.putExtra("Username",Username);
                            intent.putExtra("email",Email);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                }

            }
        });








    }
}