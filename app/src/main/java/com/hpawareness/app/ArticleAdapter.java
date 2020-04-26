package com.hpawareness.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public abstract class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> implements ChildEventListener {

    private int total = 0;
    private HashMap<String, Integer> indexMap;
    private ArrayList<String> ids;
    private HashMap<String, Article> articleMap;

    public ArticleAdapter(DatabaseReference reference) {
        indexMap = new HashMap<>();
        ids = new ArrayList<>();
        articleMap = new HashMap<>();
        reference.orderByChild("timeStamp").limitToLast(5).addChildEventListener(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        String key = position<ids.size() ? ids.get(position) : null;
        Article article = key!=null && articleMap.containsKey(key) ? articleMap.get(key) : null;
        populateViewHolder(holder,article,position);
    }

    public abstract void populateViewHolder(ArticleViewHolder holder, Article article, int position);

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return total;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Article article = dataSnapshot.getValue(Article.class);
        if(article!=null){
            String key = article.getID();
            indexMap.put(key, total);
            articleMap.put(key, article);
            ids.add(key);
            notifyItemInserted(total);
            total += 1;
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Article article = dataSnapshot.getValue(Article.class);
        if(article!=null){
            String key = article.getID();
            if(indexMap.containsKey(key)){
                int index = indexMap.get(key);
                articleMap.put(key, article);
                notifyItemChanged(index);
            }
        }
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        Article article = dataSnapshot.getValue(Article.class);
        if(article!=null){
            String key = article.getID();
            if(indexMap.containsKey(key)){
                int index = indexMap.get(key);
                indexMap.remove(key);
                articleMap.remove(key);
                ids.remove(index);
                notifyItemRemoved(index);
                total -= 1;
            }
        }
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        /**Article article = dataSnapshot.getValue(Article.class);
        if(article!=null){String key = article.getID();
            if(indexMap.containsKey(key)){
                int index = indexMap.get(key);
                indexMap.remove(key);
                articleMap.remove(key);
                notifyItemRemoved(index);
                notifyItemMoved();
            }
        }**/
    }

    @Override
    public abstract void onCancelled(@NonNull DatabaseError databaseError);

    public static class ArticleViewHolder extends RecyclerView.ViewHolder{

        private TextView source, headLine, time;
        private ImageView thumbnail;
        private View view;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            source = itemView.findViewById(R.id.source);
            headLine = itemView.findViewById(R.id.headline);
            time = itemView.findViewById(R.id.time);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }

        public void hide(){
            view.setVisibility(View.GONE);
        }

        public void setArticle(Article article){
            view.setVisibility(View.VISIBLE);
            source.setText("Source : "+article.getSource());
            headLine.setText(article.getHeadLine());
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            SimpleDateFormat hrFormat = new SimpleDateFormat("hh");
            Date now = Calendar.getInstance().getTime();
            int dayDelta = Math.abs(Integer.parseInt(dayFormat.format(now))-Integer.parseInt(dayFormat.format(article.getTimeStamp())));
            int hrDelta = Math.abs(Integer.parseInt(hrFormat.format(now))-Integer.parseInt(hrFormat.format(article.getTimeStamp())));
            String timeText = dayDelta==0 ? hrDelta+" hr"+getSuffix(hrDelta) : dayDelta+" day"+getSuffix(dayDelta);
            time.setText(timeText+" ago");
            if(article.getThumbnail()!=null){
                thumbnail.setVisibility(View.VISIBLE);
                Glide.with(thumbnail.getContext())
                        .load(article.getThumbnail())
                        .into(thumbnail);
            }else{
                thumbnail.setVisibility(View.GONE);
            }
        }

        private String getSuffix(int n){
            return n==1 ? "" : "s";
        }

        public void setOnClickListener(View.OnClickListener listener){
            view.setOnClickListener(listener);
        }
    }
}
