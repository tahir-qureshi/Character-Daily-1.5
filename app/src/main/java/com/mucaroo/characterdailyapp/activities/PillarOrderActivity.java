package com.mucaroo.characterdailyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.graphics.Color;
import android.icu.util.TimeZone;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.adapters.CustomerListAdapter;
import com.mucaroo.characterdailyapp.listeners.OnCustomerListChangedListener;
import com.mucaroo.characterdailyapp.listeners.OnStartDragListener;
import com.mucaroo.characterdailyapp.models.Pillars;
import com.mucaroo.characterdailyapp.models.SampleData;
import com.mucaroo.characterdailyapp.models.SimpleItemTouchHelperCallback;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.awt.font.TextAttribute;
import java.sql.Date;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.expandableListViewStyle;
import static android.R.attr.x;
import static android.R.attr.y;
import static android.R.id.toggle;

public class PillarOrderActivity extends AppCompatActivity implements OnCustomerListChangedListener,
        OnStartDragListener {


    private Button btnLesson, btnMore;
    private TextView tvclander, txt_title1;
    private ToggleButton toggleButton;
    private DatabaseReference poDatabaseReference;
    private TextView txtzeroPillar, txtonepillar, txttwopillar, txtthreepillar, txtfourpillar, txtfivepillar;
    String formattedDate, key;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private CustomerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;
    private List<Pillars> mCustomers;


    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String LIST_OF_SORTED_DATA_ID = "json_list_sorted_data_id";
    public final static String PREFERENCE_FILE = "preference_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pillar_order_drag_activity);

//        setContentView(R.layout.activity_pillar_order);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText("PILLAR ORDER        ");
//        txt_title.setText("PILLAR ORDER                 ");

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);


//        txt_title.setText(" " + "PILLAR ORDER");
        mSharedPreferences = this.getApplicationContext()
                .getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        Log.d("mEditor", "Share" + PREFERENCE_FILE);
        mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
//        mRecyclerView.setVisibility(View.INVISIBLE);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    Toast.makeText(PillarOrderActivity.this, "Checked", Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(PillarOrderActivity.this, "UnChecked", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        setupRecyclerView();

//        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        //      final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pillarList);
//        linearLayout.setVisibility(LinearLayout.GONE);
//        txt_title1 = (TextView) findViewById(R.id.tvtitle);
//
        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);
        tvclander = (TextView) findViewById(R.id.tv_calander);
        SharedPreferences sharedPrefs = getSharedPreferences("com.mucaroo.characterdailyapp", MODE_PRIVATE);
        toggleButton.setChecked(sharedPrefs.getBoolean("ToggleValue", true));
        if (toggleButton.isChecked()) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);
//        String txt = txt_title1.getText().toString();
//        txt_title1.setText("MY PROFILE");
        txt_title.setText(" " + "PILLAR ORDER");
        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");
//        linearLayout.setVisibility(LinearLayout.GONE);

//        linearLayout.setVisibility(LinearLayout.GONE);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    linearLayout.setVisibility(LinearLayout.GONE);
////                    Toast.makeText(PillarOrderActivity.this, "Checked", Toast.LENGTH_LONG).show();
////                    tvStateofToggleButton.setText("ON");
//                } else {
//                    poDatabaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("Pillars");//.child("default").child("0");
//                    poDatabaseReference.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            Log.d("pilkeys", String.valueOf(dataSnapshot));
//                            String key = dataSnapshot.getKey().toString();
//                            if (key.equals("0")) {
//                                long unixdate = dataSnapshot.getValue(Long.class);
//                                Date date = new Date(unixdate * 1000L);
//                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
////                                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//                                String formattedDate = sdf.format(date);
//                                System.out.println(formattedDate);
//                                txtzeroPillar.setText(formattedDate);
//                                Log.d("pilkeys1", formattedDate);
//                            }
//                            if (key.equals("1")) {
//                                long unixdate = dataSnapshot.getValue(Long.class);
//                                Date date = new Date(unixdate * 1000L);
//                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
////                                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//                                String formattedDate = sdf.format(date);
////                                System.out.println(formattedDate);
//                                txtonepillar.setText(formattedDate);
//                                Log.d("pilkeys1", formattedDate);
//                            }
//                            if (key.equals("2")) {
//                                long unixdate = dataSnapshot.getValue(Long.class);
//                                Date date = new Date(unixdate * 1000L);
//                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
////                                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//                                String formattedDate = sdf.format(date);
////                                System.out.println(formattedDate);
//                                txttwopillar.setText(formattedDate);
//                                Log.d("pilkeys1", formattedDate);
//                            }
//                            if (key.equals("3")) {
//                                long unixdate = dataSnapshot.getValue(Long.class);
//                                Date date = new Date(unixdate * 1000L);
//                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
////                                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//                                String formattedDate = sdf.format(date);
////                                System.out.println(formattedDate);
//                                txtthreepillar.setText(formattedDate);
//                                Log.d("pilkeys1", formattedDate);
//                            }
//                            if (key.equals("4")) {
//                                long unixdate = dataSnapshot.getValue(Long.class);
//                                Log.d("pilkeys1", String.valueOf(unixdate));
//                                Date date = new Date(unixdate * 1000L);
//                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
////                                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//                                String formattedDate = sdf.format(date);
////                                System.out.println(formattedDate);
//                                txtfourpillar.setText(formattedDate);
//                                Log.d("pilkeys1", formattedDate);
//                            }
//                            if (key.equals("5")) {
//                                long unixdate = dataSnapshot.getValue(Long.class);
//                                Date date = new Date(unixdate * 1000L);
//                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
////                                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//                                String formattedDate = sdf.format(date);
////                                System.out.println(formattedDate);
//                                txtfivepillar.setText(formattedDate);
//                                Log.d("pilkeys1", formattedDate);
//                            }
//
////                            for (DataSnapshot data : dataSnapshot.getChildren()) {
////                                String keys = data.getKey().toString();
////                                Log.d("pilkeys1", keys);
////                            }
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                    linearLayout.setVisibility(View.VISIBLE);
////                    Toast.makeText(PillarOrderActivity.this, "UnChecked", Toast.LENGTH_LONG).show();
////                    tvStateofToggleButton.setText("OFF");
//                }
//
//            }
//        });

        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PillarOrderActivity.this, ImageDownloading.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PillarOrderActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PillarOrderActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });
    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        if (toggleButton.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("com.mucaroo.characterdailyapp", MODE_PRIVATE).edit();
            editor.putBoolean("ToggleValue", true);
            mRecyclerView.setVisibility(View.VISIBLE);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("com.mucaroo.characterdailyapp", MODE_PRIVATE).edit();
            editor.putBoolean("ToggleValue", false);
            mRecyclerView.setVisibility(View.INVISIBLE);
            editor.commit();
        }

//        // Is the toggle on?
//        boolean on = ((ToggleButton) view).isChecked();
////        mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
//        if (on) {
//            // Enable vibrate
//       //     Toast.makeText(PillarOrderActivity.this, "Checked", Toast.LENGTH_LONG).show();
////            mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
//            mRecyclerView.setVisibility(View.VISIBLE);
//        } else {
//            // Disable vibrate
////            Toast.makeText(PillarOrderActivity.this, "UnChecked", Toast.LENGTH_LONG).show();
//
//            mRecyclerView.setVisibility(View.INVISIBLE);
//        }
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCustomers = getSampleData();
        //setup the adapter with empty list
        mAdapter = new CustomerListAdapter(mCustomers, this, this, this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onNoteListChanged(List<Pillars> customers) {
        //after drag and drop operation, the new list of Customers is passed in here

        //create a List of Long to hold the Ids of the
        //Customers in the List
        List<Long> listOfSortedCustomerId = new ArrayList<Long>();

        for (Pillars customer : customers) {
            listOfSortedCustomerId.add(customer.getId());
        }

        //convert the List of Longs to a JSON string
        Gson gson = new Gson();
        String jsonListOfSortedCustomerIds = gson.toJson(listOfSortedCustomerId);


        //save to SharedPreference
        mEditor.putString(LIST_OF_SORTED_DATA_ID, jsonListOfSortedCustomerIds).commit();
        mEditor.commit();


    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);

    }

    private List<Pillars> getSampleData() {


        //Get the sample data
        List<Pillars> customerList = SampleData.addSampleCustomers();

        //create an empty array to hold the list of sorted Customers
        List<Pillars> sortedCustomers = new ArrayList<Pillars>();

        //get the JSON array of the ordered of sorted customers
        String jsonListOfSortedCustomerId = mSharedPreferences.getString(LIST_OF_SORTED_DATA_ID, "");

        Log.d("orderDate", jsonListOfSortedCustomerId);
        //check for null
        if (!jsonListOfSortedCustomerId.isEmpty()) {

            //convert JSON array into a List<Long>
            Gson gson = new Gson();
            List<Long> listOfSortedCustomersId = gson.fromJson(jsonListOfSortedCustomerId, new TypeToken<List<Long>>() {
            }.getType());

            //build sorted list
            if (listOfSortedCustomersId != null && listOfSortedCustomersId.size() > 0) {
                for (Long id : listOfSortedCustomersId) {
                    for (Pillars customer : customerList) {
                        if (customer.getId().equals(id)) {
                            sortedCustomers.add(customer);
                            customerList.remove(customer);
                            break;
                        }
                    }
                }
            }

            //if there are still customers that were not in the sorted list
            //maybe they were added after the last drag and drop
            //add them to the sorted list
            if (customerList.size() > 0) {
                sortedCustomers.addAll(customerList);
            }

            return sortedCustomers;
        } else {
            return customerList;
        }
    }


    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MyProfileActivity.class);
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
        Intent backMainTest = new Intent(this, MyProfileActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
