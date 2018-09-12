package com.eduvanzapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eduvanzapplication.newUI.MainApplication;

/**
 * Created by Vijay on 19/8/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    public String TAG = "DATABASE";

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Eduvanz";

    // Table name
    private static final String TABLE_DATE= "date";
    // Table Columns names
    private static final String DATE = "latestdate";
    private static final String DATEID = "dateID";

    public DBHandler(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DATE + "("
                + DATE + " TEXT , " + DATEID + " TEXT " + ")";
        Log.e(MainApplication.TAG, "TABLE: "+CREATE_DATE_TABLE );
        db.execSQL(CREATE_DATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
          db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);
        // Creating tables again
          onCreate(db);
    }


    public  void addDate(String date, String dateid)
    {
        Log.e(TAG, "ADDING DATE TO DATABASE" );

        SQLiteDatabase dbh = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(DATEID, dateid);
        // Inserting Row
//        MainApplication.success =  dbh.insert(TABLE_HIGHLIGHT, null, values);
        dbh.close(); // Closing database connection
    }

    public void deleteDate(String dateID){
        SQLiteDatabase db1 = this.getReadableDatabase();
//        db1.execSQL("delete from "+ TABLE_ONSTOPPARA);
        Log.e(TAG, "deleteOnstoppara: "+dateID );
        db1.delete(TABLE_DATE, DATEID + "=?", new String[] { dateID });

        Log.e(TAG, "deleteOnstoppara: "+"TABLE DELETED" );
    }

    public String getDate(String dateID){
        String date="";
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery(" SELECT * FROM "+TABLE_DATE+" WHERE "+DATEID+" = ? ", new String[] {dateID}, null);
        Log.e(TAG, " GET Date: "+dateID );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                date = cursor.getString( cursor.getColumnIndex("latestdate") );
            } while (cursor.moveToNext());
        }
        Log.e(TAG, "getOnstoppara: DATABASE--- "+ DatabaseUtils.dumpCursorToString(cursor) );
        return date;

    }
}
