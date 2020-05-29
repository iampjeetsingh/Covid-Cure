package com.example.hp_awareness_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    RadioButton travelYes, travelNo;
    EditText recentPlace;
    RadioButton qYes, qNo, typeHome, typeGovt;
    boolean qStatus;
    String quartype;
    Button button;
    TextView cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        travelYes = findViewById(R.id.radioTravelYes);
        travelNo = findViewById(R.id.radioTravelNo);
        travelYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travelNo.setChecked(false);
               // OpenDialog();

            }
        });
    }
/*
    private void OpenDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);

        recentPlace = view.findViewById(R.id.recentPlace);
        qYes = view.findViewById(R.id.radioQuarYes);
        qNo = view.findViewById(R.id.radioQuarNo);
        typeGovt = view.findViewById(R.id.radioGovt);
        typeHome = view.findViewById(R.id.radioHome);
        button = view.findViewById(R.id.SaveDialog);
        cancel = view.findViewById(R.id.textView7);

        String place = recentPlace.getText().toString();

        qYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qNo.setChecked(false);
                qStatus = true;
            }
        });
        qNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qYes.setChecked(false);
                qStatus = false;
            }
        });
        typeGovt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeHome.setChecked(false);
                quartype = "Govt. Center";
            }
        });
        typeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeGovt.setChecked(false);
                quartype = "Home Quarantine";
            }
        });

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


 */

}
