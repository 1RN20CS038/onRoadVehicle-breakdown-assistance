package com.example.onroadservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




public class serverNotification extends AppCompatActivity implements myAdapter.OnRejectClickListener {

    RecyclerView recyclerView;
    DatabaseReference serviceProvidersRef;
    myAdapter adapter;
    Button accept, reject;
    TextView printProvider;
    String username;
    ArrayList<CustomerDetails> list;
    ServiceProvider currentServiceProvider; // Store the current service provider

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_notification);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);
        recyclerView = findViewById(R.id.recyclerView);
        printProvider = findViewById(R.id.providername);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("USERNAME");
            if (username != null && !username.isEmpty()) {
                printProvider.setText(username);
            }
        }

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new myAdapter(this, list);
        adapter.setOnRejectClickListener(this);
        recyclerView.setAdapter(adapter);

        serviceProvidersRef = FirebaseDatabase.getInstance().getReference().child("ServiceProviders");

        serviceProvidersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String targetEmail = "service@gmail.com"; // Replace with your target email

                for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                    ServiceProvider serviceProvider = providerSnapshot.getValue(ServiceProvider.class);

                    if (serviceProvider != null && serviceProvider.getEmail().equals(targetEmail)) {
                        currentServiceProvider = serviceProvider; // Store the current service provider
                        DataSnapshot customersSnapshot = providerSnapshot.child("Customers");
                        for (DataSnapshot customerSnapshot : customersSnapshot.getChildren()) {
                            CustomerDetails customerDetails = customerSnapshot.getValue(CustomerDetails.class);

                            if (customerDetails != null && !customerDetails.isRejected()) {
                                list.add(customerDetails);
                            }
                        }
                        break; // Break the loop if the target provider is found
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

    @Override
    public void onRejectClick(int position) {
        if (position >= 0 && position < list.size()) {
            CustomerDetails customer = list.get(position);
            String customerName = customer.getCname();
            removeItemFromList(customerName);
        }
    }



    private void removeItemFromList(final String customerName) {
        serviceProvidersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                    ServiceProvider serviceProvider = providerSnapshot.getValue(ServiceProvider.class);
                    if (serviceProvider != null) {
                        String providerId = serviceProvider.getProviderId();
                        if (providerId != null && providerId.equals(username)) {
                            String targetEmail = serviceProvider.getEmail();

                            DataSnapshot customersSnapshot = providerSnapshot.child("Customers");
                            for (DataSnapshot customerSnapshot : customersSnapshot.getChildren()) {
                                CustomerDetails customerDetails = customerSnapshot.getValue(CustomerDetails.class);
                                if (customerDetails != null && customerDetails.getCname().equals(customerName)) {
                                    customerSnapshot.getRef().removeValue(); // Remove the customer entry from the specific service provider's table
                                    break; // Stop searching once the customer is found and deleted
                                }
                            }
                            break; // Stop searching once the service provider is found
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error case if the database query is cancelled
            }
        });
    }




}
