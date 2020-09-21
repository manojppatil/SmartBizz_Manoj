package com.smartbizz.Util;

import android.util.Log;

public class MyLogger {

    /**
     * Flag to hold all log enable status.
     */
    private static final boolean IS_LOG_ENABLED = true;

    private static final String LOG_TAG = "Gallery 2.0";

    /**
     * Private constructor to prevent the instantiation of this class
     */
    private MyLogger() {
    }

    public static void verbose(String message) {
        if (IS_LOG_ENABLED) {
            Log.v(LOG_TAG, message);
        }
    }

    public static void verbose(String tag, String message) {
        if (IS_LOG_ENABLED) {
            Log.v(tag, message);
        }
    }

    public static void debug(String tag, String message) {
        if (IS_LOG_ENABLED) {
            Log.d(tag, message);
        }
    }

    public static void debug(String message) {
        if (IS_LOG_ENABLED) {
            Log.d(LOG_TAG, message);
        }
    }

    public static void info(String tag, String message) {
        if (IS_LOG_ENABLED) {
            Log.i(tag, message);
        }
    }

    public static void info(String message) {
        if (IS_LOG_ENABLED) {
            Log.i(LOG_TAG, message);
        }
    }

    public static void warn(String tag, String message) {
        if (IS_LOG_ENABLED) {
            Log.w(tag, message);
        }
    }

    public static void error(String tag, String message) {
        if (IS_LOG_ENABLED) {
            Log.e(tag, message);
        }
    }

    public static void error(String tag, Exception exception) {
        if (IS_LOG_ENABLED) {
            String message = Log.getStackTraceString(exception);
            Log.e(tag, message);
        }
    }

    public static void error(String tag, Throwable exception) {
        if (IS_LOG_ENABLED) {
            String message = Log.getStackTraceString(exception);
            Log.e(tag, message);
        }
    }

    public static void error(Exception exception) {
        if (IS_LOG_ENABLED) {
            String message = Log.getStackTraceString(exception);
            Log.e(LOG_TAG, message);
        }
    }
}

