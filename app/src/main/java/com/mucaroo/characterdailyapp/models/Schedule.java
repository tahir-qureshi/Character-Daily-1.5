package com.mucaroo.characterdailyapp.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mucaroo.characterdailyapp.activities.ScheduleDate;
import com.mucaroo.characterdailyapp.annotations.DBInclude;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.abs;

/**
 * Created by jinxi on 2/27/2017.
 */

public class Schedule extends Base {

    //zaman khan is edit
    @DBInclude
    public ArrayList<Long> pillars = new ArrayList<>();

    //zaman khan is edit
    @DBInclude
    public ArrayList<ArrayList<String> > behaviors = new ArrayList<ArrayList<String>>();

    public SchedulePillarDay getClosestPillar() {
        int pillar = 0, n = 0;
        Date now = new Date();
        long nowt = now.getTime();
        long closest = -1 * nowt;
        for(Long t : pillars) {
            long diff = (t*1000) - nowt;
            Log.i("SCHEDULE", "PILLAR: " + new Date(t*1000).toString() + " VERSUS " + now.toString() + " DIFF: " + diff );
            if(diff > closest && diff <= 0) {
                closest = diff;
                pillar = n;
            }
            n++;
        }//
        long day = (-1 * closest) / (1000 * 60 * 60 * 24);
        Log.d("Schedule", "DAY: " + day);

        SchedulePillarDay s = new SchedulePillarDay(pillar, (int)day);
        //Log.d("najam", s.toString());
        return s;
      //  return new SchedulePillarDay(pillar, (int)day);
    }
//    public ScheduleDate ScheduleDate() {
//        int pillar = 0, n = 0;
//        Date now = new Date();
//        long nowt = now.getTime();
//        long closest = -1 * nowt;
//      //  for(Long t : Pillars) {
//          //  long diff = (t*1000) - nowt;
//            //Log.i("SCHEDULE", "PILLAR: " + new Date(t*1000).toString() + " VERSUS " + now.toString() + " DIFF: " + diff );
//          //  if(diff > closest && diff <= 0) {
//              //  closest = diff;
//             //   pillar = n;
//            //}
//            //n++;
//       // }
//        long day = (-1 * closest) * (1000 * 60 * 60 * 24);
//        Log.d("Schedule", "DAY: " + day);
//
//        ScheduleDate s = new ScheduleDate((int)day);
//        //Log.d("najam", s.toString());
//        return s;
//        //  return new SchedulePillarDay(pillar, (int)day);
//    }
}
