package com.misis.brs.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.misis.brs.Adapters.NewsViewAdapter;
import com.misis.brs.Database.News;
import com.misis.brs.R;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);

       //изменение toolbar
//        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText(R.string.news_toolbar);
//        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.INVISIBLE);

        recyclerView = (RecyclerView) view.findViewById(R.id.news_list);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        NewsViewAdapter newsViewAdapter = new NewsViewAdapter();

        News news = new News();
        news.setHeader("Title");
        news.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ut volutpat turpis, et scelerisque quam. Vivamus maximus, eros ut congue aliquet, mauris neque ultricies ex, ac gravida nisi ...");
        news.setDateNews(new Long("1460332800000"));
        newsViewAdapter.setItems(news);

        recyclerView.setAdapter(newsViewAdapter);
        return view;
    }

    public void methodsetFragment(){

    }
}
