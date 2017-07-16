package com.mucaroo.characterdailyapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CatholicFaithActivity extends AppCompatActivity {

    private DatabaseReference cdatabaseReference;
    ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catholic_faith);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText("CATHOLIC FAITH");


        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progressdialog);
        progressDialog.show();
        Window window = progressDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);

        final TextView textView = (TextView) findViewById(R.id.tvCatholicFaith);

        cdatabaseReference = FirebaseDatabase.getInstance().getReference().child("evergreen").child("clients").child("-KdWYvdsShSJii4pVO8s").child("0");

        cdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //   ArrayList<String> arrayList = new ArrayList<String>();
//                for (DataSnapshot ds: dataSnapshot.getChildren()){

//                    if(ss.equals("0")) {
                String ss = dataSnapshot.getKey();
                String sss = dataSnapshot.getValue(String.class);
                if(ss.equals("body")) {
                    textView.setText(sss);
                    progressDialog.dismiss();
                }
//                    }
                ///  }
//                textView.setText(arrL.get(0));
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


    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MoreCharacterToolsActivity.class);
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
        Intent backMainTest = new Intent(this, MoreCharacterToolsActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
