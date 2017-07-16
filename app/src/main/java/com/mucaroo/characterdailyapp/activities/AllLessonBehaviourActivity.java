package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.util.Globals;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AllLessonBehaviourActivity extends AppCompatActivity implements ActionSheetProfile.ActionSheetListener {

    int count;
    String[] fileData = {"Print"};
    private FirebaseUser currentFirebaseUser;
    String email, userId, lessonDateday, lessonDateMonth, MonthsAndYears;
    private DatabaseReference databaseReference, postRef, statusRef;
    public String circle = "false";
    String Gradedata;
    TextView txt_behavioue;
    int lessongrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_behaviour);

        lessongrd = ImageDownloading.LessonGrade;
        lessonDateday = ImageDownloading.ADate;
        lessonDateMonth = WelComeActivity.MonthofYear;
        MonthsAndYears = WelComeActivity.MonthANDYear;

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

//
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);
//
//        final TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
//        txt_title.setText("DAILY BEHAVIOUR");
        getSupportActionBar().hide();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AllLessonBehaviourActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
        }
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = currentFirebaseUser.getEmail();
        userId = currentFirebaseUser.getUid();

        final TextView txt_back = (TextView) findViewById(R.id.back_arrow);
        final TextView txt_setting = (TextView) findViewById(R.id.setting_image);

        final ImageView imageViewCircle = (ImageView) findViewById(R.id.iv_circle);

        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        TextView tvclander = (TextView) findViewById(R.id.tv_calander);

        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);
        final String pillarID = WelComeActivity.pillarsID;

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        txt_behavioue = (TextView) findViewById(R.id.tvBehaviour);
        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");
//        Log.d("behaveData", Gradedata);
//        int grd = Integer.parseInt(Gradedata);
//        Log.d("behaveData11", String.valueOf(grd));
        statusRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd));
        statusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
                String sss = dataSnapshot.getKey().toString();
                Log.d("behaveData", sss);
                if (sss.equals("behavior")) {
                    circle = dataSnapshot.getValue(String.class);
                    Log.d("behaveData", circle);
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


        DB.getInstance(AllLessonBehaviourActivity.this).getBehaviour(Globals.ClientID, Integer.parseInt(pillarID), String.valueOf(lessongrd), new DBCallback<String>() {
            @Override
            public void success(String o) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("behaviors/" + o);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String result = dataSnapshot.getValue(String.class);
                        txt_behavioue.setText(result);
//                        Log.i("", result);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

               /*

                final String os = o;
                Log.d("behaveData111", os);
                statusRef = FirebaseDatabase.getInstance().getReference().child("behaviors");
                statusRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String sss = data.getKey().toString();
                            Log.d("os", os);
                            if (sss.equals(os)) {
                                String dat = data.getValue(true).toString();
                                Log.d("behaveData11221", dat);
                                txt_behavioue.setText("hi");

                            }
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
                });*/
            }
        });


        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonBehaviourActivity.this, IntroductionActivity.class));
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
                startActivity(new Intent(AllLessonBehaviourActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonBehaviourActivity.this, MainActivity.class));
                finish();
            }
        });
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonBehaviourActivity.this, ImageDownloading.class));
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        imageViewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (circle.equals("false")) {
                    circle = "true";
                    imageViewCircle.setImageResource(R.mipmap.fill_circle);
                    databaseReference.child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd)).child("behavior").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                                            Toast.makeText(DailyBehaviourActivity.this, "Behavior Submit Success", Toast.LENGTH_LONG).show();
                        }
                    });
//                    postRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot data : dataSnapshot.getChildren()) {
//                             String s = data.getKey().toString();
//                                if(data.child(userId).exists()) {
//                                if (s.equals("-KPxUy1poxoIvAI4amyl") || s.equals("-KfSVB-o-koiLToah0Fw") || s.equals("behavior")
//                                        || s.equals("client") || s.equals("createdAt") || s.equals("email") || s.equals("pledge")) {

//                                } else {
//                                    HashMap<String, Object> map = new HashMap<String, Object>();
//                                    map.put("behavior", "true");
//                                    map.put("client", "deflaut");
//                                    map.put("createdAt", System.currentTimeMillis());
//                                    map.put("email", email);
//                                    databaseReference.child("users").child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
////                                            Toast.makeText(DailyBehaviourActivity.this, "Behavior Score Submit Success", Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                    //do something
//                                }
//                            }
//                        }

//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }

//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    });
                } else {
                    circle = "false";
                    imageViewCircle.setImageResource(R.mipmap.empty_circle);
                    databaseReference.child("users").child(userId).child("Chalanges").child(String.valueOf(lessonDates)).child(String.valueOf(lessongrd)).child("behavior").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(DailyBehaviourActivity.this, "Pledge Submit Success", Toast.LENGTH_LONG).show();
                        }
                    });
                    count = 0;
                }
            }
        });
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
//        if (data.equals("Leave Feedback")) {
//            startActivity(new Intent(this, FeedBackActivity.class));
//            finish();
//        }
//        if (data.equals("Submit my own Lessom")) {
////            Toast.makeText(getApplicationContext(), "Lesson Submission is Selected", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(this, MyLessonSubmissionActivity.class));
//            finish();
//        }
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
