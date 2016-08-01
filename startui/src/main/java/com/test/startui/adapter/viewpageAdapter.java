package com.test.startui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.startui.R;
import com.test.startui.contants.Contants;
import com.test.startui.fragment.Imagefrgment;

/**
 * Created by acer1 on 2016/6/17.
 */

public class viewpageAdapter extends FragmentPagerAdapter{
    int[] mids;

    public viewpageAdapter(FragmentManager fm,int[] mids) {
        super(fm);
        this.mids = mids;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Imagefrgment();
        Bundle bundle = new Bundle();
        bundle.putInt(Contants.TAG_IMAGE_ID,mids[position]);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public int getCount() {
        return mids.length;
    }
}
