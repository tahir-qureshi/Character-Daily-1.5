package com.mucaroo.characterdailyapp.adapters;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.activities.WelComeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Zaman Khan on 15-Jun-17.
 */

public class ScoringListAdapter extends ArrayAdapter<Integer> {

    LayoutInflater inflater;
    Context c;
    ArrayList<Integer> date;
    HashMap<String, List<String>> map;
    private String[][] finall, finallGrade;
    int i;
    int grade[];
    int pledge, behaviour, lesson, quote = 0;


    public ScoringListAdapter(Context context, ArrayList<Integer> date, HashMap<String, List<String>> map) {
        super(context, R.layout.scoring_listview_row, date);
        this.c = context;
        this.date = date;
        this.map = map;

        finall = new String[date.size()][4];
        finallGrade = new String[date.size()][4];

        for (String k : map.keySet()) {
            Log.i("kkey222111", k);
            i = 0;
            for (Integer key : date) {

                if (k.equals(key.toString())) {
                     grade = new int[4];
                    try {
                        JSONArray jA = new JSONArray(String.valueOf(map.get(k)));
                        Log.i("kkey222111JAJA", String.valueOf(jA));
                        for (int ji = 0; ji <= jA.length(); ji++) {
                            JSONObject jO = jA.optJSONObject(ji);
                            Log.i("kkey222111JAJO", String.valueOf(jO) + " " + i);
                            if (jO != null) {
                                for (int nam = 0; nam < jO.names().length(); nam++) {
                                    String keyy = jO.names().get(nam).toString();

                                    if (keyy.startsWith("-")) {
                                        String objj = jO.get(keyy).toString();
                                        JSONObject jjO = new JSONObject(objj);
                                        String lq = jjO.names().get(0).toString();

                                        if (lq.equals("Lesson")&& (jjO.getString("Lesson").equals("true"))) {
                                            lesson = 2;
                                            grade[0]++;
                                        }else if(lq.equals("Lesson")&& (jjO.getString("Lesson").equals("false"))){
                                            lesson = 1;
                                            grade[0]++;
                                        }
                                        if (lq.equals("Quote")&& (jjO.getString("Quote").equals("true"))) {
                                            quote = 2;
                                            grade[1]++;
                                        }else if (lq.equals("Quote")&& (jjO.getString("Quote").equals("false"))) {
                                            quote = 1;
                                            grade[1]++;
                                        }

                                    }

                                    if (keyy.equals("pledge") && jO.optBoolean("pledge") == true) {
                                        pledge = 2;
                                        grade[2]++;
                                        Log.i("kkkey222111", pledge + " " + grade[2] + " " + i);

                                    }else if (keyy.equals("pledge") && jO.optBoolean("pledge") == false) {
                                        pledge = 1;
                                        grade[2]++;

                                    }

                                    if (keyy.equals("behavior") && jO.optBoolean("behavior") == true) {
                                        behaviour = 2;
                                        grade[3]++;

                                    }else if (keyy.equals("behavior") && jO.optBoolean("behavior") == false) {
                                        behaviour = 1;
                                        grade[3]++;
                                    }

                                }


                            }
                        }

//                        Log.i("kkey222", String.valueOf(pledge));
//                        Log.i("kkey2221", String.valueOf(behaviour));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.i("kkkey222111xx", grade[2] + " " + grade[3]);

                    finallGrade[i][0] = String.valueOf(grade[0]);
                    finallGrade[i][1] = String.valueOf(grade[1]);
                    finallGrade[i][2] = String.valueOf(grade[2]);
                    finallGrade[i][3] = String.valueOf(grade[3]);

                    finall[i][0] = String.valueOf(lesson);
                    finall[i][1] = String.valueOf(quote);
                    finall[i][2] = String.valueOf(pledge);    //+ " " + grade[2];
                    finall[i][3] = String.valueOf(behaviour);   //+ " " + grade[3];


//                    Log.i("kkey2221", finall[i][3]);
                }
                i++;
            }
        }

        for (int lo = 0; lo < finall.length; lo++) {
            for (int loo = 0; loo < 4; loo++) {
                if (TextUtils.isEmpty(finall[lo][loo])) {
                    finall[lo][loo] = " ";
                }
            }
        }

    }

    public class ViewHolder {
        TextView tvdate, tvLesson,tvLGrade, tvQuote,tvQGrade, tvbehave,tvBGrade, tvpladge, tvPGrade;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        int grade[] = new int[4];
//        boolean pledge = false, behaviour = false ;
//
//        String key = date.get(position).toString();
//        for ( String k : map.keySet() ) {
//            if(k.equals(key)){
//
//                try {
//                    JSONArray jA = new JSONArray(String.valueOf(map.get(k)));
//                    for(int i = 0; i<=4; i++){
//                        JSONObject jO = jA.optJSONObject(i);
//                        if(jO!=null){
//                            if(jO.optBoolean("pledge")  == true){
//                                pledge = true;
//                                grade[2] = i;
//                            }
//
//                            if(jO.optBoolean("behavior")  == true){
//                                behaviour = true;
//                                grade[3] = i;
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Log.i("kkey222", String.valueOf(pledge));
//                Log.i("kkey2221", String.valueOf(behaviour));
//                Log.i("kkey2223", String.valueOf(grade));


//                String sss= " " + String.valueOf(map.get(k));
//
//            }

        //       }
        ViewHolder holder = null;
        convertView = null;
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.scoring_listview_row, null);
            holder = new ViewHolder();
            holder.tvdate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvbehave = (TextView) convertView.findViewById(R.id.tvratbehaviour);
            holder.tvpladge = (TextView) convertView.findViewById(R.id.tvratPledge);
            holder.tvLesson = (TextView) convertView.findViewById(R.id.tvratLesson);
            holder.tvQuote = (TextView) convertView.findViewById(R.id.tvratQuote);

            holder.tvLGrade = (TextView) convertView.findViewById(R.id.tvLessonGrade);
            holder.tvQGrade = (TextView) convertView.findViewById(R.id.tvQuoteGrade);
            holder.tvBGrade = (TextView) convertView.findViewById(R.id.tvBehaviourGrade);
            holder.tvPGrade = (TextView) convertView.findViewById(R.id.tvPledgeGrade);
//        holder.tvLesson.setBackgroundColor(Color.TRANSPARENT);
//        holder.tvQuote.setBackgroundColor(Color.TRANSPARENT);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String month = WelComeActivity.MonthofYear;
//        Log.i("najam1",month);
//        Log.i("najam11",date.get(position).toString());

        holder.tvdate.setText(month + " " + (date.get(position)));
//
//        if (le.equals(" null")) {
//
//        } else {
//
////            String checked =  finall[position][0];
////            Log.d("getView",  checked);
////            holder.tvLesson.setBackgroundResource(R.drawable.tick_icon);
////            if(Objects.equals(checked, "true")){
////                holder.tvLesson.setBackgroundResource(R.drawable.tick_icon);
////            }else {
////                holder.tvLesson.setBackgroundResource(R.drawable.cross_icon);
////            }
//            holder.tvLesson.setText(" " + finall[position][0]);
//        }
//        if (qu.equals(" null")) {
//
//        } else {
//            holder.tvQuote.setText(" " + finall[position][1]);
//        }
//        if (bh.equals(" null")) {
//
//        } else {
//            holder.tvbehave.setText(" " + finall[position][2]);
//        }
//
//        if (pl.equals(" null")) {
//
//        } else {
//            holder.tvpladge.setText(" " + finall[position][3]);
//        }
//        holder.tvbehave.setText(" " + finall[position][2]);
//        holder.tvpladge.setText(" " + finall[position][3]);
////        holder.tvLesson.setText(" " + finall[position][0]);
//        holder.tvQuote.setText(" " + finall[position][1]);
        if (finall[position][0].equals("1")) {
//            holder.tvLesson.setBackgroundResource(R.drawable.cross_icon);
        } else if (finall[position][0].equals("2")) {
            holder.tvLesson.setBackgroundResource(R.drawable.tick_icon);
            holder.tvLGrade.setText(":"+finallGrade[position][0]);
        } else {

        }
        Log.i("kkey11222N", finall[position][1]);
        if (finall[position][1].equals("1")) {
//            holder.tvQuote.setBackgroundResource(R.drawable.cross_icon);
        } else if (finall[position][1].equals("2")) {
            holder.tvQuote.setBackgroundResource(R.drawable.tick_icon);
            holder.tvQGrade.setText(":"+finallGrade[position][1]);
        } else {

        }

        if (finall[position][2].equals("1")) {
//            holder.tvpladge.setBackgroundResource(R.drawable.cross_icon);
        } else if (finall[position][2].equals("2")) {
            holder.tvpladge.setBackgroundResource(R.drawable.tick_icon);
            holder.tvPGrade.setText(":"+finallGrade[position][2]);
        } else {

        }
        if (finall[position][3].equals("1")) {
//            holder.tvbehave.setBackgroundResource(R.drawable.cross_icon);
        } else if (finall[position][3].equals("2")) {
            holder.tvbehave.setBackgroundResource(R.drawable.tick_icon);
            holder.tvBGrade.setText(":"+finallGrade[position][3]);
        } else {

        }


//        Log.i("kkey11222A", finall[i][0] + i);
        //        if(behaviour!=false){
//            holder.tvbehave.setText(behaviour + " " + grade[2]);
//        }
//        if (pledge!=false){
//            holder.tvpladge.setText(pledge + " " + grade[3]);
//        }

//        holder.tvdate.setText("zaman Khan");
//        holder.price.setText(price.get(position));

        return convertView;
    }

}
