package com.example.rot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyAdapterAO extends RecyclerView.Adapter<MyAdapterAO.MyViewHolder> {

    Context context;
    String data1[];
    private  RecyclerViewClickInterfaceAO recyclerViewClickInterfaceAO;

    public  MyAdapterAO(Context ct,String s[],RecyclerViewClickInterfaceAO recyclerViewClickInterfaceAO){
        context = ct;
        data1 = s;
        this.recyclerViewClickInterfaceAO = recyclerViewClickInterfaceAO;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_row2,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText.setText(data1[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText = itemView.findViewById(R.id.AO);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterfaceAO.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
