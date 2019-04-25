package com.misis.brs.Database;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * список всех возможных запросов к DB для объекта Hometask
 */
public interface HometaskDao {
    /**
     * добавляет запись о дз в бд
     * !!!!!!!
     * при уже существующей записи метод вернёт -1
     * @param hometask домашнее задание
     * @return возврашает id добавленного элемента
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Hometask hometask);

    /**
     * обновдение дз
     * @param hometask домашнее задание
     * @return количество обновлённых записей
     */
    @Update
    int update(Hometask hometask);

    /**
        возвращает уже завершившиеся задания функция для фильтра заданий
        @param deadline время для которого мы хотим вернуть все прошедшие записи
        @return список домашних заданий
     */
    @Query("SELECT * FROM hometask WHERE deadline < :deadline")
    Hometask[] selectOverdue(long deadline);

    @Query("SELECT * FROM hometask WHERE semester = :semester")
    Hometask[] selectForSemester(int semester);

    @Query("DELETE FROM hometask WHERE deadline = :deadline")
    void deleteByDeadline(long deadline);

}
