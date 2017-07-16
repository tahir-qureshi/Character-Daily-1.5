package com.mucaroo.characterdailyapp.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.adapters.HomeAdapter;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.enums.BlockType;
import com.mucaroo.characterdailyapp.models.Article;
import com.mucaroo.characterdailyapp.models.Base;
import com.mucaroo.characterdailyapp.models.Block;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.Lesson;
import com.mucaroo.characterdailyapp.models.Schedule;
import com.mucaroo.characterdailyapp.models.ScheduleLessonQuote;
import com.mucaroo.characterdailyapp.models.SchedulePillarDay;
import com.mucaroo.characterdailyapp.util.Globals;
import com.squareup.picasso.Picasso;

import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements HomeAdapter.OnItemClickCallback {
    public static final String Sname = "MainActivity";

    //  public TextView txt_all_grades, txt_holly_cross;
    protected HomeAdapter mAdapter;
    protected ArrayList<Block> mBlocks = new ArrayList<>();
    ArrayList<String> nyu = new ArrayList<>();
    String jsonListOfSortedCustomerId;
    String fff = "hello";
    public SchedulePillarDay mSchedulePillarDay;
    protected FirebaseDatabase firebaseDatabase;
    public static final String LIST_OF_SORTED_DATA_ID = "json_list_sorted_data_id";
    public final static String PREFERENCE_FILE = "preference_file";
    Boolean aBoolean;
    ListView lListView;
    int day;
    String Gradedata;
    Lesson s;
    Lesson q;
    String pillarIDs;
    public static String Ltitle, Lbody, Lauthor, Limage, Qbody;


    //    ImageView imglesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        pillarIDs = WelComeActivity.pillarsID;

        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        TextView tvclander = (TextView) findViewById(R.id.tv_calander);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);


        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText("INTRODUCTION     ");

        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }
//        Log.d("AllGrades", Gradedata);
//        Log.i(TAG, "onCreate:", Gradedata);

        final String date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ImageDownloading.class));
                finish();
            }
        });


        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });


        //  actionBar.setHomeButtonEnabled(true);


        //   mSchedulePillarDay = new SchedulePillarDay(0, 0);


        setTitle("Loading.....");

        lListView = (ListView) findViewById(R.id.list_main);

        lListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //String value = (String) parent.getItemAtPosition(position);
//                Log.d("onItemClick:", String.valueOf(position));
                if (position == 0) {
                    Intent in = new Intent(MainActivity.this, DailyLessonActivity.class);
                    Ltitle = s.title;
                    Lbody = s.body;
                    Lauthor = s.lesson_author;
                    Limage = s.image;
                    Log.d("onItem", Ltitle);
                    //                    in.putExtra("Title", s.title);
//                    in.putExtra("Body", s.body);
//                    in.putExtra("LessonAuthor", s.lesson_author);
//                    in.putExtra("Image", s.image);
//                    Log.d("onItem", s.body);
//                   startActivity(in);
                    startActivity(in);
                    finish();
                }
                if (position == 1) {
//                    startActivity(new Intent(MainActivity.this, DailyQuoteActivity.class));
                    Intent intent = new Intent(MainActivity.this, DailyQuoteActivity.class);
                    Qbody = q.body;
//                    intent.putExtra("Body", q.body);
//                    Log.d("QuoteBody", q.body);
                    startActivity(intent);
                    finish();
                }
                if (position == 2) {
                    startActivity(new Intent(MainActivity.this, DailyBehaviourActivity.class));
                    finish();
                }
                if (position == 3) {
                    startActivity(new Intent(MainActivity.this, DailyPledgeActivity.class));
                    finish();
                }
            }
        });
        //Schedule ab = new Schedule();
        //ab.getClosestPillar();

        //Globals.ClientID insteadof 0000
        //  setContentView(R.layout.activity_well_come);

        SharedPreferences mSharedPreferences = this.getApplicationContext()
                .getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);

        jsonListOfSortedCustomerId = mSharedPreferences.getString(LIST_OF_SORTED_DATA_ID, "");
        jsonListOfSortedCustomerId = jsonListOfSortedCustomerId.replaceAll("^\\[|]$", "");
        final String str[] = jsonListOfSortedCustomerId.split(",");
        Log.d("orderDate", str[0]);


        SharedPreferences sharedPrefs = getSharedPreferences("com.mucaroo.characterdailyapp", MODE_PRIVATE);
        aBoolean = sharedPrefs.getBoolean("ToggleValue", false);

        DB.getInstance(this).getSchedule(Globals.ClientID, new DBCallback<Schedule>() {
            @Override
            public void success(Schedule o) {
                //Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                o = new Schedule();
                mSchedulePillarDay = (SchedulePillarDay) o.getClosestPillar();
                //  Log.i("Data", mSchedulePillarDay.toString());
                //Toast.makeText(getApplicationContext(),mSchedulePillarDay.toString(),Toast.LENGTH_LONG).show();
//                        mSchedulePillarDay = new SchedulePillarDay();
//                setTitle(Globals.PillarNames[mSchedulePillarDay.pillar]);
//                if (Integer.parseInt(date) < 10) {
                final String date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
                day = Integer.parseInt(date);
                day--;
//                    Log.d("singleday", String.valueOf(day));
//                } else {
//                    final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
//                    day = Integer.parseInt(date);
//                    Log.d("singleday", String.valueOf(day));
//                }
//              Toast.makeText(getApplicationContext(),Globals.PillarNames[mSchedulePillarDay.pillar],Toast.LENGTH_LONG).show();  mSchedulePillarDay.pillar, mSchedulePillarDay.day
                if (aBoolean.equals(true)) {
                    DB.getInstance(MainActivity.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(str[0]), day, Gradedata, new DBCallback<ScheduleLessonQuote>() {
                        @Override
                        public void success(ScheduleLessonQuote o) {
//                        o = new ScheduleLessonQuote();
//                        System.out.println(o);
//                        Log.d("ScheduleLessonQuote", "DAY: " + o.lesson);
                            loadLesson(o.lesson);
                            loadQuote(o.quote);
                        }
                    });

                } else {
                    DB.getInstance(MainActivity.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(pillarIDs), day, Gradedata, new DBCallback<ScheduleLessonQuote>() {
                        @Override
                        public void success(ScheduleLessonQuote o) {
//                        o = new ScheduleLessonQuote();
//                        System.out.println(o);
//                        Log.d("ScheduleLessonQuote", "DAY: " + o.lesson);
                            loadLesson(o.lesson);
                            loadQuote(o.quote);
                        }
                    });
                }
            }
        });


    }

    //back button work
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, WelComeActivity.class);
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
        Intent backMainTest = new Intent(this, WelComeActivity.class);
        startActivity(backMainTest);
        finish();
    }

    private void loadLesson(String id) {
        DB.getInstance(this).getLesson(id, new DBCallback<Lesson>() {
            @Override
            public void success(Lesson o) {
                s = o;
//                Log.d("Lesson11", o.title);
//                Log.d("Lesson11", o.body);
//                Log.d("Lesson11",o.body);
//                o.image;


//                o.image = fff;
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                String url  = sharedPreferences.getString("ImageURL", " ");
//                 //  Log.i("urlurl11", String.valueOf(nyu.size()));
//                o.image = url;
//                s = o;
//                Log.i("asdurl11222", url);
                mBlocks.add(new Block(BlockType.Article, o));

//                mBlocks.add(new Block(BlockType.Quote, o));
//                mBlocks.add(new Block(BlockType.Behavior, o));
//                mBlocks.add(new Block(BlockType.Pledge, o));
//                updateAdapter();
            }
        });

    }

    private void loadQuote(String id) {
        DB.getInstance(this).getQuote(id, new DBCallback<Lesson>() {
            @Override
            public void success(Lesson o) {
                q = o;
//                Log.d("Lesson12", o.toString());
//                Log.d("Lesson11", o.body);
                mBlocks.add(new Block(BlockType.Quote, o));
                mBlocks.add(new Block(BlockType.Behavior, o));
                mBlocks.add(new Block(BlockType.Pledge, o));
                updateAdapter();
            }
        });
    }

    private void updateAdapter() {
        mAdapter = new HomeAdapter(this, 0, mBlocks, this);
//        Log.i("aaaaa", mBlocks.get(1).lesson.quote);
        getMainList().setAdapter(mAdapter);
    }


    @Override
    public void onBlockClick(Block item) {

        switch (item.type) {
            case Article: {
                ArticleActivity.mCurrentArticle = (Lesson) item.article;
                Intent in = new Intent(this, ArticleActivity.class);
                startActivity(in);
                break;
            }
        }

    }
}
