package com.example.rot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Cost extends AppCompatActivity implements RecyclerViewClickInterfaceCost{
    String Name[], Description[], Price[];
    ArrayList<String>currOrder = new ArrayList<>();
    RecyclerView recyclerView_cost;
    String Username;
    private TextView username,Overall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        username = findViewById(R.id.Username);
        Overall = findViewById(R.id.CostPrice);

        Intent intent = getIntent();
        currOrder =  intent.getStringArrayListExtra("order");
        Username = currOrder.get(0);
        username.setText(Username);


        String[] nje1 = new String[currOrder.size()-1];
        String[] nje2 = new String[currOrder.size()-1];
        String[] nje3 = new String[currOrder.size()-1];
        double sum = 0.00;

        for(int i = 1 ; i < currOrder.size();i++){
            String[] curr = currOrder.get(i).split(" ");

            nje1[i-1] = currOrder.get(i);
            String meal = curr[2];
            nje2[i-1] = "This is " + meal;
            int quantity = Integer.parseInt(curr[0]);
            double cost = Double.parseDouble(curr[4]);
            //Toast.makeText(Cost.this,Cost+"",Toast.LENGTH_SHORT).show();
            //Toast.makeText(Cost.this,curr[4],Toast.LENGTH_SHORT).show();
            nje3[i-1]  = "Total cost : "+ (quantity*cost)+"0";
            sum = sum + (quantity*cost);

            //Toast.makeText(Cost.this,"",Toast.LENGTH_SHORT).show();
        }
        Name = nje1;
        Description = nje2;
        Price = nje3;
        Overall.setText(sum+"");

        /*for(int j = 0 ; j <  Name.length;j++){
            String NameAt = Name[j];
            String DescAt = Description[j];
            String PricAt = Price[j];
            Toast.makeText(Cost.this,NameAt+ " " + DescAt + " " + PricAt+ "\n\n",Toast.LENGTH_SHORT).show();
        }*/
       // Toast.makeText(Cost.this,"",Toast.LENGTH_SHORT).show();
        recyclerView_cost = findViewById(R.id.recyclerView_cost);

      /*  Name = getResources().getStringArray(R.array.Menu_Item);
        Description = getResources().getStringArray(R.array.Description);
        Price = getResources().getStringArray(R.array.Cost);*/

        MyAdapterCost myAdapterCost = new MyAdapterCost(this,Name,Description,Price,this);
        recyclerView_cost.setAdapter(myAdapterCost);
        recyclerView_cost.setLayoutManager(new LinearLayoutManager(this));
    }

    public void doMenu(View v){
        Intent result = new Intent();
        result.putExtra("order",currOrder);
        setResult(RESULT_OK,result);
        finish();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,DeleteOrder.class);
        intent.putExtra("username",Username);
        intent.putExtra("name",Name[position]);
        intent.putExtra("cost",Price[position]);
        intent.putExtra("Description",Description[position]);
        intent.putExtra("position",position+"");
        startActivityForResult(intent,2);
    }

    @Override
    public void onLongItemClick(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String x = "";
        double sum = 0.00;
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                int delete = Integer.parseInt(data.getStringExtra("position"));
                currOrder.remove(delete+1);


                String[] nje1 = new String[currOrder.size()-1];
                String[] nje2 = new String[currOrder.size()-1];
                String[] nje3 = new String[currOrder.size()-1];

                for(int i = 1 ; i < currOrder.size();i++){
                    String[] curr = currOrder.get(i).split(" ");

                    nje1[i-1] = currOrder.get(i);
                    String meal = curr[2];
                    nje2[i-1] = "This is " + meal;
                    int quantity = Integer.parseInt(curr[0]);
                    double cost = Double.parseDouble(curr[4]);
                    //Toast.makeText(Cost.this,Cost+"",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Cost.this,curr[4],Toast.LENGTH_SHORT).show();
                    nje3[i-1]  = "Total cost : "+ (quantity*cost)+"0";
                    //Toast.makeText(Cost.this,"",Toast.LENGTH_SHORT).show();
                    sum = sum + (quantity*cost);
                }
                Name = nje1;
                Description = nje2;
                Price = nje3;
                Overall.setText(sum+"");


                MyAdapterCost myAdapterCost = new MyAdapterCost(this,Name,Description,Price,this);
                recyclerView_cost.setAdapter(myAdapterCost);
                recyclerView_cost.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }
}