package com.misis.brs.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * список всех возможных запросов к DB для объекта Mark
 */
@Dao
public interface MarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mark mark);

    @Query("DELETE FROM mark WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM mark WHERE semester = :semester")
    Mark[] selectForSemester(int semester);

    @Query("SELECT * FROM mark WHERE semester = :semester AND markType = :markType")
    Mark[] selectForSemesterAndType(int semester, int markType);
}
