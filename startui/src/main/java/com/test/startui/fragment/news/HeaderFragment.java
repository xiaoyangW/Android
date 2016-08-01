package com.test.startui.fragment.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.test.startui.R;
import com.test.startui.Utils.JsonUtils;
import com.test.startui.adapter.NewsListViewAdapter;
import com.test.startui.entity.NewsBean;
import com.test.startui.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXY on 2016/6/23.
 */

public class HeaderFragment extends Fragment{
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //将数据转换成List数组
            String jsonString  = (String) msg.obj;
            List<NewsBean> list = (List<NewsBean>) JsonUtils.fromJson(jsonString,new TypeToken<ArrayList<NewsBean>>(){});
            Log.i("HeaderFragment",jsonString);
            //Adapter未写完。
            lv.setAdapter(new NewsListViewAdapter(list,getContext()));
        }
    };
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.frag_news_head_layout,null);
        lv = (ListView) rootView.findViewById(R.id.lv);
        new Thread(){
            @Override
            public void run() {
                String result = HttpUtils.getNewsList(0,5);
                Message msg = handler.obtainMessage();
                msg.obj = result;
                handler.sendMessage(msg);

            }
        };
        return rootView;
    }
}
