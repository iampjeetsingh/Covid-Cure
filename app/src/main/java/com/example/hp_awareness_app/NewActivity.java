package com.example.hp_awareness_app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    CardView cardView;
    CardView cardView3;
    CardView helpCard;
    ImageView bell;
    TextView msg;
    Button back;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        toolbar = findViewById(R.id.mytoolbar);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.faq:

                        startActivity(new Intent(NewActivity.this, FaqActivity.class));
                        break;
                    case R.id.helpline:

                        break;
                    case R.id.developers:
                        startActivity(new Intent(NewActivity.this, activity_contributors.class));
                        break;
                    case R.id.settings:
                        Toast.makeText(NewActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });

        cardView = findViewById(R.id.cardViewUpdate);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, CasesFragment.class);
                startActivity(intent);
            }
        });

        cardView3 = findViewById(R.id.cardViewfence);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, GeofenceActivity.class);
                startActivity(intent);
            }
        });

        helpCard = findViewById(R.id.helpCard3);
        helpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, HelpMessegeActivity.class);
                startActivity(intent);
            }
        });

        bell = findViewById(R.id.msgBell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog();

            }
        });

    }

    private void OpenDialog() {
        Dialog dialog = new Dialog(NewActivity.this);
        dialog.setContentView(R.layout.msg_dialog);
        msg = dialog.findViewById(R.id.adminMsg);
        back = dialog.findViewById(R.id.backBtn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uiD = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("User").child(uiD);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.child("Message").getValue().toString();
                if (message != null) {
                    msg.setText(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
    public void adminDashClick(View v){
        startActivity(new Intent(NewActivity.this,AdminDashboard.class));
    }
}

