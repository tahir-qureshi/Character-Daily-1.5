package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MoreOptionsActivity extends AppCompatActivity {

    protected TextView txt_tools, txt_score, txt_feedback, tvclander, txt_profile, txt_submission;
    private Button btnLesson, btnMore;
    public static ArrayList<String> pillarsDate = new ArrayList<>();
    private DatabaseReference poDatabaseReference;

    String formattedDate, key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);


        poDatabaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("pillars");//.child("default").child("0");
        poDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                key = dataSnapshot.getKey().toString();
                long unixdate = dataSnapshot.getValue(Long.class);
                Date date = new Date(unixdate * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
                formattedDate = sdf.format(date);
                System.out.println(formattedDate);
//                txtzeroPillar.setText(formattedDate);
                Log.d("pilkeys1", formattedDate);
//                customer1.setId(Long.valueOf(key));
                pillarsDate.add(formattedDate);

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

        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText("MORE");

//        setTitle("MORE");

        txt_tools = (TextView) findViewById(R.id.tvMoreCharacterTools);
        txt_score = (TextView) findViewById(R.id.tvCharacterScore);
        txt_feedback = (TextView) findViewById(R.id.tvFeedback);
        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);
        tvclander = (TextView) findViewById(R.id.tv_calander);
        txt_profile = (TextView) findViewById(R.id.tvProfile);
        txt_submission = (TextView) findViewById(R.id.tvMySubmission);

        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);

        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, ImageDownloading.class));
                finish();
            }
        });
        txt_tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, MoreCharacterToolsActivity.class));
                finish();
            }
        });

        txt_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, AppFeedBackActivity.class));
                finish();
            }
        });

        txt_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, MyScoreActivity.class));
                finish();
            }
        });
        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, MyProfileActivity.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, MainActivity.class));
                finish();
            }
        });
        txt_submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, MoreOptionMyLessonSubMissionActivity.class));
                finish();
            }
        });

    }
    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MoreCharacterToolsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, ImageDownloading.class));
        finish();

    }
}
