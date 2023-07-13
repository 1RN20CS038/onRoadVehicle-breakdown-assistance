package com.example.onroadservice;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivityServicePLogin extends AppCompatActivity {

    Button serviceloginback;
    EditText edemail,edPassword;
    Button btn;

    DatabaseReference registeredRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_service_plogin);

        registeredRef = FirebaseDatabase.getInstance().getReference().child("ServiceProviders");

        serviceloginback = findViewById(R.id.serviceloginback);
        serviceloginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });

        edPassword = findViewById(R.id.sploginpass);
        edemail = findViewById(R.id.loginmailsp);
        btn = findViewById(R.id.sploginsubmit);
        edPassword.setTransformationMethod(new PasswordTransformationMethod());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edemail.getText().toString();
                String password = edPassword.getText().toString();
                validateLogin(email, password);
            }
        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(MainActivityServicePLogin.this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    private void validateLogin(String email, String password) {
        Query query = registeredRef.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isValidLogin = false;
                boolean isCustomerRole = false;
                String userName=null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Registered registeredUser = snapshot.getValue(Registered.class);
                    if (registeredUser != null && registeredUser.getPassword().equals(password)) {
                        isValidLogin = true;
                        if (registeredUser.getRole().equals("Service Provider")) {
                            isCustomerRole = true;
                            userName=registeredUser.getName();
                        }
                        break;
                    }
                }

                if (isValidLogin) {
                    if (isCustomerRole) {
                        // Login successful for customer role, navigate to the next activity
                        Toast.makeText(MainActivityServicePLogin.this, "Service provider login successful", Toast.LENGTH_SHORT).show();
                        switchActivity2(userName);
                    } else {
                        // Login successful but not for customer role
                        Toast.makeText(MainActivityServicePLogin.this, "Invalid role. Please login as a service provider", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Login failed, show error message
                    Toast.makeText(MainActivityServicePLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error case if the database query is cancelled
                Toast.makeText(MainActivityServicePLogin.this, "Error occurred while logging in", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchActivity2(String userName) {
        Intent switchActivityIntent = new Intent(MainActivityServicePLogin.this, serverNotification.class);
        switchActivityIntent.putExtra("USERNAME",userName);
        startActivity(switchActivityIntent);
        finish();
    }
}
