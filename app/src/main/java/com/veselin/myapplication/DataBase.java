package com.veselin.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";   // Declaring strings which we will use in the database
    public static final String STUDENTS_COLUMN_NAME = "name";
    public static final String STUDENTS_COLUMN_EMAIL = "email";
    public static final String STUDENTS_COLUMN_SKYPE = "skype";
    public static final String STUDENTS_COLUMN_STREET = "street";
    public static final String STUDENTS_COLUMN_FIELD = "field";
    public static final String STUDENTS_COLUMN_CPR = "cpr";

    public DataBase(Context context) {     // we create a database when we first time run it
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  //we create a table inside the database

        db.execSQL(
                "create table students " + "(id integer primary key,  name text,cpr text,email text, skype text, street text,field text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // we run on upgrade when database is updated, changed like "drop tables" or "add tables" commands
        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }
//--------------------------------------------------------------------------------------------
    public boolean insertStudents(String name, String phone, String email, String skype, String street, String place) {    //inserting
        // name cpr email street and field values to the database  with creating a new table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("cpr", phone);
        contentValues.put("email", email);
        contentValues.put("skype", skype);
        contentValues.put("street", street);
        contentValues.put("field", place);
        db.insert("students", null, contentValues);
        return true;
    }
//------------------------------------------------------------------------------------------
    public Cursor getData(int id) { //This part is responsible by finding seperated students information, it is later used in display_students
        SQLiteDatabase db = this.getReadableDatabase();     // we make the data readable and we can use this later to get the information from
        Cursor res =  db.rawQuery( "select * from students where id="+id+"", null );
        return res;
    }
    public boolean updateStudents(String name, String phone, String email, String skype, String street, String place){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("cpr", phone);
        contentValues.put("email", email);
        contentValues.put("skype", skype);
        contentValues.put("street", street);
        contentValues.put("field", place);
        db.update("students", contentValues, "name=?", new String[]{name});
        return true;
    }
//-------------------------------------------------------------------------------------------
    public ArrayList<String> getAllStudents() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from students", null );
        res.moveToFirst();           // we make an array, which consist only of students names, so we can include it in the activity_main and have nice list of names that we can click on.

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(STUDENTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;


    }
}