
//package com.example.onroadservice;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class FisrtPageLogin extends AppCompatActivity {
//    Button logout, back, submit;
//    EditText v_num, time, c_name, number;
//    Spinner veh_type, f_type, require;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//    String vehicle_type, fuel_type, req;
//    private Button btnGetLocation;
//    private TextView tvLocation;
//    DatabaseReference customerref;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationCallback locationCallback;
//    private LocationRequest locationRequest;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fisrt_page_login);
//        back = findViewById(R.id.clback);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switchActivities1();
//            }
//        });
//        logout = findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switchActivities2();
//            }
//        });
//        customerref = FirebaseDatabase.getInstance().getReference().child("CustomerDetails");
//
//        v_num = findViewById(R.id.v_num);
//        time = findViewById(R.id.time);
//        veh_type = findViewById(R.id.veh_typ);
//        f_type = findViewById(R.id.fuel_typ);
//        require = findViewById(R.id.req_typ);
//        c_name = findViewById(R.id.c_name);
//        number = findViewById(R.id.number);
//
//        btnGetLocation = findViewById(R.id.btnGetLocation);
//        tvLocation = findViewById(R.id.tvLocation);
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000); // Set the desired interval for location updates
//        locationRequest.setFastestInterval(2000); // Set the fastest interval for location updates
//
//        btnGetLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(FisrtPageLogin.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//                } else {
//                    ActivityCompat.requestPermissions(FisrtPageLogin.this,
//                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                            LOCATION_PERMISSION_REQUEST_CODE);
//                }
//            }
//        });
//
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult != null) {
//                    Location location = locationResult.getLastLocation();
//                    if (location != null) {
//                        String latitude = String.valueOf(location.getLatitude());
//                        String longitude = String.valueOf(location.getLongitude());
//                        String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
//                        tvLocation.setText(locationText);
//                    }
//                }
//            }
//        };
//
//        veh_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                vehicle_type = parent.getItemAtPosition(position).toString();
//                // Use the selectedValue string as needed
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Handle the case where no item is selected
//            }
//        });
//        f_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                fuel_type = parent.getItemAtPosition(position).toString();
//                // Use the selectedValue string as needed
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Handle the case where no item is selected
//            }
//        });
//        require.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                req = parent.getItemAtPosition(position).toString();
//                // Use the selectedValue string as needed
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Handle the case where no item is selected
//            }
//        });
//
//        submit = findViewById(R.id.data_submit);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertdata();
//            }
//        });
//    }
//
//    private void insertdata() {
//        String cname = c_name.getText().toString();
//        String phone_no = number.getText().toString();
//        String vehicle_type = veh_type.getSelectedItem().toString();
//        String fuel_type = f_type.getSelectedItem().toString();
//        String req = require.getSelectedItem().toString();
//        String vehicle_num = v_num.getText().toString();
//        String timing = time.getText().toString();// Retrieve the selected value from the spinner
//        String location = tvLocation.getText().toString();
//        //String cname,String cphone, String cvehicle, String cfuel, String creq, String cvehicleno, String ctime, String clocation
//        CustomerDetails cust = new CustomerDetails(cname, phone_no, vehicle_type, fuel_type, req, vehicle_num, timing, location);
//        customerref.push().setValue(cust);
//        Toast.makeText(FisrtPageLogin.this, "Data inserted", Toast.LENGTH_SHORT).show();
//    }
//
//    private void getLocation() {
//        try {
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        String latitude = String.valueOf(location.getLatitude());
//                        String longitude = String.valueOf(location.getLongitude());
//                        String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
//                        tvLocation.setText(locationText);
//                    } else {
//                        // Handle case where location is null
//                    }
//                }
//            });
//        } catch (SecurityException e) {
//            // Handle exception gracefully and inform the user
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLocation();
//            }
//        }
//    }
//
//    private void switchActivities1() {
//        Intent switchActivityIntent = new Intent(this, MainActivityLogin.class);
//        startActivity(switchActivityIntent);
//    }
//
//    private void switchActivities2() {
//        Intent switchActivityIntent = new Intent(this, MainActivity.class);
//        startActivity(switchActivityIntent);
//    }
//}
//package com.example.onroadservice;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//public class FisrtPageLogin extends AppCompatActivity {
//    Button logout, back, submit;
//    EditText v_num, time, c_name, number;
//    Spinner veh_type, f_type, require;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//    String vehicle_type, fuel_type, req;
//    private Button btnGetLocation;
//    private TextView tvLocation;
//    DatabaseReference customerref;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationCallback locationCallback;
//    private LocationRequest locationRequest;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fisrt_page_login);
//        back = findViewById(R.id.clback);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switchActivities1();
//            }
//        });
//        logout = findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switchActivities2();
//            }
//        });
//        customerref = FirebaseDatabase.getInstance().getReference().child("CustomerDetails");
//
//        v_num = findViewById(R.id.v_num);
//        time = findViewById(R.id.time);
//        veh_type = findViewById(R.id.veh_typ);
//        f_type = findViewById(R.id.fuel_typ);
//        require = findViewById(R.id.req_typ);
//        c_name = findViewById(R.id.c_name);
//        number = findViewById(R.id.number);
//
//        btnGetLocation = findViewById(R.id.btnGetLocation);
//        tvLocation = findViewById(R.id.tvLocation);
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000); // Set the desired interval for location updates
//        locationRequest.setFastestInterval(2000); // Set the fastest interval for location updates
//
//        btnGetLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(FisrtPageLogin.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//                } else {
//                    ActivityCompat.requestPermissions(FisrtPageLogin.this,
//                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                            LOCATION_PERMISSION_REQUEST_CODE);
//                }
//            }
//        });
//
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult != null) {
//                    Location location = locationResult.getLastLocation();
//                    if (location != null) {
//                        double latitude = location.getLatitude();
//                        double longitude = location.getLongitude();
//                        String address = getAddressFromLocation(latitude, longitude);
//                        String locationText = "Address: " + address + "\nLatitude: " + latitude + ", Longitude: " + longitude;
//                        tvLocation.setText(locationText);
//                    }
//                }
//            }
//        };
//
//        veh_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                vehicle_type = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        f_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                fuel_type = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        require.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                req = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        submit = findViewById(R.id.data_submit);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertData();
//            }
//        });
//    }
//
//    private void insertData() {
//        String cname = c_name.getText().toString();
//        String phone_no = number.getText().toString();
//        String vehicle_type = veh_type.getSelectedItem().toString();
//        String fuel_type = f_type.getSelectedItem().toString();
//        String req = require.getSelectedItem().toString();
//        String vehicle_num = v_num.getText().toString();
//        String timing = time.getText().toString();
//        String location = tvLocation.getText().toString();
//        CustomerDetails cust = new CustomerDetails(cname, phone_no, vehicle_type, fuel_type, req, vehicle_num, timing, location);
//        customerref.push().setValue(cust);
//        Toast.makeText(FisrtPageLogin.this, "Data inserted", Toast.LENGTH_SHORT).show();
//    }
//
//    private void getLocation() {
//        try {
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        double latitude = location.getLatitude();
//                        double longitude = location.getLongitude();
//                        String address = getAddressFromLocation(latitude, longitude);
//                        String locationText = "Address: " + address + "\nLatitude: " + latitude + ", Longitude: " + longitude;
//                        tvLocation.setText(locationText);
//                    } else {
//                        // Handle case where location is null
//                    }
//                }
//            });
//        } catch (SecurityException e) {
//            // Handle exception gracefully and inform the user
//        }
//    }
//
//    private String getAddressFromLocation(double latitude, double longitude) {
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            if (addresses != null && addresses.size() > 0) {
//                Address address = addresses.get(0);
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
//                    sb.append(address.getAddressLine(i));
//                    if (i < address.getMaxAddressLineIndex()) {
//                        sb.append(", ");
//                    }
//                }
//                return sb.toString();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "Location not found";
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLocation();
//            }
//        }
//    }
//
//    private void switchActivities1() {
//        Intent switchActivityIntent = new Intent(this, MainActivityLogin.class);
//        startActivity(switchActivityIntent);
//    }
//
//    private void switchActivities2() {
//        Intent switchActivityIntent = new Intent(this, MainActivity.class);
//        startActivity(switchActivityIntent);
//    }
//}
package com.example.onroadservice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.Query;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FisrtPageLogin extends AppCompatActivity {
    Button logout, back, submit;
    EditText v_num, time, c_name, number;
    Spinner veh_type, f_type, require;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    String vehicle_type, fuel_type, req;
    private Button btnGetLocation;
    private TextView tvLocation;
    DatabaseReference customerref;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisrt_page_login);

        // Initialize views and buttons
        logout = findViewById(R.id.logout);
        back = findViewById(R.id.clback);
        submit = findViewById(R.id.data_submit);
        v_num = findViewById(R.id.v_num);
        time = findViewById(R.id.time);
        veh_type = findViewById(R.id.veh_typ);
        f_type = findViewById(R.id.fuel_typ);
        require = findViewById(R.id.req_typ);
        c_name = findViewById(R.id.c_name);
        number = findViewById(R.id.number);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        tvLocation = findViewById(R.id.tvLocation);

        // Initialize Firebase
        customerref = FirebaseDatabase.getInstance().getReference().child("CustomerDetails");

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Set button click listeners
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities1();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities2();
            }
        });

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FisrtPageLogin.this, permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(FisrtPageLogin.this,
                            new String[]{permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });

        // Set location callback

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        String latitude = String.valueOf(location.getLatitude());
                        String longitude = String.valueOf(location.getLongitude());
                        String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
                        tvLocation.setText(locationText);
                    }
                }
        }
    };
        // Set spinner item selected listeners
        veh_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicle_type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        f_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fuel_type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        require.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                req = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        updateLocation(location);
                    } else {
                        startLocationUpdates();
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void updateLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Geocode the coordinates to get the address
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressText = address.getAddressLine(0);
                tvLocation.setText(addressText);
            } else {
                tvLocation.setText("No address found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            tvLocation.setText("Error: " + e.getMessage());
        }
    }


    private void insertData() {
        String cname = c_name.getText().toString();
        String phone_no = number.getText().toString();
        String vehicle_num = v_num.getText().toString();
        String timing = time.getText().toString();
        String location = tvLocation.getText().toString();
        String vehicle_type = veh_type.getSelectedItem().toString();
        String fuel_type = f_type.getSelectedItem().toString();
        String req = require.getSelectedItem().toString();

        // Insert data into CustomerDetails table
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("CustomerDetails");
        String customerId = customerRef.push().getKey();
        CustomerDetails cust = new CustomerDetails(cname, phone_no, vehicle_type, fuel_type, req, vehicle_num, timing, location, false);
        customerRef.child(customerId).setValue(cust);

        // Append customer details as a child node to each service provider in ServiceProviders table
        DatabaseReference serviceProvidersRef = FirebaseDatabase.getInstance().getReference().child("ServiceProviders");
        serviceProvidersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                    String serviceProviderId = providerSnapshot.getKey();
                    DatabaseReference serviceProviderRef = serviceProvidersRef.child(serviceProviderId).child("Customers");
                    String providerCustomerId = serviceProviderRef.push().getKey();
                    serviceProviderRef.child(providerCustomerId).setValue(cust);
                }
                Toast.makeText(FisrtPageLogin.this, "Data inserted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FisrtPageLogin.this, "Failed to insert data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    private void switchActivities1() {
        Intent switchActivityIntent = new Intent(this,CustomerIntermediate.class);
        startActivity(switchActivityIntent);
    }

    private void switchActivities2(){

        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}
