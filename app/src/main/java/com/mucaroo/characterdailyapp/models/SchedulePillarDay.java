package com.mucaroo.characterdailyapp.models;

/**
 * Created by jinxi on 2/28/2017.
 */

public class SchedulePillarDay extends Base {
    public int pillar, day,date;

    public SchedulePillarDay(int pillar, int day) {
        this.pillar = pillar;
        this.day = day;
    }
    public SchedulePillarDay(int date) {
//        this.pillar = pillar;
        this.date = date;
    }
}
