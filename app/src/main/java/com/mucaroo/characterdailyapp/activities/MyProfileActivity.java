package com.mucaroo.characterdailyapp.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.User;
import com.mucaroo.characterdailyapp.util.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.net.ssl.SSLEngineResult;

public class MyProfileActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener, ActionSheetProfile.ActionSheetListener {

    protected TextView tvclander, txt_all_grades, txt_Email, txt_join, txt_pillar;
    protected Button btnLesson, btnMore, btnSignOut;
    String[] Gradesdata = {"K-2nd", "3rd-5th", "6th-8th", "9th-12th", "All Grades"};
    String[] profileData = {"Photos", "Camera"};
    ImageView imagePro;
    private FirebaseUser currentFirebaseUser;
    private StorageReference mlsStorageReference;
    private Uri mCapturedImageURI;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mpDatabaseReference, databaseReference, imageRef;

    Dialog dialog = null;
    static String imageURL;
    static String imageName;
    long imageSize;
    String userId;
//    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText("MY PROFILE       ");

        btnLesson = (Button) findViewById(R.id.btn_all_lessons);
        btnMore = (Button) findViewById(R.id.btn_more_options);
        tvclander = (TextView) findViewById(R.id.tv_calander);
        txt_all_grades = (TextView) findViewById(R.id.tvAllGrades);
        imagePro = (ImageView) findViewById(R.id.imageProfile);
        btnSignOut = (Button) findViewById(R.id.btn_signout);
        txt_Email = (TextView) findViewById(R.id.tvEmail);
        txt_pillar = (TextView) findViewById(R.id.tvPillarOrder);
        txt_join = (TextView) findViewById(R.id.tvJoinGroup);


        btnLesson.setBackgroundColor(Color.TRANSPARENT);
        btnMore.setBackgroundColor(Color.TRANSPARENT);
        final String date = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        tvclander.setText(date + "\nToday");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
        String Gradedata = sharedPreferences.getString("Data", "All Grades");
        txt_all_grades.setText(Gradedata);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        email = currentFirebaseUser.getEmail();
        userId = currentFirebaseUser.getUid();

        mlsStorageReference = FirebaseStorage.getInstance().getReference();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        txt_Email.setText(currentFirebaseUser.getEmail());
        imageRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("image");

      imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              Picasso.with(MyProfileActivity.this).load(dataSnapshot.getValue(String.class)).into(imagePro);
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
//
//        txt_name.setText(currentFirebaseUser.getDisplayName());
        //   Toast.makeText(MyProfileActivity.this, currentFirebaseUser.getDisplayName(), Toast.LENGTH_LONG).show();
        //currentFirebaseUser.getDisplayName();
//
//        DB.getInstance(MyProfileActivity.this).getProfileImage("20170220_181157.jpg", new DBCallback<ImageInfo>() {
//            @Override
//            public void success(ImageInfo f) {
//                Log.d("dailLessonURL", f.url);
//                Picasso.with(MyProfileActivity.this).load(f.url).into(imagePro);
//            }
//        });
//        if (imageURL != null) {
//            Picasso.with(MyProfileActivity.this).load(imageURL).fit().centerCrop().into(imagePro);
//        }
        btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this, ImageDownloading.class));
                finish();
            }
        });

        tvclander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this, MoreOptionsActivity.class));
                finish();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                Utility.reset(MyProfileActivity.this);
                startActivity(new Intent(MyProfileActivity.this, LoginActivity.class));
                finish();

            }
        });
        txt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
//                Utility.showProgress(MyProfileActivity.this);
            }
        });
        txt_pillar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this, PillarOrderActivity.class));
                finish();
            }
        });


    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAllGrades:
                setTheme(R.style.ActionSheetStyleIOS7);
                showActionSheetGrades();
                break;
            case R.id.imageProfile:
                setTheme(R.style.ActionSheetStyleIOS7);
                ShowActionSheetProfile();
                break;
        }
    }

    public void showActionSheetGrades() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(Gradesdata)
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismissGrades(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClickGrades(ActionSheet actionSheet, int indexGrade) {
//        Toast.makeText(getApplicationContext(), "Grades item index = " + Gradesdata[indexGrade], Toast.LENGTH_LONG).show();
//        String grades = Gradesdata[indexGrade];
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("GradeData", grades);
//        editor.commit();
        String grades = Gradesdata[indexGrade];
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("GradeData", String.valueOf(indexGrade));
        editor.putString("Data", grades);
        editor.commit();
        txt_all_grades.setText(grades);

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

    @Override
    public void onOtherButtonClickProfile(ActionSheetProfile actionSheet, int indexProfile) {
        String data = profileData[indexProfile];
        if (data.equals("Photos")) {
            Intent photopick = new Intent(Intent.ACTION_PICK);
            photopick.setType("image/jpeg");
            int RESULT_LOAD_IMAGE = 1;
            startActivityForResult(photopick, RESULT_LOAD_IMAGE);
            Toast.makeText(getApplicationContext(), "Hello camera", Toast.LENGTH_LONG).show();
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
                int REQUEST_IMAGE_CAPTURE = 2;
                startActivityForResult(photopick, REQUEST_IMAGE_CAPTURE);
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

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
//            try {
            final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                imagePro.setImageBitmap(selectedImage);

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            File f = new File(picturePath);
            imageName = f.getName();
            imageSize = f.length() / 1024;

            mpDatabaseReference = FirebaseDatabase.getInstance().getReference().child("files").child("userSubmitted");
            StorageReference storageReference = mlsStorageReference.child("userSubmitted").child(imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri = taskSnapshot.getDownloadUrl();
                    imageURL = String.valueOf(uri);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("description", "");
                    map.put("size", imageSize);
                    map.put("type", "jpeg");
                    map.put("file", "userSubmitted/" + imageName);
                    map.put("url", imageURL);
                    mpDatabaseReference.child(userId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                startActivity(new Intent(MyProfileActivity.this, MoreOptionsActivity.class));
//                                finish();
                                Toast.makeText(MyProfileActivity.this, "Recorded Inserted Success", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MyProfileActivity.this, "Recorded Not inserted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MyProfileActivity.this, "Please Select The JPEG Image", Toast.LENGTH_LONG).show();
                        }
                    });
                    Picasso.with(MyProfileActivity.this).load(uri).into(imagePro);
//                    startActivity(getIntent());
//                    Log.d("Succesed", String.valueOf(uri));
                    databaseReference.child("users").child(userId).child("image").setValue(imageURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                }
            });

//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//            }

        } else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    public void ShowDialog() {
        dialog = new Dialog(MyProfileActivity.this);
        dialog.setContentView(R.layout.custom_profile_dialogbox);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void onClickedCancel(View view) {
//        startActivity(new Intent(this, MyProfileActivity.class));
//        Utility.DissmissDialog(MyProfileActivity.this);
//        finish();
        dialog.dismiss();
    }

    public void onClickedOk(View view) {
        dialog.dismiss();
    }

    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MoreOptionsActivity.class);
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
        Intent backMainTest = new Intent(this, MoreOptionsActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
