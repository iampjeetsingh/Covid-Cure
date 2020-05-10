package com.example.hp_awareness_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArticleAdapter extends PagerAdapter {

    Context context;
    Activity activity;
    List<Article> articleList;
    LayoutInflater inflator;

    String url;

    public ArticleAdapter(Context context, List<Article> articleList) {
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
        ((ViewPager) container).removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //inflate View
        View view = inflator.inflate(R.layout.article_item, container, false);

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat hrFormat = new SimpleDateFormat("hh");
        Date now = Calendar.getInstance().getTime();
        int dayDelta = Math.abs(Integer.parseInt(dayFormat.format(now)) - Integer.parseInt(dayFormat.format(articleList.get(position).getTimeStamp())));
        int hrDelta = Math.abs(Integer.parseInt(hrFormat.format(now)) - Integer.parseInt(hrFormat.format(articleList.get(position).getTimeStamp())));
        String timeText = dayDelta == 0 ? hrDelta + " hr" + getSuffix(hrDelta) : dayDelta + " day" + getSuffix(dayDelta);
        //View
        WebView web = (WebView) view.findViewById(R.id.newsWeb);

        url = articleList.get(position).getLink();
        web.loadUrl(url);


        //Event


        container.addView(view);
        return view;
    }

    private String getSuffix(int n) {
        return n == 1 ? "" : "s";
    }
}