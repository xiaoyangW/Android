package com.test.startui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.test.startui.R;
import com.test.startui.Utils.ActivityUtils;
import com.test.startui.activity.AddMoreActivity;
import com.test.startui.adapter.NewsPageAdapter;

/**
 * Created by acer1 on 2016/6/22.
 */

public class NewsFragment extends Fragment {
    public static  String[] IN_TEXT = {
            "头条",
            "大湘江",
            "经济",
            "深度",
            "视觉",
            "湖南印象",
            "观点",
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.frag_news_layout,null);
         final RadioGroup rg_indictor = (RadioGroup) rootView.findViewById(R.id.rg_indictor);
         final HorizontalScrollView hsv = (HorizontalScrollView) rootView.findViewById(R.id.hsv);
        initRadioGroup(rg_indictor,inflater);
         final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new NewsPageAdapter(getChildFragmentManager(),IN_TEXT.length));
        //建立RadioGroup和viewpager之间的联系
        rg_indictor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //法一：viewpager也要移动到对应页面。
                /*for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton.getId() == checkedId){
                        viewPager.setCurrentItem(i);
                    }
                }*/
                viewPager.setCurrentItem(checkedId);
            }
        });
        //建立viewpager和radiogroup之间的联系
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rg_indictor.check(position);
                int width = 0;
                for (int i = 0; i < position; i++) {
                    RadioButton rb = (RadioButton) rg_indictor.getChildAt(position);
                    width += rb.getWidth();
                    if (i == position-1) {//最后一个button的时候要进行修补算法
                        width = width - (hsv.getWidth() - rb.getWidth())/2;
                    }
                }
                hsv.scrollTo(width,0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ImageView iv_addmore = (ImageView) rootView.findViewById(R.id.iv_addmore);
        iv_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ActivityUtils.toActivityForResult(getActivity(),AddMoreActivity.class,100);
                //必须要使用Fragment的startActivityForResult才会进入到onActivityResult
                Intent intent = new Intent();
                intent.setClass(getActivity(),AddMoreActivity.class);
                startActivityForResult(intent,100);
            }
        });
        return rootView;
    }

    /**
     * 初始化RadioGroup
     * @param rg_indictor
     * @param inflater
     */
    public void initRadioGroup(RadioGroup rg_indictor,LayoutInflater inflater){
        for (int i = 0; i < 7; i++) {
            RadioButton rb = (RadioButton) inflater.inflate(R.layout.rb_indictor,null);
            //法二：强制设置Id
            rb.setId(i);
            rb.setButtonDrawable(null);
            rb.setPadding(20,20,20,30);
            rb.setText(IN_TEXT[i]);
            rb.setTextSize(17);
            rg_indictor.addView(rb);
        }
        //初始为第一个选中
        rg_indictor.check(rg_indictor.getChildAt(0).getId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //更新界面
    }
}
