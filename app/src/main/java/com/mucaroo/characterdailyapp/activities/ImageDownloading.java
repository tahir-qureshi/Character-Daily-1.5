package com.mucaroo.characterdailyapp.activities;

import java.util.Locale;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.GregorianCalendar;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.adapters.AllLessonsAdapter;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.enums.BlockType;
import com.mucaroo.characterdailyapp.models.Article;
import com.mucaroo.characterdailyapp.models.Block;
import com.mucaroo.characterdailyapp.models.Lesson;
import com.mucaroo.characterdailyapp.models.Schedule;
import com.mucaroo.characterdailyapp.models.ScheduleLessonQuote;
import com.mucaroo.characterdailyapp.models.SchedulePillarDay;
import com.mucaroo.characterdailyapp.util.Globals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ImageDownloading extends BaseActivity implements AllLessonsAdapter.OnItemClickCallbacked {

    //    private DatabaseReference mDatabase, pillarData;
    protected AllLessonsAdapter mAdapter;
    //    ArrayList<ArrayList> arrL;
    Context c;
    //    int count;
    String Gradedata;
    public SchedulePillarDay mSchedulePillarDay;
    String jsonListOfSortedCustomerId;
    private DatabaseReference databaseReference;
    public static String MonthofYear;
    public ArrayList<ArrayList<Block>> mB = new ArrayList<>();
    protected ArrayList<Block> mBlocks = new ArrayList<>();
    protected ArrayList<String> mBlocksQuote = new ArrayList<>();
    protected ArrayList<Integer> monthofdays = new ArrayList<>();
    public ArrayList<String> LessonIDs = new ArrayList<>();
    public ArrayList<String> QuoteIDs = new ArrayList<>();
    String pillarIDs;

    public static final String LIST_OF_SORTED_DATA_ID = "json_list_sorted_data_id";
    ProgressDialog progressDialog = null;
    public static String ALtitle, ALbody, ALauthor, ALimage, AQbody, LessonID, QuoteID,ADate;
    public static int LessonGrade;
    public static int AraayListSize;
    Article L;
    Lesson Q;
    Boolean aBoolean;
    int xcv;
    int dayOfMonth;
    int i;

    ListView lListView;
    private TextView txt_grade, tvclander;
    public String url;
    public final static String PREFERENCE_FILE = "preference_file";
    public String pillars;
    int day, start, end, loop;
    //    Date dateStart; Date dateEnd; Date dateCurrent; Date dateTemp;
    int check = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_downloading);
        setContentView(R.layout.activity_all_lessons);
        setTitle("All LESSONS");

        c = getApplicationContext();

        lListView = (ListView) findViewById(R.id.listAllLessons);


        pillarIDs = WelComeActivity.pillarsID;
        Log.d("PillarsIDS", pillarIDs);
//        lListView.setDivider(null);
//        lListView.setDividerHeight(0);


//        SearchView searchView = (SearchView) findViewById(R.id.search);
//        searchView.setQueryHint("Search");
        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        final EditText editTextSearch = (EditText) findViewById(R.id.et_search);
//        editText.setFocusable(true);
        tvclander = (TextView) findViewById(R.id.tv_calander);
//        txt_grade = (TextView) findViewById(R.id.tv_grade);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ImageDownloading.this);
//        String data = sharedPreferences.getString("GradeData", null);
//        txt_grade.setText("Filter:" + data);

        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ImageDownloading.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        Log.d("Gradedata111", Gradedata);
        if (Gradedata.equals("All Grade")) {
            Gradedata = String.valueOf(4);

        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);


        final String date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editTextSearch.getText().toString().toLowerCase(Locale.getDefault());
                mAdapter.filter(text);
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("lessons").child(pillarIDs);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey().toString();
                monthofdays.add(Integer.valueOf(key)+1);
                Log.d("pilardays", key);
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


//        for(int x =0;x<20;x++){
//
//        }


//        final String dateCheck = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("Pillars");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String key = dataSnapshot.getKey().toString();
//                long keyvalue = dataSnapshot.getValue(long.class);
//                Date date = new Date(keyvalue * 1000L);
//                SimpleDateFormat sdf = new SimpleDateFormat("MMM");
//                String formattedDate = sdf.format(date);
//                Log.d("MonthPillarsvalue", String.valueOf(keyvalue));
//                Log.d("pilkeyMonth", formattedDate);
//                Log.d("pilkeyMonth", dateCheck);
//                if (formattedDate.equals(dateCheck)) {
//                    MonthofYear = formattedDate;//xyz
//                    Log.d("pilkeyMonth111", formattedDate);
//                    Log.d("pilkeyMonth111", "DateExist");
//                } else if (key.equals("0")) {
//                    MonthofYear = formattedDate;//nov"Nov"
//                    Log.d("pilkeyMonth111", formattedDate);
//                    Log.d("pilkeyMonth111", "NOT DateExist");
//                }
//                monthsofPillars.add(formattedDate);
//
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


//        lListView.setFocusable(false);

//        lListView.setEmptyView();
        lListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                String value = (String)parent.getItemAtPosition(position);

                startActivity(new Intent(ImageDownloading.this, IntroductionActivity.class));
                ALtitle = mBlocks.get(position).article.title;
                ALauthor = mBlocks.get(position).article.lesson_author;
                Log.d("lessonAuthor", position + mBlocks.get(position).article.lesson_author);
                ALbody = mBlocks.get(position).article.body;
                ALimage = mBlocks.get(position).article.image;
                LessonGrade = mBlocks.get(position).article.grade;
                LessonID = LessonIDs.get(position);
                QuoteID = QuoteIDs.get(position);
                AQbody = mBlocksQuote.get(position);
                if(Gradedata.equals("4") || Gradedata.equals("All Grade")){
                    ADate = monthofdays.get(position/4).toString();
                }
                else{
                    ADate = monthofdays.get(position).toString();
                }

                Log.d("Quotebody", "" + QuoteID);
                Lesson o = new Lesson();
                String kbs = String.valueOf(mBlocks.get(position).lesson);
                Log.d("lessonAuthor", kbs);
//                Log o.lesson author
//                Log o.body
//                Log.d("onItemClick",mBlocks.get(position).lesson.quote);
//                AQbody = mBlocks.get(position).lesson.quote;// Q.body;
//                 mBlocks.get(position).Q.body;
                // mBlocks.get(position).Q.
//                ALtitle = L.title;
//                ALbody = L.body;

//                ALauthor = L.lesson_author;
//                Log.d("lessonAuthor1","lesson"+ ALauthor);

//                ALimage = L.image;

                // Toast.makeText(AllLessonsActivity.this, "Hello" , Toast.LENGTH_LONG).show();
                finish();
//                Intent intent = new Intent(AllLessonsActivity.this, MainActivity.class);
//                String message = "abc";
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
            }
        });

//        / perform set on query text listener event
        mAdapter = new AllLessonsAdapter(this, 0, mBlocks, monthofdays, Gradedata, this);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//// do something on text submit
////                Toast.makeText(ImageDownloading.this, query, Toast.LENGTH_LONG).show();
////                return false;
//                String text =   query;///editsearch.getText().toString().toLowerCase(Locale.getDefault());
//                mAdapter.filter(text);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//// do something when text changes
//                Toast.makeText(ImageDownloading.this, "Hello", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageDownloading.this, MoreOptionsActivity.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageDownloading.this, MainActivity.class));
                finish();
            }
        });


        Calendar calenderLessaon = Calendar.getInstance();
        Date dateCurrent = calenderLessaon.getTime();
        calenderLessaon.add(Calendar.DATE, -10);
        Date dateStart = calenderLessaon.getTime();
        calenderLessaon.add(Calendar.DATE, +20);
        Date dateEnd = calenderLessaon.getTime();
        int indexLoop = -11;

//        do {
//            indexLoop++;
//            Calendar calenderLoop = Calendar.getInstance();
//            calenderLoop.add(Calendar.DATE, indexLoop);
//            Date dateLoop = calenderLoop.getTime();
////            Log.d("indexLoop", String.valueOf(indexLoop));
////            Log.d("dateLoop", String.valueOf(dateLoop));
//
//            dayOfMonth = calenderLoop.get(Calendar.DAY_OF_MONTH);
//            Log.d("monthdateLoop", String.valueOf(dayOfMonth));
//            monthofdays.add(dayOfMonth);
//
//
//        }
//        while (indexLoop < 10);

//        for (int k=0;k<monthofdays.size();k++){
//                    Log.d("ArayListData", k + " " + String.valueOf(monthofdays.get(k)));
////                    if(keys.equals(String.valueOf(monthofdays.get(k)))){
////                        Log.d(" Data111","found"+ keys +String.valueOf(monthofdays.get(k)));
////                    }
////                    else {
////                        Log.d("Data000", "Not found"+String.valueOf(monthofdays.get(k)));
////                    }
//                }
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("lessons").child("0");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                String keys = dataSnapshot.child("0").child("0").child("lesson").getKey();
//                Log.i("CurrentKey:", keys);
//                Log.i("CurrentKey:", String.valueOf(dataSnapshot.child("lesson").getValue()));
//                //Log.i("CurrentKey:", String.valueOf(dataSnapshot.child("0").child("lesson").getValue()));
//                //Log.i("CurrentKeyChildren: ","values"+ String.valueOf(dataSnapshot.child("0").child("0").child("lesson")));
//                //String value1 = dataSnapshot.child("0").child("0").getValue(String.class);//.toString();//.toString();
//                //Log.i("CurrentKeyValue: ","data"+ value1);
////                for (int k=0;k<monthofdays.size();k++){
////                    Log.d("ArayListData", k + " " + String.valueOf(monthofdays.get(k)));
////                    Log.d("ArayListData", String.valueOf(monthofdays.get(k)));
////                    Log.d("ArayListData", String.valueOf(monthofdays.get(k)));
////
//////                    if(keys.equals(String.valueOf(monthofdays.get(k)))){
//////                        Log.d(" Data111","found"+ keys +String.valueOf(monthofdays.get(k)));
//////                    }
//////                    else {
//////                        Log.d("Data000", "Not found"+String.valueOf(monthofdays.get(k)));
//////                    }
////                }
//
////                }
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
         SharedPreferences mSharedPreferences = this.getApplicationContext()
                 .getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);


        jsonListOfSortedCustomerId = mSharedPreferences.getString(LIST_OF_SORTED_DATA_ID, "");
        jsonListOfSortedCustomerId = jsonListOfSortedCustomerId.replaceAll("^\\[|]$","");
        final String str[] = jsonListOfSortedCustomerId.split(",");
        Log.d("orderDate", str[0]);


        SharedPreferences sharedPrefs = getSharedPreferences("com.mucaroo.characterdailyapp", MODE_PRIVATE);
         aBoolean= sharedPrefs.getBoolean("ToggleValue", false);
        Log.d("ToggleValues", String.valueOf(aBoolean));

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
//                DateFormat dateformate = new SimpleDateFormat("d");//.format(Calendar.getInstance().getTime());
//                Date date = new Date();
//                String Datetoday = dateformate.format(date);
////               javaDate dateCurrent = date;
//                Log.d("DAYStoday", Datetoday);
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DATE, -10);
//
//                Date todaydatestart = cal.getTime();
//                String startdate = dateformate.format(todaydatestart);
//
//                Calendar calenderLessaon = Calendar.getInstance();
//                Date dateCurrent = calenderLessaon.getTime();
//                Date dateLoop = calenderLessaon.getTime();
//                calenderLessaon.add(Calendar.DATE, -10);
//                Date dateStart = (java.sql.Date) calenderLessaon.getTime();
//                calenderLessaon.add(Calendar.DATE, +20);
//                Date dateEnd = (java.sql.Date) calenderLessaon.getTime();
//                Calendar calenderLoop = Calendar.getInstance();
//                int indexLoop = 0;
//
//                do {
//
//
//                    indexLoop++;
//                    calenderLoop.add(Calendar.DATE, 1);
//                    dateLoop = calenderLoop.getTime();
//                    Log.d("indexLoop", String.valueOf(indexLoop));
//                    Log.d("dateLoop", String.valueOf(dateLoop));
//                }
//                while (indexLoop < 20);
//
//
//
//
//                Log.d("DAYSstart", startdate);
//                Calendar cal2 = Calendar.getInstance();
//                cal2.add(Calendar.DATE, 10);
//                Date todaydateend = cal2.getTime();
//                String dateFinish = dateformate.format(todaydateend);
//                Log.d("DAYSfinish", dateFinish);
////                    final String date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
////                    day = Integer.parseInt(date);
////                } else {
////                    day = Integer.parseInt(date);
////                }
////              Toast.makeText(getApplicationContext(),Globals.PillarNames[mSchedulePillarDay.pillar],Toast.LENGTH_LONG).show();  mSchedulePillarDay.pillar, mSchedulePillarDay.day
////                for (int j = 0; j < 14; j++) {
////                    for(int j = Integer.parseInt(formatdate); j> Integer.parseInt(Datetoday); j++){
//////                        Log.d("DAYSForward", String.valueOf(j));
////                        if(j==27){
////                            j=0;
////                        }
////                        if(j==26){
////                            j=0;
////                        }
////
////                    }
//
//                day = Integer.parseInt(Datetoday);
//                start = Integer.parseInt(startdate);
//                end = Integer.parseInt(dateFinish);
////                Date dateTimeTemp = start;
//
//
//                loop = start;
//                // Check if we should take pillar of the month or user-selected pillar?
//                //pillarOfTheMonth = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("Pillars");
////                databaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("lessons").child("0");
////                databaseReference.addChildEventListener(new ChildEventListener() {
////                    @Override
////                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////
////
////
//////                for(DataSnapshot data : dataSnapshot.getChildren()){
////                        String keys = dataSnapshot.getKey().toString();
////                        Log.i("kays", keys);
////                        if(String.valueOf(loop) == keys){
////                            Log.d(" Data111", String.valueOf(loop));
////                        }
////                        else {
////                            Log.d("data111", "Not found");
////                        }
//////                }
////                    }
////
////                    @Override
////                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////
////                    }
////
////                    @Override
////                    public void onChildRemoved(DataSnapshot dataSnapshot) {
////
////                    }
////
////                    @Override
////                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////
////                    }
////
////                    @Override
////                    public void onCancelled(DatabaseError databaseError) {
////
////                    }
////                });
//
////                day = Integer.parseInt(Datetoday);
////                start = Integer.parseInt(startdate);
////                end = Integer.parseInt(dateFinish);
////
////
////                loop = start;

//                do {


//                for (int x=0;x<monthsofPillars.size();x++) {
//                    final String date = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
//                    Log.d("ArayListDataMontn", date);
//                    if(date.equals(String.valueOf(monthsofPillars.get(x)))){
//                        Log.d("ArayListDataMontn", "found");
//                    }else {
//                        Log.d("ArayListDataMontn", "MonthNOTfound");
//                    }
//
//                }
                if(aBoolean.equals(true)){
                    for (int k = 0; k < monthofdays.size(); k++) {
                        Log.d("ArayListData", k + "//" + String.valueOf(monthofdays.get(k)));
                        if (check == Integer.parseInt(Gradedata)) {
                            for (i = 0; i < 4; i++) {
                                //months of days        //Grades
//                            if(loop>26) {
                                DB.getInstance(ImageDownloading.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(str[0]), k, String.valueOf(i), new DBCallback<ScheduleLessonQuote>() {
                                    @Override
                                    public void success(ScheduleLessonQuote o) {
                                        LessonIDs.add(o.lesson);
                                        QuoteIDs.add(o.quote);
                                        loadLesson(o.lesson);
                                        loadQuote(o.quote);
                                    }
                                });
                            }

                        } else {
                            DB.getInstance(ImageDownloading.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(str[0]), k, Gradedata, new DBCallback<ScheduleLessonQuote>() {
                                @Override
                                public void success(ScheduleLessonQuote o) {
                                    LessonIDs.add(o.lesson);
                                    QuoteIDs.add(o.quote);
                                    loadLesson(o.lesson);
                                    loadQuote(o.quote);
                                }
                            });
                        }
                    }
                }else {
                    for (int k = 0; k < monthofdays.size(); k++) {
                        Log.d("ArayListData", k + "//" + String.valueOf(monthofdays.get(k)));
                        if (check == Integer.parseInt(Gradedata)) {
                            for (i = 0; i < 4; i++) {
                                //months of days        //Grades
//                            if(loop>26) {
                                DB.getInstance(ImageDownloading.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(pillarIDs), k, String.valueOf(i), new DBCallback<ScheduleLessonQuote>() {
                                    @Override
                                    public void success(ScheduleLessonQuote o) {
                                        LessonIDs.add(o.lesson);
                                        QuoteIDs.add(o.quote);
                                        loadLesson(o.lesson);
                                        loadQuote(o.quote);
                                    }
                                });
                            }

                        } else {
                            DB.getInstance(ImageDownloading.this).getScheduleLessonQuote(Globals.ClientID, Integer.parseInt(pillarIDs), k, Gradedata, new DBCallback<ScheduleLessonQuote>() {
                                @Override
                                public void success(ScheduleLessonQuote o) {
                                    LessonIDs.add(o.lesson);
                                    QuoteIDs.add(o.quote);
                                    loadLesson(o.lesson);
                                    loadQuote(o.quote);
                                }
                            });
                        }
                    }
                }
            }
        });


//        progressDialog = new ProgressDialog(ImageDownloading.this);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.custom_progressdialog);
//        progressDialog.show();
//        Window window = progressDialog.getWindow();
//
//        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        progressDialog.setCancelable(false);


//        arrL = new ArrayList<ArrayList>();
//
//
//        obj = new ArrayList<String>();
//        obj1 = new ArrayList<String>();
//        obj2 = new ArrayList<String>();
//        obj3 = new ArrayList<String>();
//        obj4 = new ArrayList<String>();
//        obj5 = new ArrayList<String>();
////getReferenceFromUrl("https://aubriaco-characterdailyapp.firebaseio.com/")
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("lessons");
//        mDatabase.addChildEventListener(new ChildEventListener() {
//
//            //final DataSnapshot dataSnapshot
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                obj = new ArrayList<String>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    count++;
//                    String ss = ds.getKey();
//
//                    if (ss.equals("grade")) {
//                        //ds.getValue(long.class);
//                        obj.add(ds.getValue(true).toString());
//                    } else if (ss.equals("pillar")) {
//                        // obj1.add(ds.getValue(true).toString());
//                        //Pillars = ds.getValue(true).toString();
//                        // for (DataSnapshot ssd : dataSnapshot.child("schedules").child("default").child("Pillars").child(Pillars).getChildren()) {
//                        obj1.add(ds.getValue(true).toString());
////                            Log.i("obj", String.valueOf(obj.size()));
////                        }
////                        pillarData = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("Pillars").child(Pillars);
////                        pillarData.addChildEventListener(new ChildEventListener() {
////                            @Override
////                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                                long unixsecond = (long) dataSnapshot.getValue();
////                                Date date = new Date(unixsecond * 1000L);
////                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
////                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4"));
////                                String formattedDate = simpleDateFormat.format(date);
////                                System.out.println(formattedDate);
////                                Log.i("url1122", formattedDate);
////                                obj1.add(formattedDate);
////
////                            }
////
////                            @Override
////                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////
////                            }
////
////                            @Override
////                            public void onChildRemoved(DataSnapshot dataSnapshot) {
////
////                            }
////
////                            @Override
////                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////
////                            }
////
////                            @Override
////                            public void onCancelled(DatabaseError databaseError) {
////
////                            }
////                        });
//                    } else if (ss.equals("image")) {
//                        String temp = String.valueOf(ds.getValue(String.class));
//                        FirebaseStorage storage = FirebaseStorage.getInstance();
//                        StorageReference storageRef = storage.getReferenceFromUrl("gs://aubriaco-characterdailyapp.appspot.com/" + temp);//.child(temp);
//                        storageRef.getDownloadUrl();
////        obj.add("https://firebasestorage.googleapis.com/v0/b/aubriaco-characterdailyapp.appspot.com/o/001.jpeg?alt=media&token=264ce0e5-cb32-4d10-968c-779eb4dfa03b");
////                                addOnCompleteListener(new OnCompleteListener<Uri>() {
////                            @Override
////                            public void onComplete(@NonNull Task<Uri> task) {
////                                url = task.toString();
////                                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
////                            }
////                        });
////                        Toast.makeText(AllLessonsActivity.this, url.toString(), Toast.LENGTH_LONG).show();
//
////                        String url = storageRef.getDownloadUrl().toString();
//
//                        ///   obj.add(url);
////                        String data;
////                        if(storageRef.getDownloadUrl().isSuccessful()){
////                           Uri uu = storageRef.getDownloadUrl().getResult();
////                            Log.i("najam", uu.toString());
////                        }
//
//                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                url = uri.toString();
//                                //abcx.add(url);
//                                obj2.add(url);
//                                xcv = xcv + 1;
//                                Log.i("url11", url);
//                                if (xcv == 3) {
//                                    Log.i("obj", String.valueOf(obj.size()));
//                                    Log.i("obj1", String.valueOf(obj1.get(0)));
//                                    Log.i("obj2", String.valueOf(obj2.size()));
//                                    Log.i("obj3", String.valueOf(obj3.size()));
//                                    Log.i("obj4", String.valueOf(obj4.size()));
//                                    Log.i("obj5", String.valueOf(obj5.size()));
//
//                                    for (int i = 0; i < obj.size(); i++) {
//                                        ArrayList<String> o1 = new ArrayList<String>();
//                                        o1.add(obj.get(i));
//                                        o1.add(obj1.get(i));
//                                        o1.add(obj2.get(i));
//                                        o1.add(obj3.get(i));
//                                        o1.add(obj4.get(i));
//                                        o1.add(obj5.get(i));
//
//                                        arrL.add(o1);
//
//                                    }
//
//                                    adap = new AllLessonsAdapter(c, arrL);
//                                    lListView.setAdapter(adap);
////                                    progressDialog.dismiss();
//
//
//                                }
//
//
//                                // obj.add(data);
////                                String[] result = new String[1];
//                                //   ssss = data;
//                                //Log.i("URL222", "url2 " + data);
////                                for(int i=0;i<obj.size();i++)
////                                Toast.makeText(AllLessonsActivity.this, obj.get(i).toString(), Toast.LENGTH_LONG).show();
////                                SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
////                                editor.putString("Uri", uri.toString());
////                                editor.commit();
//
////                                obj.add(ur
//                                //System.out.println(data);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                Log.i("exception", exception.toString());
//                                //  Toast.makeText(AllLessonsActivity.this, "image not dowloaded", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
////                        obj.add(url);
//
//
////                        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
////                        String restoredText = prefs.getString("Uri", null);
//
////                        Log.i("URL2", "urL2 " + url);//here is null
//                        //   Toast.makeText(AllLessonsActivity.this, restoredText, Toast.LENGTH_LONG).show();
////                        String temp = String.valueOf(obj.add(ds.getValue(String.class)));
////                        DB.getInstance(AllLessonsActivity.this).getImage(temp, new DBCallback<ImageInfo>() {
////                            @Override
////                            public void success(ImageInfo o) {
////                                Toast.makeText(getApplicationContext(),"Image Download",Toast.LENGTH_LONG).show();
////                            }
////                        });
//
//                    } else if (ss.equals("body")) {
//                        obj3.add(ds.getValue(String.class));
//
//                    } else if (ss.equals("lesson_author")) {
//
//                        obj4.add(ds.getValue(String.class));
//                    } else if (ss.equals("title")) {
//                        obj5.add(ds.getValue(String.class));
//                    }
//                }
//                if (count >= dataSnapshot.getChildrenCount()) {
////                    progressDialog.dismiss();
//                }
//
////                for (int x = 0; x < obj.size(); x++)
////                    System.out.println(x);
////                for (int y = 0; y < objimage.size(); y++)
////                    System.out.println("Zaman Khan");
//
////                Log.i("URL1", "urL1 " + url);
//                //Log.i("Obj", String.valueOf(obj));
////                mainList.add(obj);
////                adap.notifyDataSetChanged();
////                adap = new AllLessonsAdapter(c, mainList);
////                if (count >= dataSnapshot.getChildrenCount()) {
////                    progressDialog.dismiss();
////                }
////                mainList.add(obj);
////                adap.notifyDataSetChanged();
////                adap = new AllLessonsAdapter(c, mainList);
////                if (count >= dataSnapshot.getChildrenCount()) {
////                    progressDialog.dismiss();
////                }
//                //  for (DataSnapshot child : dataSnapshot.getChildren()) {
////                    String key = dataSnapshot.getKey();
////                    String value = dataSnapshot.getValue(String.class);
////                    mKeys.add(key);
//                //    Log.i("onChildAdded:HP", key);
//                //  mTxtView.setText(value);
////                    mUserName.add(value);
////                    arrayAdapter.notifyDataSetChanged();
//                // }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                //  int latest = dataSnapshot.child("Campaigns").child(key).child("count").getValue(Integer.class);
////                String key = dataSnapshot.getKey();
////                String value = dataSnapshot.getValue(String.class);
////
////
////                int index = mKeys.indexOf(key);
////                mUserName.set(index, value);
////                arrayAdapter.notifyDataSetChanged();
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
//
////        startActivity(new Intent(ImageDownloading.this, AllLessonsActivity.class));
//        Intent intent = new Intent(ImageDownloading.this, AllLessonsActivity.class);
//        intent.putExtra("obj", obj);
//        intent.putExtra("obj1", obj1);
//        intent.putExtra("obj2", obj2);
//        intent.putExtra("obj3", obj3);
//        intent.putExtra("obj4", obj4);
//        intent.putExtra("obj5", obj5);
//
//        Log.i("obj", obj.toString());
//        Log.i("obj2", obj2.toString());
//        startActivity(intent);

//        finish();

    }

    private void loadLesson(String id) {
        DB.getInstance(this).getLesson(id, new DBCallback<Lesson>() {
            @Override
            public void success(Lesson o) {
                L = o;
                mBlocks.add(new Block(BlockType.Article, o));
                upDateAdapter();
            }
        });
    }

    private void loadQuote(String id) {
        DB.getInstance(this).getQuote(id, new DBCallback<Lesson>() {
            @Override
            public void success(Lesson o) {
                Q = o;
//                Log.d("Lesson12", o.toString());
//                Log.d("Lesson11", o.body);
                mBlocksQuote.add(o.body);

//                upDateAdapter();

//                mBlocks.add(new Block(BlockType.Behavior, o));
//                mBlocks.add(new Block(BlockType.Pledge, o));
//                upDateAdapter();
            }
        });
    }

    private void upDateAdapter() {
        mAdapter = new AllLessonsAdapter(this, 0, mBlocks, monthofdays, Gradedata, this);
        getLessonList().setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, WelComeActivity.class);
        startActivity(backMainTest);
        finish();
    }

    @Override
    public void onBlockClick(Block item) {
    }
}
