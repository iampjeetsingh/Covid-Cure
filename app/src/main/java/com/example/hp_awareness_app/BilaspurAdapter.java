package com.example.hp_awareness_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import java.util.List;

public class BilaspurAdapter extends RecyclerView.Adapter<BilaspurAdapter.CaseVH> {

    public static final String TAG = "Bilaspur Adapter";
    List<DistrictCases> districtCases;
    Context context;

    public BilaspurAdapter(List<DistrictCases> districtCases, Context context) {
        this.districtCases = districtCases;
        this.context = context;
    }

    @NonNull
    @Override
    public CaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cases_content, parent, false);
        return new CaseVH(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseVH holder, int position) {
        DistrictCases districtCase = districtCases.get(position);
        holder.act.setText("" + districtCase.getActive());
        holder.rec.setText("" + districtCase.getRecvr());
        holder.conf.setText("" + districtCase.getConfrm());
        holder.death.setText("" + districtCase.getDeath());
        holder.name.setText("" + districtCase.getDistrictName());
        boolean isExpanded = districtCases.get(position).isExpanded();
        holder.cardView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return districtCases.size();
    }

    class CaseVH extends RecyclerView.ViewHolder {
        private static final String TAG = "CaseVH";
        CardView cardView;
        TextView conf, act, rec, death, name;
        Button btn;
        CardView cardTitle;

        public CaseVH(@NonNull final View itemView, Context context) {

            super(itemView);
            name = (TextView) itemView.findViewById(R.id.districtName);
            conf = (TextView) itemView.findViewById(R.id.bilconfNo);
            act = (TextView) itemView.findViewById(R.id.bilActiveNo);
            rec = (TextView) itemView.findViewById(R.id.bilRecNo);
            death = (TextView) itemView.findViewById(R.id.bilDeathNo);
            cardView = (CardView) itemView.findViewById(R.id.bilaspurCard);
           // btn = (Button) itemView.findViewById(R.id.buttonExpand);
            cardTitle = (CardView) itemView.findViewById(R.id.cardViewTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DistrictCases districtCase = districtCases.get(getAdapterPosition());
                    districtCase.setExpanded(!districtCase.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }

    }
}
