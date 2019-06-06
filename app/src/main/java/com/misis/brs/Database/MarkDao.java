package com.misis.brs.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Vector;

/**
 * список всех возможных запросов к DB для объекта Mark
 */
@Dao
public interface MarkDao {

    @Insert
    void insert(Mark mark);

    @Query("DELETE FROM mark WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM mark WHERE semester = :semester")
    Vector<Mark> selectForSemester(int semester);

    @Query("SELECT * FROM mark WHERE semester = :semester AND markType = :markType")
    Vector<Mark> selectForSemesterAndType(int semester, @TypeConverters({Mark.class}) MarkType markType);
}
