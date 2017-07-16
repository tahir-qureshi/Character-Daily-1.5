package com.mucaroo.characterdailyapp.activities;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.adapters.AllLessonsAdapter;
import com.mucaroo.characterdailyapp.adapters.ScoringListAdapter;

import org.xml.sax.Parser;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyScoreActivity extends AppCompatActivity {

    private Button btnLesson, btnMore;
    private TextView tvclander;
    Context c;
    private DatabaseReference msdatabaseReference, msdatabaseReferencevalues;
    protected ScoringListAdapter scoringListAdapter;
    protected ArrayList<Integer> monthofdays = new ArrayList<>();
    protected ArrayList<Integer> scoringDates = new ArrayList<>();
    protected ArrayList<String> scoringDatesVales = new ArrayList<>();
    String Gradedata;
    ListView listView;
    String pillarIDs;
    String kesys;
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);

        c = getApplicationContext();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);
        tvclander = (TextView) findViewById(R.id.tv_calander);
        listView = (ListView) findViewById(R.id.lvscors);

        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);

        final String date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        pillarIDs = WelComeActivity.pillarsID;

        txt_title.setText("MY SCORE      ");
        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyScoreActivity.this, ImageDownloading.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyScoreActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyScoreActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyScoreActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }

        msdatabaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("lessons").child(pillarIDs);
        msdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey().toString();
                monthofdays.add(Integer.valueOf(key)+ 1);
                Log.d("pilardays", key);
//                upDateAdapter();
//                scoringListAdapter = new ScoringListAdapter(c, monthofdays);
//                listView.setAdapter(scoringListAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();//.child("17332").child("0")

        msdatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentFirebaseUser.getUid()).child("Chalanges");
        msdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                kesys = dataSnapshot.getKey();
                long unixdate = (Integer.parseInt(kesys) * (60 * 60 * 24));
                Date date = new Date(unixdate * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("d");
                String formattedDate = sdf.format(date);
                List<String> td = (ArrayList<String>) dataSnapshot.getValue();
                Log.d("keasysTD", String.valueOf(td));
                scoringDatesVales.add(kesys);
                map.put(formattedDate, td);

                //updateadapter
                upDateAdapter();

//                String childs = dataSnapshot.child(kesys).child("0").child("behavior").toString();
//                Log.d("myScoreDateKeychilds", childs);
//                String values = dataSnapshot.getValue(true).toString();
//                long unixdate = (Integer.parseInt(kesys) * (60 * 60 * 24));
//                Date date = new Date(unixdate * 1000L);
//                SimpleDateFormat sdf = new SimpleDateFormat("d");
//                String formattedDate = sdf.format(date);
////                System.out.println(formattedDate);
//                scoringDates.add(Integer.valueOf(kesys));
////                Log.d("myScoreDatekesy", ""+ kesys);
////                Log.d("myScoreDate", ""+ values);
//                for (int j = 0; j < scoringDates.size(); j++) {
//                    Log.d("ScoringDates111", String.valueOf(scoringDates.get(j)));
//                }
//                Log.d("myScoreDateKey", kesys);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void upDateAdapter() {
//        mAdapter = new AllLessonsAdapter(this, 0, mBlocks, monthofdays, Gradedata, this);
//        getLessonList().setAdapter(mAdapter);
        Log.d("keasysTDmap", String.valueOf(map));
        scoringListAdapter = new ScoringListAdapter(c, monthofdays, map);
        listView.setAdapter(scoringListAdapter);

    }

    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MoreOptionsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MoreOptionsActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
