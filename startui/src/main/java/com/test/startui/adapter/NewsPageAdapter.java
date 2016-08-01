package com.test.startui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.startui.contants.Contants;
import com.test.startui.fragment.NewsFragment;
import com.test.startui.fragment.news.HeaderFragment;
import com.test.startui.fragment.news.NewsOtherFragment;

/**
 * Created by acer1 on 2016/6/23.
 */

public class NewsPageAdapter extends FragmentPagerAdapter{
    int count;
    public NewsPageAdapter(FragmentManager fm,int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new HeaderFragment();
        }else {
            fragment = new NewsOtherFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Contants.TAG_NEWS_POS,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
