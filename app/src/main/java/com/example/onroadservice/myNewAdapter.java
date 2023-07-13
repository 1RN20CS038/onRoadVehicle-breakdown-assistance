package com.example.onroadservice;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myNewAdapter extends RecyclerView.Adapter<myNewAdapter.MyViewHolder> {
    private final ArrayList<CustomerDetails> list;
    private Context context;

    public myNewAdapter(Context context, ArrayList<CustomerDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_users_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomerDetails customerDetails = list.get(position);
        holder.name_id.setText(customerDetails.getCname());
        holder.phone_id.setText(customerDetails.getCphone());
        holder.vehicletype_id.setText(customerDetails.getCvehicle());
        holder.fuel_id.setText(customerDetails.getCfuel());
        holder.req_id.setText(customerDetails.getCreq());
        holder.vehicleno_id.setText(customerDetails.getCvehicleno());
        holder.location_id.setText(customerDetails.getClocation());
        holder.time_id.setText(customerDetails.getCtime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_id, phone_id, vehicletype_id, fuel_id, req_id, vehicleno_id, time_id, location_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            name_id = itemView.findViewById(R.id.textname);
            phone_id = itemView.findViewById(R.id.textphone);
            vehicletype_id = itemView.findViewById(R.id.textvehicle);
            fuel_id = itemView.findViewById(R.id.textfuel);
            req_id = itemView.findViewById(R.id.textreq);
            vehicleno_id = itemView.findViewById(R.id.textvehiclenum);
            location_id = itemView.findViewById(R.id.textlocation);
            time_id = itemView.findViewById(R.id.texttime);

     }
    }
}
