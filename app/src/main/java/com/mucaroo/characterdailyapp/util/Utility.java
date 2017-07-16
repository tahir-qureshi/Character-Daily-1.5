package com.mucaroo.characterdailyapp.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;

import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.activities.ImageDownloading;
import com.mucaroo.characterdailyapp.activities.MyProfileActivity;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by .Jani on 2/6/2017.
 */

public class Utility {
//    final Dialog dialog = null;
    Dialog dialog = null;
    public static void showAlert(Context c, String title, String msg, final Runnable callback) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null)
                            callback.run();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void showProgress(Context context) {
//        ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(tag);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.custom_progressdialog);
//        progressDialog.show();
//        Window window = progressDialog.getWindow();
//
//        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        progressDialog.setCancelable(false);
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_profile_dialogbox);
        dialog.setTitle("Title...");
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void DissmissDialog(Context c) {
//        final Dialog dialog = new Dialog(c);
//        dialog.dismiss();

    }

    public static void showAlert(Context c, String title, String msg) {
        showAlert(c, title, msg, null);
    }

    public static void storeUser(Context c, User user) {
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString("user", user.serialize()).apply();
    }

//    public static void storeGrade(Context c, User user) {
//        PreferenceManager.getDefaultSharedPreferences(c).edit().putString("grade", user.serialize()).apply();
//    }

    public static User getUser(Context c) {
        String json = PreferenceManager.getDefaultSharedPreferences(c).getString("user", null);

        if (json != null) {
            return new User().unserialize(json);
        }
        return null;
    }

    public static User reset(Context c) {
        PreferenceManager.getDefaultSharedPreferences(c).edit().clear().apply();
        return null;
    }

    public static String getAssetAsString(Context c, String assetName) {
        try {
            InputStream is = c.getResources().getAssets().open(assetName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getImage(Context c, String ident, DBCallback<ImageInfo> callback) {
        try {
            DB db = new DB();
            db.getInstance(c).getImage(ident, callback);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                AssetFileDescriptor fd = c.getResources().getAssets().openFd("images/default.png");
                FileInputStream is = fd.createInputStream();
                byte[] buf = new byte[(int) fd.getLength()];
                is.read(buf);
                is.close();

                ImageInfo imageInfo = new ImageInfo();
                imageInfo.url = "data:image/png;base64," + Base64.encodeToString(buf, Base64.NO_PADDING);
                callback.success(imageInfo);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
}
