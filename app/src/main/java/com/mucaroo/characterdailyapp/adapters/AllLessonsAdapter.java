package com.mucaroo.characterdailyapp.adapters;

import java.util.Locale;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.style.LocaleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.R;
import com.mucaroo.characterdailyapp.activities.ImageDownloading;
import com.mucaroo.characterdailyapp.activities.WelComeActivity;
import com.mucaroo.characterdailyapp.data.DB;
import com.mucaroo.characterdailyapp.data.DBCallback;
import com.mucaroo.characterdailyapp.models.Article;
import com.mucaroo.characterdailyapp.models.Block;
import com.mucaroo.characterdailyapp.models.ImageInfo;
import com.squareup.picasso.Picasso;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mucaroo.characterdailyapp.R.id.imageView;

/**
 * Created by Zaman Khan on 05-May-17.
 */

public class AllLessonsAdapter extends ArrayAdapter<Block> {
    LayoutInflater inflater;
    int pposition = 0;
    Context c;
    ArrayList<ArrayList> arrL;
    private Context mContext;
    private DatabaseReference databaseReference;
    List<Block> objects = null;
    private ArrayList<Block>  arraylist;
    String MonthofYears;
    ArrayList<Integer> monthofdays;
    String gradedata;

    public interface OnItemClickCallbacked {
        void onBlockClick(Block item);
    }

    Context context;
    private OnItemClickCallbacked mCallback;

    public AllLessonsAdapter(Context context, int resource, List<Block> objects, ArrayList<Integer> monthofdays, String gradedata, OnItemClickCallbacked callback) {
        super(context, resource, objects);
        mCallback = callback;
        this.context = context;
        this.monthofdays = monthofdays;
        this.gradedata = gradedata;
        //zaman khan her add searching code
        this.objects = objects;
        this.arraylist = new ArrayList<Block>();
        this.arraylist.addAll(objects);

    }

    public class ViewHolder {
        TextView text1, text2, text3;
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//       if(position==0){
//
//       }else{
           // pposition++;
           if(gradedata.equals("4")){
               pposition = position/4;
           }
           else{
               pposition = position;
           }

           Log.d("pposition", String.valueOf(pposition));

           final Block block = getItem(position);
           Article article = (Article) block.article;
//        if (convertView == null) {
//            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.list_main_item_all_lessons, null);
//        }

           convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_main_item_all_lessons, parent, false);
           TextView title = (TextView) convertView.findViewById(R.id.tv_detail);
           TextView grade = (TextView) convertView.findViewById(R.id.tv_garde_no);
           final TextView tvDate = (TextView) convertView.findViewById(R.id.tv_lessonDate);

           title.setText(article.title);
           String grd = String.valueOf(article.grade);
           if(article.grade == 0){
               grade.setText("K-2nd");
           }
           if(article.grade == 1){
               grade.setText("3rd-5th");
           }
           if(article.grade == 2){
               grade.setText("6th-8th");
           }
           if(article.grade == 3){
               grade.setText("9th-12th");
           }

//        final String dateCheck = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("schedules").child("default").child("pillars");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String key = dataSnapshot.getKey().toString();
//                long keyvalue = dataSnapshot.getValue(long.class);
//                Date date = new Date(keyvalue * 1000L);
//                SimpleDateFormat sdf = new SimpleDateFormat("MMM");
//                String formattedDate = sdf.format(date);
//                Log.d("MonthPillarsvalue", String.valueOf(keyvalue));
//                Log.d("pilkeyMonth", formattedDate);
//                Log.d("pilkeyMonth", dateCheck);
//                if (formattedDate.equals(dateCheck)) {
//                    MonthofYear = formattedDate;//xyz
////                    tvDate.setText(MonthofYear + "  "+  (monthofdays.get(pposition)+1));
//                    Log.d("pilkeyMonth111", formattedDate);
//                    Log.d("pilkeyMonth111", "DateExist");
//                } else if (key.equals("0")) {
//                    MonthofYear = formattedDate;//nov"Nov"
//                    Log.d("pilkeyMonth111", formattedDate);
//                    Log.d("pilkeyMonth111", "NOT DateExist");
//                }
////                monthsofPillars.add(formattedDate);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

           MonthofYears = WelComeActivity.MonthofYear;
           tvDate.setText(MonthofYears + "  "+  (monthofdays.get(pposition)));

//        int sizes = ImageDownloading.AraayListSize;
//        sizes = sizes+1;
//        for (int k=1;k<sizes+1;k++) {
//            Log.d("indexAllLesson", String.valueOf(k));
//        }
           final View finalConvertView = convertView;
           DB.getInstance(context).getImage(article.image, new DBCallback<ImageInfo>() {
               @Override
               public void success(ImageInfo f) {

                   ImageView imageView = (ImageView) finalConvertView.findViewById(R.id.ivlesson);
                   Log.d("imageURL11", f.url);
                   Picasso.with(context).load(f.url).into(imageView);
               }
           });

//        final ViewHolder holder = new ViewHolder();
//        holder.text1 = (TextView) convertView.findViewById(R.id.tv_lesson);
//        holder.text2 = (TextView) convertView.findViewById(R.id.tv_detail);
//        holder.text3 = (TextView) convertView.findViewById(R.id.tv_garde_no);
//        ImageView imageview = (ImageView) convertView.findViewById(R.id.imageView);
//        holder.imageView = (ImageView) convertView.findViewById(imageView);
//
//        holder.imageView.setBackgroundColor(Color.TRANSPARENT);
//
//        holder.text3.setText(arrL.get(position).get(0).toString());
//        String file = arrL.get(position).get(2).toString();
//        holder.text1.setText(arrL.get(position).get(3).toString());
//        holder.text2.setText(arrL.get(position).get(5).toString());
//
//        Picasso.with(getContext()).load(file).into(holder.imageView);

//        Glide.with(this)
//                .using(new FirebaseImageLoader())
//                .load(file)
//                .into(holder.imageView);

//        imageview.setImageDrawable();
//        holder.name = (TextView) convertView.findViewById(R.id.tvname);
//        holder.price = (TextView) convertView.findViewById(R.id.tvprice);
//
//        holder.name.setText(name.get(position));
//        holder.price.setText(price.get(position));
//       }

        return convertView;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        objects.clear();
        if (charText.length() == 0) {
            objects.addAll(arraylist);
        }
        else
        {
            for (Block wp : arraylist)
            {
                if (wp.article.title.toLowerCase(Locale.getDefault()).contains(charText))
                {
                     objects.add(wp);
                }
//                String gra = String.valueOf(wp.article.grade);
//                else if ((String) wp.article.grade)
//                {
//                    objects.add(wp);
//                }
            }
        }
        notifyDataSetChanged();
    }

}

