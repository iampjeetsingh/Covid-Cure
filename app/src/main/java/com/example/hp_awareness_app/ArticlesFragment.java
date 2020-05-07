package com.example.hp_awareness_app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private Context context = getContext();

    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerView);
        reference = FirebaseDatabase.getInstance().getReference().child("Articles");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        ArticleAdapter articleAdapter = new ArticleAdapter(reference) {
            @Override
            public void populateViewHolder(ArticleViewHolder holder, Article article, int position) {
                if (article != null) {
                    holder.setArticle(article);
                    holder.setOnClickListener((v) -> {
                        Intent intent = new Intent(getActivity(), ViewArticle.class);
                        intent.putExtra("url", article.getLink());
                        startActivity(intent);
                    });
                } else
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
    public void onStop() {
        super.onStop();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
//        return true;
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.action_add) {
                Intent intent = new Intent(getActivity(), AddArticle.class);
                startActivity(intent);
                return true;
            }
        return false;
    }
}