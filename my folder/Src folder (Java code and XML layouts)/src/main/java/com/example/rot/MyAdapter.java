package com.example.rot;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    String data1[], data2[];
    private  RecyclerViewClickInterface recyclerViewClickInterface;
    Context context;

    public MyAdapter(Context ct, String s1[], String s2[],RecyclerViewClickInterface recyclerViewClickInterface){
        context = ct;
        data1 = s1;
        data2 = s2;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Order.setText(data1[position]);
        holder.TOC.setText(data2[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Order, TOC;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Order = itemView.findViewById(R.id.Order);
            TOC = itemView.findViewById(R.id.TOC);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
