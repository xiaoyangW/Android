package com.test.faceq.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.test.faceq.Entity.Res;
import com.test.faceq.Interface.MyOnClickListener;
import com.test.faceq.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer1 on 2016/6/14.
 */

public class MyFragment extends Fragment {
    boolean isBoy = false; //性别
    int faceType = 0;      //表情部件类型
    MyOnClickListener listener;

    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getArguments()!=null){
            isBoy = this.getArguments().getBoolean(Res.EXTRA_ISBOY);
            faceType = this.getArguments().getInt(Res.EXTRA_TYEP);
        }

        GridView gridView = new GridView(getActivity());
        gridView.setNumColumns(4);
        Integer[] res = Res.getRes(isBoy,faceType);
        List<Map<String,Object>>  lists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < res.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("img",res[i]);
            lists.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),lists, R.layout.facepart_item,new String[]{"img"},new int[]{R.id.iv_img});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.facepartClick(isBoy,faceType,position);
            }
        });
        return gridView;
    }
}
