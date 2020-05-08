package com.example.hp_awareness_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArticleAdapter extends PagerAdapter {

    Context context;
    Activity activity;
    List<Article> articleList;
    LayoutInflater inflator;

    public ArticleAdapter(Context context,List<Article> articleList){
        this.context = context;
        this.articleList = articleList;
        inflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //inflate View
        View view = inflator.inflate(R.layout.article_item,container,false);

        //View
        ImageView article_image = (ImageView)view.findViewById(R.id.news_image);
        TextView article_title = (TextView)view.findViewById(R.id.news_title);
        TextView article_time = (TextView)view.findViewById(R.id.news_time);
        Button btnView = (Button)view.findViewById(R.id.btnView);
        TextView article_source = (TextView)view.findViewById(R.id.news_source);
        //        FloatingActionButton

        //Time Calculation
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat hrFormat = new SimpleDateFormat("hh");
        Date now = Calendar.getInstance().getTime();
        int dayDelta = Math.abs(Integer.parseInt(dayFormat.format(now))-Integer.parseInt(dayFormat.format(articleList.get(position).getTimeStamp())));
        int hrDelta = Math.abs(Integer.parseInt(hrFormat.format(now))-Integer.parseInt(hrFormat.format(articleList.get(position).getTimeStamp())));
        String timeText = dayDelta==0 ? hrDelta+" hr"+getSuffix(hrDelta) : dayDelta+" day"+getSuffix(dayDelta);

        //set Data
        Picasso.get().load(articleList.get(position).getThumbnail()).into(article_image);
        article_title.setText(articleList.get(position).getHeadLine());
        article_time.setText(timeText+" ago");
        article_source.setText("Source: "+articleList.get(position).getSource());

        //Event
        btnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //show news activity
                Intent intent = new Intent(context, ViewArticle.class);
                intent.putExtra("url", articleList.get(position).getLink());
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }
    private String getSuffix(int n){
        return n==1 ? "" : "s";
    }
}