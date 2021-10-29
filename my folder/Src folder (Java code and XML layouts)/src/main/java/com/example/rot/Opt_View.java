package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//import static com.example.rot.Sign_in.ExtraUsername;

public class Opt_View extends AppCompatActivity {
ArrayList<String>order = new ArrayList<String>();
    TextView Title,Amount,Description,Quantity;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opt__view);

        Title = (TextView)findViewById(R.id.title);
        Amount = (TextView)findViewById(R.id.Amount);
        Description = (TextView)findViewById(R.id.description);
        Quantity = (TextView)findViewById(R.id.quantity_num);

        Intent intent = getIntent();
        Title.setText(intent.getStringExtra("Order"));
        Amount.setText(intent.getStringExtra("Cost"));
        username = intent.getStringExtra("Username");
        Description.setText(intent.getStringExtra("description"));
        TextView t = (TextView)findViewById(R.id.Username_tag4);
        t.setText(username);

    }

    public void doIncrement(View v){
        TextView t = (TextView)findViewById(R.id.quantity_num);
        int i = Integer.parseInt(t.getText().toString())+1;
        t.setText(i+"");
    }

    public void doDecrement(View v){
        TextView t = (TextView)findViewById(R.id.quantity_num);
        int i = Integer.parseInt(t.getText().toString());
        if(i != 1){
            t.setText((i-1)+"");
        }
    }

    public void AddOrder(View v){
       String quantity = Quantity.getText().toString();
       String amount = Amount.getText().toString();
       String curr_order = Title.getText().toString();
       String Order = quantity + " x " + curr_order + " : " + amount;
       order.add(Order);

       Intent result = new Intent();
       result.putExtra("order",order);
       setResult(RESULT_OK,result);
       finish();



    }
}