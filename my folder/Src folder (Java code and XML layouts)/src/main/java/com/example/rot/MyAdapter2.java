package com.example.rot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    String data1[], data2[];
    Context context;

    private  RecyclerViewClickInterface2 recyclerViewClickInterface2;

    public MyAdapter2(Context ct, String s1[], String s2[],RecyclerViewClickInterface2 recyclerViewClickInterface2){
        context = ct;
        data1 = s1;
        data2 = s2;
        this.recyclerViewClickInterface2 =  recyclerViewClickInterface2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView myText1, myText2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.Order);
            myText2 = itemView.findViewById(R.id.TOC);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface2.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
