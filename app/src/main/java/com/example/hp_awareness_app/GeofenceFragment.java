package com.example.hp_awareness_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class GeofenceFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geofence, container, false);
        btn = view.findViewById(R.id.btnGeofence);
        homebtn = view.findViewById(R.id.hq_yes);

        isolationbtn = view.findViewById(R.id.ic_yes);
        name = view.findViewById(R.id.geoName);
        age = view.findViewById(R.id.geoAge);
        sharedPrefs = getContext().getSharedPreferences("App", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isolationbtn.setChecked(false);
                quartype = "Home Quarantine";

            }
        });

        isolationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homebtn.setChecked(false);
                quartype = "Isolation Center";
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GeofenceActivity.class);
                startActivity(intent);
                SendDetails();
            }
        });


        return view;

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
