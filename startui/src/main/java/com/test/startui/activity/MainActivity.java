package com.test.startui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.test.startui.R;
import com.test.startui.contants.Contants;
import com.test.startui.fragment.NewsFragment;

/**
 * Created by WXY on 2016/6/22.
 */

public class MainActivity extends AppCompatActivity{
    FragmentTabHost mTabHost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabhost();
    }

    /**
     * 初始化TabHost
     */
    private void initTabhost(){
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        //初始化frgmenttabhost
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realcontent);
        //设置内容
        for (int i = 0; i < 4; i++) {
            TabHost.TabSpec tabSpec =  mTabHost.newTabSpec(i+"");
            View itemView = getLayoutInflater().inflate(R.layout.tabhost_item,null);
            ImageView iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            iv_icon.setImageResource(Contants.SPEC_IDS[i]);
            TextView tv = (TextView) itemView.findViewById(R.id.tv_name);
            tv.setText(Contants.SPEC_NAMES[i]);
            tabSpec.setIndicator(itemView);
            Bundle bundle = new Bundle();
            bundle.putString("text","第"+i+"页");
            mTabHost.addTab(tabSpec, Contants.FRAG_CALSS[i],bundle);
        }
    }

}
