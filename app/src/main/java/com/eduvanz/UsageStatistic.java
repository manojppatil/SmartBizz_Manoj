package com.eduvanz;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class UsageStatistic extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_statistic);
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

        for(int i=0; i<getUsageStatsList().size();i++) {
            Log.e(MainApplication.TAG, "size: " + getUsageStatsList().size());
            Log.e(MainApplication.TAG, "getPackageName: " + getUsageStatsList().get(i).getPackageName());
            Log.e(MainApplication.TAG, "getTotalTimeInForeground: " + getUsageStatsList().get(i).getTotalTimeInForeground());
            Log.e(MainApplication.TAG, "getTotalTimeInForeground: " + getUsageStatsList().get(i).getTotalTimeInForeground());
        }

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public List<UsageStats> getUsageStatsList(){
        UsageStatsManager usm = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();
        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY,startTime,endTime);
        return usageStatsList;
    }
}
