package com.df.dianping;

/**
 * Created by shunshun on 05/10/2017.
 */

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "restaurants";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_RANK = "rank";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table restaurants " +
                        "(id integer primary key, name text, phone text, street text, latitude real, longitude real, rank real)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS restaurants");
        onCreate(db);
    }

    public boolean insertContact (String name, String phone, String street, double latitude, double longitude, double rank) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("street", street);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("rank", rank);
        db.insert("restaurants", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from restaurants where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String street, double latitude, double longitude, double rank) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("street", street);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("rank", rank);
        db.update("restaurants", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("restaurants",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Map<String,Object>> getAllContacts() {
        //ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from restaurants", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            map = new HashMap<String,Object>();
            map.put(COLUMN_ID,res.getInt(res.getColumnIndex(COLUMN_ID)));
            map.put(COLUMN_NAME,res.getInt(res.getColumnIndex(COLUMN_NAME)));
            map.put(COLUMN_STREET,res.getInt(res.getColumnIndex(COLUMN_STREET)));
            map.put(COLUMN_PHONE,res.getInt(res.getColumnIndex(COLUMN_PHONE)));
            map.put(COLUMN_LATITUDE,res.getInt(res.getColumnIndex(COLUMN_LATITUDE)));
            map.put(COLUMN_LONGITUDE,res.getInt(res.getColumnIndex(COLUMN_LONGITUDE)));
            //array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
            list.add(map);
            res.moveToNext();
        }
        return list;
    }
}
