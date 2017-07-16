package com.mucaroo.characterdailyapp.activities;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Zaman Khan on 14-May-17.
 */

public class MyApplication extends Application {

    public ArrayList<String> myGlobalArray = null;

    public MyApplication() {
        myGlobalArray = new ArrayList<String>();
    }
}
