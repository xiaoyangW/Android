package com.test.startui.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.startui.contants.Contants;

/**
 * Created by acer1 on 2016/6/25.
 */

public class NewsOtherFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(inflater.getContext());
        int pos = getArguments().getInt(Contants.TAG_NEWS_POS);
        textView.setText("新闻的第"+pos+"页");
        return textView;
    }
}
