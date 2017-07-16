package com.mucaroo.characterdailyapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.callbacks.AuthResultCallback;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.models.User;
import com.mucaroo.characterdailyapp.util.Utility;

public class LoginActivity extends BaseActivity {

    public static final String TAG = "LoginActivity";
    private Button button;
    //    ProgressDialog progressDialog;
    String email;
    //final Context context = this;
    SharedPreferences prefs;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        switch (requestCode) {
            case 1: {
                if (resultCode == 1) {

                    email = data.getStringExtra("email");
                    String password = data.getStringExtra("password");
                    getEmailText().setText(prefs.getString("autoSave", " "));
//                    getEmailText().setText(email);
                    getPasswordText().setText(password);
                    login();
                }
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        getEmailText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                prefs.edit().putString("autoSave", charSequence.toString()).commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                prefs.edit().putString("autoSave", editable.toString()).commit();
            }
        });
        getSupportActionBar().hide();
        User user = Utility.getUser(this);
        if (user != null) {
            login(false, user.email, user.password);
            //her MainActivity define replace with welcomeactivity
            startActivity(new Intent(LoginActivity.this, WelComeActivity.class));
            finish();
            return;
        }

        getLoginButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
//                progressDialog = new ProgressDialog(LoginActivity.this);
//                progressDialog.show();
//                progressDialog.setContentView(R.layout.custom_progressdialog);
//                progressDialog.show();
//                Window window = progressDialog.getWindow();
//
//                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                progressDialog.setCancelable(false);
            }
        });

        getRegisterButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

//        button = (Button) findViewById(R.id.buttonShowCustomDialog);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, DailyChallengeScoreActivity.class));
//                finish();
////                final Dialog dialog = new Dialog(LoginActivity.this);
////                dialog.setContentView(R.layout.custom_login_dialogbox);
////                dialog.setTitle("Title...");
////                Window window = dialog.getWindow();
////                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
////                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
////                dialog.show();
//            }
//        });
    }


    private void register() {
        Intent in = new Intent(this, RegisterActivity.class);
        startActivityForResult(in, 1);
    }

    private void login() {
        login(true, null, null);
    }

    private void login(final boolean alerts, String email, String password) {

        if (alerts) {
            email = getEmailText().getText().toString();
            password = getPasswordText().getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in email and password.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        final String fpassword = password;

        DB.getInstance(this).authenticateUser(email, password, new AuthResultCallback() {
            @Override
            public void onLogin(User user) {
                Log.d(TAG, "User logged in!");
                if (alerts) {
                    getEmailText().setText("");
                    getPasswordText().setText("");
                    user.password = fpassword;
                    Utility.storeUser(LoginActivity.this, user);
                    startActivity(new Intent(LoginActivity.this, WelComeActivity.class));
//                    progressDialog.dismiss();
                    finish();
                }
            }

            @Override
            public void onFail() {
                Log.d(TAG, "Failed login.");
                if (alerts)
                    // Utility.showAlert(LoginActivity.this, "Failed login.", "Invalid email/password.");
//                    Utility.showProgress(LoginActivity.this);
                    ShowDialog();
            }
        });


    }

    public void ShowDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.custom_login_dialogbox);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    public void onClickedTry(View v) {
        startActivity(new Intent(this, LoginActivity.class));
        Toast.makeText(LoginActivity.this, "hi try again", Toast.LENGTH_LONG).show();
    }

    public void onClickedSendMail(View v) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = getEmailText().getText().toString();
        ;// "zamanciit@gmail.com";
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
                            Toast.makeText(LoginActivity.this, "Mail Sent", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });

    }
}
