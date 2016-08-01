package com.test.startui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.startui.R;
import com.test.startui.adapter.viewpageAdapter;
import com.test.startui.view.IndicatorView;

public class GuideActivity extends AppCompatActivity {
    private ViewPager vp;
    private IndicatorView mview;
    private viewpageAdapter vpAdapter;
    public static int[] imageids = {
            R.drawable.page1,
            R.drawable.page2,
            R.drawable.page3
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mview = (IndicatorView) findViewById(R.id.mview);
        vp = (ViewPager) findViewById(R.id.vp);
        vpAdapter = new viewpageAdapter(getSupportFragmentManager(),imageids);
        vp.setAdapter(vpAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mview.setCurrentPos(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
