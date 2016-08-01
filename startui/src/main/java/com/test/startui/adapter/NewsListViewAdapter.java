package com.test.startui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.test.startui.entity.NewsBean;

import java.util.List;

/**
 * Created by acer1 on 2016/6/25.
 */

public class NewsListViewAdapter extends BaseAdapter {
    LayoutInflater minflater;
    List<NewsBean> list;

    public NewsListViewAdapter(List<NewsBean> list, Context context) {
        this.list = list;
        minflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
