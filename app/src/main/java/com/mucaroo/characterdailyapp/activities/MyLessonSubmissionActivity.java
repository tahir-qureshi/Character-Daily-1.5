package com.mucaroo.characterdailyapp.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mucaroo.characterdailyapp.R;

import java.io.File;
import java.util.HashMap;

public class MyLessonSubmissionActivity extends AppCompatActivity implements ActionSheetProfile.ActionSheetListener {

    String[] profileData = {"Photos", "Camera"};
    private Uri mCapturedImageURI;
    private EditText etTitle, etBody, etAuthor, grade, pillar;
    private DatabaseReference mlsdatabaseReference,mldatabaseReference;
    String Gradedata;
    String lestitle, lesbody, lesAuthor;
    String imageName;
    static String imageURL;
    private StorageReference mlsStorageReference;
    private int RESULT_LOAD_IMAGE = 1, REQUEST_IMAGE_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_submission);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyLessonSubmissionActivity.this);
        Gradedata = sharedPreferences.getString("GradeData", "All Grade");
        if (Gradedata.equals("All Grade") || Gradedata.equals("4")) {
            Gradedata = String.valueOf(0);
//            Log.d("AllGrades1112", Gradedata);
        }

        getSupportActionBar().hide();
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
//
//        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
//        txt_title.setText("SUBMIT A LESSON");

        ImageView imageAttach = (ImageView) findViewById(R.id.iv_attachment);
        TextView txtCross = (TextView) findViewById(R.id.cross_button);
        etTitle = (EditText) findViewById(R.id.et_LessonTitle);
        etAuthor = (EditText) findViewById(R.id.et_attribution);
        etBody = (EditText) findViewById(R.id.et_body);

        mlsStorageReference = FirebaseStorage.getInstance().getReference();

        Button submitLesson = (Button) findViewById(R.id.btnSubmit);

        imageAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme(R.style.ActionSheetStyleIOS7);
                ShowActionSheetProfile();
            }
        });
        txtCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyLessonSubmissionActivity.this, DailyLessonActivity.class));
                finish();
            }
        });
        mlsdatabaseReference = FirebaseDatabase.getInstance().getReference().child("lessons");
        mldatabaseReference = FirebaseDatabase.getInstance().getReference().child("files").child("images");
        submitLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lestitle = etTitle.getText().toString().trim();
                lesbody = etBody.getText().toString().trim();
                lesAuthor = etAuthor.getText().toString().trim();
                int grade = Integer.parseInt(Gradedata);
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("title", lestitle);
                map.put("lesson_author", lesAuthor);
                map.put("grade", grade);
                map.put("pillar", 1);
                map.put("body", lesbody);
                map.put("image", imageName);
                map.put("createdAt", System.currentTimeMillis());
                map.put("user", currentFirebaseUser.getUid());
                mlsdatabaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MyLessonSubmissionActivity.this, DailyLessonActivity.class));
                            finish();
                            Toast.makeText(MyLessonSubmissionActivity.this, "Recorded Inserted Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MyLessonSubmissionActivity.this, "Recorded Not inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    public void ShowActionSheetProfile() {
        ActionSheetProfile.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(profileData)
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismissProfile(ActionSheetProfile actionSheet, boolean isCancel) {

    }

    public void onOtherButtonClickProfile(ActionSheetProfile actionSheet, int indexProfile) {
        String data = profileData[indexProfile];
        if (data.equals("Photos")) {
            Intent photopick = new Intent(Intent.ACTION_PICK);
            photopick.setType("image/*");
            int RESULT_LOAD_IMAGE = 1;
            startActivityForResult(photopick, RESULT_LOAD_IMAGE);
//            onActivityResult(1,0,photopick);
//            Toast.makeText(getApplicationContext(), "Hello camera", Toast.LENGTH_LONG).show();
        }
        if (data.equals("Camera")) {
            Intent photopick = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (photopick.resolveActivity(getPackageManager()) != null) {
                String fileName = "temp.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                mCapturedImageURI = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                photopick.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

                startActivityForResult(photopick, REQUEST_IMAGE_CAPTURE);
//                onActivityResult(1,0,photopick);
                Toast.makeText(getApplicationContext(), "Hello Photos", Toast.LENGTH_LONG).show();
            }
        }
//        Toast.makeText(getApplicationContext(), "Grades item index = " + Gradesdata[indexGrade], Toast.LENGTH_LONG).show();
//        String grades = Gradesdata[indexGrade];
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("GradeData", grades);
//        editor.commit();
//        txt_all_grades.setText(grades);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) || (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            File f = new File(picturePath);
            StorageReference storageReference = mlsStorageReference.child("images").child(selectedImage.getLastPathSegment());
            storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri = taskSnapshot.getDownloadUrl();
                    imageURL = String.valueOf(uri);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("description", "");
                    map.put("type", "jpeg");
                    map.put("file", "images/" + imageName);
                    map.put("url", imageURL);
                    mldatabaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                startActivity(new Intent(MyLessonSubmissionActivity.this, MoreOptionsActivity.class));
//                                finish();
                                Toast.makeText(MyLessonSubmissionActivity.this, "Recorded Inserted Success", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MyLessonSubmissionActivity.this, "Recorded Not inserted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MyLessonSubmissionActivity.this, "Please Select The JPG Image", Toast.LENGTH_LONG).show();
                        }
                    });

                    Log.d("Succesed", "Success");
                }
            });
            imageName = f.getName();
            Log.d("imageName", imageName);
            cursor.close();

            // String picturePath contains the path of selected Image
        }
    }


    public void onBackPressed() {
        startActivity(new Intent(this, DailyLessonActivity.class));
        finish();

    }
}
