package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class Sign_in extends AppCompatActivity {

    //public static final String ExtraUsername = "EXTRA_USERNAME";
    String email;
    String password;

    private EditText Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Email = findViewById(R.id.lgnEmail);
        Password = findViewById(R.id.lgnPassword);
    }

    public void doSign_in(View v){
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if(!VerifyEmail() || !VerifyPassword()){
            return;
        }
        SignIn(email,password);
    }

    public  void SignIn(String Email,String Password){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", Email)
                .add("password", Password)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/login.php")
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

                    Sign_in.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseData.equals("[]")){
                                String pop = "PLease make sure you are registered and have provided valid login details";
                                Toast.makeText(Sign_in.this,pop,Toast.LENGTH_LONG).show();
                                return;
                            }
                            AccessProcess(responseData);
                        }
                    });
                }

            }
        });
    }

    public void AccessProcess(String json){
        try {
            JSONArray ja = new JSONArray(json);
            JSONObject jo = ja.getJSONObject(0);
            String access = jo.getString("ACCESS_SPECIFIER");
            String name = jo.getString("USERNAME");
            String email = jo.getString("EMAIL_ADDRESS");
            if(access.equals("USER")){
                Intent intent = new Intent(Sign_in.this,Customer_Screen.class);
                intent.putExtra("ExtraUsername",name);
                intent.putExtra("ExtraEmail", email);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else if(access.equals("STAFF")){
                Intent intent = new Intent(Sign_in.this,Staff_order_screen.class);
                intent.putExtra("ExtraUsername",name);
                intent.putExtra("ExtraEmail", email);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else{
                String pop = "Please make sure you are registered and have provided valid login details";
                Toast.makeText(Sign_in.this,pop,Toast.LENGTH_LONG).show();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean VerifyEmail() {
        String emailInput = Email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            Email.setError("Field cannot be empty");
            return (false);
        }
        return true;
    }
    private boolean VerifyPassword() {
        String PasswordInput = Password.getText().toString().trim();

        if (PasswordInput.isEmpty()) {
            Password.setError("Field cannot be empty");
            return (false);
        }
        return true;
    }

}