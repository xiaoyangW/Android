package com.test.music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.music.R;
import com.test.music.entity.MusicInfo;

import java.util.List;

/**
 * Created by WXY on 2016/6/18.
 */

public class MusicListViewAdapter extends BaseAdapter {
    List<MusicInfo> list;
    LayoutInflater minflater;
    public MusicListViewAdapter(Context context, List<MusicInfo> list){
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
        ViewHolder mholder = null;
        if (convertView ==null){
            mholder = new ViewHolder();
            convertView = minflater.inflate(R.layout.music_lv_item,null);
            mholder.iv_music = (ImageView) convertView.findViewById(R.id.iv_music);
            mholder.tv_title = (TextView) convertView.findViewById(R.id.tv_dispalyName);
            mholder.tv_dispalyName = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(mholder);
        }else {
            mholder = (ViewHolder) convertView.getTag();
        }
        MusicInfo musicInfo = list.get(position);
        mholder.tv_title.setText(musicInfo.getTitle());
        mholder.tv_dispalyName.setText(musicInfo.getDisplay_name());
        return convertView;
    }

    public static class ViewHolder{
        ImageView iv_music;
        TextView tv_title;
        TextView tv_dispalyName;
    }

}
