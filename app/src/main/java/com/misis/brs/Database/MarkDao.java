package com.misis.brs.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface MarkDao {
    @Insert
    void insert(Mark mark);

    @Query("DELETE FROM mark WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM mark WHERE semester = :semester")
    Mark[] selectForSemester(int semester);

    @Query("SELECT * FROM mark WHERE semester = :semester AND markType = :markType")
    Mark[] selectForSemesterAndType(int semester, int markType);
}
