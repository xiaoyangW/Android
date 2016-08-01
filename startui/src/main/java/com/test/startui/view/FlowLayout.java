package com.test.startui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by acer1 on 2016/6/23.
 */

public class FlowLayout extends ViewGroup{

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子元素
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //便利所有的子元素，设置他们的位置
        int groupWidth = getWidth();
        int childWidth = getChildAt(0).getMeasuredWidth();
        int count = groupWidth/childWidth;
        int startPosition = (groupWidth - count*childWidth)/2;
        int x_start = startPosition;
        int y_start = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
            //将子控件放在哪
            childView.layout(
                    x_start,//左上角的X
                    y_start,//左上角的y,
                    x_start+width,//右下角x//childView.getWidth()还未画出来总中0
                    y_start+height//左下角y
            );
            x_start += width;
            if (x_start + width > groupWidth){
                //换行
                x_start = startPosition;
                y_start += height;//换行
            }
        }
    }
}
