package com.test.sms.Adapter;

import android.content.Context;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.sms.Entity.MySms;
import com.test.sms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arvin on 2016/6/15.
 */

public class Adapter extends BaseAdapter{
    List<MySms> list = new ArrayList<>();
    LayoutInflater minflater;

    public Adapter(List<MySms> list, Context context) {
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
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = minflater.inflate(R.layout.sms_list_item,null);
            viewHolder.tv_sms_address = (TextView) convertView.findViewById(R.id.tv_sms_address);
            viewHolder.tv_sms_body = (TextView) convertView.findViewById(R.id.tv_sms_body);
            viewHolder.tv_sms_date = (TextView) convertView.findViewById(R.id.tv_sms_date);
            viewHolder.tv_sms_type = (TextView) convertView.findViewById(R.id.tv_sms_type);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MySms mySms = list.get(position);
        viewHolder.tv_sms_type.setText(mySms.getType()+"");
        viewHolder.tv_sms_body.setText(mySms.getBody()+"");
        viewHolder.tv_sms_date.setText(mySms.getDate()+"");
        viewHolder.tv_sms_address.setText(mySms.getAddress()+"");
        return convertView;
    }
    public static class ViewHolder{
        TextView tv_sms_address;
        TextView tv_sms_body;
        TextView tv_sms_date;
        TextView tv_sms_type;
    }
}
