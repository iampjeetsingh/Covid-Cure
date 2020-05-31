package com.example.hp_awareness_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class HelpMessegeActivity extends AppCompatActivity {

    DatabaseReference userDatabase;
//    EditText name, contact, address, message;
    String name, contact, address, message;;
    String dateTime;
    static Button sendButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String no;
    private static HelpMessegeActivity instance;
    String adminUid;
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout helpLayout;
    private EditText helpQueryEditText;

    DatabaseReference adminRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_help);

//        LayoutDetails();
//        instance = this;
//        helpSendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SendData();
//            }
//        });

        preferences = getSharedPreferences("App", MODE_PRIVATE);

//        adminUid = preferences.getString("adminUid", "");
//        no = preferences.getString("Contact", "");
//        contact.setText(no);
//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
        dateTime = sdf.format(new Date());


        ///ChatView


        final ScrollView scrollview = findViewById(R.id.helpScrollView);
        scrollview.post(() -> scrollview.fullScroll(ScrollView.FOCUS_DOWN));

        helpLayout =findViewById(R.id.help_Layout);


        ImageView helpSendBtn = findViewById(R.id.helpSendBtn);
        helpSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = sendMessage();
            }
        });

        helpQueryEditText = findViewById(R.id.helpQueryEditText);

        helpQueryEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        String res = sendMessage();
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        String userName = "USER";

        showTextViewWithoutFocus("Welcome to the Helpline" ,BOT);
        showTextViewWithoutFocus("Hello!! Please Enter Your Name?",BOT);

    }

    private void showTextViewWithoutFocus(String message, int type) {
        FrameLayout layout;
        if (type == USER) {
            layout = getUserLayout();
        }else{
            layout = getBotLayout();
        }
        layout.setFocusableInTouchMode(true);
        helpLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
    }
    private void showTextView(String message, int type) {
        FrameLayout layout;
        if (type == USER) {
            layout = getUserLayout();
        }else{
            layout = getBotLayout();
        }
        layout.setFocusableInTouchMode(true);
        helpLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
        layout.requestFocus();
        helpQueryEditText.requestFocus(); // change focus back to edit text to continue typing
    }

    private String sendMessage() {
        String msg = helpQueryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your query!", Toast.LENGTH_LONG).show();

            return "Null";
        } else {
            showTextView(msg, USER);
//            name = msg;
            helpQueryEditText.setText("");
            return msg;
//            showTextView("Welcome to the Helpline" ,BOT);
        }

    }

    private FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);

        FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.user_msg_layout, null);

        TextView bot_message_time = frame.findViewById(R.id.user_message_time);
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String time = simpleDateFormat.format(calander.getTime());
        bot_message_time.setText(time);

        return frame;
    }

    private FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.bot_msg_layout, null);

        TextView bot_message_time = frame.findViewById(R.id.bot_message_time);
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String time = simpleDateFormat.format(calander.getTime());
        bot_message_time.setText(time);


        return frame;
    }

    static HelpMessegeActivity getInstance() {
        return instance;
    }

    private void SendData() {


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uID = firebaseUser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("Name", name);
        userMap.put("Contact", no);
        userMap.put("Address", address);
        userMap.put("DateTime", dateTime);
        userMap.put("Message", message);
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





}
