package com.example.hp_awareness_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
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
    private static final String TAG = MainActivity.class.getSimpleName();
    String no;
    private static HelpMessegeActivity instance;
    String adminUid;
    String QueryType;
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout helpLayout;
    private EditText helpQueryEditText;

    private LinearLayout chatLayout;
    private EditText queryEditText;

    private SessionsClient sessionsClient;
    private SessionName session;

    public HelpMessegeActivity() {
        // Required empty public constructor
    }

    DatabaseReference adminRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_help);

//        LayoutDetails();
        instance = this;
//        helpSendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SendData();
//            }
//        });

        preferences = getSharedPreferences("App", MODE_PRIVATE);

        adminUid = preferences.getString("adminUid", "");
        no = preferences.getString("Contact", "");
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
                String res = sendMessageHelp();

                switch (QueryType) {
                    case "Name":
                        name = res;
                        showHelpTextViewWithoutFocus("Hi " + name, BOT);
                        QueryType = "Address";
                        showHelpTextView("Please Enter  your current address", BOT);
                        break;
                    case "Address":
                        address = res;
                        showHelpTextViewWithoutFocus("you entered address - " + address, BOT);
                        QueryType = "Messege";
                        showHelpTextView("What Do you Want to ask?", BOT);
                        break;
                    case "Messege":
                        message = res;
                        showHelpTextViewWithoutFocus("Sending your request to authorities - " + message, BOT);
                        SendData();
                        QueryType = "Done";
                        showHelpTextViewWithoutFocus("Will reach you ASAP", BOT);
                        break;
                    case "Done":
                        QueryType = "Done";
                        showHelpTextViewWithoutFocus("Already sent your request wait for reply!!", BOT);
                        break;
                }
            }
        });

        helpQueryEditText = findViewById(R.id.helpQueryEditText);

        helpQueryEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        String res = sendMessageHelp();

                        switch (QueryType) {
                            case "Name":
                                name = res;
                                showHelpTextViewWithoutFocus("Hi " + name, BOT);
                                QueryType = "Address";
                                showHelpTextViewWithoutFocus("Please Enter  your current address", BOT);
                                break;
                            case "Address":
                                address = res;
                                showHelpTextViewWithoutFocus("you entered address - " + address, BOT);
                                QueryType = "Messege";
                                showHelpTextViewWithoutFocus("What Do you Want to ask?", BOT);
                                break;
                            case "Messege":
                                message = res;
                                showHelpTextViewWithoutFocus("Sending your request to authorities - " + message, BOT);
                                SendData();
                                QueryType = "Done";
                                showHelpTextViewWithoutFocus("Will reach you ASAP", BOT);
                                break;
                            case "Done":
                                QueryType = "Done";
                                showHelpTextViewWithoutFocus("Already sent your request wait for reply!!", BOT);
                                break;
                        }

                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        String userName = "USER";

        showHelpTextViewWithoutFocus("Welcome to the Helpline" ,BOT);
        QueryType = "Name";
        showHelpTextViewWithoutFocus("Hello!! Please Enter Your Name?",BOT);


        //DialogFlow

        FloatingActionButton btnOpenChatView = findViewById(R.id.btnChatView);
        ImageView btnCloseChatView = findViewById(R.id.btnOpenChatView);
        ImageView btnCloselayout = findViewById(R.id.btnCloselayout);
        TextView howHelpText = findViewById(R.id.how_help_text);
        LinearLayout openChatViwlayout = findViewById(R.id.openChatViewLayout);
        LinearLayout closeChatViwlayout = findViewById(R.id.CloseChatViewLayout);
        RelativeLayout helpinputlayout = findViewById(R.id.help_input_layout);

        btnOpenChatView.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.GONE);
            closeChatViwlayout.setVisibility(View.VISIBLE);
            helpinputlayout.setVisibility(View.GONE);
        });

        howHelpText.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.GONE);
            closeChatViwlayout.setVisibility(View.VISIBLE);
        });

        btnCloselayout.setOnClickListener(v -> {
            howHelpText.setVisibility(View.GONE);
            btnCloselayout.setVisibility(View.GONE);
        });

        btnCloseChatView.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.VISIBLE);
            closeChatViwlayout.setVisibility(View.GONE);
            helpinputlayout.setVisibility(View.VISIBLE);
        });

        final ScrollView scrollview2 = findViewById(R.id.chatScrollView);
        scrollview2.post(() -> scrollview2.fullScroll(ScrollView.FOCUS_DOWN));

        chatLayout = findViewById(R.id.chatLayout);


        ImageView sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this::sendMessage);

        queryEditText = findViewById(R.id.queryEditText);

        queryEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        sendMessage(sendBtn);
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });


        showTextViewWithoutFocus("Welcome to the FAQ" ,BOT);
        showTextViewWithoutFocus("Hello " + userName + " !!",BOT);

        // Java V2
        initV2Chatbot();
        // Inflate the layout for this fragment


    }

    private void showHelpTextViewWithoutFocus(String message, int type) {
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
    private void showHelpTextView(String message, int type) {
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

    private String sendMessageHelp() {
        String msg = helpQueryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your query!", Toast.LENGTH_LONG).show();

            return "NoInput";
        } else {
            showHelpTextView(msg, USER);
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
    private void initV2Chatbot() {
        try {
            InputStream stream = getResources().openRawResource(R.raw.dialog_flow_credential);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            session = SessionName.of(projectId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void callbackV2(DetectIntentResponse response) {
        if (response != null) {
            // process aiResponse here
            String botReply = response.getQueryResult().getFulfillmentText();
            Log.d(TAG, "V2 Bot Reply: " + botReply);
            showTextView(botReply, BOT);
        } else {
            Log.d(TAG, "Bot Reply: Null");
            showTextView("There was some communication issue. Please Try again!", BOT);
        }
    }
    private void showTextViewWithoutFocus(String message, int type) {
        FrameLayout layout;
        if (type == USER) {
            layout = getUserLayout();
        }else{
            layout = getBotLayout();
        }
        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
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
        chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
        layout.requestFocus();
        queryEditText.requestFocus(); // change focus back to edit text to continue typing
    }
    private void sendMessage(View view) {
        String msg = queryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(msg, USER);
            queryEditText.setText("");


            // Java V2
            QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("en-US")).build();
            new RequestJavaV2Task(this, session, sessionsClient, queryInput).execute();
        }
    }
    static HelpMessegeActivity getInstance() {
        return instance;
    }

    private void SendData() {


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
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

        editor = preferences.edit();
        editor.putString("Date&Time", dateTime);
        editor.apply();
/*
        adminRef = FirebaseDatabase.getInstance().getReference().child("User").child("JioUKTzeV5WPsdcU3ckmf8QvghJ3");
        HashMap<String, String> map = new HashMap<>();
        map.put("Message", "message");
        adminRef.setValue(map);

 */
    }

}
