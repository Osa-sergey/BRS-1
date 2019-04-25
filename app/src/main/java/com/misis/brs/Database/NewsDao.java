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

    // при одинаковых текстах новостей обновляем всё остальное
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    /**
     * удаляет новости которые были созданы раньше dateNews даты
     * @param dateNews Время до которого все новости удаляются
     */
    @Query("DELETE FROM news WHERE dateNews < dateNews")
    void deletebyDateNews(long dateNews);

    @Query("SELECT * FROM NEWS")
    News[] selectAll();
}
