package com.mucaroo.characterdailyapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mucaroo.characterdailyapp.actions.BottomSheetLayout;
import com.mucaroo.characterdailyapp.actions.IntentPickerSheetView;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.Article;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.ScheduleLessonQuote;
import com.mucaroo.characterdailyapp.util.Globals;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DailyLessonActivity extends AppCompatActivity implements ActionSheetProfile.ActionSheetListener {

    int count;
    String s ;
    String[] fileData = {"Leave Feedback", "Submit my own Lesson", "Print"};
    Dialog dialog = null;
    TextView txt_title, txt_lesson_author, txt_body;
    String title, body, lesson_author, image;
    protected BottomSheetLayout bottomSheetLayout;
    private DatabaseReference databaseReference, postRef, statusRef;
    private FirebaseUser currentFirebaseUser;
    String email, userId;
    public String circle = "false";
    ImageView daiilyImage;
    String someVarB;
    String Gradedata;
    int day;

    static final String STATE_USER = "user";
    String ret;
    private String mUser;
//    @Override
//    public void onSaveInstanceState(Bundle savedIState) {
//        super.onSaveInstanceState(savedIState);
//       savedIState.putString(STATE_USER, mUser);
//        Log.d("onRestoreInstanceState1", "onRestoreInstanceState");
//        // Always call the superclass so it can save the view hierarchy state
//
////    }
//@Override
//public void onSaveInstanceState(Bundle savedInstanceState) {
//    super.onSaveInstanceState(savedInstanceState);
//    savedInstanceState.putString(STATE_USER, mUser);
//    // Always call the superclass so it can save the view hierarchy state
//
//}
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        ret = (String) savedInstanceState.get(STATE_USER);
//        Log.d("onRestoreInsta11", ret);
//    }


    String currentdate = String.valueOf((System.currentTimeMillis()/(1000*60 * 60 *24 )));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//       s = MainActivity.Sname;
//        Log.i("Sstring", " 0" + s);

//        String message = savedInstanceState.getString("someVarB");

//        if (savedInstanceState != null) {
//            // Restore value of members from saved state
//            mUser = savedInstanceState.getString(someVarB);
//            Log.d("onRestoreInstance1", ret);
//        } else {
//            // Probably initialize members with default values for a new instance
//            mUser = "NewUser";
//            Log.d("onRestoreInstanceState", mUser);
//        }
        title = MainActivity.Ltitle;//   getIntent().getStringExtra("Title");
        body = MainActivity.Lbody;// getIntent().getStringExtra("Body");
        lesson_author =MainActivity.Lauthor;// getIntent().getStringExtra("LessonAuthor");
        image = MainActivity.Limage;// getIntent().getStringExtra("Image");

        setContentView(R.layout.activity_daily_lesson);

//        String meggg = savedInstanceState.get("someVarB");


//        savedInstanceState.putString("one", "helo");
//        String dat;
//        dat = savedInstanceState.getString("one");


//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);
        getSupportActionBar().hide();
        final TextView txt_back = (TextView) findViewById(R.id.back_arrow);
        final TextView txt_setting = (TextView) findViewById(R.id.setting_image);
        daiilyImage = (ImageView) findViewById(R.id.dailyLessonImage);

        txt_title = (TextView) findViewById

                (R.id.tvtitle);
        txt_lesson_author = (TextView) findViewById(R.id.tvlesson_author);
        txt_body = (TextView) findViewById(R.id.tvbody);

        txt_title.setText(title);
        txt_lesson_author.setText(lesson_author);
        txt_body.setText(body);
//        txt_title.setText("DAILY LESSON");
//        email = String.valueOf(System.currentTimeMillis());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DailyLessonActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = currentFirebaseUser.getEmail();
        userId = currentFirebaseUser.getUid();
        final ImageView imageViewLessonCircle = (ImageView) findViewById(R.id.iv_lesson_circle);
        final ImageView imageAttachment = (ImageView) findViewById(R.id.ivattach);
        final LinearLayout linearLayoutNotes = (LinearLayout) findViewById(R.id.LinearlayoutNotes);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);

        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        TextView tvclander = (TextView) findViewById(R.id.tv_calander);

        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);


        final String date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
        day = Integer.parseInt(date);
        tvclander.setText(date + "\nToday");

        //download image daillesson

        DB.getInstance(DailyLessonActivity.this).getImage(image, new DBCallback<ImageInfo>() {
            @Override
            public void success(ImageInfo f) {
                Log.d("dailLessonURL", f.url);
                Picasso.with(DailyLessonActivity.this).load(f.url).into(daiilyImage);
            }
        });

        final String pillarID= WelComeActivity.pillarsID;


        DB.getInstance(DailyLessonActivity.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(pillarID), day, Gradedata, new DBCallback<ScheduleLessonQuote>() {
                    @Override
                    public void success(final ScheduleLessonQuote o) {
                        final String lessonID = o.lesson;
                        statusRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Chalanges").child(currentdate).child(Gradedata).child(lessonID);
                        statusRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    String sss = dataSnapshot.getKey().toString();
                                    Log.d("circlesss", sss);
                                    if (sss.equals("Lesson")) {
                                        circle = dataSnapshot.getValue(String.class);
                                        Log.d("circle", circle);
                                        if (circle.equals("true")) {
                                            imageViewLessonCircle.setImageResource(R.mipmap.fill_circle);
                                        } else {
                                            imageViewLessonCircle.setImageResource(R.mipmap.empty_circle);
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
                startActivity(new Intent(DailyLessonActivity.this, MainActivity.class));
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
                startActivity(new Intent(DailyLessonActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyLessonActivity.this, ImageDownloading.class));
                finish();
            }
        });
        linearLayoutNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyLessonActivity.this, NotesActivity.class));
                finish();
            }
        });

//        imageAttachment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(DailyLessonActivity.this, NotesActivity.class));
//                finish();
//
////                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
////                shareIntent.putExtra(Intent.EXTRA_TEXT, "shareText");
////                shareIntent.setType("text/plain");
////                IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(DailyLessonActivity.this, shareIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
////                    @Override
////                    public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
////                        bottomSheetLayout.dismissSheet();
////                        startActivity(activityInfo.getConcreteIntent(shareIntent));
////                    }
////                });
////                // Filter out built in sharing options such as bluetooth and beam.
////                intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
////                    @Override
////                    public boolean include(IntentPickerSheetView.ActivityInfo info) {
////                        return !info.componentName.getPackageName().startsWith("com.andriod");
////                    }
////                });
////                // Sort activities in reverse order for no good reason
////                intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
////                    @Override
////                    public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
////                        return rhs.label.compareTo(lhs.label);
////                    }
////                });
////
////                // Add custom mixin example
////                Drawable customDrawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null);
////                IntentPickerSheetView.ActivityInfo customInfo = new IntentPickerSheetView.ActivityInfo(customDrawable, "Custom mix-in", DailyLessonActivity.this, LoginActivity.class);
////                intentPickerSheet.setMixins(Collections.singletonList(customInfo));
//////                try {
////                    bottomSheetLayout.showWithSheetView(intentPickerSheet);
//////                } catch (Exception e) {
//////                }
////
//
//            }
//        });
        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyLessonActivity.this, MainActivity.class));
                finish();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        imageViewLessonCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DB.getInstance(DailyLessonActivity.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(pillarID), day, Gradedata, new DBCallback<ScheduleLessonQuote>() {
                    @Override
                    public void success(final ScheduleLessonQuote o) {
                        final String lessonID = o.lesson;
                        Log.d("Lessonid", lessonID);


                        if (circle.equals("false")) {
                            circle = "true";
                            imageViewLessonCircle.setImageResource(R.mipmap.fill_circle);

                            databaseReference.child("users").child(userId).child("Chalanges").child(currentdate).child(Gradedata).child(lessonID).child("Lesson").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                            Toast.makeText(DailyLessonActivity.this, "Lesson Submit Success", Toast.LENGTH_LONG).show();
                                }
                            });

//                            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
////                                if (data.getKey().equals(userId)) {
////                                    Toast.makeText(DailyLessonActivity.this, "Exits", Toast.LENGTH_LONG).show();
//                                        String s = data.getKey().toString();
////                                if(data.child(userId).exists()) {
////                                        if (s.equals("-KfSVB-o-koiLToah0Fw") || s.equals("behavior")
////                                                || s.equals("client") || s.equals("createdAt") || s.equals("email") || s.equals("pledge")) {
//
////                                        } else {
////
////                                            HashMap<String, Object> map = new HashMap<String, Object>();
////                                            map.put(lessonID, "true");
////                                            map.put("client", "deflaut");
////                                            map.put("createdAt", System.currentTimeMillis());
////                                            map.put("email", email);
////                                            databaseReference.child("users").child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                                @Override
////                                                public void onComplete(@NonNull Task<Void> task) {
//////                                            Toast.makeText(DailyLessonActivity.this, "Lesson Score Submit Success", Toast.LENGTH_LONG).show();
////                                                }
////                                            });
////                                            //do something
////                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });
                        } else {
                            circle = "false";
                            imageViewLessonCircle.setImageResource(R.mipmap.empty_circle);
                            databaseReference.child("users").child(userId).child("Chalanges").child(currentdate).child(Gradedata).child(lessonID).child("Lesson").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(DailyLessonActivity.this, "Lesson Submit Success", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });

            }
        });
    }

    public void onCancelled(DatabaseError databaseError) {

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //        return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // app icon in action bar clicked; go home
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//                return true;
        int id = item.getItemId();
        if (id == R.id.options) {
            setTheme(R.style.ActionSheetStyleIOS7);
            ShowActionSheetProfile();
//            Toast.makeText(getApplicationContext(), "Item option Selected", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
//            Toast.makeText(getApplicationContext(), "Item home Selected", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (data.equals("Submit my own Lesson")) {
//            Toast.makeText(getApplicationContext(), "Lesson Submission is Selected", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MyLessonSubmissionActivity.class));
            finish();
        }
        if (data.equals("Print")) {
            Toast.makeText(getApplicationContext(), "Print is Selected", Toast.LENGTH_LONG).show();
        }

    }


    public void ShowDialog() {
        dialog = new Dialog(DailyLessonActivity.this);
        dialog.setContentView(R.layout.custom_dailylesson_dialogbox);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MainActivity.class);
        startActivity(backMainTest);
        finish();
    }
}