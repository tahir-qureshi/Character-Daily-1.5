package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;

import java.util.HashMap;

public class AllLessonQuoteSubmissionActivity extends AppCompatActivity {

    //    String[] profileData = {"Photos", "Camera"};
    private DatabaseReference fbdatabaseReference;
    EditText quoteBody, quoteAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_submission);
        getSupportActionBar().hide();
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
//
//        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
//        txt_title.setText("SUBMIT A LESSON");

//        ImageView imageAttach = (ImageView) findViewById(R.id.iv_attachment);
        TextView txtCross = (TextView) findViewById(R.id.cross_button);
        quoteBody = (EditText) findViewById(R.id.et_body);
        quoteAuthor = (EditText) findViewById(R.id.txt_attribution);
        Button btnsubmit = (Button) findViewById(R.id.btnSubmit);

        fbdatabaseReference = FirebaseDatabase.getInstance().getReference().child("quotes");
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String body = quoteBody.getText().toString().trim();
                final String Author = quoteAuthor.getText().toString().trim();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                HashMap<String, Object> map = new HashMap<String, Object>();

                map.put("body", body);
                map.put("author", Author);
                map.put("createdAt", System.currentTimeMillis());
                map.put("user", currentFirebaseUser.getUid());
                fbdatabaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(AllLessonQuoteSubmissionActivity.this, AllLessonDailyQuote.class));
                            finish();
                            Toast.makeText(AllLessonQuoteSubmissionActivity.this, "Recorded Inserted Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AllLessonQuoteSubmissionActivity.this, "Recorded Not inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

//        imageAttach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setTheme(R.style.ActionSheetStyleIOS7);
//                ShowActionSheetProfile();
//            }
//        });
        txtCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllLessonQuoteSubmissionActivity.this, AllLessonDailyQuote.class));
                finish();
            }
        });
    }

//    public void ShowActionSheetProfile() {
//        ActionSheetProfile.createBuilder(this, getSupportFragmentManager())
//                .setCancelButtonTitle("Cancel")
//                .setOtherButtonTitles(profileData)
//                .setCancelableOnTouchOutside(true).setListener(this).show();
//    }
//
//    @Override
//    public void onDismissProfile(ActionSheetProfile actionSheet, boolean isCancel) {
//
//    }
//
//    public void onOtherButtonClickProfile(ActionSheetProfile actionSheet, int indexProfile) {
//        String data = profileData[indexProfile];
//        if (data.equals("Photos")) {
//            Intent photopick = new Intent(Intent.ACTION_PICK);
//            photopick.setType("image/*");
//            int RESULT_LOAD_IMAGE = 1;
//            startActivityForResult(photopick, RESULT_LOAD_IMAGE);
//            Toast.makeText(getApplicationContext(), "Hello camera", Toast.LENGTH_LONG).show();
//        }
//        if (data.equals("Camera")) {
//            Intent photopick = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (photopick.resolveActivity(getPackageManager()) != null) {
//                String fileName = "temp.jpg";
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE, fileName);
//                mCapturedImageURI = getContentResolver()
//                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                values);
//                photopick.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//                int REQUEST_IMAGE_CAPTURE = 2;
//                startActivityForResult(photopick, REQUEST_IMAGE_CAPTURE);
//                Toast.makeText(getApplicationContext(), "Hello Photos", Toast.LENGTH_LONG).show();
//            }
//        }
//        Toast.makeText(getApplicationContext(), "Grades item index = " + Gradesdata[indexGrade], Toast.LENGTH_LONG).show();
//        String grades = Gradesdata[indexGrade];
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("GradeData", grades);
//        editor.commit();
//        txt_all_grades.setText(grades);

    public void onBackPressed() {
        startActivity(new Intent(this, AllLessonDailyQuote.class));
        finish();

    }
}
