package com.test.refresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 刷新框架的使用
 */
public class MainActivity extends AppCompatActivity {
    PtrClassicFrameLayout mPtrFrame;
    ArrayAdapter<String> mAdapter;
    ListView rotate_header_list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String data[]=new String[50];
        for(int i=0;i<data.length;i++){
            data[i]="Test"+i;
        }
        mAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,data);
        rotate_header_list_view = (ListView) findViewById(R.id.rotate_header_list_view);
        rotate_header_list_view.setAdapter(mAdapter);
        //1.找到控件
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        //2.设置上次更新时间
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();//更新list,刷Adapter
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);//
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }
    protected void updateData() {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();

    }
}
