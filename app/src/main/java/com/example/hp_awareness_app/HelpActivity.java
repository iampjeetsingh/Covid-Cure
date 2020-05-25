package com.example.hp_awareness_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HelpActivity extends AppCompatActivity {

    DatabaseReference userDatabase;
    EditText name, contact, address, message;
    String dateTime;
  static Button sendButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String no;
    String adminUid;

    DatabaseReference adminRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        LayoutDetails();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendData();
            }
        });

        preferences = getSharedPreferences("App", MODE_PRIVATE);

        adminUid = preferences.getString("adminUid", "");
        no = preferences.getString("Contact", "");
        contact.setText(no);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
        dateTime = sdf.format(new Date());


    }

    private void SendData() {


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uID = firebaseUser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("Name", name.getText().toString());
        userMap.put("Contact", no);
        userMap.put("Address", address.getText().toString());
        userMap.put("DateTime", dateTime);
        userMap.put("Message", message.getText().toString());
        userMap.put("Id", uID);
        userDatabase.child(uID).setValue(userMap);
        sendButton.setText("Message Sent");
        sendButton.setEnabled(false);
        editor = preferences.edit();
        editor.putString("Date&Time", dateTime);
        editor.commit();
/*
        adminRef = FirebaseDatabase.getInstance().getReference().child("User").child("JioUKTzeV5WPsdcU3ckmf8QvghJ3");
        HashMap<String, String> map = new HashMap<>();
        map.put("Message", "message");
        adminRef.setValue(map);

 */
    }

    private void LayoutDetails() {
        name = findViewById(R.id.nameEdit);
        contact = findViewById(R.id.PhoneEdit);
        address = findViewById(R.id.AddressEdit);
        message = findViewById(R.id.message);
        sendButton = findViewById(R.id.sendButton);

    }


}
