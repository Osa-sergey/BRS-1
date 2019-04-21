package com.misis.brs.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Hometask {
    @PrimaryKey()
    private long deadline;
    private String description;
    private Boolean checkDone;
    private int semester;
    private Boolean checkNotify;
    private long timeNotification;

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCheckDone() {
        return checkDone;
    }

    public void setCheckDone(Boolean checkDone) {
        this.checkDone = checkDone;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Boolean getCheckNotify() {
        return checkNotify;
    }

    public void setCheckNotify(Boolean checkNotify) {
        this.checkNotify = checkNotify;
    }

    public long getTimeNotification() {
        return timeNotification;
    }

    public void setTimeNotification(long timeNotification) {
        this.timeNotification = timeNotification;
    }
}
