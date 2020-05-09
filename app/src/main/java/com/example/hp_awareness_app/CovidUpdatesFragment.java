package com.example.hp_awareness_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CovidUpdatesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Updates, UpdatesViewHolder> firebaseRecyclerAdapter;
    private DatabaseReference ref;

    public CovidUpdatesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_covid_updates, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ref = FirebaseDatabase.getInstance().getReference().child("Updates");
    }

    @Override
    public void onStart() {
        super.onStart();
        String type = MainActivity.type;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Updates, UpdatesViewHolder>(Updates.class,R.layout.updates_row,UpdatesViewHolder.class,ref) {
            @Override
            protected void populateViewHolder(UpdatesViewHolder updatesViewHolder, Updates updates, int i) {


                    updatesViewHolder.setData(updates, v -> {
                        if (Objects.equals(type, "Admin")) {
                            Intent intent = new Intent(getActivity(), UpdateCasesActivity.class);
                            intent.putExtra("update_id", firebaseRecyclerAdapter.getRef(i).getKey());
                            startActivity(intent);
                        }
                    });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UpdatesViewHolder extends RecyclerView.ViewHolder{
        private TextView locationtxt,confirmedtxt,recoveredtxt,deceasedtxt,confirmeddeltatxt,recovereddeltatxt,deceaseddeltatxt;
        public UpdatesViewHolder(@NonNull View itemView) {
            super(itemView);
            locationtxt = itemView.findViewById(R.id.location);
            confirmedtxt = itemView.findViewById(R.id.confirmed);
            confirmeddeltatxt = itemView.findViewById(R.id.confirmed_delta);
            recoveredtxt = itemView.findViewById(R.id.recovered);
            recovereddeltatxt = itemView.findViewById(R.id.recovered_delta);
            deceasedtxt = itemView.findViewById(R.id.deceased);
            deceaseddeltatxt = itemView.findViewById(R.id.deceased_delta);
        }

        void setData(Updates updates, View.OnClickListener clickListener){
            confirmeddeltatxt.setVisibility(View.VISIBLE);
            recovereddeltatxt.setVisibility(View.VISIBLE);
            deceaseddeltatxt.setVisibility(View.VISIBLE);
            locationtxt.setText(String.valueOf(updates.getLocation()));
            confirmedtxt.setText(String.valueOf(updates.getConfirmed()));
            recoveredtxt.setText(String.valueOf(updates.getRecovered()));
            deceasedtxt.setText(String.valueOf(updates.getDeceased()));
            confirmeddeltatxt.setText(delta(updates.getConfirmedDelta()));
            recovereddeltatxt.setText(delta(updates.getRecoveredDelta()));
            deceaseddeltatxt.setText(delta(updates.getDeceasedDelta()));

            itemView.setOnClickListener(clickListener);
        }


        private String delta(int n){
            if(n>0)
                return "↑ "+n;
            else if(n<0)
                return "↓ "+n;
            return ""+n;
        }
    }
}
