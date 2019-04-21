package com.misis.brs.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.ExecutionException;

public class DBHelper {
    private static DBHelper instance;
    private AppDatabase db;
    private static String nameofDB;

    private DBHelper(Context context, String nameofDB){
        DBHelper.nameofDB = nameofDB;
        db = Room.databaseBuilder(context,AppDatabase.class,nameofDB).build();
    }

    public static DBHelper getInstance(Context context, String nameofDB){
        if(instance == null || !nameofDB.equals(DBHelper.nameofDB)){
            instance = new DBHelper(context, nameofDB);
        }
        return instance;
    }

    public static void insertMark(final Mark mark){
        AsyncTask<Mark, Void, Void> task = new AsyncTask<Mark, Void, Void>() {
            @Override
            protected Void doInBackground(Mark... marks) {
                instance.db.markDao().insert(mark);
                return null;
            }
        };
        task.execute(mark);
    }

    public static void deleteMarkById(final long id){
        AsyncTask<Long, Void, Void> task = new AsyncTask<Long, Void, Void>() {
            @Override
            protected Void doInBackground(Long... longs) {
                instance.db.markDao().deleteById(id);
                return null;
            }
        };
        task.execute(id);
    }

    public static Mark[] selectMarksForSemester(final int semester){
        AsyncTask<Integer, Void, Mark[]> task = new AsyncTask<Integer, Void, Mark[]>() {
            @Override
            protected Mark[] doInBackground(Integer... integers) {
                return instance.db.markDao().selectForSemester(semester);
            }
        };
        task.execute(semester);
        try{
            return task.get();
        } catch (InterruptedException e) {
            Log.e("selectMarksForSemester", e.toString());
        } catch (ExecutionException e) {
            Log.e("selectMarksForSemester", e.toString());
        } return null;
    }

    public  static Mark[] selectMarksForSemesterAndType(final int semester, final int markType){
        AsyncTask<Integer, Void, Mark[]> task = new AsyncTask<Integer, Void, Mark[]>() {
            @Override
            protected Mark[] doInBackground(Integer... integers) {
                return instance.db.markDao().selectForSemesterAndType(integers[0],integers[1]);
            }
        };
        task.execute(semester,markType);
        try{
            return task.get();
        } catch (InterruptedException e) {
            Log.e("selectMarksForSem&Type", e.toString());
        } catch (ExecutionException e) {
            Log.e("selectMarksForSem&Type", e.toString());
        }   return null;
    }

    public static void insertNews(final News news1){
        AsyncTask<News, Void, Void> task = new AsyncTask<News, Void, Void>() {
            @Override
            protected Void doInBackground(News... news) {
                instance.db.newsDao().insert(news1);
                return null;
            }
        };
        task.execute(news1);
    }

    public static void deleteNewsbyDateNews(final long dateNews){
        AsyncTask<Long, Void, Void> task = new AsyncTask<Long, Void, Void>() {
            @Override
            protected Void doInBackground(Long... longs) {
                instance.db.newsDao().deletebyDateNews(dateNews);
                return null;
            }
        };
        task.execute(dateNews);
    }

    public static News[] selectAllNews(){
        AsyncTask<Void, Void, News[]> task = new AsyncTask<Void, Void, News[]>() {
            @Override
            protected News[] doInBackground(Void... voids) {
                return instance.db.newsDao().selectAll();
            }
        };
        task.execute();
        try{
            return task.get();
        } catch (InterruptedException e) {
            Log.e("selectAllNews", e.toString());
        } catch (ExecutionException e) {
            Log.e("selectAllNews", e.toString());
        }return null;
    }
}
