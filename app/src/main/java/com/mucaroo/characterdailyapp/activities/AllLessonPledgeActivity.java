package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AllLessonPledgeActivity extends AppCompatActivity implements ActionSheetProfile.ActionSheetListener {

    int count;
    String[] fileData = {"Print"};
    private DatabaseReference dpdatabaseReference;
    private DatabaseReference databaseReference, postRef, statusRef;
    private FirebaseUser currentFirebaseUser;
    String email, userId, lessonDateday, lessonDateMonth, MonthsAndYears;
    public String circle = "false";
    String Gradedata;
    int lessongrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pledge);

        lessonDateday = ImageDownloading.ADate;
        lessonDateMonth = WelComeActivity.MonthofYear;
        MonthsAndYears = WelComeActivity.MonthANDYear;

        lessongrd = ImageDownloading.LessonGrade;
        final String str[] = MonthsAndYears.split("-");
        Log.d("datessscccee0", String.valueOf(str[0]));
        Log.d("datessscccee1", String.valueOf(str[1]));
        final int day = Integer.parseInt(lessonDateday);
        int month = Integer.parseInt(str[0]);//Integer.parseInt(lessonDateday);
        int year = Integer.parseInt(str[1]); //Integer.parseInt(lessonDateMonth);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        Log.d("datessscccyear", String.valueOf(str[1]));
        c.set(Calendar.MONTH, month);
        Log.d("datessscccmonth", String.valueOf(str[0]));
        c.set(Calendar.DAY_OF_MONTH, day);
        Log.d("datessscccday", String.valueOf(day));
        final int lessonDates = (int) (c.getTimeInMillis() / (1000 * 60 * 60 * 24));
        Log.d("datesssccc", String.valueOf(lessonDates));


//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

//        final TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
//        txt_title.setText("DAILY PLEDGE");
        getSupportActionBar().hide();

        final TextView txt_back = (TextView) findViewById(R.id.back_arrow);
        final TextView txt_setting = (TextView) findViewById(R.id.setting_image);
        final TextView txt_prayer = (TextView) findViewById(R.id.tvprayer);

        final ImageView imageViewCircle = (ImageView) findViewById(R.id.iv_circle);

        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        TextView tvclander = (TextView) findViewById(R.id.tv_calander);

        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AllLessonPledgeActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);

        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = currentFirebaseUser.getEmail();
        userId = currentFirebaseUser.getUid();

        statusRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd));
        statusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
                String sss = dataSnapshot.getKey();
//                    Log.d("circlesss", sss);
                if (sss.equals("pledge")) {
                    Log.d("circle", String.valueOf(dataSnapshot));
                    circle = dataSnapshot.getValue(String.class);
                    Log.d("circle", circle);
                    if (circle.equals("true")) {
                        imageViewCircle.setImageResource(R.mipmap.fill_circle);
                    } else {
                        imageViewCircle.setImageResource(R.mipmap.empty_circle);
                    }
                    Log.d("statuss", circle);
//                    }
                }

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

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonPledgeActivity.this, IntroductionActivity.class));
                finish();
            }
        });
        txt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme(R.style.ActionSheetStyleIOS7);
                ShowActionSheetProfile();
            }
        });
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonPledgeActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonPledgeActivity.this, MainActivity.class));
                finish();
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonPledgeActivity.this, ImageDownloading.class));
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        imageViewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                count++;
                if (circle.equals("false")) {
                    circle = "true";
                    imageViewCircle.setImageResource(R.mipmap.fill_circle);
                    databaseReference.child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd)).child("pledge").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //   Toast.makeText(DailyPledgeActivity.this, "Behavior Submit Success", Toast.LENGTH_LONG).show();
                        }
                    });
//                    postRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            for (DataSnapshot data : dataSnapshot.getChildren()) {
////                                String s = data.getKey().toString();
////                                if(data.child(userId).exists()) {
////                                    if (s.equals("-KPxUy1poxoIvAI4amyl") || s.equals("-KfSVB-o-koiLToah0Fw") || s.equals("behavior")
////                                            || s.equals("client") || s.equals("createdAt") || s.equals("email") || s.equals("pledge")) {
//
////                            }
////                                else {
////                                    HashMap<String, Object> map = new HashMap<String, Object>();
////                                    map.put("pledge", "true");
////                                    map.put("client", "deflaut");
////                                    map.put("createdAt", System.currentTimeMillis());
////                                    map.put("email", email);
////                                    databaseReference.child("users").child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                        @Override
////                                        public void onComplete(@NonNull Task<Void> task) {
//////                                            Toast.makeText(DailyPledgeActivity.this, "pladge Score Submit Success", Toast.LENGTH_LONG).show();
////                                        }
////                                    });
////                                    //do something
////                                }
////                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//
////                        @Override
////                        public void onCancelled(FirebaseError firebaseError) {
////
////                        }
//                    });
//                    final String feedback = editText.getText().toString().trim();
//                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    map.put("challenge", "");
//                    map.put("-KfNNbGDmlCMamF97TJ3", "true");
//                    map.put("createdAt", System.currentTimeMillis());
//                    map.put("client", "deflaut");
//                    map.put("user", currentFirebaseUser.getUid());
//                    databaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(DailyLessonActivity.this, "Recorded Inserted Success", Toast.LENGTH_LONG).show();
//                        }
//                    });
                } else {
                    circle = "false";
                    imageViewCircle.setImageResource(R.mipmap.empty_circle);
                    databaseReference.child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd)).child("pledge").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(DailyPledgeActivity.this, "Pledge Submit Success", Toast.LENGTH_LONG).show();
                        }
                    });
//                    count = 0;
                }
            }
        });

        dpdatabaseReference = FirebaseDatabase.getInstance().getReference().child("clients").child("default").child("pledge");
        dpdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String data = dataSnapshot.getValue(String.class);
                if (key.equals("body")) {
                    txt_prayer.setText(data);
                }

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


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, IntroductionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ShowActionSheetProfile() {
        ActionSheetProfile.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Dismiss")
                .setOtherButtonTitles(fileData)
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismissProfile(ActionSheetProfile actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClickProfile(ActionSheetProfile actionSheet, int index) {
        String data = fileData[index];
        if (data.equals("Leave Feedback")) {
            startActivity(new Intent(this, FeedBackActivity.class));
            finish();
        }
        if (data.equals("Submit my own Lessom")) {
//            Toast.makeText(getApplicationContext(), "Lesson Submission is Selected", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MyLessonSubmissionActivity.class));
            finish();
        }
        if (data.equals("Print")) {
            Toast.makeText(getApplicationContext(), "Print is Selected", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, IntroductionActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
