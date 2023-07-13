package com.example.onroadservice;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import androidx.annotation.Nullable;


public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table users(name text,phone text,email text,password text,role text)";
        sqLiteDatabase.execSQL(qry1);
        //String qry2="create table data(name text,phno text,vehicle_type text,fuel_type text,requirement text,vehicle_no text,time text,location text)";
        String qry2="create table data(vehicle_no text, name text, vehicle_type text, location text, requirement Text, time text, fuel_type text, phno text)";
        sqLiteDatabase.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void register(String name,String phone,String email,String password){
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("phone",phone);
        cv.put("email",email);
        cv.put("password",password);
        //cv.put("role",role);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users",null,cv);
        db.close();
    }
    public int login(String email, String password, String role){
//        int result = 0;
//        String[] str = new String[2];
//        str[0] = email;
//        str[1] = password;
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor c = db.rawQuery("select * from users where email=? and password=?",str);
//        if(c.moveToFirst()){
//            result =1;
//        }
//        return result;
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});

        if (cursor.moveToFirst()) {
            // User found, check if the role matches
            @SuppressLint("Range") String userRole = cursor.getString(cursor.getColumnIndex("role"));
            if (userRole.equals(role)) {
                result = 1; // Login success
            }
        }

        cursor.close();
        db.close();
        return result;
    }
    public void request_data(String name,String phno,String vehicle_type,String fuel_type,String requirement,String vehicle_no,String time,String location)
    {

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phno", phno); // Updated column name to "phno"
        cv.put("vehicle_type", vehicle_type);
        cv.put("fuel_type", fuel_type);
        cv.put("requirement", requirement);
        cv.put("vehicle_no", vehicle_no);
        cv.put("time", time);
        cv.put("location", location);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("data", null, cv);
        db.close();
    }



//    public Cursor getData()
//    {
//        SQLiteDatabase db=this.getWritableDatabase();
//        Cursor cursor=db.rawQuery("Select name,phno,vehicle_type,fuel_type,requirement,vehicle_no,time,location from data",null);
//
//        return cursor;
//    }
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT vehicle_no, name, vehicle_type, location, requirement, time, fuel_type, phno FROM data", null);
        return cursor;
}

}

