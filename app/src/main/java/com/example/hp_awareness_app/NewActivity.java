package com.example.hp_awareness_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class NewActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    CardView helpline,updates,virtual_fence;
    static String type = null;
    TextView user;
    ActionBarDrawerToggle actionBarDrawerToggle;
    HashMap<String, Object> map;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        user = findViewById(R.id.user);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                map = (HashMap<String, Object>) dataSnapshot.getValue();
                if (map != null) {
                    user.setText(String.valueOf(map.get("name")));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserName",databaseError.getMessage());
            }
        });


        toolbar=findViewById(R.id.mytoolbar);
        drawerLayout=findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        type = getIntent().getStringExtra("type");
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.faq:

                   startActivity(new Intent(NewActivity.this,FaqActivity.class));
                    break;
                case R.id.helpline:
                    startActivity(new Intent(NewActivity.this,HelpMessegeActivity.class));
                    break;
                case R.id.developers:
                    startActivity(new Intent(NewActivity.this,activity_contributors.class));
                    break;
                case R.id.edit_prifile:
                    startActivity(new Intent(NewActivity.this,RegisterActivity.class));
                    break;
                case R.id.settings:
                    Toast.makeText(NewActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(NewActivity.this, PhoneActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                    break;
            }

            return false;
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
