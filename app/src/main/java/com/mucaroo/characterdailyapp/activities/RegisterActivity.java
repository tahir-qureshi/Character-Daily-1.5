package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.callbacks.CreateResultCallback;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.util.Utility;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//registration working
        getRegisterButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void register() {
        getRegisterButton().setEnabled(false);
        final String email = getEmailText().getText().toString();
        final String password = getPasswordText().getText().toString();
        String passwordConfirm = getPasswordConfirmText().getText().toString();

        int err = 0;
        if(email.isEmpty()) {
            getEmailText().setError("Can not be empty.");
            err++;
        }
        if(password.isEmpty()) {
            getPasswordText().setError("Can not be empty.");
            err++;
        }
        if(passwordConfirm.isEmpty()) {
            getPasswordConfirmText().setError("Can not be empty.");
            err++;
        }

        if(!password.equals(passwordConfirm)) {
            getPasswordConfirmText().setError("Does not match password.");
            err++;
        }

        if(err == 0) {
            DB.getInstance(this).createUser(email, password, new CreateResultCallback() {
                @Override
                public void onSuccess() {
                    Utility.showAlert(RegisterActivity.this, "Success", "Account has been created!", new Runnable() {
                        @Override
                        public void run() {
                            Intent result = new Intent();
                            result.putExtra("email", email).putExtra("password", password);
                            setResult(1, result);
                            finish();
                        }
                    });

                }

                @Override
                public void onFail() {
                    Utility.showAlert(RegisterActivity.this, "Error", "Could not create account/ Uer Already Exist.");
                    getRegisterButton().setEnabled(true);
                }
            });
        }
        else
            getRegisterButton().setEnabled(true);
    }


}
