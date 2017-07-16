package com.mucaroo.characterdailyapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.activities.MainActivity;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.enums.BlockType;
import com.mucaroo.characterdailyapp.models.Article;
import com.mucaroo.characterdailyapp.models.Block;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.mucaroo.characterdailyapp.models.Lesson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by .Jani on 2/2/2017.
 */

public class HomeAdapter extends ArrayAdapter<Block> {

    public interface OnItemClickCallback {
        void onBlockClick(Block item);
    }

    Context context;
    private OnItemClickCallback mCallback;

    public HomeAdapter(Context context, int resource, List<Block> objects, OnItemClickCallback callback) {
        super(context, resource, objects);
        mCallback = callback;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Block block = getItem(position);
//        Article article = new Article();
        if (convertView == null) {
            Article article = (Article) block.article;
            if (block.type == BlockType.Quote) {
//                Lesson lesson = new Lesson();
                Lesson lesson = (Lesson) article;
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_main_item_quote, parent, false);
                TextView caption = (TextView) convertView.findViewById(R.id.caption);
                caption.setText(lesson.body);
//                caption.setText("Undertake not what you canno1t\n" +
//                        "perform, but be careful to keep\n" +
//                        "you promises.");

            } else if (block.type == BlockType.Behavior) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_main_item_behavior, parent, false);
                TextView caption = (TextView) convertView.findViewById(R.id.caption);
                //   Article article = new Article();
                //   caption.setText(article.title);
                caption.setText("Acknowledge \n students who...");
            } else if (block.type == BlockType.Pledge) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_main_item_pledge, parent, false);
                TextView caption = (TextView) convertView.findViewById(R.id.caption);
                //   Article article = new Article();
                //   caption.setText(article.title);
                caption.setText("I am grateful...");
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_main_item_lesson, parent, false);
                TextView caption = (TextView) convertView.findViewById(R.id.caption);
                final View finalConvertView = convertView;
                DB.getInstance(context).getImage(article.image, new DBCallback<ImageInfo>() {
                    @Override
                    public void success(ImageInfo f) {

                        ImageView imglesson = (ImageView) finalConvertView.findViewById(R.id.lessonimage);
                        Log.d("imageURL11", f.url);
                        Picasso.with(context).load(f.url).into(imglesson);
                    }
                });

                //   Article article = new Article();
                caption.setText(article.title);
//                caption.setText("KEEPING PROMISES");
            }

//            list_main_item_quote.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    context.startActivity(new Intent(getContext(), DailyLessonActivity.class));
//
//                }
//            });

//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(mCallback != null)
//                        mCallback.onBlockClick(block);
//                }
//            });
        }


        return convertView;
    }
}
