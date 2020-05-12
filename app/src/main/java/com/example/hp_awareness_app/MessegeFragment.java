package com.example.hp_awareness_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class MessegeFragment extends Fragment {

    DatabaseReference userDatabase;
    EditText name, contact, address, message;
    String dateTime;
    Button sendButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String no;
    String adminUid;

    DatabaseReference adminRef;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messege, container, false);


        LayoutDetails(view);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendData();
            }
        });

        preferences = getActivity().getSharedPreferences("App", MODE_PRIVATE);

        adminUid = preferences.getString("adminUid", "");
        no = preferences.getString("Contact", "");
        contact.setText(no);
        return view;
    }

    private void SendData() {


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uID = firebaseUser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("Name", name.getText().toString());
        userMap.put("Contact", no);
        userMap.put("Address", address.getText().toString());
        userMap.put("DateTime", "dateTime");
        userMap.put("Message", message.getText().toString());
        userMap.put("Id", uID);
        userDatabase.child(uID).setValue(userMap);
        sendButton.setText("Message Sent");
/*
        adminRef = FirebaseDatabase.getInstance().getReference().child("User").child("JioUKTzeV5WPsdcU3ckmf8QvghJ3");
        HashMap<String, String> map = new HashMap<>();
        map.put("Message", "message");
        adminRef.setValue(map);

 */
    }

    private void LayoutDetails(View view) {
        name = view.findViewById(R.id.fragnameEdit);
        contact = view.findViewById(R.id.fragPhoneEdit);
        address = view.findViewById(R.id.fragAddressEdit);
        message = view.findViewById(R.id.fragMessage);
        sendButton = view.findViewById(R.id.fragSendButton);

    }
}
