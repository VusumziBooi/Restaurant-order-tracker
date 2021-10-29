package com.example.rot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import static com.example.rot.Sign_in.ExtraUsername;

public class Customer_Screen extends AppCompatActivity implements RecyclerViewClickInterface2, NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> Order = new ArrayList<String>();
    ArrayList<String> Order2 = new ArrayList<String>();
    RecyclerView recyclerView2;
    String s1[],s2[],s3[];
    String Username,Email;
    private TextView textView;
   // public static final String ExtraUsername = "EXTRA_USERNAME";
   /* ArrayList<String>t1 = new ArrayList<String>();
    ArrayList<String>t2 = new ArrayList<String>();
    ArrayList<String>t3 = new ArrayList<String>();
    */

    Button PlaceOrder,Cost;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__screen);
        PlaceOrder = findViewById(R.id.PlaceOrder);
        Cost = findViewById(R.id.Cost);


        Intent intent = getIntent();
         Username = intent.getStringExtra("ExtraUsername");
         Email = intent.getStringExtra("ExtraEmail");
        Order2.add(Username);

        recyclerView2 = findViewById(R.id.recyclerView2);
        RequestMenu();


        TextView username = (TextView)findViewById(R.id.Username);
        username.setText(Username);





        /*--------------Hooks--------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        /*--------------NavigationDrawer Menu--------------------*/

        Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_about).setVisible(false);
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.Profile).setVisible(false);
        menu.findItem(R.id.share).setVisible(false);
        menu.findItem(R.id.Rate).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
       //   Order = intent.getStringArrayListExtra("Order");
        //Toast.makeText(this,Order.get(0),Toast.LENGTH_LONG).show();

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.User);
        navUsername.setText(Username);
    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_history:
                Intent intent = new Intent(this,History.class);
                intent.putExtra("value","0");
                intent.putExtra("username",Username);
                intent.putExtra("email",Email);
                startActivityForResult(intent,3);
                break;
            case R.id.nav_about:
                Intent intent1 = new Intent(this,AboutUs.class);
                intent1.putExtra("username",Username);
                intent1.putExtra("email",Email);
                startActivity(intent1);
                break;
            case R.id.nav_order:
                Intent intent2 = new Intent(Customer_Screen.this,CurrentOrders.class);
                intent2.putExtra("username",Username);
                intent2.putExtra("email",Email);
                startActivityForResult(intent2,4);
                break;
            case R.id.Logout:
                logout(1);
        }
        return true;
    }

    public void logout(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent3 = new Intent(Customer_Screen.this,MainActivity.class);
                Toast.makeText(Customer_Screen.this,"Thank you for using our service",Toast.LENGTH_SHORT).show();
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent3);
            }
        };

        handler.postDelayed(runnable,milliseconds);
    }

    public void RequestMenu(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/menu.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                final String responseData = response.body().string();
                Customer_Screen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProcessData(responseData);
                        //Toast.makeText(Customer_Screen.this,responseData,Toast.LENGTH_LONG).show();
                        //makeArray(t1,t2,t3);
                    }
                });
            }
        });
    }



    public void ProcessData(String json){
        try {

            JSONArray ja = new JSONArray(json);
            String order[] = new String[ja.length()];
            String description[] = new String[ja.length()];
            String cost[] = new String[ja.length()];

            for(int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                order[i] = jo.getString("ORDER_NAME");
                description[i] = jo.getString("DESCRIPTION");
                cost[i] = jo.getString("COST");
            }

            s1 = order;
            s2 = cost;
            s3 = description;

            //Toast.makeText(Customer_Screen.this,s3[0],Toast.LENGTH_LONG).show();
            MyAdapter2 myAdapter2 = new MyAdapter2(this,s1,s2,this);
            recyclerView2.setAdapter(myAdapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));


           // ArrayList<String[]> Holder = new ArrayList<String[]>();
           // Holder.set(0,order);
          //  Holder.


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        TextView username = (TextView)findViewById(R.id.Username);
        String name = username.getText().toString();
        String s = position + 1 +"";
        //Toast.makeText(this,"You clicked meal"+s,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Opt_View.class);
        intent.putExtra("Order",s1[position]);
        intent.putExtra("Cost",s2[position]);
        intent.putExtra("description",s3[position]);
        intent.putExtra("Username",name);
        startActivityForResult(intent,1);
    }

    public void doPlaceOrder(View v){
        if(Order2.size() == 1){
            PlaceOrder.setEnabled(false);
            Cost.setEnabled(false);

            Toast.makeText(Customer_Screen.this,"No items added into the list",Toast.LENGTH_SHORT).show();

            return;
        }


        String items = "";
        String username = Order2.get(0);
        for(int i = 1 ; i < Order2.size();i++){
            items+= Order2.get(i);
            if(i != Order2.size()-1){
                items+= " , ";
            }
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("order", items)
                .add("email",Email)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/order.php")
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

                    Customer_Screen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(Customer_Screen.this,responseData,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Customer_Screen.this,Access_type.class);
                            intent.putExtra("Order_number",responseData);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                }

            }
        });


    }

    public void doCost(View v){
        if(Order2.size() == 1){
            PlaceOrder.setEnabled(false);
            Cost.setEnabled(false);
            return;

        }
        Intent intent = new Intent(Customer_Screen.this,Cost.class);
        intent.putExtra("order",Order2);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String x = "";
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Order = data.getStringArrayListExtra("order");
                Order2.add(Order.get(0));
                if(Order2.size() > 1){
                    PlaceOrder.setEnabled(true);
                    Cost.setEnabled(true);
                }
                if(Order2.size() == 1){
                    PlaceOrder.setEnabled(false);
                    Cost.setEnabled(false);
                }
                for(int i = 0 ; i < Order2.size();i++){
                    x+= Order2.get(i)+ "\n\n";
                }
                    //Toast.makeText(Customer_Screen.this,x,Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 2){
            Order2 = data.getStringArrayListExtra("order");

        }
        if(requestCode == 3){

        }
        if(requestCode == 4){

        }
    }


}