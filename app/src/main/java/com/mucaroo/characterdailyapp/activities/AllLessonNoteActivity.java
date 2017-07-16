package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.ScheduleLessonQuote;
import com.mucaroo.characterdailyapp.util.Globals;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AllLessonNoteActivity extends AppCompatActivity {


    private Button btnLesson, btnMore;
    private TextView tvclander;
    private DatabaseReference databaseReference, postRef;
    String email, userId;
    String allLessonID;
    String Gradedata;
    private EditText editTextNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        allLessonID = ImageDownloading.LessonID;

        TextView txt_title1 = (TextView) findViewById(R.id.action_bar_title1);
        txt_title1.setText("DAILY LESSON");
        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText(" " + "NOTES");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AllLessonNoteActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }
        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);
        tvclander = (TextView) findViewById(R.id.tv_calander);
        editTextNotes = (EditText) findViewById(R.id.et_body);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = currentFirebaseUser.getUid();
        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);

        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonNoteActivity.this, ImageDownloading.class));
                finish();
            }
        });
        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonNoteActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonNoteActivity.this, MoreOptionsActivity.class));
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
                Intent intent = new Intent(this, AllLessonDailyLessonActivity.class);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                postRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                final String feedback = editTextNotes.getText().toString().trim();
                databaseReference.child("users").child(userId).child("notes").child(allLessonID).setValue(feedback).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Inserted Notes of all Lesson", Toast.LENGTH_LONG).show();
                    }
                });
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, AllLessonDailyLessonActivity.class));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        final String feedback = editTextNotes.getText().toString().trim();
        databaseReference.child("users").child(userId).child("notes").child(allLessonID).setValue(feedback).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Inserted Notes of all Lesson", Toast.LENGTH_LONG).show();
            }
        });
        finish();

    }
}
