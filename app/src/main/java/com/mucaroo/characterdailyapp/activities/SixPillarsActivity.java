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

public class SixPillarsActivity extends AppCompatActivity {

    private TextView tvTrust, tvRespect, tvResposibility, tvFaire, tvCar, tvCitizen, tvclander;
    private Button btnLesson, btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_pillars);

        tvTrust = (TextView) findViewById(R.id.tvTrustWorthiness);
        tvRespect = (TextView) findViewById(R.id.tvRespect);
        tvResposibility = (TextView) findViewById(R.id.tvResponsibility);
        tvFaire = (TextView) findViewById(R.id.tvFairness);
        tvCar = (TextView) findViewById(R.id.tvCaring);
        tvCitizen = (TextView) findViewById(R.id.tvCitizenship);

        tvclander = (TextView) findViewById(R.id.tv_calander);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText("THE SIX PILLARS      ");
        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);

        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);

        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, ImageDownloading.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        tvTrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, TrustworthinessActivity.class));
                finish();
            }
        });
        tvRespect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, RespectActivity.class));
                finish();
            }
        });
        tvResposibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, ResponsibilityActivity.class));
                finish();
            }
        });
        tvFaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, FairnessActivity.class));
                finish();
            }
        });
        tvCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, CaringActivity.class));
                finish();
            }
        });
        tvCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SixPillarsActivity.this, CitizenShipActivity.class));
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

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MoreCharacterToolsActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
