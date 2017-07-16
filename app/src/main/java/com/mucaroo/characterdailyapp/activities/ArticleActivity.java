package com.mucaroo.characterdailyapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.Lesson;
import com.mucaroo.characterdailyapp.util.Utility;

public class ArticleActivity extends BaseActivity {
    private static final String TAG = "ArticleActivity";

    public static Lesson mCurrentArticle = new Lesson();

    public String mContent = new String();
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        setTitle(R.string.daily_lesson);

         webView = (WebView) findViewById(R.id.view);
        Utility utility = new Utility();
        mContent = utility.getAssetAsString(this, "templates/article.html");

        try {
            mContent = mContent.replace("$(title)", mCurrentArticle.title);//mCurrentArticle.title
            mContent = mContent.replace("$(quote)", mCurrentArticle.quote);//mCurrentArticle.quote
            mContent = mContent.replace("$(body)", mCurrentArticle.body);//mCurrentArticle.body
        }catch (Exception e){
            Log.d(TAG, "onCreate:Zaman Khan", e);
        }

        try {
         //   Utility utility1 = new Utility();
            utility.getImage(this, mCurrentArticle.image, new DBCallback<ImageInfo>() {
                @Override
                public void success(ImageInfo o) {
                    //o = new ImageInfo();
                    Log.d(TAG,"asda" + o.url);
                    mContent = mContent.replace("$(image)", o.url);
                    webView.loadUrl(o.url);
                }
            });

        }catch (Exception e){
            Log.d(TAG, "onCreate: Khan", e);
        }



    }
}
