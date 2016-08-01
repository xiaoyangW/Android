package com.fwd.filemanage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fwd.filemanage.Dao.FileDao;
import com.fwd.filemanage.R;
import com.fwd.filemanage.entity.FileBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arvin on 2016/6/6.
 */

public class FileAdapter extends BaseAdapter {
    List<FileBean> list = new ArrayList<>();
    LayoutInflater mInflater;

    public FileAdapter(List<FileBean> list, Context context){
        this.list=list;
        mInflater = LayoutInflater.from(context);

    }

    public void setList(List<FileBean> list){
        this.list = list;
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

        ViewHolder mHolder = null;
        if (convertView == null){
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.file_list_item,null);
            mHolder.lv_item_image= (ImageView) convertView.findViewById(R.id.lv_item_image);
            mHolder.lv_item_text = (TextView) convertView.findViewById(R.id.lv_item_text);
            convertView.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        FileBean fileBean = list.get(position);
        File file = new File(fileBean.getPath());
        if (FileDao.TO_ROOT.equals(fileBean.getFileName())|FileDao.TO_UP.equals(fileBean.getFileName())){
            mHolder.lv_item_image.setImageResource(R.drawable.file_toolbar_back);
        }else {
            //如果文件可读
            if (file.canRead()){
                //是文件夹
                if (file.isDirectory()){
                    mHolder.lv_item_image.setImageResource(R.drawable.filesystem_icon_folder);
                }else {
                    if (fileBean.getFileName().endsWith("rar")){
                        mHolder.lv_item_image.setImageResource(R.drawable.filesystem_icon_rar);
                    }else if (fileBean.getFileName().endsWith("txt")){
                        mHolder.lv_item_image.setImageResource(R.drawable.filesystem_grid_icon_text);
                    }else if (fileBean.getFileName().endsWith("jpg")||fileBean.getFileName().endsWith("png")){
                        mHolder.lv_item_image.setImageResource(R.drawable.filesystem_icon_photo);
                    }else if (fileBean.getFileName().endsWith("doc")){
                        mHolder.lv_item_image.setImageResource(R.drawable.filesystem_icon_word);
                    }else {
                        mHolder.lv_item_image.setImageResource(R.drawable.filesystem_icon_default);
                    }
                }
            }
        }
        mHolder.lv_item_text.setText(fileBean.getFileName());
        return convertView;
    }

    public static class ViewHolder{
        TextView lv_item_text;
        ImageView lv_item_image;
    }
}
