package com.example.hp_awareness_app;


import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp_awareness_app.MainActivity;
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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    ImageView info, help;
    Button msg;
    ImageView bell;
    private static FirstFragment instance;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText queryEditText;

    private SessionsClient sessionsClient;
    private SessionName session;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        instance = this;
        info = view.findViewById(R.id.infoImage);
        help = view.findViewById(R.id.HelpImage);
        bell = view.findViewById(R.id.bell);

        info.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InfoWeb.class);
            startActivity(intent);
        });


        String type = MainActivity.type;

        if (Objects.equals(type, "Admin")) {
            bell.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), Notifications.class);
                startActivity(intent);
            });
        }

        if (Objects.equals(type, "User")) {

            help.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            });

            bell.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GetMessage.class);
                startActivity(intent);
            });
        }
        FloatingActionButton btnOpenChatView = view.findViewById(R.id.btnChatView);
        ImageView btnCloseChatView = view.findViewById(R.id.btnOpenChatView);
        ImageView btnCloselayout = view.findViewById(R.id.btnCloselayout);
        TextView howHelpText = view.findViewById(R.id.how_help_text);
        LinearLayout openChatViwlayout = view.findViewById(R.id.openChatViewLayout);
        LinearLayout closeChatViwlayout = view.findViewById(R.id.CloseChatViewLayout);

                btnOpenChatView.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.GONE);
            closeChatViwlayout.setVisibility(View.VISIBLE);
        });

        btnOpenChatView.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.GONE);
            closeChatViwlayout.setVisibility(View.VISIBLE);
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
        });

        final ScrollView scrollview = view.findViewById(R.id.chatScrollView);
        scrollview.post(() -> scrollview.fullScroll(ScrollView.FOCUS_DOWN));

        chatLayout = view.findViewById(R.id.chatLayout);


        ImageView sendBtn = view.findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this::sendMessage);

        queryEditText = view.findViewById(R.id.queryEditText);

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

        String userName = "USER";

        showTextViewWithoutFocus("Welcome to the FAQ" ,BOT);
        showTextViewWithoutFocus("Hello " + userName + " !!",BOT);

        // Java V2
        initV2Chatbot();
        // Inflate the layout for this fragment
        return view;
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

    private void sendMessage(View view) {
        String msg = queryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(msg, USER);
            queryEditText.setText("");


            // Java V2
            QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("en-US")).build();
            new RequestJavaV2Task(getActivity(), session, sessionsClient, queryInput).execute();
        }
    }

    private FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.user_msg_layout, null);

        TextView bot_message_time = frame.findViewById(R.id.user_message_time);
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String time = simpleDateFormat.format(calander.getTime());
        bot_message_time.setText(time);

        return frame;
    }

    private FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.bot_msg_layout, null);

        TextView bot_message_time = frame.findViewById(R.id.bot_message_time);
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        String time = simpleDateFormat.format(calander.getTime());
        bot_message_time.setText(time);


        return frame;
    }

    static FirstFragment getInstance() {
        return instance;
    }
}


