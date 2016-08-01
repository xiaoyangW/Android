package com.test.startui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.test.startui.contants.Contants;

/**
 * Created by WXY on 2016/6/17.
 */

public class Imagefrgment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView = new ImageView(inflater.getContext());
        Bundle bundle = getArguments();
        //设置和边框对齐
        imageView.setAdjustViewBounds(true);
        //设置缩放
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(bundle.getInt(Contants.TAG_IMAGE_ID));
        return imageView;
    }
}
