package com.eduvanzapplication.database;

/**
 * Created by Vijay on 31/Oct/18.
 */

public class Utils {

    ///////////////ErrorLog///////////////////////////
    public static final String ErrorLog_INFO_TABLE = "ErrorLog";
    public static final String ErrorLog_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ErrorLog (errorLogID TEXT,appName TEXT," +
            "appVersion TEXT,userID TEXT,errorDate TEXT,moduleName TEXT,methodName TEXT,errorMessage TEXT,errorMessageDtls TEXT," +
            "OSVersion TEXT,IPAddress TEXT,deviceName TEXT,lineNumber TEXT,ISSaved TEXT,ISUploaded TEXT);";

}

    




