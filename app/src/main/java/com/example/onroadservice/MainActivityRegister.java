package com.example.onroadservice;

import static android.opengl.ETC1.isValid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class MainActivityRegister extends AppCompatActivity {
    Button registerback, btn;
    EditText edname, edEmail, edPassword, edphone;
    Spinner edrole;

    DatabaseReference registerref;
    DatabaseReference serviceProvidersRef; // Separate reference for service providers

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        edPassword = findViewById(R.id.editpass);
        edname = findViewById(R.id.editname);
        edEmail = findViewById(R.id.editemail);
        edphone = findViewById(R.id.editphone);
        edrole = findViewById(R.id.spinnerRole);
        btn = findViewById(R.id.registersubmit);

        registerref = FirebaseDatabase.getInstance().getReference().child("Registered");
        serviceProvidersRef = FirebaseDatabase.getInstance().getReference().child("ServiceProviders");

        registerback = findViewById(R.id.registerback);
        registerback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    insertData();
                }
            }
        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    private boolean isFormValid() {
        String name = edname.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String phone = edphone.getText().toString().trim();
        String role = edrole.getSelectedItem().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(MainActivityRegister.this, "Please fill in all details.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8 || !isPasswordValid(password)) {
            Toast.makeText(MainActivityRegister.this, "Password must be at least 8 characters long and contain a capital letter and a special character.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPhoneNumber(phone)) {
            Toast.makeText(MainActivityRegister.this, "Phone number must be 10 digits.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(MainActivityRegister.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isPasswordValid(String password) {
        String pattern = "(?=.*[A-Z])(?=.*[@#$%^&+=]).*";
        return password.matches(pattern);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneNumberPattern = "^[0-9]{10}$";
        return phoneNumber.matches(phoneNumberPattern);
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void insertData() {
        String name = edname.getText().toString();
        String password = edPassword.getText().toString();
        String email = edEmail.getText().toString();
        String phone = edphone.getText().toString();
        String role = edrole.getSelectedItem().toString();

        Registered registered = new Registered(name, email, password, role, phone);

        if (role.equals("Service Provider")) {
            // Store data in the "ServiceProviders" table
            serviceProvidersRef.push().setValue(registered);
        } else {
            // Store data in the "Registered" table
            registerref.push().setValue(registered);
        }

        Toast.makeText(MainActivityRegister.this, "Data inserted", Toast.LENGTH_SHORT).show();
    }
}

