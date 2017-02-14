package com.bba.orents.disk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by USER on 7/7/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{


    private final String TAG = DatabaseHandler.class.getName();


    private static final String DATABASE_NAME = "androidorents.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "orentstable";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_EXTRAS = "extra";
    private static final String COLUMN_SYNC = "sync";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE "+TABLE_NAME+"( "+
                COLUMN_URL + " TEXT PRIMARY KEY, "+
                COLUMN_DATA + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT, "+
                COLUMN_EXTRAS + " TEXT, "+
                COLUMN_SYNC + " INTEGER "+
                ")";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXIST "+TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
    }

    public void insert(String url,String data,int sysnctatus){
        //if present then update else insert
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL,url);
        values.put(COLUMN_DATA,data);
        values.put(COLUMN_TIMESTAMP,new Date().toString());
        values.put(COLUMN_SYNC,sysnctatus);
        if(getData(url) != null){
            update(url,values);
        }
        else {
            SQLiteDatabase db=null;
            db = this.getWritableDatabase();
            db.insert(TABLE_NAME,null,values);
            db.close();
        }
    }

    public void insertWithExtra(String url,String data,int sysnctatus,String extra){
        //if present then update else insert
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL,url);
        values.put(COLUMN_DATA,data);
        values.put(COLUMN_TIMESTAMP,new Date().toString());
        values.put(COLUMN_SYNC,sysnctatus);
        values.put(COLUMN_EXTRAS,extra);
        if(getData(url) != null){
            update(url,values);
        }
        else {
            SQLiteDatabase db=null;
            db = this.getWritableDatabase();
            db.insert(TABLE_NAME,null,values);
            db.close();
        }
    }
    public void update(String url ,ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.update(TABLE_NAME, values, COLUMN_URL + " = ? ", new String[]{url});
            //db.execSQL("UPDATE "+TABLE_NAME +" SET "+COLUMN_DATA+" = '"+values.get(COLUMN_DATA)+"' WHERE "+COLUMN_URL+"'"+url+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }
    public String getData(String url){

        String GETDATA_QUERY = "SELECT "+COLUMN_DATA +" FROM "+TABLE_NAME +" WHERE "+COLUMN_URL +" = '"+url+"'";
        Cursor cursor=null;
        SQLiteDatabase db=null;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(GETDATA_QUERY,null);
        if(cursor == null)
            return null;
        else {
            try {
                cursor.moveToFirst();
                String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
                cursor.close();
                return data;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
