package com.misis.brs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.misis.brs.Database.News;
import com.misis.brs.R;

import java.util.Calendar;
import java.util.Vector;

public class NewsViewAdapter extends BaseAdapter{

    private Context context;
    private Vector<News> news;

    public NewsViewAdapter(Context context, Vector<News> news){
        this.context = context;
        this.news = news;
    }

    //TODO разобраться со временем
    private String getTime(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        return mDay+"."+mMonth+"."+mYear;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_news_simple_item,null);

        ((TextView) view.findViewById(R.id.title)).setText(news.get(position).getHeader());
        ((TextView) view.findViewById(R.id.date)).setText(getTime(news.get(position).getDateNews()));
        ((TextView) view.findViewById(R.id.textNews)).setText(news.get(position).getDescription());

        return view;
    }
}