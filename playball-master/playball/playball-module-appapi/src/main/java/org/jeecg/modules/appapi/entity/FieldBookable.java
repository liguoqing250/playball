package org.jeecg.modules.appapi.entity;

import java.util.Date;

public class FieldBookable {
    private String weekDay;
    private String bookableTime;
    private boolean isAvailable;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getBookableTime() {
        return bookableTime;
    }

    public void setBookableTime(String bookableTime) {
        this.bookableTime = bookableTime;
    }
}
