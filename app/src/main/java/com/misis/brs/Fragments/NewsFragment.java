package com.misis.brs.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.misis.brs.Adapters.NewsViewAdapter;
import com.misis.brs.Database.DBHelper;
import com.misis.brs.Database.News;
import com.misis.brs.MainActivity;
import com.misis.brs.R;
import com.misis.brs.TimeHelper;

import java.util.Arrays;
import java.util.Vector;

public class NewsFragment extends Fragment {

    private NewsViewAdapter newsViewAdapter;
    private ListView listView;
    private Vector<News> news;

    @Override
    public void onResume() {
        super.onResume();
        //обновляем новости
        news = getNews();
        NewsViewAdapter.setNews(news);
        newsViewAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);

       //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText(R.string.news_toolbar);
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.INVISIBLE);

        news = getNews();

        newsViewAdapter = new NewsViewAdapter(getActivity(),news);
        listView =(ListView) view.findViewById(R.id.news_list);
        listView.setAdapter(newsViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsViewFragment newsViewFragment = new NewsViewFragment();
                Bundle args = new Bundle();
                args.putString("title",news.elementAt(position).getHeader());
                args.putLong("date", news.elementAt(position).getDateNews());
                args.putString("text",news.elementAt(position).getDescription());
                newsViewFragment.setArguments(args);

                ((MainActivity) getActivity()).replaceFragment(R.id.themaincontainer,newsViewFragment);
            }
        });
        return view;
    }

    private Vector<News> getNews() {
        long curDate = TimeHelper.currentTime()/1000;

        //удаляем новости со времени публикации которых прошло больше недели
        DBHelper.deleteNewsByDateNews(curDate-604800);

        //TODO фи-я для подгрузки новостей

        News[] bdNews = DBHelper.selectAllNews();
        Vector<News> news = new Vector<>();
        if (bdNews != null) {
            news = new Vector<>(Arrays.asList(bdNews));
        }
        return news;
    }

}
