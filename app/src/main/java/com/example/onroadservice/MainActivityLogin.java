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

public class MainActivityLogin extends AppCompatActivity {

    Button customerloginback;
    EditText edemail, edPassword;
    Button btn;

    DatabaseReference registeredRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        registeredRef = FirebaseDatabase.getInstance().getReference().child("Registered");

        customerloginback = findViewById(R.id.customerloginback);
        customerloginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });
        edPassword = findViewById(R.id.loginpass);
        edemail = findViewById(R.id.loginmail);
        btn = findViewById(R.id.loginsubmit);
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
        Intent switchActivityIntent = new Intent(MainActivityLogin.this, MainActivity.class);
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
                        if (registeredUser.getRole().equals("Customer")) {
                            isCustomerRole = true;
                            userName=registeredUser.getName();
                        }
                        break;
                    }
                }

                if (isValidLogin) {
                    if (isCustomerRole) {
                        // Login successful for customer role, navigate to the next activity
                        Toast.makeText(MainActivityLogin.this, "Customer login successful", Toast.LENGTH_SHORT).show();
                        switchActivity2(userName);
                    } else {
                        // Login successful but not for customer role
                        Toast.makeText(MainActivityLogin.this, "Invalid role. Please login as a customer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Login failed, show error message
                    Toast.makeText(MainActivityLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error case if the database query is cancelled
                Toast.makeText(MainActivityLogin.this, "Error occurred while logging in", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchActivity2(String userName) {
        Intent switchActivityIntent = new Intent(MainActivityLogin.this, CustomerIntermediate.class);
        switchActivityIntent.putExtra("USERNAME",userName);
        startActivity(switchActivityIntent);
        finish();
    }
}
