package com.example.hp_awareness_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class GeofenceFragActivity extends AppCompatActivity {
    Button btn;
    RadioButton homebtn, isolationbtn;
    String quartype;

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    String fullAddress;
    DatabaseReference reference;
    EditText name, age;
    FusedLocationProviderClient providerClient;
    private int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private double latitude, longitude;
    private Geocoder geocoder;

    String address1;
    String area;
    String city;
    String postalCode;
    String country;

    List<Address> addressList;
    String fulladdress;
    Boolean status = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_geofence);

        btn = findViewById(R.id.btnGeofence);
        homebtn = findViewById(R.id.hq_yes);

        isolationbtn = findViewById(R.id.ic_yes);
        name = findViewById(R.id.geoName);
        age = findViewById(R.id.geoAge);
        sharedPrefs = this.getSharedPreferences("App", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isolationbtn.setChecked(false);
                quartype = "Home Quarantine";
                status = true;

            }
        });

        isolationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homebtn.setChecked(false);
                quartype = "Isolation Center";
                status = true;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(age.getText().toString()) || status.equals(false)) {
                    Toast.makeText(GeofenceFragActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(GeofenceFragActivity.this, GeofenceActivity.class);
                    startActivity(intent);
                    SendDetails();
                }

            }
        });
    }
    private void SendDetails() {
        fullAddress = sharedPrefs.getString("Geo Location", "");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Geofencing").child(uid);

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("Name", name.getText().toString());
        userMap.put("Age", age.getText().toString());
        userMap.put("Quarantine Type", quartype);
        userMap.put("Address", fullAddress);
        reference.setValue(userMap);

    }
}
