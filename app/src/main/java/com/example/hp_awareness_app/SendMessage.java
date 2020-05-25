package com.example.hp_awareness_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SendMessage extends AppCompatActivity {

    String id;
    String name, phn, add, msg, time;
    EditText reply;

    TextView usrname, usrcnt, usradd, usrmsg;
    DatabaseReference userRef;
    Button send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        reply = findViewById(R.id.AdminReply);
        send = findViewById(R.id.SendReply);
        usrname = findViewById(R.id.UserName);
        usrcnt = findViewById(R.id.UserContact);
        usradd = findViewById(R.id.UserAddress);
        usrmsg = findViewById(R.id.UserMessage);

        ViewMessage();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendReply();
            }
        });


    }

    private void SendReply() {
        userRef = FirebaseDatabase.getInstance().getReference().child("User").child(id);
        Map<String, String> map = new HashMap<>();
        map.put("Message", reply.getText().toString());
        map.put("Date&Time", time);
        userRef.setValue(map);
        send.setText("Sent");


    }

    private void ViewMessage() {
        Intent intent = getIntent();
        id = intent.getStringExtra("User Id");
        name = intent.getStringExtra("name");
        phn = intent.getStringExtra("phn");
        add = intent.getStringExtra("add");
        msg = intent.getStringExtra("msg");
        time = intent.getStringExtra("time");


        usrname.setText(name);
        usrcnt.setText(phn);
        usradd.setText(add);
        usrmsg.setText(msg);
    }

}
