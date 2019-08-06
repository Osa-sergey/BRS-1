package com.misis.brs.Fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
        getNews();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);

       //изменение toolbar
        ((TextView)getActivity().findViewById(R.id.toolbarText)).setText(R.string.news_toolbar);
        ((Spinner)getActivity().findViewById(R.id.semester_picker)).setVisibility(View.INVISIBLE);

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
        //получаем новости
        getNews();
        return view;
    }

    private void getNews() {
        long curDate = TimeHelper.currentTime()/1000;

        //удаляем новости со времени публикации которых прошло больше недели
        DBHelper.deleteNewsByDateNews(curDate-604800);

        News[] bdNews = DBHelper.selectAllNews();
        Vector<News> news = new Vector<>();
        if (bdNews != null) {
            news = new Vector<>(Arrays.asList(bdNews));
        }
        //Загружаем сохранённые новости
        NewsViewAdapter.setNews(news);
        newsViewAdapter.notifyDataSetChanged();
        //Скачиваем новости
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Vector<News> news = new Vector<>();
                try {
                    //TODO добавить адресс
                    URL url = new URL("");
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    //убираем namespace xml
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();

                    //выдаём поток данных
                    xpp.setInput(getInputStream(url),"UTF_8");
                    boolean insideItem = false;
                    //тип эвента при чтении
                    int eventType = xpp.getEventType();
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        News newsItem = new News();
                        if(eventType == XmlPullParser.START_TAG){
                            if(xpp.getName().equalsIgnoreCase("item")){
                                insideItem = true;
                            //TODO сменить теги
                            }else if(xpp.getName().equalsIgnoreCase("title") && insideItem){
                                newsItem.setHeader(xpp.nextText());
                            }else if(xpp.getName().equalsIgnoreCase("text") && insideItem){
                                newsItem.setDescription(xpp.nextText());
                            }else if(xpp.getName().equalsIgnoreCase("time") && insideItem){
                                newsItem.setDateNews(Long.getLong(xpp.nextText()));
                            }
                            //выходим из элемента
                        }else if(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                            insideItem = false;
                        }
                        //переходим на следующий эвент
                        eventType = xpp.next();
                        news.add(newsItem);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                //обновляем данные
                NewsViewAdapter.setNews(news);
                newsViewAdapter.notifyDataSetChanged();
                return null;
            }
        };
        //выполняем таск
        task.execute();

    }

    private InputStream getInputStream(URL url){
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
