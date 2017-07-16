package com.mucaroo.characterdailyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mucaroo.characterdailyapp.R;

/**
 * Created by .Jani on 2/6/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    // Register
    protected Button getRegisterButton() { return (Button)findViewById(R.id.btn_register); }
    protected EditText getPasswordConfirmText() { return (EditText)findViewById(R.id.txt_password_confirm); }

    // Generic
    protected Button getBackButton() { return (Button)findViewById(R.id.btn_back); }

    // Login
    protected EditText getEmailText() { return (EditText)findViewById(R.id.txt_email); }
    protected EditText getPasswordText() { return (EditText)findViewById(R.id.txt_password); }
    protected Button getLoginButton() { return (Button)findViewById(R.id.btn_login); }

    // Main
    protected ListView getMainList() { return (ListView)findViewById(R.id.list_main); }

    protected ListView getLessonList() { return (ListView)findViewById(R.id.listAllLessons); }

    // Article
    protected WebView getWebView() { return (WebView)findViewById(R.id.view); }

}
