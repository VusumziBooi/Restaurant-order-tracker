package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DeleteOrder extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4;
    private String username;
    private TextView Username;
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_order);
        Username = findViewById(R.id.Username);
        textView1 = findViewById(R.id.Title);
        textView2 = findViewById(R.id.descriptiondelete);
        textView3 = findViewById(R.id.CostDelete);
        textView4 = findViewById(R.id.Quantity);
        Intent intent = getIntent();
        position = intent.getStringExtra("position");
        username = intent.getStringExtra("username");
        Username.setText(username);
        String[] a = intent.getStringExtra("name").split(" ");
        textView1.setText(a[2] + " : " + a[4] + " each ");
        textView2.setText(intent.getStringExtra("Description"));
        textView3.setText(intent.getStringExtra("cost"));
        textView4.setText("Quantity : " + a[0]);
    }

    public void doDelete(View v){
        Intent result = new Intent();
        result.putExtra("position",position);
        //setResult(RESULT_OK,result);
        setResult(RESULT_OK,result);
        finish();
    }
}