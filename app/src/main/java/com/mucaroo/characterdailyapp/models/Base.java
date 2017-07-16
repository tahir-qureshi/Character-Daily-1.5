package com.mucaroo.characterdailyapp.models;

import android.util.Base64;

import com.google.gson.Gson;
import com.mucaroo.characterdailyapp.annotations.DBInclude;

/**
 * Created by .Jani on 2/8/2017.
 */

public class Base {
    @DBInclude
    public static String rid;

    public String serialize() {
        try {
            return new Gson().toJson(this);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T unserialize(String json) {
        try {
            return (T) new Gson().fromJson(json, this.getClass());
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
