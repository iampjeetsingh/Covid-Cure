package com.example.hp_awareness_app;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FaqFragment extends Fragment {

    private static FaqFragment instance;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText queryEditText;

    private SessionsClient sessionsClient;
    private SessionName session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        requireActivity().findViewById(R.id.fragment_container).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.bottomNavigation).setVisibility(View.GONE);
        View view = inflater.inflate(R.layout.faq_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.faq_recycler_view);
        recyclerView.setHasFixedSize(true);
        instance = this;

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<FAQ> faqData = new ArrayList<FAQ>() ;
        faqData.add(new FAQ("Question","Answer"));
        faqData.add(new FAQ("Question","Answer"));
        faqData.add(new FAQ("Question","Answer"));
        faqData.add(new FAQ("Question","Answer"));
        // specify an adapter (see also next example)
        FaqAdapter mAdapter = new FaqAdapter(faqData);
        recyclerView.setAdapter(mAdapter);

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

        ImageView btnOpenChatView = view.findViewById(R.id.btnChatView);
        ImageView btnCloseChatView = view.findViewById(R.id.btnOpenChatView);
        LinearLayout openChatViwlayout = view.findViewById(R.id.openChatViewLayout);
        LinearLayout closeChatViwlayout = view.findViewById(R.id.CloseChatViewLayout);

        btnOpenChatView.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.GONE);
            closeChatViwlayout.setVisibility(View.VISIBLE);
        });

        openChatViwlayout.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.GONE);
            closeChatViwlayout.setVisibility(View.VISIBLE);
        });

        btnCloseChatView.setOnClickListener(v -> {
            openChatViwlayout.setVisibility(View.VISIBLE);
            closeChatViwlayout.setVisibility(View.GONE);
        });

        showTextView("Welcome to the FAQ" ,BOT);
        showTextView("Hello " + userName + " !!",BOT);

        // Java V2
        initV2Chatbot();
        // Inflate the layout for this fragment
        return view;
    }

    static FaqFragment getInstance() {
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

}
