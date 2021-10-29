package com.example.rot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterCurOrder extends RecyclerView.Adapter<MyAdapterCurOrder.MyViewHolder> {
    private RecyclerViewClickInterfaceCurOrders recyclerViewClickInterfaceCurOrders;


    String data1[];
    Context context;
    public MyAdapterCurOrder(Context ct,String s1[], RecyclerViewClickInterfaceCurOrders recyclerViewClickInterfaceCurOrders){
        context = ct;
        data1 = s1;
        this.recyclerViewClickInterfaceCurOrders = recyclerViewClickInterfaceCurOrders;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(data1[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterfaceCurOrders.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
