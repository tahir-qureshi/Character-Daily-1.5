package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.SystemClock;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DailyPledgeActivity extends AppCompatActivity implements ActionSheetProfile.ActionSheetListener {

    int count;
    String[] fileData = {"Print"};
    private DatabaseReference dpdatabaseReference;
    private DatabaseReference databaseReference, postRef, statusRef;
    private FirebaseUser currentFirebaseUser;
    String email, userId;
    public String circle = "false";
    String Gradedata;
    long currenttimesmilli = System.currentTimeMillis();
    long currenttimesRemoveMilli =currenttimesmilli/1000L;
    long curenttimeRemoveSecond = currenttimesRemoveMilli/60;
    long curenttimeRemoveMints = curenttimeRemoveSecond/60;
    long curenttimesRemoveHours = curenttimeRemoveMints/24;

    protected String GetMilliSeccondFromUnixDate(long unixTimeStamp){
        return String.valueOf(unixTimeStamp*(1000*60 * 60 *24 ));
    }
    protected String GetDateUnixFromCurrentMilliSecond(long unixTimeStamp){
        return String.valueOf(unixTimeStamp/(1000*60 * 60 *24 ));
    }

//    long calculatedDate = currenttimes/(1000*60*60*24);

    String currentdate = String.valueOf((System.currentTimeMillis()/(1000*60 * 60 *24 )));
//    long yourmilliseconds = System.currentTimeMillis();

//    long roundedtimeMs = Math.round( (double)( (double)yourmilliseconds/(double)(15*60*1000) ) ) * (15*60*1000);

//    java.util.Date now = new java.util.Date();
//    long nowt = now.getTime();
//    long closest = -1 * nowt;
//    long day = (-1 * closest) / (1000 * 60 * 60 * 24);

//    NumberFormat f = new DecimalFormat("00");
//    SimpleDateFormat sdf = new SimpleDateFormat("9");
//    String date = String.valueOf(f.format(sdf));

//    NumberFormat formatter = new DecimalFormat("0000000000");
//    String s = formatter.format(1);


//System.out.println(format);

//    Date resultdate = new Date(yourmilliseconds * 1000L);


    //    Date date = new Date(yourmilliseconds);

//System.out.println(sdf.format(resultdate));
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("CurrentTime", String.valueOf(currenttimesmilli));
        Log.d("RemoveMilli", String.valueOf(currenttimesRemoveMilli));
        Log.d("RemoveSecond", String.valueOf(curenttimeRemoveSecond));
        Log.d("RemoveMints", String.valueOf(curenttimeRemoveMints));
        Log.d("RemoveHours", String.valueOf(curenttimesRemoveHours));
//        String pattern = "###,###.###";
//        DecimalFormat decimalFormat = new DecimalFormat(pattern);
//        String format = decimalFormat.format(123456789.123);

//
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, 2017);
//        c.set(Calendar.MONTH, 6);
//        c.set(Calendar.DAY_OF_MONTH, 14);
//        double result = (double) (c / 1000L);

//        long currentdate11 = (System.currentTimeMillis());
//        double date111= Double.parseDouble(new SimpleDateFormat("dd MM yyyy")
//                        .format(System.currentTimeMillis()));

        String date222=
                new SimpleDateFormat("dd MM yyyy")
                        .format(System.currentTimeMillis());
//        long lll = Long.parseLong(date222);
//        Log.d("result123", String.valueOf(lll));



//        String pattern = "#,###.###";
//        DecimalFormat decimalFormat = new DecimalFormat(pattern);
//        decimalFormat.setGroupingSize(4);
//
//        String number = decimalFormat.format(123456789.123);
//        Log.d("Schedule", "DAY: " + number);
//        System.out.println(number);


//        Date currentDate = new Date(yourmilliseconds);
//        int dd=   currentDate.getDate();
//        Log.d("milliDate111", String.valueOf(roundedtimeMs));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pledge);
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DailyPledgeActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);

        final String date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = currentFirebaseUser.getEmail();
        userId = currentFirebaseUser.getUid();

        statusRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Chalanges").child(currentdate).child(Gradedata);
        statusRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String sss = dataSnapshot.getKey();
                    Log.d("circlesss", sss);
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
                startActivity(new Intent(DailyPledgeActivity.this, MainActivity.class));
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
                startActivity(new Intent(DailyPledgeActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyPledgeActivity.this, MainActivity.class));
                finish();
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyPledgeActivity.this, ImageDownloading.class));
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

                    databaseReference.child("users").child(userId).child("Chalanges").child(currentdate).child(Gradedata).child("pledge").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    databaseReference.child("users").child(userId).child("Chalanges").child(currentdate).child(Gradedata).child("pledge").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
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

//    private String getDate(long timeStamp){
//
//        try{
//            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//            Date netDate = (new Date(timeStamp));
//            return sdf.format(netDate);
//        }
//        catch(Exception ex){
//            return "xx";
//        }
//    }
    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
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
        Intent backMainTest = new Intent(this, MainActivity.class);
        startActivity(backMainTest);
        finish();
    }
}