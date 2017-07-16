package com.mucaroo.characterdailyapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;

import java.util.ArrayList;

public class ImageGettingActivity extends Activity {

    private DatabaseReference imageDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_getting);


        imageDatabase = FirebaseDatabase.getInstance().getReference().child("files").child("images");
        imageDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ArrayList<String> objimage = new ArrayList<String>();
                for (DataSnapshot dds : dataSnapshot.getChildren()) {
                    String keys = dds.getKey();
                            objimage.add(dds.getValue(String.class));
              //      System.out.println(objimage.add(dds.getValue(String.class)));
                }

                Intent intent = new Intent(ImageGettingActivity.this, AllLessonsActivity.class);
                intent.putExtra("imagdata", objimage);
                startActivity(intent);
//                                System.out.print("Zaman Khan");
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
}
