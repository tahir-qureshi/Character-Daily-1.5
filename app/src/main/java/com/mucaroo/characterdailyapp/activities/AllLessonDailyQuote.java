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
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.ScheduleLessonQuote;
import com.mucaroo.characterdailyapp.util.Globals;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AllLessonDailyQuote extends AppCompatActivity implements ActionSheetProfile.ActionSheetListener {

    int count;
    String[] fileData = {"Submit my own Quote", "Print"};
    String body, Quoteid,MonthsAndYears,lessonDateday;
    TextView txt_Body;
    String email, userId;
    private DatabaseReference databaseReference, postRef, statusRef;
    public String circle = "false";
    String Gradedata;
    int lessongrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quote);
        lessonDateday = ImageDownloading.ADate;
        MonthsAndYears = WelComeActivity.MonthANDYear;
        body =  ImageDownloading.AQbody;
        Quoteid = ImageDownloading.QuoteID;
        lessongrd = ImageDownloading.LessonGrade;
        Log.d("quotID", Quoteid);

        final String str[] = MonthsAndYears.split("-");
        Log.d("datessscccee0", String.valueOf(str[0]));
        Log.d("datessscccee1", String.valueOf(str[1]));
        final int day = Integer.parseInt(lessonDateday);
        int month = Integer.parseInt(str[0]);//Integer.parseInt(lessonDateday);
        int year = Integer.parseInt(str[1]); //Integer.parseInt(lessonDateMonth);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        final int lessonDates = (int) (c.getTimeInMillis() / (1000 * 60 * 60 * 24));
        Log.d("datesssccc", String.valueOf(lessonDates));


//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);
//
//        final TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
//        txt_title.setText("DAILY QUOTE");
        getSupportActionBar().hide();
        final TextView txt_back = (TextView) findViewById(R.id.back_arrow);
        final TextView txt_setting = (TextView) findViewById(R.id.setting_image);
        txt_Body = (TextView) findViewById(R.id.tvBody);
        txt_Body.setText(body);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AllLessonDailyQuote.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = currentFirebaseUser.getEmail();
        userId = currentFirebaseUser.getUid();
//        final ImageView imageQuote = (ImageView) findViewById(R.id.imquote);

        final ImageView imageViewCircle = (ImageView) findViewById(R.id.iv_circle);

        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        TextView tvclander = (TextView) findViewById(R.id.tv_calander);

//        imageQuote.setAlpha(127);
//        imageQuote.setBackgroundResource(R.color.transparent);
//        imageQuote.setBackgroundColor(Color.TRANSPARENT);
        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);

        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");
        final String pillarID = WelComeActivity.pillarsID;
        statusRef =  FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd)).child(Quoteid);
        DB.getInstance(AllLessonDailyQuote.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(pillarID), day, String.valueOf(lessongrd), new DBCallback<ScheduleLessonQuote>() {
            @Override
            public void success(final ScheduleLessonQuote o) {
//                final String lessonID = o.lesson;
//                final String quoteID = o.quote;
                statusRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String sss = dataSnapshot.getKey().toString();
                        Log.d("circlesss", sss);
                        if (sss.equals("Quote")) {
                            circle = dataSnapshot.getValue(String.class);
                            Log.d("circle", circle);
                            if (circle.equals("true")) {
                                imageViewCircle.setImageResource(R.mipmap.fill_circle);
                            } else {
                                imageViewCircle.setImageResource(R.mipmap.empty_circle);
                            }
                            Log.d("statuss", circle);
                        }
//                                }
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
        });


        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonDailyQuote.this, IntroductionActivity.class));
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
                startActivity(new Intent(AllLessonDailyQuote.this, MoreOptionsActivity.class));
                finish();
            }
        });


        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonDailyQuote.this, IntroductionActivity.class));
                finish();
            }
        });
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonDailyQuote.this, ImageDownloading.class));
                finish();
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        imageViewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(circle.equals("false")){
//                    circle = "true";
//                    imageViewCircle.setImageResource(R.mipmap.fill_circle);
//                }
//                else{
//                    circle = "false";
//                    imageViewCircle.setImageResource(R.mipmap.empty_circle);
//                }


                DB.getInstance(AllLessonDailyQuote.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(pillarID), day, String.valueOf(lessongrd), new DBCallback<ScheduleLessonQuote>() {
                    @Override
                    public void success(final ScheduleLessonQuote o) {
//                        final String lessonID = o.lesson;
//                        final String quoteID = o.quote;
//                        Log.d("Lessonid", lessonID);
//                        Log.d("quoteID", quoteID);
                        if (circle.equals("false")) {
                            circle = "true";
                            imageViewCircle.setImageResource(R.mipmap.fill_circle);

                            databaseReference.child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd)).child(Quoteid).child("Quote").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                                    Toast.makeText(DailyQuoteActivity.this, "Quote Submit Success", Toast.LENGTH_LONG).show();
                                }
                            });

//                            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                        String s = data.getKey().toString();
////                                if (s.equals("-KfSVB-o-koiLToah0Fw")) {
////                                    String item = data.getValue(String.class);
////                                    if (item.equals("false")) {
////                                        imageViewCircle.setImageResource(R.mipmap.fill_circle);
////                                        if ( s.equals(quoteID) || s.equals(lessonID) || s.equals("behavior")
////                                                || s.equals(lessonID) || s.equals(quoteID)|| s.equals("email") || s.equals("pledge")) {
//
////                                        } else {
////
////                                            //do something
////                                        }
////                                    }
////                                else {
////                                    imageViewCircle.setImageResource(R.mipmap.empty_circle);
////                                    databaseReference.child("users").child(userId).child("-KfSVB-o-koiLToah0Fw").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
////                                        @Override
////                                        public void onComplete(@NonNull Task<Void> task) {
////                                            Toast.makeText(DailyQuoteActivity.this, "Quote Score Submit flase", Toast.LENGTH_LONG).show();
////                                        }
////                                    });
////                                }
////                                if(data.child(userId).exists()) {
//
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//
////                        @Override
////                        public void onCancelled(FirebaseError firebaseError) {
////
////                        }
//                            });
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    map.put("challenges", "");
//                    map.put("-KfNNbGDmlCMamF97TJ3", "true");
//                    map.put("-KfSVB-o-koiLToah0Fw", "true");
//                    map.put("client", "deflaut");
//                    map.put("createdAt", System.currentTimeMillis());
//                    map.put("email", email);
//                    map.put("user", userId);
//                    databaseReference.child("users").child(currentFirebaseUser.getUid()).setValue(map);
//                    databaseReference.child("users").child(userId).child("-KfSVB-o-koiLToah0Fw").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(DailyQuoteActivity.this, "Quote Score Submit Success", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                } else {
//                    imageViewCircle.setImageResource(R.mipmap.empty_circle);
////                    HashMap<String, Object> map = new HashMap<String, Object>();
////                    map.put("challenges", "");
////                    map.put("-KfNNbGDmlCMamF97TJ3", "false");
////                    map.put("-KfSVB-o-koiLToah0Fw", "false");
////                    map.put("client", "deflaut");
////                    map.put("createdAt", System.currentTimeMillis());
////                    map.put("email", email);
////                    map.put("user", userId);
//                    databaseReference.child("users").child(userId).child("-KfSVB-o-koiLToah0Fw").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(DailyQuoteActivity.this, "Quote Score Submit flase", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    count = 0;
//                }
                        } else {
                            circle = "false";
                            imageViewCircle.setImageResource(R.mipmap.empty_circle);
                            databaseReference.child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd)).child(Quoteid).child("Quote").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(DailyQuoteActivity.this, "Quote Score Submit flase", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }
//    @Override
//    public boolean onPrepareOptionsMenu (Menu menu) {
////        if (isFinalized) {
////            menu.getItem(1).setEnabled(false);
//            // You can also use something like:
////             menu.findItem(R.id.new_game).setVisible(false);
////        }
//        return true;
//    }

//    TextView counts;
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
//        case R.id.about:
//            Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
//            return(true);
//        case R.id.exit:
//            finish();
//            return(true);
//
//    }
//        return(super.onOptionsItemSelected(item));
//    }

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
//        if (data.equals("Leave Feedback")) {
//            startActivity(new Intent(this, AllLessonFeedBackActivity.class));
//            finish();
//        }
        if (data.equals("Submit my own Quote")) {
//            Toast.makeText(getApplicationContext(), "Lesson Submission is Selected", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AllLessonQuoteSubmissionActivity.class));
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
