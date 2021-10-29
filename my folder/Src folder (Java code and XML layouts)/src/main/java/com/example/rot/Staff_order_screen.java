package com.example.rot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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



//import static com.example.rot.Sign_in.ExtraUsername;

public class Staff_order_screen extends AppCompatActivity implements RecyclerViewClickInterface, NavigationView.OnNavigationItemSelectedListener {

    SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawer;
    NavigationView navigationView;

    RecyclerView recyclerView;

    String s1[], s2[],s3[];
    String Username,Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_order_screen);


        /*-----------------Hook-----------------*/
        drawer = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view_2);

        /*----------------------Navigation Drawer Menu----------------------*/

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,
                R.string.navigation_drawer_open2,R.string.navigation_drawer_close2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        // TextView textView = findViewById(R.id.username);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMenu(250);
                //recyclerView.notifyAll();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        Intent intent = getIntent();
        Username = intent.getStringExtra("ExtraUsername");
        Email = intent.getStringExtra("ExtraEmail");
        // textView.setText(Username);
        //  s1 = getResources().getStringArray(R.array.Orders);
        //s2 = getResources().getStringArray(R.array.Time_of_Creation);
        RequestMenu();





    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.staffID:
                Intent intent = new Intent(Staff_order_screen.this,staffRequest.class);
                intent.putExtra("email",Email);
                intent.putExtra("username",Username);
                startActivity(intent);
                break;
            case R.id.logCustomer:
                Intent intent2 = new Intent(Staff_order_screen.this,Customer_Screen.class);
                intent2.putExtra("ExtraUsername",Username);
                intent2.putExtra("ExtraEmail",Email);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                break;
            case R.id.Logout:
                logout(1);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent3 = new Intent(Staff_order_screen.this,MainActivity.class);
                Toast.makeText(Staff_order_screen.this,"Thank you for using our service",Toast.LENGTH_SHORT).show();
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent3);
            }
        };

        handler.postDelayed(runnable,milliseconds);
    }

    public void doToast(){

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", Username)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/Toast.php")
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

                    Staff_order_screen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            accessStatus(responseData);
                        }
                    });
                }

            }
        });
    }


    public void RequestMenu(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/staff.php")
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
                Staff_order_screen.this.runOnUiThread(new Runnable() {
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

    public void refreshMenu(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String text = "Refreshing";
                SpannableString ss = new SpannableString(text);

                StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
                ss.setSpan(boldspan,0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(Staff_order_screen.this,ss,Toast.LENGTH_SHORT).show();
                RequestMenu();
            }
        };

        handler.postDelayed(runnable,milliseconds);
    }

    public void accessStatus(String json){
        try {

            JSONArray ja = new JSONArray(json);
            String rating[] = new String[ja.length()];

            for(int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                rating[i] = jo.getString("TU_TD");
            }
            for(int j = 0 ; j < rating.length ; j++){
                Toast.makeText(Staff_order_screen.this,rating[j],Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  ProcessData(String json){
        try {

            JSONArray ja = new JSONArray(json);
            String orderNumber[] = new String[ja.length()];
            String timeRequested[] = new String[ja.length()];
            String OrderItems[] = new String[ja.length()];

            for(int i = 0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                orderNumber[i] = jo.getString("ORDER_NUMBER");
                timeRequested[i] = jo.getString("TIME_REQUESTED");
                OrderItems[i] = jo.getString("ORDER_ITEMS");
            }

            s1 = orderNumber;
            s2 = OrderItems;
            s3 = timeRequested;


            MyAdapter myAdapter = new MyAdapter(this,s1,s2,this);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));



            // ArrayList<String[]> Holder = new ArrayList<String[]>();
            // Holder.set(0,order);
            //  Holder.


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // refreshMenu(5000);


    }

    @Override
    public void onItemClick(int position) {
        String s = position + 1 + "";
        // Toast.makeText(this,"You clicked order"+s,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Staff_ID_request.class);
        intent.putExtra("orderNumber",s1[position]);
        intent.putExtra("OrderItems",s2[position]);
        intent.putExtra("timeRequested",s3[position]);
        intent.putExtra("Username",Username);
        intent.putExtra("email",Email);
        startActivity(intent);

    }

    @Override
    public void onLongItemClick(int position) {

    }


}