package com.example.rot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterCost extends RecyclerView.Adapter<MyAdapterCost.MyViewHolder> {
    String data1[], data2[], data3[];
    Context context;

    private  RecyclerViewClickInterfaceCost recyclerViewClickInterfaceCost;

    public MyAdapterCost(Context ct, String s1[], String s2[], String s3[], RecyclerViewClickInterfaceCost recyclerViewClickInterfaceCost){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        this.recyclerViewClickInterfaceCost = recyclerViewClickInterfaceCost;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_rowcost, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myText3.setText(data3[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1,myText2,myText3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.Name);
            myText2 = itemView.findViewById(R.id.Cost);
            myText3 = itemView.findViewById(R.id.Description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterfaceCost.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
