package com.misis.brs.Database;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * список всех возможных запросов к DB для объекта Hometask
 */
public interface HometaskDao {
    /**
     * использовать как для добавдения новой записи, так и для обновления старой
     * @param hometask домашнее задание
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Hometask hometask);

    @Query("SELECT * FROM hometask WHERE deadline = :deadline")
    Hometask selectByDeadline (long deadline);

    @Query("SELECT * FROM hometask WHERE deadline < :deadline")
    Hometask[] selectOverdue(long deadline);

    @Query("SELECT * FROM hometask WHERE semester = :semester")
    Hometask[] selectForSemester(int semester);

    @Query("DELETE FROM hometask WHERE deadline = :deadline")
    void deleteByDeadline(long deadline);

}
