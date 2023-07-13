package com.example.onroadservice;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    private final ArrayList<CustomerDetails> list;
    private Context context;
    private OnRejectClickListener rejectClickListener;

    public myAdapter(Context context, ArrayList<CustomerDetails> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnRejectClickListener(OnRejectClickListener listener) {
        rejectClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomerDetails customerDetails = list.get(position);
        if (customerDetails.isRejected()) {
            // Hide the item if it has been rejected
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            // Show the item
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            // ... set other fields
            holder.name_id.setText(customerDetails.getCname());
            holder.phone_id.setText(customerDetails.getCphone());
            holder.vehicletype_id.setText(customerDetails.getCvehicle());
            holder.fuel_id.setText(customerDetails.getCfuel());
            holder.req_id.setText(customerDetails.getCreq());
            holder.vehicleno_id.setText(customerDetails.getCvehicleno());
            holder.location_id.setText(customerDetails.getClocation());
            holder.time_id.setText(customerDetails.getCtime());

            holder.rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rejectClickListener != null) {
//                    int position = holder.getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
                        rejectClickListener.onRejectClick(position);
                    }
                }
                //}
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnRejectClickListener {
        void onRejectClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_id, phone_id, vehicletype_id, fuel_id, req_id, vehicleno_id, time_id, location_id;
        Button rejectButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id = itemView.findViewById(R.id.textname);
            phone_id = itemView.findViewById(R.id.textph);
            vehicletype_id = itemView.findViewById(R.id.veh);
            fuel_id = itemView.findViewById(R.id.fuel);
            req_id = itemView.findViewById(R.id.req);
            vehicleno_id = itemView.findViewById(R.id.veh_no);
            location_id = itemView.findViewById(R.id.loc);
            time_id = itemView.findViewById(R.id.time);
            rejectButton = itemView.findViewById(R.id.reject);
        }
    }
}
