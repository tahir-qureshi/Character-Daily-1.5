package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Zaman Khan on 01-May-17.
 */

public class WelComeActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener {

    protected TextView txt_all_grades, txt_holly_cross;
    protected Button btn_cont;
    String email, userId;
    public static String MonthofYear,pillarsID, MonthANDYear;
    private DatabaseReference databaseReference, postRef;
    ArrayList<String> arrayList;
    String[] Gradesdata = {"K-2nd", "3rd-5th", "6th-8th", "9th-12th", "All Grades"};
//    String[] PillarsData = {"Holly Cross", "Our Mother of Sorrows/ St.lgnative of Loyola", "St.Barnabas", "St. Cyril of Alexandria",
//            "St.Frances Cabrini", "St.Gabriel", "St.Helena Incarnation",
//            "St.Malachy", "St.Martin de Porres"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        getSupportActionBar().hide();


        final String dateCheck = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("pillars");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey().toString();
                long keyvalue = dataSnapshot.getValue(long.class);
                Date date = new Date(keyvalue * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("MMM");
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM-yyyy");
                String formattedDate = sdf.format(date);
                String monthYear = sdf1.format(date);
//                Log.d("MonthPillarsvalue", String.valueOf(keyvalue));
//                Log.d("pilkeyMonth", formattedDate);
//                Log.d("pilkeyMonth", dateCheck);
                if (formattedDate.equals(dateCheck)) {
                    MonthofYear = formattedDate;//xyz
                    pillarsID = key;
                    MonthANDYear = monthYear;
                    Log.d("PillarsIDss", pillarsID);
//                    tvDate.setText(MonthofYear + "  "+  (monthofdays.get(pposition)+1));
                    Log.d("pilkeyMonth111", formattedDate);
                    Log.d("pilkeyMonth111", "DateExist");
                } else if (key.equals("0")) {
                    MonthofYear = formattedDate;//nov"Nov"
                    pillarsID = key;
                    MonthANDYear = monthYear;
                    Log.d("pilkeyMonth111", formattedDate);
                    Log.d("PillarsIDsz", pillarsID);
                }
//                monthsofPillars.add(formattedDate);

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

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = currentFirebaseUser.getEmail();
        userId = currentFirebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = FirebaseDatabase.getInstance().getReference().child("users");

        postRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // TODO: handle the case where the data already exists
                    Log.d("LogData", "User Exist");
                } else {
                    // TODO: handle the case where the data does not yet exist
                    Log.d("LogData", "User not Exist");
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    Log.d("LogData1", userId);
                    map.put("client", "deflaut");
                    map.put("Chalanges", "");
                    map.put("createdAt", System.currentTimeMillis());
                    map.put("email", email);
                    map.put("notes", "");
                    map.put("grade", "");
                    map.put("pillar", "");
                    databaseReference.child("users").child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
//                String keys = snapshot.getKey().toString();
////                for (DataSnapshot data : snapshot.getChildren()) {
////                    Log.d("LogData", String.valueOf(data));
////                    String s = data.getKey().toString();
////                    if (data.getKey().toString().equals(userId)){
//                if(keys.equals(userId)) {
//                    for (DataSnapshot data : snapshot.getChildren()) {
//                        String s = data.getKey().toString();
//                    if( s.equals("Chalanges")  || s.equals("client") || s.equals("createdAt") || s.equals("email")){
//                        // TODO: handle the case where the data already exists
//                        Toast.makeText(getApplicationContext(), "User Exist", Toast.LENGTH_LONG).show();
//                        Log.d("LogData", s);
//                    } else {
//                        // TODO: handle the case where the data does not yet exist
//                            HashMap<String, Object> map = new HashMap<String, Object>();
//                            Log.d("LogData1", userId);
//                            map.put("client", "deflaut");
//                            map.put("Chalanges", "");
//                            map.put("createdAt", System.currentTimeMillis());
//                            map.put("email", email);
//                            databaseReference.child("users").child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                }
//                            });
//                        }
//                    }
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
//        postRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    String keys = data.getKey().toString();
//                    if (keys.equals(userId)) {
//
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Not User Exist", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        txt_all_grades = (TextView) findViewById(R.id.txt_grades);
//        txt_holly_cross = (TextView) findViewById(R.id.txt_holly_cross);
        btn_cont = (Button) findViewById(R.id.btn_continue);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WelComeActivity.this);
        String Gradedata = sharedPreferences.getString("Data", "All Grades");
        txt_all_grades.setText(Gradedata);
//        if (Gradedata.equals("All Grade")) {
//            int index1 = 0;
//            Log.d("Grades", Gradedata);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("GradeData", String.valueOf(0));
//        }

//        if (Gradedata.isEmpty()) {
//            txt_all_grades.setText("All Grade");
////            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WelComeActivity.this);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            int index = 0;
//            editor.putString("GradeData", String.valueOf(index));
//        }

        btn_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelComeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_grades:
                setTheme(R.style.ActionSheetStyleIOS7);
                showActionSheetGrades();
                break;
//            case R.id.txt_holly_cross:
//                setTheme(R.style.ActionSheetStyleIOS7);
//                showActionSheetPillars();
//                break;
        }

    }

    public void showActionSheetGrades() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(Gradesdata)
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onOtherButtonClickGrades(ActionSheet actionSheet, int indexGrade) {
//        Toast.makeText(getApplicationContext(), "Grades item index = " + Gradesdata[indexGrade], Toast.LENGTH_LONG).show();
        String grades = Gradesdata[indexGrade];
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WelComeActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("GradeData", String.valueOf(indexGrade));
        editor.putString("Data", grades);
        editor.commit();
        txt_all_grades.setText(grades);
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

    }

    @Override
    public void onDismissGrades(ActionSheet actionSheet, boolean isCancle) {
//        Toast.makeText(getApplicationContext(), "Grades dismissed isCancle = " + isCancle, Toast.LENGTH_LONG).show();
    }

//
//    public void showActionSheetPillars() {
//        ActionSheetPillars.createBuilder(this, getSupportFragmentManager())
//                .setCancelButtonTitle("Cancel")
//                .setOtherButtonTitles(PillarsData)
//                .setCancelableOnTouchOutside(true).setListener(this).show();
//    }
//
//    public void onOtherButtonClickPillars(ActionSheetPillars actionSheet, int indexPillar) {
////        Toast.makeText(getApplicationContext(), "Pillar index = " + PillarsData[indexPillar], Toast.LENGTH_LONG).show();
//        String Pillars = PillarsData[indexPillar];
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WelComeActivity.this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("PillarData", Pillars);
//        editor.commit();
//
////        txt_holly_cross.setText(Pillars);
//    }
//
//    public void onDismissPillars(ActionSheetPillars actionSheet, boolean isCanclePillars) {
////        Toast.makeText(getApplicationContext(), "Pillar dismissed isCancle = " + isCanclePillars, Toast.LENGTH_LONG).show();
//    }

//All POpUP Screens codes
//    public void onGradeClick(View v) {
//
//        ListView listView = new ListView(this);
//        txt_all_grades = (TextView) findViewById(R.id.txt_grades);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
//        arrayAdapter.add("K-2nd");
//        arrayAdapter.add("3rd-5th");
//        arrayAdapter.add("6th-8th");
//        arrayAdapter.add("9th-12th");
//        arrayAdapter.add("All Grades");
//
//        listView.setAdapter(arrayAdapter);
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
////        dialogBuilder.setTitle("My Dialog");
//        dialogBuilder.setView(listView);
////        dialogBuilder.setPositiveButton("OK", null); // TODO
////        dialogBuilder.setNegativeButton("Cancel", null); // nothing simply dismiss
//
//        final AlertDialog dialog = dialogBuilder.show();
//        dialog.show();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
//                Toast.makeText(getApplicationContext(), " Grade is Selected " + pos, Toast.LENGTH_SHORT).show();
//                final String grades = arrayAdapter.getItem(pos);
//                txt_all_grades.setText(grades);
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WelComeActivity.this);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("GradeData", grades);
//                editor.commit();
//                dialog.hide();
//                // Here I call a method to update listView?
//            }
//        });
//
//
////        final Dialog caldialog = new Dialog(this);
////        caldialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////        caldialog.setContentView(R.layout.activity_list);
////        caldialog.setTitle("Hello");
////        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
////        lp.copyFrom(caldialog.getWindow().getAttributes());
////        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
////        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        lp.gravity = Gravity.CENTER;
////        caldialog.getWindow().setAttributes(lp);
////        caldialog.show();
//    }
//
//    public void onHollyClick(View v) {
//        ListView listView = new ListView(this);
//        txt_holly_cross = (TextView) findViewById(R.id.txt_holly_cross);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
//        arrayAdapter.add("Holly Cross");
//        arrayAdapter.add("Our Mother of Sorrows/ St.lgnative of Loyola");
//        arrayAdapter.add("St.Barnabas");
//        arrayAdapter.add("St.Frances Cabrini");
//        arrayAdapter.add("St.Gabriel");
//        arrayAdapter.add("St.Helena Incarnation");
//        arrayAdapter.add("St.Malachy");
//        arrayAdapter.add("St.Martin de Porres");
//
//        listView.setAdapter(arrayAdapter);
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
////        dialogBuilder.setTitle("My Dialog");
//        dialogBuilder.setView(listView);
////        dialogBuilder.setPositiveButton("OK", null); // TODO
////        dialogBuilder.setNegativeButton("Cancel", null); // nothing simply dismiss
//
//        final AlertDialog dialog = dialogBuilder.show();
//        dialog.show();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
//                Toast.makeText(getApplicationContext(), " Pillar is Selected " + pos, Toast.LENGTH_SHORT).show();
//                final String Pillars = arrayAdapter.getItem(pos);
//                txt_holly_cross.setText(Pillars);
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WelComeActivity.this);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("PillarsData", Pillars);
//                editor.commit();
//                dialog.hide();
//                // Here I call a method to update listView?
//            }
//        });
//
//
//    }


}
