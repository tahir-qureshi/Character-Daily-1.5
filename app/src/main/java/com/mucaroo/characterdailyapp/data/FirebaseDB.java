package com.mucaroo.characterdailyapp.data;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.callbacks.AuthResultCallback;
import com.mucaroo.characterdailyapp.callbacks.CreateResultCallback;
import com.mucaroo.characterdailyapp.models.Base;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.Lesson;
import com.mucaroo.characterdailyapp.models.Schedule;
import com.mucaroo.characterdailyapp.models.ScheduleLessonQuote;
import com.mucaroo.characterdailyapp.models.User;

import java.io.StringBufferInputStream;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by .Jani on 2/6/2017.
 */

class FirebaseDB extends DB {

    private User mUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDatabase;

    protected FirebaseDB() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void authenticateUser(String username, String password, final AuthResultCallback callback) {

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    User u = new User();
                    u.id = user.getUid();
                    u.email = user.getEmail();
                    u.name = user.getDisplayName();
                    mUser = u;

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                mFirebaseAuth.removeAuthStateListener(mAuthListener);
            }
        };
        mFirebaseAuth.addAuthStateListener(mAuthListener);

        mFirebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "Failed authentication.");
                    callback.onFail();
                } else {
                    Log.d(TAG, "Successful authentication.");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    User u = new User();
                    u.id = user.getUid();
                    u.email = user.getEmail();
                    u.name = user.getDisplayName();
                    mUser = u;
                    callback.onLogin(u);
                }
            }
        });
    }

    //creating user validation if user exist not create the account
    @Override
    public void createUser(String username, String password, final CreateResultCallback callback) {


        mFirebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "Failed creation.");
                    callback.onFail();
                } else {
                    Log.d(TAG, "Created!");
                    callback.onSuccess();
                }
            }
        });
    }

    @Override
    public void getLesson(String id, final DBCallback<Lesson> callback) {

        DatabaseReference ref = mDatabase.getReference("lessons/" + id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lesson lesson = dataSnapshot.getValue(Lesson.class);
                lesson.rid = dataSnapshot.getKey();
//                Log.i("onDataLesson: ", dataSnapshot.toString());
                callback.success(lesson);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getQuote(String id, final DBCallback<Lesson> callback) {

        DatabaseReference ref = mDatabase.getReference("quotes/" + id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lesson lesson = dataSnapshot.getValue(Lesson.class);
                lesson.rid = dataSnapshot.getKey();
                Log.i("onDataLesson: ", dataSnapshot.toString());
                callback.success(lesson);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //here is image getting code
    @Override
    public void getImage(final String id, final DBCallback<ImageInfo> callback) {
        final String iid = "images/" + id;
        Log.i("asdfid", iid);
        DatabaseReference ref = mDatabase.getReference("files/images/");
//        final String a = id;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String key = data.getKey();
                    Log.i("asdfkey", key);
                    ImageInfo image = data.getValue(ImageInfo.class);
                    if (iid.equals(image.file)) {
                        Log.i("asdfgurl", image.url);
                        callback.success(image);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void getProfileImage(final String id, final DBCallback<ImageInfo> callback) {
        final String iid = "userSubmitted/" + id;
        Log.i("asdfid", iid);
        DatabaseReference ref = mDatabase.getReference("files/userSubmitted/");
//        final String a = id;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String key = data.getKey();
                    Log.i("asdfkey", key);
                    ImageInfo image = data.getValue(ImageInfo.class);
                    if (iid.equals(image.file)) {
                        Log.i("asdfgurl", image.url);
                        callback.success(image);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getSchedule(String clientId, final DBCallback<Schedule> callback) {
        DatabaseReference ref = mDatabase.getReference("schedules/" + clientId);//schedules/0000
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Schedule item = dataSnapshot.getValue(Schedule.class);
                    String s = dataSnapshot.getKey();
                    //Log.i("key", s);

                    item.rid = dataSnapshot.getKey();//es line par error ha
                    Log.i("keys", item.rid);
                    callback.success(item);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void getScheduleLessonQuote(String clientId, int pillar, int day, String grade, final DBCallback<ScheduleLessonQuote> callback) {
        DatabaseReference ref = mDatabase.getReference("schedules/" + clientId + "/lessons/" + pillar + "/" + day + "/" + grade);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ScheduleLessonQuote item = dataSnapshot.getValue(ScheduleLessonQuote.class);
                item.rid = dataSnapshot.getKey().toString();
//                Log.i("LessoQuote", item.lesson);
                callback.success(item);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//    @Override
//    public void getMonthofDay(String clientId, int pillar, int day, final DBCallback<ScheduleLessonQuote> callback) {
//        DatabaseReference ref = mDatabase.getReference("schedules/" + clientId + "/lessons/" + pillar + "/" + day );
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ScheduleLessonQuote item = dataSnapshot.getValue(ScheduleLessonQuote.class);
//                item.rid = dataSnapshot.getKey().toString();
////                Log.i("LessoQuote", item.lesson);
//                callback.success(item);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    ///////////////////////////////////////////////////

    public void getBehaviour(String clientId, int pillar, final String idx, final DBCallback<String> callback) {
        DatabaseReference ref = mDatabase.getReference("schedules/" + clientId + "/behaviors/" + pillar);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String s = data.getKey().toString();
//                    Log.d("behaveData", idx);
                    if (s.equals(idx)) {
                        String ss = data.getValue(true).toString();
                        callback.success(ss);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //////////////////////////////////////////////////////
//    public void getBehavior(String clientId, int pillar, String grade, final DBCallback<>)
    @Override
    public void get(final Class c, String id, final DBCallback<Base> callback) {
        DatabaseReference ref = mDatabase.getReference(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Base result = (Base) dataSnapshot.getValue(c);
                result.rid = dataSnapshot.getKey();
                callback.success(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
