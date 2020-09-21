package com.smartbizz.newUI.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.smartbizz.newUI.fragments.DynamicPostCardFragment;
import com.smartbizz.newUI.fragments.SMSBulkUploadCardFragment;
import com.smartbizz.newUI.fragments.SMSSenderFragment;
import com.smartbizz.newUI.fragments.SMSSenderIDUpdateFragment;
import com.smartbizz.newUI.pojo.Category;

import java.util.ArrayList;
import java.util.List;

public class SMSTabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public SMSTabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SMSSenderFragment homeFragment = new SMSSenderFragment();
                return homeFragment;
            case 1:
                SMSBulkUploadCardFragment sportFragment = new SMSBulkUploadCardFragment();
                return sportFragment;
            case 2:
                SMSSenderIDUpdateFragment smsSenderIDUpdateFragment = new SMSSenderIDUpdateFragment();
                return smsSenderIDUpdateFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}