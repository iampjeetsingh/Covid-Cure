package com.example.hp_awareness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminDashboard extends AppCompatActivity {

    EditText totalET, recoveredET, himachalET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        totalET = findViewById(R.id.total);
        recoveredET = findViewById(R.id.totalRecovered);
        himachalET = findViewById(R.id.himachal);
    }

    public void viewClick(View v){
        Intent intent = new Intent(AdminDashboard.this, Notifications.class);
        startActivity(intent);
    }

    public void updateClick(View v){
        String total = totalET.getText().toString();
        String recovered = recoveredET.getText().toString();
        String himachal = himachalET.getText().toString();
        HashMap<String,String> map = new HashMap<>();
        map.put("total",total);
        map.put("recovered",recovered);
        map.put("himachal",himachal);
        FirebaseDatabase.getInstance().getReference().child("Cases").setValue(map);
        totalET.setText("");
        recoveredET.setText("");
        himachalET.setText("");
        Toast.makeText(AdminDashboard.this,"Data Updated!",Toast.LENGTH_SHORT).show();
    }
}