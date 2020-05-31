package com.example.hp_awareness_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class NewActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    CardView helpline,updates,virtual_fence;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        toolbar=findViewById(R.id.mytoolbar);
        drawerLayout=findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.faq:

                       startActivity(new Intent(NewActivity.this,FaqActivity.class));
                        break;
                    case R.id.helpline:

                        break;
                    case R.id.developers:
                        startActivity(new Intent(NewActivity.this,activity_contributors.class));
                        break;
                    case R.id.settings:
                        Toast.makeText(NewActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });

        helpline = findViewById(R.id.cardView);
        updates = findViewById(R.id.cardView2);
        virtual_fence = findViewById(R.id.cardView3);

        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewActivity.this,HelpMessegeActivity.class));
            }
        });

        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewActivity.this,CovidUpdatesActivity.class));
            }
        });

        virtual_fence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewActivity.this,GeofenceFragActivity.class));
            }
        });

    }
}

