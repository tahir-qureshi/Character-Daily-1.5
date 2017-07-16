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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IntroductionActivity extends AppCompatActivity {

    private TextView txt_grade, tvclander, txt_bar_title, txt_title, txtquotebody;
    String lestitle, lesimage, lesQuote;
    private Button btnLesson,btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

//        setTitle("INTRODUCTION");

        lestitle = ImageDownloading.ALtitle;
        lesimage = ImageDownloading.ALimage;
        lesQuote = ImageDownloading.AQbody;//   "Quote"; //ImageDownloading.AQbody;

        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        final ImageView imageView = (ImageView) findViewById(R.id.lessonimage);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        tvclander = (TextView) findViewById(R.id.tv_calander);
        txt_bar_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title = (TextView) findViewById(R.id.lessontitle);
        txtquotebody = (TextView) findViewById(R.id.tvquoteBody);
        RelativeLayout relativeLayoutLesson = (RelativeLayout) findViewById(R.id.LessonLayout);
        RelativeLayout relativeLayoutQuote = (RelativeLayout) findViewById(R.id.QuoteLayout);
        RelativeLayout relativeLayoutBehave = (RelativeLayout) findViewById(R.id.behaviour);
        RelativeLayout relativeLayoutPledge = (RelativeLayout) findViewById(R.id.Pledge);


        txt_title.setText(lestitle);
        txtquotebody.setText(lesQuote);
        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);

        txt_bar_title.setText("INTRODUCTION     ");
        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        DB.getInstance(IntroductionActivity.this).getImage(lesimage, new DBCallback<ImageInfo>() {
            @Override
            public void success(ImageInfo f) {
                Log.d("dailLessonURL", f.url);
                Picasso.with(IntroductionActivity.this).load(f.url).into(imageView);
            }
        });
        relativeLayoutLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, AllLessonDailyLessonActivity.class));
                finish();
            }
        });
        relativeLayoutQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, AllLessonDailyQuote.class));
                finish();
            }
        });
        relativeLayoutBehave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, AllLessonBehaviourActivity.class));
                finish();
            }
        });
        relativeLayoutPledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, AllLessonPledgeActivity.class));
                finish();
            }
        });
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, ImageDownloading.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, MainActivity.class));
                finish();
            }
        });
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, MoreOptionsActivity.class));
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
                Intent intent = new Intent(this, ImageDownloading.class);
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
        Intent backMainTest = new Intent(this, ImageDownloading.class);
        startActivity(backMainTest);
        finish();
    }
}
