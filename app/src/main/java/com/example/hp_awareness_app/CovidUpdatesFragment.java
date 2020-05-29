package com.example.hp_awareness_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.toolbox.StringRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidUpdatesFragment extends Fragment {
    private RecyclerView recyclerView;
   // private FirebaseRecyclerAdapter<Updates, UpdatesViewHolder> firebaseRecyclerAdapter;
    private DatabaseReference ref;

    List<ModelCovidUpdate>updatesList=new ArrayList<>();

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.covid19india.org/state_district_wise.json";


    public CovidUpdatesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_covid_updates, container, false);
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        TextView total_confirmed = view.findViewById(R.id.text_total_confirmed);
        TextView bilaspur=view.findViewById(R.id.locationbilaspur);
        TextView bilaspur_active=view.findViewById(R.id.activebilas);
        TextView bilaspur_recovered=view.findViewById(R.id.recoveredbilas);

        TextView total_active = view.findViewById(R.id.text_total_active);
        TextView total_deceased = view.findViewById(R.id.text_total_deceased);
        TextView total_recovered = view.findViewById(R.id.text_total_recovered);
        CovidApiService service = RetrofitclientInstance.getRetrofitInstance().create(CovidApiService.class);
        Call<ModelCovidCase> call = service.getStatus(BASE_URL);
        call.enqueue(new Callback<ModelCovidCase>() {
            @Override
            public void onResponse(Call<ModelCovidCase> call, Response<ModelCovidCase> response) {
                if(response.isSuccessful()){
                    Log.e("SS","success");

                    //set corrresponding texts

                    bilaspur.setText("Bilaspur");
//                    Log.e("stat",String.valueOf(response.body().getHimachalPradesh().getDistrictData().getBilaspur().getActive()));
                    bilaspur_active.setText(""+response.body().getHimachalPradesh().getDistrictData().getBilaspur().getActive());
                    bilaspur_recovered.setText(""+response.body().getHimachalPradesh().getDistrictData().getBilaspur().getRecovered());






                }

            }

            @Override
            public void onFailure(Call<ModelCovidCase> call, Throwable t) {
                Log.e("ff",t.getMessage());

            }
        });








        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Updates");



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int confirmed = 0;
                int active = 0;
                int recovered = 0;
                int deceased = 0;



                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Updates updates = snapshot.getValue(Updates.class);

                    confirmed += updates.getConfirmed();
                    active += updates.getActive();
                    recovered += updates.getRecovered();
                    deceased += updates.getDeceased();

                }

                total_active.setText(Integer.toString(active));
                total_confirmed.setText(Integer.toString(confirmed));
                total_deceased.setText(Integer.toString(deceased));
                total_recovered.setText(Integer.toString(recovered));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 3000);

        return view;
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ref = FirebaseDatabase.getInstance().getReference().child("Updates");
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        String type = MainActivity.type;
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Updates, UpdatesViewHolder>(Updates.class, R.layout.updates_row, UpdatesViewHolder.class, ref) {
//            @Override
//            protected void populateViewHolder(UpdatesViewHolder updatesViewHolder, Updates updates, int i) {
//                updatesViewHolder.setData(updates, v -> {
//                    if (Objects.equals(type, "Admin")) {
//                        Intent intent = new Intent(getActivity(), UpdateCasesActivity.class);
//                        intent.putExtra("update_id", firebaseRecyclerAdapter.getRef(i).getKey());
//                        startActivity(intent);
//                    }
//                });
//            }
//        };
//        recyclerView.setAdapter(firebaseRecyclerAdapter);
//    }
//
//    public static class UpdatesViewHolder extends RecyclerView.ViewHolder {
//        private TextView locationtxt, confirmedtxt, recoveredtxt, deceasedtxt, activetxt, confirmeddeltatxt, recovereddeltatxt, deceaseddeltatxt, activedeltatxt;
//
//        public UpdatesViewHolder(@NonNull View itemView) {
//            super(itemView);
//            locationtxt = itemView.findViewById(R.id.location);
//            confirmedtxt = itemView.findViewById(R.id.confirmed);
//            confirmeddeltatxt = itemView.findViewById(R.id.confirmed_delta);
//            recoveredtxt = itemView.findViewById(R.id.recovered);
//            recovereddeltatxt = itemView.findViewById(R.id.recovered_delta);
//            deceasedtxt = itemView.findViewById(R.id.deceased);
//            deceaseddeltatxt = itemView.findViewById(R.id.deceased_delta);
//            activetxt = itemView.findViewById(R.id.active);
//            activedeltatxt = itemView.findViewById(R.id.active_delta);
//        }
//
//        public void setData(Updates updates, View.OnClickListener clickListener) {
//            confirmeddeltatxt.setVisibility(View.VISIBLE);
//            recovereddeltatxt.setVisibility(View.VISIBLE);
//            deceaseddeltatxt.setVisibility(View.VISIBLE);
//            activedeltatxt.setVisibility(View.VISIBLE);
//            locationtxt.setText(String.valueOf(updates.getLocation()));
//            confirmedtxt.setText(String.valueOf(updates.getConfirmed()));
//            recoveredtxt.setText(String.valueOf(updates.getRecovered()));
//            deceasedtxt.setText(String.valueOf(updates.getDeceased()));
//            activetxt.setText(String.valueOf(updates.getActive()));
//            confirmeddeltatxt.setText(delta(updates.getConfirmedDelta()));
//            recovereddeltatxt.setText(delta(updates.getRecoveredDelta()));
//            deceaseddeltatxt.setText(delta(updates.getDeceasedDelta()));
//            activedeltatxt.setText(delta(updates.getActiveDelta()));
//
//            itemView.setOnClickListener(clickListener);
//        }
//
//        private String delta(int n) {
//            if (n > 0)
//                return "↑ " + n;
//            else if (n < 0)
//                return "↓ " + n;
//            return "" + n;
//        }
//    }
}

