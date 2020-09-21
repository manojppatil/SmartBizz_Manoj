package com.smartbizz.newUI.adapter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.smartbizz.newUI.fragments.DynamicPostCardFragment;
import com.smartbizz.newUI.pojo.Category;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    private List<Category> disbursedList = new ArrayList<>();

    public TabAdapter(FragmentManager fm, int NumOfTabs, List<Category> disbursedList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.disbursedList = disbursedList;
    }

    @Override
    public DynamicPostCardFragment getItem(int position) {
            return DynamicPostCardFragment.addfrag(position,disbursedList.get(position));
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return disbursedList.get(position).getCategoryType();
//    }

}