package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mucaroo.characterdailyapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MoreCharacterToolsActivity extends AppCompatActivity {

    protected TextView txt_six, txt_catholic, txt_lock, txt_team, tvclander;
    private Button btnLesson, btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_character_tools);

        txt_six = (TextView) findViewById(R.id.tvSixPillars);
//        txt_catholic = (TextView) findViewById(R.id.tvCatholicFaith);
        txt_lock = (TextView) findViewById(R.id.tvLocksandKeys);
        txt_team = (TextView) findViewById(R.id.tvteam);
        tvclander = (TextView) findViewById(R.id.tv_calander);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);

        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);

        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");


        txt_title.setText("CHARACTER TOOLS   ");

        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreCharacterToolsActivity.this, ImageDownloading.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreCharacterToolsActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreCharacterToolsActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });
        txt_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreCharacterToolsActivity.this, SixPillarsActivity.class));
                finish();
            }
        });
//        txt_catholic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MoreCharacterToolsActivity.this, CatholicFaithActivity.class));
//                finish();
//            }
//        });
        txt_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreCharacterToolsActivity.this, LockAndKeyActivity.class));
                finish();
            }
        });
        txt_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreCharacterToolsActivity.this, TeamActivity.class));
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
