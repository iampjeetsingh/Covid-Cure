package com.example.hp_awareness_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText nameTV,ageTV,phoneNoTV,addressTV;


    RadioButton travelYes, travelNo, genderM, genderF;
    int travelStatus = 0, gender = 0;
    EditText recentPlace;
    RadioButton qYes, qNo, typeHome, typeGovt;
    boolean qStatus;
    String quartype;
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
            gender = 2;
        });
        genderF.setOnClickListener(v -> {
            genderM.setChecked(false);
            gender = 1;
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
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("age",age);
        map.put("phone",phone);
        map.put("address",address);
        map.put("gender", gender==1 ? "M" : "F");
        map.put("travelled", travelStatus == 1);
        map.put("quarantineStatus",qStatus);
        map.put("quarantineType",quartype);
        map.put("placesTravelled",recentPlace);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).setValue(map);

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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

        String place = recentPlace.getText().toString();

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

        proceedBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


}
