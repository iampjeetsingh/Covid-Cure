package com.example.hp_awareness_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragment extends AppCompatDialogFragment {
/*
    private DialogFragmentListener listener;
    EditText recentPlace;
    RadioButton qYes, qNo, typeHome, typeGovt;
    boolean qStatus;
    String quartype;
    Button button;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        recentPlace = view.findViewById(R.id.recentPlace);
        qYes = view.findViewById(R.id.radioQuarYes);
        qNo = view.findViewById(R.id.radioQuarNo);
        typeGovt = view.findViewById(R.id.radioGovt);
        typeHome = view.findViewById(R.id.radioHome);
        button = view.findViewById(R.id.SaveDialog);


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
                                qYes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        qYes.setChecked(false);
                                        qStatus = false;
                                    }
                                });
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
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.SendDetails(place, qStatus, quartype);

                            }
                        });
                      builder.setView(view);
                      AlertDialog dialog =builder.create();
                      dialog.show();


       return builder.create();


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogFragmentListener) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"must implementListener");
        }
    }

    public interface DialogFragmentListener {
        void SendDetails(String place, Boolean qStatus, String quartype);
    }


 */
}
