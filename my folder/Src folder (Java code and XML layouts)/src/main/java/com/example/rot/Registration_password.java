package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registration_password extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{5,}" +               //at least 5 characters
                    "$");

    private String username,email,contacts,password, Access_Type;
    private EditText Password;
    private EditText ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_password);
        Intent intent = getIntent();
         username = intent.getStringExtra("username");
         email = intent.getStringExtra("email");
         contacts = intent.getStringExtra("contacts");

        Password = findViewById(R.id.rgtPassword);
        ConfirmPassword = findViewById(R.id.rgtConfirmPassword);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Access_Specifier,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //Access_Type = spinner.getSelectedItem().toString();
        //Toast.makeText(Registration_password.this,Access_Type,Toast.LENGTH_LONG).show();



    }

    private boolean VerifyPassword(){
        String PasswordInput = Password.getText().toString().trim();

        if(PasswordInput.isEmpty()){
            Password.setError("Field cannot be empty");
            return(false);
        }
        else if(!PASSWORD_PATTERN.matcher(PasswordInput).matches()){
            Password.setError("Password too weak \n" + "Your Password should have : \n" + "1.At least 1 digit \n" + "2.At least 1 lowercase letter \n" + "3.At least 1 uppercase letter \n" + "4.At least 1 special character([@#$%^&+=]) \n"+"5.Must not have whitespaces \n"+ "6.You must have at least 5 characters \n");
            return(false);
        }
        else{
            Password.setError(null);
            return(true);
        }
    }

    private boolean VerifyConfirmPassword(){
        String PasswordConfirmInput = ConfirmPassword.getText().toString().trim();

        if(PasswordConfirmInput.isEmpty()){
            ConfirmPassword.setError("Field cannot be empty");
            return(false);
        }
        ConfirmPassword.setError(null);
        return(true);
    }

    private Boolean ConfirmPasswordField(){
        String password = Password.getText().toString().trim();
        String confirm = ConfirmPassword.getText().toString().trim();
        if(!password.equals(confirm)){
            //all.setText();
            Toast.makeText(this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
            return(false);
        }
        return(true);
    }




    public void Register(View v){
        if(!VerifyPassword() || !VerifyConfirmPassword() || !ConfirmPasswordField()){
            return;
        }
        password = Password.getText().toString().trim();
       // Toast.makeText(this,"Ncaa",Toast.LENGTH_SHORT).show();
        doRegister(username,contacts,email,password,Access_Type);
    }

    public void doRegister(final String username, String contacts, final String email, String password, String access_type){

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("contacts", contacts)
                .add("email", email)
                .add("password", password)
                .add("access_specifier", access_type.toUpperCase())
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/user_table.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                     final String responseData = response.body().string();

                    Registration_password.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String clean = responseData.trim();
                            if(clean.compareToIgnoreCase("Records inserted successfully.") == 0){
                                Toast.makeText(Registration_password.this,Access_Type,Toast.LENGTH_LONG).show();
                                if(Access_Type.compareToIgnoreCase("Customer") == 0){
                                    Intent intent = new Intent(Registration_password.this,Customer_Screen.class);
                                    intent.putExtra("ExtraUsername",username);
                                    intent.putExtra("ExtraEmail",email);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(Registration_password.this,Staff_order_screen.class);
                                    intent.putExtra("ExtraUsername",username);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                            else{
                                Toast.makeText(Registration_password.this,"Something went wrong ,email address already taken,please try another email",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Access_Type = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}