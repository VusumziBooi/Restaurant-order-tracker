package com.example.rot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActiveOrders extends AppCompatActivity implements RecyclerViewClickInterfaceAO {
    RecyclerView recyclerViewAO;
    String s[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_orders);
        s = getResources().getStringArray(R.array.Active_Orders);

        recyclerViewAO = findViewById(R.id.recyclerViewAO);

        MyAdapterAO myAdapterAO = new MyAdapterAO(this,s,this);
        recyclerViewAO.setAdapter(myAdapterAO);
        recyclerViewAO.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        
    }
}