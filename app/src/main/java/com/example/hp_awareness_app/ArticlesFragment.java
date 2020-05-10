package com.example.hp_awareness_app;


import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment implements IFirebaseLoadDone, ValueEventListener {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private Context context = getContext();

    ViewPager viewPager;
    ArticleAdapter adapter;

    IFirebaseLoadDone iFirebaseLoadDone;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        // Inflate the layout for this fragment
        reference = FirebaseDatabase.getInstance().getReference().child("Articles");
        iFirebaseLoadDone = this;

        loadArticle();
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);


        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 3000);
        return view;
    }

    private void loadArticle() {
        reference.addValueEventListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem add_item = menu.findItem(R.id.action_add);
        String type = MainActivity.type;
        if (Objects.equals(type, "Admin")) {
            add_item.setVisible(true);
        } else {
            add_item.setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

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

    @Override
    public void onFirebaseLoadSuccess(List<Article> articleList) {
        adapter = new ArticleAdapter(getActivity(), articleList);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(80, 10, 80, 10);


    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Article> articleList = new ArrayList<>();
        for (DataSnapshot articleSnapshot : dataSnapshot.getChildren())
            articleList.add(articleSnapshot.getValue(Article.class));
        iFirebaseLoadDone.onFirebaseLoadSuccess(articleList);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
    }

    @Override
    public void onDestroy() {
        reference.removeEventListener(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {

        super.onResume();
        reference.addValueEventListener(this);
    }

    @Override
    public void onStop() {
        reference.removeEventListener(this);
        super.onStop();
    }
}