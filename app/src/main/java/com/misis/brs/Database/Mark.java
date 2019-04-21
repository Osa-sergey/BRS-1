package com.misis.brs.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Mark {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int markType;
    private int semester;
    private int mark;
    private int maxMark;
    private String description;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(int maxMark) {
        this.maxMark = maxMark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
