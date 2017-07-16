package com.mucaroo.characterdailyapp.callbacks;

import com.mucaroo.characterdailyapp.models.User;

/**
 * Created by .Jani on 2/6/2017.
 */

public interface AuthResultCallback {

    void onLogin(User user);
    void onFail();
}
