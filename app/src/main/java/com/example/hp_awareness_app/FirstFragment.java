package com.example.hp_awareness_app;


import android.app.Notification;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp_awareness_app.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    ImageView info, help;
    Button msg;
    ImageView bell;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        info = view.findViewById(R.id.infoImage);
        help = view.findViewById(R.id.HelpImage);
        bell = view.findViewById(R.id.bell);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoWeb.class);
                startActivity(intent);
            }
        });


        String type = MainActivity.type;

        if (Objects.equals(type, "Admin")) {
            bell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Notifications.class);
                    startActivity(intent);
                }
            });
        }

        if (Objects.equals(type, "User")) {

            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), HelpActivity.class);
                    startActivity(intent);
                }
            });

            bell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), GetMessage.class);
                    startActivity(intent);
                }
            });
        }

        //   TextView loginType = (TextView)view.findViewById(R.id.loginType);
        //  loginType.setText(type);

        if (Objects.equals(type, "Admin")) {
            /*
            Button addAdmin = view.findViewById(R.id.btnAddAdmin);
            addAdmin.setVisibility(View.VISIBLE);



            addAdmin.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AddAdminActivity.class);
                startActivity(intent);
            });
        }
/*
        view.findViewById(R.id.buttonLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), PhoneActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        });
        // Inflate the layout for this fragment

 */

        }


        return view;
    }
}
