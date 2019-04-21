package com.misis.brs.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * список всех возможных запросов к DB для объекта News
 */
@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    @Query("DELETE FROM news WHERE dateNews < dateNews")
    void deletebyDateNews(long dateNews);

    @Query("SELECT * FROM NEWS")
    News[] selectAll();
}
