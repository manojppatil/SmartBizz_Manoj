package com.eduvanzapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eduvanzapplication.Util.Globle;

import java.util.ArrayList;

/**
 * Created by Vijay on 31/Oct/18.
 */

public class DBAdapter {

    public static final boolean DEBUG = true;

    public static final String KEY_ID = "id";

    public static final String DATABASE_NAME = "EDUVANZ.DB";
    public static final int DATABASE_VERSION = 2;

    public static final String[] ALL_TABLES = {
            Utils.ErrorLog_INFO_TABLE};

    private static Context mcontext;

    public static DataBaseHelper DBHelper = null;

    public DBAdapter() {
    }

    public static String Init(Context context) {
        try {
            if (DBHelper == null) {
                DBHelper = new DataBaseHelper(context);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL(Utils.ErrorLog_CREATE_TABLE);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // if (DEBUG)
            //    Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
            //        + "to" + newVersion + "...");

            for (String table : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
            onCreate(db);
        }
    }

    public static synchronized SQLiteDatabase open() throws SQLException {
        return DBHelper.getWritableDatabase();
    }

    public static synchronized SQLiteDatabase read() throws SQLException {
        return DBHelper.getReadableDatabase();
    }

    public static Cursor getLocalData(Context context, String sSql) throws Exception {
        Cursor cursor;
        SQLiteDatabase localDb;
        localDb = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        cursor = localDb.rawQuery(sSql, null);
        cursor.moveToLast();
        localDb.close();
        return cursor;

    }


    public static void ExecuteSql(String sSql) {
        try {
            final SQLiteDatabase db = read();
            db.execSQL(sSql);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
