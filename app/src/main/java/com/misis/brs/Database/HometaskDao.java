package com.misis.brs.Database;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

public interface HometaskDao {

    @Insert
    void insert(Hometask hometask);

    @Query("SELECT * FROM hometask WHERE deadline = :deadline")
    Hometask selectByDeadline (long deadline);

    @Query("SELECT * FROM hometask WHERE deadline < :deadline")
    Hometask[] selectOverdue(long deadline);

    @Query("SELECT * FROM hometask WHERE semester = :semester")
    Hometask[] selectForSemester(int semester);

    @Query("DELETE FROM hometask WHERE deadline = :deadline")
    void deleteByDeadline(long deadline);

    @Query("UPDATE hometask SET description = :description, " +
            "checkDone = :checkDone, checkNotify = :checkNotify," +
            " timeNotification = :timeNotification WHERE deadline = :deadline")
    void updateByDeadline(long deadline, String description, Boolean checkDone, Boolean checkNotify, long timeNotification);
}
