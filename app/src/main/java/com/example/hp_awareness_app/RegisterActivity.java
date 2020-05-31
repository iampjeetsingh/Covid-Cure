package com.example.hp_awareness_app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText nameTV,ageTV,phoneNoTV,addressTV;


    RadioButton travelYes, travelNo, genderM, genderF;
    int travelStatus = 0, gender = 0;
    EditText recentPlace;
    RadioButton qYes, qNo, typeHome, typeGovt;
    boolean qStatus;
    String quartype;
    String places;
    TextView proceedBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameTV = findViewById(R.id.regName);
        ageTV = findViewById(R.id.regAge);
        phoneNoTV = findViewById(R.id.regContact);
        addressTV = findViewById(R.id.regAddress);

        travelYes = findViewById(R.id.radioTravelYes);
        travelNo = findViewById(R.id.radioTravelNo);
        genderM = findViewById(R.id.radioMale);
        genderF = findViewById(R.id.radioFemale);
        travelNo.setOnClickListener(v -> {
            travelYes.setChecked(false);
            travelStatus = 2;
        });
        travelYes.setOnClickListener(v -> {
            travelNo.setChecked(false);
            travelStatus = 1;
            OpenDialog();
        });
        genderM.setOnClickListener(v -> {
            genderF.setChecked(false);
            gender = 1;
        });
        genderF.setOnClickListener(v -> {
            genderM.setChecked(false);
            gender = 2;
        });
        loadData();
    }

    HashMap<String, Object> map;

    private void loadData(){
        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                map = (HashMap<String, Object>) dataSnapshot.getValue();
                if (map != null) {
                    nameTV.setText(String.valueOf(map.get("name")));
                    ageTV.setText(String.valueOf(map.get("age")));
                    phoneNoTV.setText(String.valueOf(map.get("phone")));
                    addressTV.setText(String.valueOf(map.get("address")));
                    String gender = (String) map.get("gender");
                    if (gender != null && gender.equals("M")){
                        RegisterActivity.this.gender = 1;
                    genderM.setChecked(true);
                    } else if (gender != null && gender.equals("F")){
                        RegisterActivity.this.gender = 2;
                        genderF.setChecked(true);
                    }
                    boolean travelStatus = (boolean) map.get("travelled");
                    if(travelStatus) {
                        RegisterActivity.this.travelStatus = 1;
                        travelYes.setChecked(true);
                    }else{
                        RegisterActivity.this.travelStatus = 2;
                        travelNo.setChecked(true);
                    }
                    qStatus = (boolean) map.get("quarantineStatus");
                    quartype = (String) map.get("quarantineType");
                    places = (String) map.get("placesTravelled");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Log.e("Register",databaseError.getMessage());
            }
        });
    }

    public void registerClick(View v){
        String name = nameTV.getText().toString();
        String age = ageTV.getText().toString();
        String phone = phoneNoTV.getText().toString();
        String address = addressTV.getText().toString();
        if(name.isEmpty() || age.isEmpty() || phone.isEmpty() || address.isEmpty() || gender==0 || travelStatus==0){
            Dialog dialog = new Dialog(RegisterActivity.this);
            dialog.setContentView(R.layout.alert_dialogue);
            TextView back = dialog.findViewById(R.id.proceed);
            back.setOnClickListener(v1 ->{
                dialog.dismiss();
            });
            dialog.show();
        }
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("name",name);
        objectMap.put("age",age);
        objectMap.put("phone",phone);
        objectMap.put("address",address);
        objectMap.put("gender", gender==1 ? "M" : "F");
        objectMap.put("travelled", travelStatus == 1);
        objectMap.put("quarantineStatus",qStatus);
        objectMap.put("quarantineType",quartype);
        objectMap.put("placesTravelled",places);
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).setValue(objectMap);

        Intent intent = new Intent(RegisterActivity.this, NewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("type", "User");
        startActivity(intent);
    }


    private void OpenDialog() {
        Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        recentPlace = dialog.findViewById(R.id.recentPlace);
        qYes = dialog.findViewById(R.id.radioQuarYes);
        qNo = dialog.findViewById(R.id.radioQuarNo);
        typeGovt = dialog.findViewById(R.id.radioGovt);
        typeHome = dialog.findViewById(R.id.radioHome);
        proceedBtn = dialog.findViewById(R.id.proceed);

        if(map!=null){
            recentPlace.setText(places);
            if(qStatus)
                qYes.setChecked(true);
            else
                qNo.setChecked(true);
            if(quartype!=null && quartype.equals("Govt. Center")){
                typeGovt.setChecked(true);
            }else if(quartype!=null && quartype.equals("Home Quarantine")){
                typeHome.setChecked(true);
            }
        }

        qYes.setOnClickListener(v -> {
            qNo.setChecked(false);
            qStatus = true;
        });
        qNo.setOnClickListener(v -> {
            qYes.setChecked(false);
            qStatus = false;
        });
        typeGovt.setOnClickListener(v -> {
            typeHome.setChecked(false);
            quartype = "Govt. Center";
        });
        typeHome.setOnClickListener(v -> {
            typeGovt.setChecked(false);
            quartype = "Home Quarantine";
        });

        proceedBtn.setOnClickListener(v -> {
            dialog.dismiss();
            places = recentPlace.getText().toString();
        });

        dialog.show();
    }


}