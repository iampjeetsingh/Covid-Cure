package com.example.hp_awareness_app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FaqActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.faq_layout);

        RecyclerView recyclerView =findViewById(R.id.faq_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<FAQ> faqData = new ArrayList<FAQ>();
        faqData.add(new FAQ("Q- What is corona virus?", "Ans- Corona viruses are a large family of viruses which may cause illness\n" +
                "in animals or humans. In humans, several coronaviruses are known\n" +
                "to cause respiratory infections ranging from the common cold to more\n" +
                "severe diseases such as Middle East Respiratory Syndrome (MERS)\n" +
                "and Severe Acute Respiratory Syndrome (SARS). The most recently\n" +
                "discovered coronavirus causes coronavirus disease COVID-19. "));
        faqData.add(new FAQ("Q- What are the symptoms of COVID-19?", "Ans - The most common symptoms of COVID-19 are fever, tiredness, and\n" +
                "dry cough. Some patients may have aches and pains, nasal\n" +
                "congestion, runny nose, sore throat or diarrhea. These symptoms are\n" +
                "usually mild and begin gradually. Some people become infected but\n" +
                "don’t develop any symptoms and don't feel unwell. Most people\n" +
                "(about 80%) recover from the disease without needing special\n" +
                "treatment. Around 1 out of every 6 people who gets COVID-19\n" +
                "becomes seriously ill and develops difficulty breathing. Older people,\n" +
                "and those with underlying medical problems like high blood pressure,\n" +
                "heart problems or diabetes, are more likely to develop serious illness.\n" +
                "People with fever, cough and difficulty breathing should seek medical\n" +
                "attention. "));
        // specify an adapter (see also next example)
        FaqAdapter mAdapter = new FaqAdapter(faqData);
        recyclerView.setAdapter(mAdapter);

    }
}
