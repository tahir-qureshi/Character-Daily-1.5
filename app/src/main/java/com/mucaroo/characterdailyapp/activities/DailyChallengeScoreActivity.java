package com.mucaroo.characterdailyapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mucaroo.characterdailyapp.R;

public class DailyChallengeScoreActivity extends AppCompatActivity {

    int count;
    TextView txtprog;
    int rats, rats1, rats2;
    String rat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenge_score);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);

        final TextView txt_title = (TextView) findViewById(R.id.action_bar_title);
        txt_title.setText("DAILY CHALLENGE");

        final ImageView imageViewOne = (ImageView) findViewById(R.id.imageViewOne);
        final ImageView imageViewTwo = (ImageView) findViewById(R.id.imageViewTwo);
        final ImageView imageViewThree = (ImageView) findViewById(R.id.imageViewThree);
        txtprog = (TextView) findViewById(R.id.tvprogress);
        imageViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count == 1) {
                    imageViewOne.setImageResource(R.mipmap.fill_circle);
                    rats = 30;
                    txtprog.setText(rats + "%");
                } else {
                    imageViewOne.setImageResource(R.mipmap.empty_circle);
                    rats = 0;
                    txtprog.setText(0 + "%");
                    count = 0;
                }
            }
        });
        imageViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count == 1) {
                    imageViewTwo.setImageResource(R.mipmap.fill_circle);
                    rats1 = rats + 30;
                    txtprog.setText(rats1 + "%");
                } else {
                    imageViewTwo.setImageResource(R.mipmap.empty_circle);
                    rats1 = rats - 30;
                    txtprog.setText(rats1 + "%");
                    count = 0;
                }
            }
        });
        imageViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count == 1) {
                    imageViewThree.setImageResource(R.mipmap.fill_circle);
                    rats2 = rats1 + 40;
                    txtprog.setText(rats2 + "%");
                } else {
                    imageViewThree.setImageResource(R.mipmap.empty_circle);
                    rats2 = rats1 - 40;
                    txtprog.setText(rats2 + "%");
                    count = 0;
                }

            }
        });
    }

    //back navigation button working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MoreCharacterToolsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MoreCharacterToolsActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
