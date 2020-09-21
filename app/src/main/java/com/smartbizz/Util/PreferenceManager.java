package com.smartbizz.Util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;


public class PreferenceManager {

    private static final String PREFERENCES_NAME = ".GalleryPreferences";

    private PreferenceManager() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context == null ? null : context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null && preferences.contains(key)) {
            return preferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public static void saveString(Context context, String key, String value) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null) {
            preferences.edit().putString(key, value).apply();
        }
    }

    public static boolean getBoolean(Context context, String key) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences != null && preferences.contains(key) && preferences.getBoolean(key, false);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null) {
            preferences.edit().putBoolean(key, value).apply();
        }
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return (preferences != null && preferences.contains(key)) ? preferences.getInt(key, defaultValue) : defaultValue;
    }

    public static void saveInt(Context context, String key, int value) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null) {
            preferences.edit().putInt(key, value).apply();
        }
    }

    public static long getLong(Context context, String key, long defaultValue) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return (preferences != null && preferences.contains(key)) ? preferences.getLong(key, defaultValue) : defaultValue;
    }

    public static long getLong(Context context, String key) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return (preferences != null && preferences.contains(key)) ? preferences.getLong(key, 0) : 0;
    }

    public static void saveLong(Context context, String key, long value) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null) {
            preferences.edit().putLong(key, value).apply();
        }
    }

    public static void delete(Context context, String key) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null) {
            preferences.edit().remove(key).apply();
        }
    }

    public static void delete(Context context, String... keys) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null && keys != null) {
            for (String key : keys) {
                preferences.edit().remove(key).apply();
            }
        }
    }

    public static void clearAll(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        if (preferences != null) {
            preferences.edit().clear().apply();
        }
    }

    public static boolean contains(@NonNull Context context, String key) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences != null && preferences.contains(key);
    }
}

