package com.mucaroo.characterdailyapp.models;


import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mucaroo.characterdailyapp.activities.MoreOptionsActivity;
import com.mucaroo.characterdailyapp.activities.MyProfileActivity;
import com.mucaroo.characterdailyapp.activities.PillarOrderActivity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentine on 10/18/2015.
 */
public class SampleData {

//    List<Pillars> customers;
  static List<Pillars> customers = new ArrayList<Pillars>();
    static ArrayList<String> strings = new ArrayList<>();
    static String formattedDate, key;
    public static List<Pillars> addSampleCustomers() {

        Pillars pillars0 = new Pillars();
        pillars0.setId((long) 0);
        pillars0.setValue(String.valueOf(MoreOptionsActivity.pillarsDate.get(0)));
        pillars0.setPillarName("Trustworthiness");
        customers.add(pillars0);

        Pillars pillars1 = new Pillars();
        pillars1.setId((long)1);
        pillars1.setPillarName("Respect");
        pillars1.setValue(String.valueOf(MoreOptionsActivity.pillarsDate.get(2)));
        customers.add(pillars1);

        Pillars pillars2 = new Pillars();
        pillars2.setId((long)2);
        pillars2.setPillarName("Responsibility");
        pillars2.setValue(String.valueOf(MoreOptionsActivity.pillarsDate.get(3)));
        customers.add(pillars2);

        Pillars pillars3 = new Pillars();
        pillars3.setId((long)3);
        pillars3.setPillarName("Fairness");
        pillars3.setValue(String.valueOf(MoreOptionsActivity.pillarsDate.get(3)));
        customers.add(pillars3);

        Pillars pillars4 = new Pillars();
        pillars4.setId((long)4);
        pillars4.setPillarName("Caring");
        pillars4.setValue(String.valueOf(MoreOptionsActivity.pillarsDate.get(4)));
        customers.add(pillars4);


        Pillars pillars5 = new Pillars();
        pillars5.setId((long)5);
        pillars5.setPillarName("Citizenship");
        pillars5.setValue(String.valueOf(MoreOptionsActivity.pillarsDate.get(5)));
        customers.add(pillars5);

        Pillars pillars6 = new Pillars();
        pillars6.setId((long)6);
        pillars6.setPillarName("Introduction");
        pillars6.setValue(String.valueOf(MoreOptionsActivity.pillarsDate.get(6)));
        customers.add(pillars6);

//        for(int i = 0; i< MyProfileActivity.pillarsDate.size(); i++)
//        {
//            Log.d("DatesofYear",String.valueOf(MyProfileActivity.pillarsDate.get(i)));
//            if(i==0){
//                Pillars pillars0 = new Pillars();
//                pillars0.setId((long) i);
//                pillars0.setValue(String.valueOf(MyProfileActivity.pillarsDate.get(0)));
//                pillars0.setPillarName("TRUSTWORTHINESS");
//                customers.add(pillars0);
//            }
//            if(i==1){
//                Pillars pillars1 = new Pillars();
//                pillars1.setId((long)i);
//                pillars1.setPillarName("RESPECT");
//                pillars1.setValue(String.valueOf(MyProfileActivity.pillarsDate.get(i)));
//                customers.add(pillars1);
//            }
//            if(i==2){
//                Pillars pillars2 = new Pillars();
//                pillars2.setId((long)i);
//                pillars2.setPillarName("RESPONSIBILITY");
//                pillars2.setValue(String.valueOf(MyProfileActivity.pillarsDate.get(i)));
//                customers.add(pillars2);
//            }
//
//
//        }

//        for(int i=0;i< PillarOrderActivity.pillarsDate.size();i++){
//            Log.d("DatesofYear",PillarOrderActivity.pillarsDate.get(i));
//        }

//        Pillars customer6 = new Pillars();
//        customer6.setId((long) 1);
//        customer6.setValue("Respect");
//        customers.add(customer6);
//
//        Pillars customer4 = new Pillars();
//        customer4.setId((long) 2);
//        customer4.setValue("Responsibility");
//        customers.add(customer4);
//
//
//        Pillars customer5 = new Pillars();
//        customer5.setId((long) 3);
//        customer5.setValue("Fairness");
//        customers.add(customer5);
//
//
//        Pillars customer6 = new Pillars();
//        customer6.setId((long) 4);
//        customer6.setValue("Caring");
//        customers.add(customer6);
//
//        Pillars customer7 = new Pillars();
//        customer2.setId((long) 5);
//        customer2.setValue("Najam Mir");
//        customers.add(customer2);
//
//        customer2.setId((long) 6);
//        customer2.setValue("Najam Mir");
//        customers.add(customer2);


//        Customer customer3 = new Customer();
//        customer3.setId((long) 3);
//        customer3.setName("Gregg McQuire");
//        customer3.setEmailAddress("emailing@nobody.com");
//        customer3.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest3.JPG");
//        customers.add(customer3);
//
//
//        Customer customer4 = new Customer();
//        customer4.setId((long) 4);
//        customer4.setName("Jamal Puma");
//        customer4.setEmailAddress("jamal@hotmail.com");
//        customer4.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest4.JPG");
//        customers.add(customer4);
//
//
//        Customer customer5 = new Customer();
//        customer5.setId((long) 5);
//        customer5.setName("Dora Keesler");
//        customer5.setEmailAddress("dora@yahoo.com");
//        customer5.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest5.JPG");
//        customers.add(customer5);
//
//        Customer customer6 = new Customer();
//        customer6.setId((long) 6);
//        customer6.setName("Anthony Lopez");
//        customer6.setEmailAddress("toney@gmail.com");
//        customer6.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest6.JPG");
//        customers.add(customer6);
//
//        Customer customer7 = new Customer();
//        customer7.setId((long) 7);
//        customer7.setName("Ricardo Weisel");
//        customer7.setEmailAddress("ricardo@gmail.com");
//        customer7.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest7.JPG");
//        customers.add(customer7);
//
//        Customer customer8 = new Customer();
//        customer8.setId((long) 8);
//        customer8.setName("Angele Lu");
//        customer8.setEmailAddress("angele@ymail.com");
//        customer8.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest8.JPG");
//        customers.add(customer8);
//
//
//        Customer customer9 = new Customer();
//        customer9.setId((long) 9);
//        customer9.setName("Brendon Suh");
//        customer9.setEmailAddress("brendon@outlook.com");
//        customer9.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest9.JPG");
//        customers.add(customer9);
//
//
//        Customer customer10 = new Customer();
//        customer10.setId((long) 10);
//        customer10.setName("Pietro Augustino");
//        customer10.setEmailAddress("pietro@company.com");
//        customer10.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest10.JPG");
//        customers.add(customer10);
//
//
//        Customer customer11 = new Customer();
//        customer11.setId((long) 11);
//        customer11.setName("Matt Zebrotta");
//        customer11.setEmailAddress("matt@stopasking.com");
//        customer11.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest11.JPG");
//        customers.add(customer11);
//
//        Customer customer12 = new Customer();
//        customer12.setId((long) 12);
//        customer12.setName("James McGiney");
//        customer12.setEmailAddress("james@outlook.com");
//        customer12.setImagePath("https://dl.dropboxusercontent.com/u/15447938/attendanceapp/guest12.JPG");
//        customers.add(customer12);

//        Pillars customer1 = new Pillars();
//        customer1.setId(Long.valueOf(key));
//        customer1.setValue(formattedDate);
//        customers.add(customer1);
        return customers;


    }
}
