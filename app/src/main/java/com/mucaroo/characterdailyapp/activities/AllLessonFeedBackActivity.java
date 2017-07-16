package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AllLessonFeedBackActivity extends AppCompatActivity {

    private EditText editText;
    private Button button, btnLesson, btnMore;
    private DatabaseReference fbdatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        TextView txt_title1 = (TextView) findViewById(R.id.action_bar_title1);
        txt_title1.setText("DAILY LESSON");
        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText(" " + "FEEDBACK");


        editText = (EditText) findViewById(R.id.edfeedback);
        button = (Button) findViewById(R.id.btnSubmit);
        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);
        TextView tvclander = (TextView) findViewById(R.id.tv_calander);
        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");


        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);

        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonFeedBackActivity.this, ImageDownloading.class));
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonFeedBackActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonFeedBackActivity.this, MainActivity.class));
                finish();
            }
        });
        fbdatabaseReference = FirebaseDatabase.getInstance().getReference().child("feedback");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String feedback = editText.getText().toString().trim();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                HashMap<String, Object> map = new HashMap<String, Object>();

                map.put("createdAt", System.currentTimeMillis());
                map.put("text", feedback);
                map.put("user", currentFirebaseUser.getUid());
                fbdatabaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(AllLessonFeedBackActivity.this, MoreOptionsActivity.class));
                            finish();
                            Toast.makeText(AllLessonFeedBackActivity.this, "Recorded Inserted Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AllLessonFeedBackActivity.this, "Recorded Not inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, AllLessonDailyLessonActivity.class);
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
        Intent backMainTest = new Intent(this, AllLessonDailyLessonActivity.class);
        startActivity(backMainTest);
        finish();
    }

}
