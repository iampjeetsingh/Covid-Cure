package com.example.hp_awareness_app;


import android.graphics.Rect;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {

    private static ThirdFragment instance;

    public ThirdFragment() {
        // Required empty public constructor
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText queryEditText;

    private SessionsClient sessionsClient;
    private SessionName session;
    private String UserName = null;
    private String Age = null;
    private String Gender = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);

        instance = this;
        Rect displayRectangle = new Rect();
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.help_desk_basic, viewGroup, false);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        EditText user_name = dialogView.findViewById(R.id.user_name);
        EditText user_age = dialogView.findViewById(R.id.user_age);
        EditText user_gender = dialogView.findViewById(R.id.user_gender);




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

        Button buttonOk=dialogView.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(v -> {
            UserName = user_name.getText().toString();
            Age = user_age.getText().toString();
            Gender = user_gender.getText().toString();

            if(TextUtils.isEmpty(user_name.getText()) || TextUtils.isEmpty(user_age.getText()) ||TextUtils.isEmpty(user_gender.getText()))
            {
                if(TextUtils.isEmpty(user_name.getText())) {
                    user_name.setError("field is Required");
                }
                if(TextUtils.isEmpty(user_age.getText())) {
                    user_age.setError("field is Required");
                }
                if(TextUtils.isEmpty(user_gender.getText())) {
                    user_gender.setError("field is Required");
                }
            }
            else{
                showTextView("Hello " + UserName,BOT);

                alertDialog.dismiss();
            }

        });
        alertDialog.show();
        // Java V2
        initV2Chatbot();
        // Inflate the layout for this fragment
        return view;
    }

    static ThirdFragment getInstance() {
        return instance;
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
        return (FrameLayout) inflater.inflate(R.layout.user_msg_layout, null);
    }

    private FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return (FrameLayout) inflater.inflate(R.layout.bot_msg_layout, null);
    }

}
