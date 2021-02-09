package com.example.pickit.customer.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;

import java.util.List;

public class CustomerRecycler extends RecyclerView.Adapter<CustomerRecycler.CustomerViewHolder>{
    Context context;
    List<CustomerRecyclerHelperClass>list;
    public CustomerRecycler(Context context, List<CustomerRecyclerHelperClass>list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.customer_recycler,parent,false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTextView());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.Recyclertext);
        }

    }
}
