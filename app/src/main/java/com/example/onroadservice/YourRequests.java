package com.example.onroadservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourRequests extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference cdetails;
    myNewAdapter adapter;
    ArrayList<CustomerDetails> list; // Initialize the ArrayList
    private String username; // Variable to store the passed username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_requests);

        recyclerView = findViewById(R.id.recyclerView);
        cdetails = FirebaseDatabase.getInstance().getReference().child("CustomerDetails");

        list = new ArrayList<>(); // Initialize the ArrayList

        recyclerView.setLayoutManager(new LinearLayoutManager(YourRequests.this));
        adapter = new myNewAdapter(YourRequests.this, list);
        recyclerView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("USERNAME");
        }

        cdetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    CustomerDetails customerdetails = datasnapshot.getValue(CustomerDetails.class);
                    if (customerdetails != null && customerdetails.getCname().equals(username)) {
                        list.add(customerdetails);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error case if the database query is cancelled
            }
   });
}
}