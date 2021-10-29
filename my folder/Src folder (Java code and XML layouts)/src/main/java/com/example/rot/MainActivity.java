package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Sign_In(View v){
        Intent intent = new Intent(this,Sign_in.class);
        startActivity(intent);
    }
    public void Register(View v){
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
    }
}