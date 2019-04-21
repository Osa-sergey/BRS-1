package com.misis.brs.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * класс предназначен для обращения к DB
 * класс реализован с использованием паттерна одиночка
 * для начала работы надо вызвать метод getInstance в MainActivity
 */
public class DBHelper {
    private static DBHelper instance;
    private AppDatabase db;
    private static String nameofDB;

    /**
     * реализует паттерн одиночка
     * @param context контекст приложения
     * @param nameofDB фамилия и имя авторизованного пользователя в формате конкатенации
     */
    private DBHelper(Context context, String nameofDB){
        DBHelper.nameofDB = nameofDB;
        db = Room.databaseBuilder(context,AppDatabase.class,nameofDB).build();
    }

    public static DBHelper getInstance(Context context, String nameofDB){
        if(instance == null || !nameofDB.equals(DBHelper.nameofDB)){ //обновляем каждый раз, как меняем пользователя
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

    public static Mark[] selectMarksForSemesterAndType(final int semester, final int markType){
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

    public static void insertHometask(final Hometask hometask){
        AsyncTask<Hometask, Void, Void> task = new AsyncTask<Hometask, Void, Void>() {
            @Override
            protected Void doInBackground(Hometask... hometasks) {
                instance.db.hometaskDao().insert(hometask);
                return null;
            }
        };
        task.execute(hometask);
    }

    public static Hometask selectHometaskByDeadline(final Long deadline){
        AsyncTask<Long, Void, Hometask> task = new AsyncTask<Long, Void, Hometask>() {
            @Override
            protected Hometask doInBackground(Long... longs) {
                return instance.db.hometaskDao().selectByDeadline(deadline);
            }
        };
        task.execute(deadline);
        try{
            task.get();
        } catch (InterruptedException e) {
            Log.e("selectTaskByDeadline", e.toString());
        } catch (ExecutionException e) {
            Log.e("selectTaskByDeadline", e.toString());
        }return null;
    }

    public static Hometask[] selectOverdueHometask(final Long deadline){
        AsyncTask<Long, Void, Hometask[]> task = new AsyncTask<Long, Void, Hometask[]>() {
            @Override
            protected Hometask[] doInBackground(Long... longs) {
                return instance.db.hometaskDao().selectOverdue(deadline);
            }
        };
        task.execute(deadline);
        try{
            task.get();
        } catch (InterruptedException e) {
            Log.e("selectOverdueHometask", e.toString());
        } catch (ExecutionException e) {
            Log.e("selectOverdueHometask", e.toString());
        }return null;
    }

    public static Hometask[] selectHometaskForSemester(final int semester){
        AsyncTask<Integer, Void, Hometask[]> task = new AsyncTask<Integer, Void, Hometask[]>() {
            @Override
            protected Hometask[] doInBackground(Integer... integers) {
                return instance.db.hometaskDao().selectForSemester(semester);
            }
        };
        task.execute(semester);
        try{
            task.get();
        } catch (InterruptedException e) {
            Log.e("selectTaskForSemester", e.toString());
        } catch (ExecutionException e) {
            Log.e("selectTaskForSemester", e.toString());
        }return null;
    }

    public static void deleteHometaskByDeadline(final long deadline){
        AsyncTask<Long, Void, Void> task = new AsyncTask<Long, Void, Void>() {
            @Override
            protected Void doInBackground(Long... longs) {
                instance.db.hometaskDao().deleteByDeadline(deadline);
                return null;
            }
        };
        task.execute(deadline);
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
