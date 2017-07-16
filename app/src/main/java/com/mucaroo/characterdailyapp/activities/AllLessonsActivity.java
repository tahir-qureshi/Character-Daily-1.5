package com.mucaroo.characterdailyapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.adapters.AllLessonsAdapter;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.ImageInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AllLessonsActivity extends AppCompatActivity {
    ArrayList<String> abcx;
    private DatabaseReference mDatabase, imageDatabase;
    private ListView lListView;
    ArrayList<ArrayList> mainList;
    int count = 0;
    ArrayList<String> obj;
    AllLessonsAdapter adap;
    public String url;
    String test;
    //    static String ssss;
    Context c;
    ProgressDialog progressDialog = null;

    public AllLessonsActivity() {
    }
    //zaman arraylist declarations
//    private ArrayList<String> mUserName = new ArrayList<>();
//    private ArrayList<String> mKeys = new ArrayList<>();
    // ArrayList<String> objimage = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lessons);



        abcx = new ArrayList<String>();
        setTitle("All LESSONS");


        c = getApplicationContext();
        mainList = new ArrayList<>();
        lListView = (ListView) findViewById(R.id.listAllLessons);
//        adap = new AllLessonsAdapter(c, mainList);
        lListView.setAdapter(adap);

        LinearLayout layout = (LinearLayout) findViewById(R.id.listitem);
        Button btn_more = (Button) findViewById(R.id.btn_more_options);
        Button btn_all = (Button) findViewById(R.id.btn_all_lessons);
        TextView tvclander = (TextView) findViewById(R.id.tv_calander);

        btn_all.setBackgroundColor(Color.TRANSPARENT);
        btn_more.setBackgroundColor(Color.TRANSPARENT);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);

        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AllLessonsActivity.this, MainActivity.class));
//                finish();
//            }
//        });
        lListView.setFocusable(false);
        lListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                String value = (String)parent.getItemAtPosition(position);
                startActivity(new Intent(AllLessonsActivity.this, MainActivity.class));
                // Toast.makeText(AllLessonsActivity.this, "Hello" , Toast.LENGTH_LONG).show();
                finish();
//                Intent intent = new Intent(AllLessonsActivity.this, MainActivity.class);
//                String message = "abc";
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonsActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        progressDialog = new ProgressDialog(AllLessonsActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progressdialog);
        progressDialog.show();
        Window window = progressDialog.getWindow();

        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("lessons");
        mDatabase.addChildEventListener(new ChildEventListener() {

            //final DataSnapshot dataSnapshot
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                obj = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    count++;
                    String ss = ds.getKey();
                    if (ss.equals("grade")) {
                        //ds.getValue(long.class);
                        obj.add(ds.getValue(true).toString());
                    } else if (ss.equals("pillar")) {
                        obj.add(ds.getValue(true).toString());
                    } else if (ss.equals("image")) {
                        String temp = String.valueOf(ds.getValue(String.class));
//                        String data = tempe(temp);
//                        Log.i("dataURL", data);

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://aubriaco-characterdailyapp.appspot.com/" + temp);//.child(temp);
//                        storageRef.getDownloadUrl();
//        obj.add("https://firebasestorage.googleapis.com/v0/b/aubriaco-characterdailyapp.appspot.com/o/001.jpeg?alt=media&token=264ce0e5-cb32-4d10-968c-779eb4dfa03b");
//                                addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                url = task.toString();
//                                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
//                            }
//                        });
//                        Toast.makeText(AllLessonsActivity.this, url.toString(), Toast.LENGTH_LONG).show();

//                        String url = storageRef.getDownloadUrl().toString();

                        ///   obj.add(url);
//                        String data;
//                        if(storageRef.getDownloadUrl().isSuccessful()){
//                           Uri uu = storageRef.getDownloadUrl().getResult();
//                            Log.i("najam", uu.toString());
//                        }

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                                obj.add(url);
                                Log.i("url11", url);

                                // obj.add(data);
//                                String[] result = new String[1];
                                //   ssss = data;
                                //Log.i("URL222", "url2 " + data);
//                                for(int i=0;i<obj.size();i++)
//                                Toast.makeText(AllLessonsActivity.this, obj.get(i).toString(), Toast.LENGTH_LONG).show();
//                                SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
//                                editor.putString("Uri", uri.toString());
//                                editor.commit();

//                                obj.add(ur
                                //System.out.println(data);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                Log.i("exception", exception.toString());
                                //  Toast.makeText(AllLessonsActivity.this, "image not dowloaded", Toast.LENGTH_SHORT).show();
                            }
                        });

                        obj.add(ds.getValue(String.class));


//                        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
//                        String restoredText = prefs.getString("Uri", null);

//                        Log.i("URL2", "urL2 " + url);//here is null
                        //   Toast.makeText(AllLessonsActivity.this, restoredText, Toast.LENGTH_LONG).show();
//                        String temp = String.valueOf(obj.add(ds.getValue(String.class)));
//                        DB.getInstance(AllLessonsActivity.this).getImage(temp, new DBCallback<ImageInfo>() {
//                            @Override
//                            public void success(ImageInfo o) {
//                                Toast.makeText(getApplicationContext(),"Image Download",Toast.LENGTH_LONG).show();
//                            }
//                        });

                    } else if (ss.equals("body")){
                        obj.add(ds.getValue(String.class));

                    }else if (ss.equals("lesson_author")){
                        obj.add(ds.getValue(String.class));

                    }else if (ss.equals("title")){
                        obj.add(ds.getValue(String.class));
                        Log.i("Obj", String.valueOf(obj));
                    }
                }


//                for (int x = 0; x < obj.size(); x++)
//                    System.out.println(x);
//                for (int y = 0; y < objimage.size(); y++)
//                    System.out.println("Zaman Khan");

//                Log.i("URL1", "urL1 " + url);
                //Log.i("Obj", String.valueOf(obj));
                mainList.add(obj);
                adap.notifyDataSetChanged();
//                adap = new AllLessonsAdapter(c, mainList);
                if (count >= dataSnapshot.getChildrenCount()) {
                    progressDialog.dismiss();
                }
//                mainList.add(obj);
//                adap.notifyDataSetChanged();
//                adap = new AllLessonsAdapter(c, mainList);
//                if (count >= dataSnapshot.getChildrenCount()) {
//                    progressDialog.dismiss();
//                }
                //  for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    String key = dataSnapshot.getKey();
//                    String value = dataSnapshot.getValue(String.class);
//                    mKeys.add(key);
                //    Log.i("onChildAdded:HP", key);
                //  mTxtView.setText(value);
//                    mUserName.add(value);
//                    arrayAdapter.notifyDataSetChanged();
                // }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //  int latest = dataSnapshot.child("Campaigns").child(key).child("count").getValue(Integer.class);
//                String key = dataSnapshot.getKey();
//                String value = dataSnapshot.getValue(String.class);
//
//
//                int index = mKeys.indexOf(key);
//                mUserName.set(index, value);
//                arrayAdapter.notifyDataSetChanged();

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


    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, WelComeActivity.class);
        startActivity(backMainTest);
        finish();
    }

    public String tempe(String temp) {

        return url;
    }
}
