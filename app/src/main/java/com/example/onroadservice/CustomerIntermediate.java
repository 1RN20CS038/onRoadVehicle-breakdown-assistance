package com.example.onroadservice;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerIntermediate extends AppCompatActivity {

    Button requestservice, yourrequests;
    Button logout;
    TextView printname;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_intermediate);

        requestservice = findViewById(R.id.requestservice);
        yourrequests = findViewById(R.id.yourrequests);
        logout = findViewById(R.id.logout);
        printname = findViewById(R.id.printname);

        // Get the username passed from MainActivityLogin
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("USERNAME");
            if (username != null && !username.isEmpty()) {
                // Set the username in the TextView
                printname.setText(username);
            }
        }

        requestservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(CustomerIntermediate.this, FisrtPageLogin.class);
                startActivity(switchActivityIntent);
            }
        });

        yourrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(CustomerIntermediate.this, YourRequests.class);
                switchActivityIntent.putExtra("USERNAME", username);
                startActivity(switchActivityIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(CustomerIntermediate.this, MainActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}



