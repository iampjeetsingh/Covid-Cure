package com.example.hp_awareness_app;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.hp_awareness_app.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        String type = MainActivity.type;
        TextView loginType = (TextView)view.findViewById(R.id.loginType);
        loginType.setText(type);

        if(Objects.equals(type, "Admin"))
        {
            Button addAdmin = view.findViewById(R.id.btnAddAdmin);
            addAdmin.setVisibility(View.VISIBLE);

            addAdmin.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AddAdminActivity.class);
                startActivity(intent);
            });
        }

        view.findViewById(R.id.buttonLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), PhoneActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        });
        // Inflate the layout for this fragment
        return view;
    }

}
