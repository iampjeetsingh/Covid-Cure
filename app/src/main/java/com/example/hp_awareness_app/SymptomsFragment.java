package com.example.hp_awareness_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SymptomsFragment extends Fragment {

    public SymptomsFragment() {
    }


    String[] questions = {"Are you experiencing any of the following symptoms?",
            "Have you ever had any of the following:",
            "Have you travelled anywhere internationally in the last 28-45 days?",
            "Which of the following apply to you?"};
    Object[] options = {new String[]{"Cough","Fever","Difficulty in Breathing","None of the above"},
            new String[]{"Diabetes","Hypertension","Lung disease","Heart disease","None of the above"},
            new String[]{"Yes","No"},
            new String[]{"I have recently interacted or lived with someone who has tested positive for COVID-19",
                    "I am a healthcare worker and i examined a COVID-19 confirmed case without protective gear","" +
                    "None of the above"}};
    int[] expectedAnswer = {3,4,1,2};

    TextView quesTextView;
    RadioButton opt1,opt2,opt3,opt4,opt5;
    Button nextButton;

    int currentIndex = 0;
    int currentAnswer = -1;
    int points = 0;
    boolean ended = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_symptoms,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quesTextView = view.findViewById(R.id.quesTextView);
        opt1 = view.findViewById(R.id.opt1);
        opt2 = view.findViewById(R.id.opt2);
        opt3 = view.findViewById(R.id.opt3);
        opt4 = view.findViewById(R.id.opt4);
        opt5 = view.findViewById(R.id.opt5);
        nextButton = view.findViewById(R.id.nextButton);
        for(int i=0 ; i<5 ; i++){
            int finalI = i;
            getOpt(i).setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked)
                    optionSelected(finalI);
            });
        }
        refreshQuestion();
        nextButton.setOnClickListener(v -> {
            if(ended)
                return;
            if(currentAnswer==-1){
                Toast.makeText(getContext(),"Select an option first",Toast.LENGTH_SHORT).show();
                return;
            }
            int ans = expectedAnswer[currentIndex];
            if(ans!=currentAnswer){
                points += 1;
            }
            currentIndex += 1;
            currentAnswer = -1;
            refreshQuestion();
        });
    }

    void optionSelected(int n){
        currentAnswer = n;
    }

    void showResult(){
        ended = true;
        for(int i=0 ; i<5 ; i++){
            getOpt(i).setVisibility(View.GONE);
        }
        if(points==0){
            quesTextView.setText("Risk : Low");
        }else if(points>0 && points <=2){
            quesTextView.setText("Risk : Moderate");
        }else if(points>2){
            quesTextView.setText("Risk : High");
        }
    }

    void refreshQuestion(){
        if(currentIndex>=questions.length) {
            showResult();
            return;
        }
        for(int i=0 ; i<5 ; i++){
            getOpt(i).setVisibility(View.GONE);
            getOpt(i).setChecked(false
            );
        }
        String quesText = questions[currentIndex];
        String[] opts = (String[]) options[currentIndex];
        quesTextView.setText(quesText);
        for(int i=0 ; i<opts.length ; i++){
            getOpt(i).setVisibility(View.VISIBLE);
            getOpt(i).setText(opts[i]);
        }
    }

    RadioButton getOpt(int o){
        switch (o){
            case 0 : return opt1;
            case 1 : return opt2;
            case 2 : return opt3;
            case 3 : return opt4;
            case 4 : return opt5;
            default:return null;
        }
    }

}
