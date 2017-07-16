package com.mucaroo.characterdailyapp.data;

import android.content.Context;

import com.mucaroo.characterdailyapp.callbacks.AuthResultCallback;
import com.mucaroo.characterdailyapp.callbacks.CreateResultCallback;
import com.mucaroo.characterdailyapp.models.Base;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.Lesson;
import com.mucaroo.characterdailyapp.models.Schedule;
import com.mucaroo.characterdailyapp.models.ScheduleLessonQuote;

/**
 * Created by .Jani on 2/6/2017.
 */

public class DB {

    private static DB mInstance;

    public void authenticateUser(String username, String password, final AuthResultCallback callback) {}
    public void createUser(String username, String password, final CreateResultCallback callback) {}
    public void getLesson(String id, final DBCallback<Lesson> callback) {}
    public void getQuote(String id, final DBCallback<Lesson> callback) {}
    public void getImage(String id, final DBCallback<ImageInfo> callback) {}
    public void getProfileImage(String id, final DBCallback<ImageInfo> callback) {}
    public void getSchedule(String clientId, final DBCallback<Schedule> callback) {}
    public void getScheduleLessonQuote(String clientId, int pillar, int day, String idx, final DBCallback<ScheduleLessonQuote> callback) {}
    public void getBehaviour(String clientId, int pillar, final String idx, final DBCallback<String> callback){}
    public void get(final Class c, String id, final DBCallback<Base> callback) {}

    public static DB getInstance(Context c) {
        if(mInstance == null) {
            mInstance = new FirebaseDB();
        }

        return mInstance;
    }
}
