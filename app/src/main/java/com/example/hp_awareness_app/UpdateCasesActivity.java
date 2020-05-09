package com.example.hp_awareness_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateCasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cases);

        String update_id = getIntent().getStringExtra("update_id");

        TextView textLocation = findViewById(R.id.textLocation);
        EditText editDeceased = findViewById(R.id.editDeceased);
        EditText editRecovered = findViewById(R.id.editRecovered);
        EditText editRecoveredDelta = findViewById(R.id.editRecoveredDelta);
        EditText editDeceasedDelta = findViewById(R.id.editDeceasedDelta);
        EditText editConfirmed = findViewById(R.id.editConfirmed);
        EditText editConfirmedDelta = findViewById(R.id.editConfirmedDelta);
        EditText editActive = findViewById(R.id.editActive);
        EditText editActiveDelta = findViewById(R.id.editActiveDelta);
        Button btnUpdate = findViewById(R.id.btnUpdate);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Updates").child(update_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Updates updates = dataSnapshot.getValue(Updates.class);

                String location = updates.getLocation();
                int deceased = updates.getDeceased();
                int recovered = updates.getRecovered();
                int deceasedDelta = updates.getDeceasedDelta();
                int recoveredDelta = updates.getRecoveredDelta();
                int confirmed = updates.getConfirmed();
                int confirmedDelta = updates.getConfirmedDelta();
                int active = updates.getActive();
                int activeDelta = updates.getActiveDelta();

                textLocation.setText(location);
                editDeceased.setText(Integer.toString(deceased));
                editRecovered.setText(Integer.toString(recovered));
                editConfirmed.setText(Integer.toString(confirmed));
                editActive.setText(Integer.toString(active));
                editRecoveredDelta.setText(Integer.toString(recoveredDelta));
                editConfirmedDelta.setText(Integer.toString(confirmedDelta));
                editDeceasedDelta.setText(Integer.toString(deceasedDelta));
                editActiveDelta.setText(Integer.toString(activeDelta));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Updates updatedCase = new Updates();
                updatedCase.setLocation(textLocation.getText().toString());
                updatedCase.setConfirmed(Integer.parseInt(editConfirmed.getText().toString()));
                updatedCase.setRecovered(Integer.parseInt(editRecovered.getText().toString()));
                updatedCase.setDeceased(Integer.parseInt(editDeceased.getText().toString()));
                updatedCase.setConfirmedDelta(Integer.parseInt(editConfirmedDelta.getText().toString()));
                updatedCase.setRecoveredDelta(Integer.parseInt(editRecoveredDelta.getText().toString()));
                updatedCase.setDeceasedDelta(Integer.parseInt(editDeceasedDelta.getText().toString()));
                updatedCase.setActive(Integer.parseInt(editActive.getText().toString()));
                updatedCase.setActiveDelta(Integer.parseInt(editActiveDelta.getText().toString()));

                reference.setValue(updatedCase)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Write was successful!
                                finish();
                                // ...
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                // ...
                            }
                        });

            }
        });
    }
}
