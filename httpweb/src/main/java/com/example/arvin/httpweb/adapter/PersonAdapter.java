package com.example.arvin.httpweb.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arvin.httpweb.R;
import com.example.arvin.httpweb.entity.persons;
import com.example.arvin.httpweb.tools.ImageDownloader;
import com.example.arvin.httpweb.tools.OnImageDownload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer1 on 2016/5/19.
 */
public class PersonAdapter extends BaseAdapter {
    List<persons> lists=new ArrayList<>();
    LayoutInflater mInflater;
    Activity context;
    ListView lv;
    ImageDownloader mDownloader;//图片缓存
    public PersonAdapter(List<persons> lists, Activity context, ListView lv) {
        this.lists = lists;
        mInflater=LayoutInflater.from(context);
        this.context = context;
        this.lv = lv;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.list_item,null);
            mHolder=new ViewHolder();
            mHolder.iv_item_head= (ImageView) convertView.findViewById(R.id.iv_item_head);
            mHolder.tv_item_name= (TextView) convertView.findViewById(R.id.tv_item_name);
            mHolder.tv_item_word= (TextView) convertView.findViewById(R.id.tv_item_word);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        persons persons=lists.get(position);
        String c_url=persons.pic;
        c_url= "http://192.168.1.164:8080/gogo/"+c_url;
         Log.d("----",c_url);
        mHolder.tv_item_name.setText(persons.getUsername());
        mHolder.tv_item_word.setText(persons.getPassword());
        mHolder.iv_item_head.setTag(c_url);
        if(mDownloader == null){
            mDownloader = new ImageDownloader();
        }
        //这句代码的作用是为了解决convertView被重用的时候，图片预设的问题
        mHolder.iv_item_head.setImageResource(R.drawable.daqiao);
        if (mDownloader != null) {
            //异步下载图片
            mDownloader.imageDownload(c_url, mHolder.iv_item_head, "/test", context, new OnImageDownload() {
                @Override
                public void onDownloadSucc(Bitmap bitmap,
                                           String c_url, ImageView mimageView) {
                    //ImageView imageView=mimageView;
                    ImageView imageView = (ImageView) lv.findViewWithTag(c_url);
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                        //imageView.setTag("");
                    }
                }
            });
        }
        return convertView;
    }
    private static class ViewHolder{
        ImageView iv_item_head;
        TextView tv_item_name;
        TextView tv_item_word;
    }
}
