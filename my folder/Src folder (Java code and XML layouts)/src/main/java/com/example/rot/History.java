package com.example.rot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class History extends AppCompatActivity {
    DataBaseHelper History;
    private TextView username;
    String OrderItems,StaffName,OrderCreated,Username,Email,OrderNumber;
    private int value;
    String ExtraUsername = "EXTRA_USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        History = new DataBaseHelper(this);
        username = findViewById(R.id.Username);
        Intent intent = getIntent();
        value = Integer.parseInt(intent.getStringExtra("value"));

         if(value == 1){
             OrderItems = intent.getStringExtra("Order");
             OrderCreated = intent.getStringExtra("TimeCreated");
             StaffName = intent.getStringExtra("Staff");
             Username = intent.getStringExtra("username");
             username.setText(Username);
             Email = intent.getStringExtra("email");

             //tghnToast.makeText(History.this, OrderCreated + " " + StaffName + " " + OrderItems,Toast.LENGTH_SHORT).show();
             AddData();

         }
         else{
             Username = intent.getStringExtra("username");
             username.setText(Username);
         }


    }
    public void doMenu(View v){
        if(value == 0){
            Intent intent = new Intent(History.this,Customer_Screen.class);
            //intent.putExtra("ExtraUsername",Username);
            //intent.putExtra("email",Email);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            setResult(RESULT_OK,intent);
            finish();
        }
        else{
            Intent intent = new Intent(History.this,Customer_Screen.class);
            intent.putExtra("ExtraUsername",Username);
            intent.putExtra("ExtraEmail",Email);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        //startActivity(intent);

    }
    public void AddData(){
        boolean res = History.insertData(StaffName,OrderCreated,OrderItems,Username);
        if(res){
            Toast.makeText(History.this,"Recent Order added to History", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(History.this,"Recent Order not added to History", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewall(View view){
        Cursor res = History.getAllData(Username);
        if(res.getCount() == 0){
            showMessage("Error", "No data found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("ID : " + res.getString(0) + "\n");
            buffer.append("StaffName : " + res.getString(1) + "\n");
            buffer.append("OrderCreated : " + res.getString(2) + "\n");
            buffer.append("Order : " + res.getString(3) + "\n");
            buffer.append("Username : " + res.getString(4)+"\n");
            buffer.append("Status : Collected \n\n" );
        }
        showMessage("Data",buffer.toString());
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}