package com.test.coordinatorlayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXY on 2016/7/4.
 * TabLayout + Fragment 通用Adapter
 */

public class TabLayoutAdapter extends FragmentPagerAdapter{
    private static final List<Fragment> mFragments = new ArrayList<>();
    private static final List<String> mFragmentTitles = new ArrayList<>();

    public TabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
