package com.hpawareness.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ArticlesList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private DatabaseReference reference;
    private Context context = ArticlesList.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        recyclerView = findViewById(R.id.recyclerView);
        reference = FirebaseDatabase.getInstance().getReference().child("Articles");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        articleAdapter = new ArticleAdapter(reference) {
            @Override
            public void populateViewHolder(ArticleViewHolder holder, Article article, int position) {
                if(article!=null) {
                    holder.setArticle(article);
                    holder.setOnClickListener((v)->{
                        Intent intent = new Intent(context, ViewArticle.class);
                        intent.putExtra("url", article.getLink());
                        startActivity(intent);
                    });
                }else
                    holder.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", databaseError.getMessage());
            }
        };
        recyclerView.setAdapter(articleAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_add){
            Intent intent = new Intent(context, AddArticle.class);
            startActivity(intent);
        }
        return true;
    }
}
