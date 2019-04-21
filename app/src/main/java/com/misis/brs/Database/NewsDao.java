package com.misis.brs.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface NewsDao {

    @Insert()
    void insert(News news);

    @Query("DELETE FROM news WHERE dateNews < dateNews")
    void deletebyDateNews(long dateNews);

    @Query("SELECT * FROM NEWS")
    News[] selectAll();
}
