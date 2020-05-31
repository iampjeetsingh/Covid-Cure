package com.example.hp_awareness_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetMessage extends AppCompatActivity {


    DatabaseReference reference;
    TextView reply;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String dateTime;
    String getDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        reply = findViewById(R.id.reply);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uiD = user.getUid();

        preferences = getSharedPreferences("App", MODE_PRIVATE);
        dateTime = preferences.getString("Date&Time", "");

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(uiD);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String msg = dataSnapshot.child("Message").getValue().toString();
                getDate = dataSnapshot.child("Date&Time").getValue().toString();

                reply.setText(msg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (dateTime == getDate) {
            HelpActivity.sendButton.setText("Send Message");
            HelpActivity.sendButton.setClickable(true);
        }

    }
}
