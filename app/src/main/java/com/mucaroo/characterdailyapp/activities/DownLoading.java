package com.mucaroo.characterdailyapp.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Zaman Khan on 15-May-17.
 */

public class DownLoading {
    String url;
    public String DownLoading(String temp){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://aubriaco-characterdailyapp.appspot.com/" + temp);//.child(temp);
        storageRef.getDownloadUrl();
//        obj.add("https://firebasestorage.googleapis.com/v0/b/aubriaco-characterdailyapp.appspot.com/o/001.jpeg?alt=media&token=264ce0e5-cb32-4d10-968c-779eb4dfa03b");
//                                addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                url = task.toString();
//                                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
//                            }
//                        });
//                        Toast.makeText(AllLessonsActivity.this, url.toString(), Toast.LENGTH_LONG).show();

//                        String url = storageRef.getDownloadUrl().toString();

        ///   obj.add(url);
//                        String data;
//                        if(storageRef.getDownloadUrl().isSuccessful()){
//                           Uri uu = storageRef.getDownloadUrl().getResult();
//                            Log.i("najam", uu.toString());
//                        }

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
                Log.i("DOWNURL", url);

                // obj.add(data);
//                                String[] result = new String[1];
                //   ssss = data;
                //Log.i("URL222", "url2 " + data);
//                                for(int i=0;i<obj.size();i++)
//                                Toast.makeText(AllLessonsActivity.this, obj.get(i).toString(), Toast.LENGTH_LONG).show();
//                                SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
//                                editor.putString("Uri", uri.toString());
//                                editor.commit();

//                                obj.add(ur
                //System.out.println(data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("exception", exception.toString());
                //  Toast.makeText(AllLessonsActivity.this, "image not dowloaded", Toast.LENGTH_SHORT).show();
            }
        });

        return url;

    }
}
