package com.example.hp_awareness_app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class AddArticle extends AppCompatActivity {

    private EditText source, headline, thumbnail, webpage;
    private DatabaseReference reference;
    private Context context = AddArticle.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        source = findViewById(R.id.source);
        headline = findViewById(R.id.headline);
        thumbnail = findViewById(R.id.thumbnail);
        webpage = findViewById(R.id.webpage);
        reference = FirebaseDatabase.getInstance().getReference().child("Articles");
    }

    public void addClick(View v){
        String src = source.getText().toString();
        String head = headline.getText().toString();
        String thumb = thumbnail.getText().toString();
        String web = webpage.getText().toString();
        if(src.isEmpty() || head.isEmpty() || thumb.isEmpty() || web.isEmpty()){
            Toast.makeText(context,"Fill all the details", Toast.LENGTH_SHORT).show();
            return;
        }
        Article article = new Article();
        article.setHeadLine(head);
        article.setSource(src);
        article.setThumbnail(thumb);
        article.setLink(web);
        article.setVisible(true);
        article.setTimeStamp(ServerValue.TIMESTAMP);
        String key = reference.push().getKey();
        article.setID(key);
        reference.child(key).setValue(article);
        Toast.makeText(context, "Article Added!", Toast.LENGTH_SHORT).show();
        finish();
    }
}

