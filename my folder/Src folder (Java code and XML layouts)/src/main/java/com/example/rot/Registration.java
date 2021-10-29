package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class Registration extends AppCompatActivity {

    //final static String username = "Registration_password.username";
  //  final static String email = "Registration_password.email";
    //final static String phone_num = "Registration_password.phone_num";

    private EditText Username;
    private EditText Email;
    private EditText Contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Username  =findViewById(R.id.rgtUsername);
        Email  =findViewById(R.id.rgtEmail);
        Contacts  =findViewById(R.id.rgtPhoneNum);
    }


    private boolean VerifyUsername(){
        String UsernameInput = Username.getText().toString().trim();

        if(UsernameInput.isEmpty()){
            Username.setError("Field cannot be empty");
            return(false);
        }
        Username.setError(null);
        return(true);
    }

    private boolean VerifyEmail(){
        String emailInput = Email.getText().toString().trim();

        if(emailInput.isEmpty()){
            Email.setError("Field cannot be empty");
            return(false);
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Email.setError("Email address is not valid");
            return(false);
        }
        else{
            Email.setError(null);
            return(true);
        }
    }

    private boolean VerifyContacts(){
        String ContactsInput = Contacts.getText().toString().trim();

        if(ContactsInput.isEmpty()){
            Contacts.setError("Field cannot be empty");
            return(false);
        }
        Contacts.setError(null);
        return(true);
    }

    public void doNext(View v){
        if(!VerifyUsername() || !VerifyEmail() || !VerifyContacts()){
            return;



        }


           Intent intent = new Intent(Registration.this,Registration_password.class);
            intent.putExtra("username",Username.getText().toString().trim());
            intent.putExtra("email",Email.getText().toString().trim());
            intent.putExtra("contacts",Contacts.getText().toString().trim());
            startActivity(intent);





    }






}