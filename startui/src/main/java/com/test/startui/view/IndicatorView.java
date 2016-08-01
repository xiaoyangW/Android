package com.test.startui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by acer1 on 2016/6/17.
 */

public class IndicatorView extends View {
    int x,y,r;
    int num;
    int offx;
    Paint paint;
    int currentPos;
    public void setCurrentPos(int position){
        currentPos = position;
        //刷新控件
        invalidate();
    }

    public IndicatorView(Context context) {
        super(context);
        init();
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void init(){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        r = 10;
        x=r;
        y=r;
        num = 3;
        offx = 20;
        currentPos = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量模式
        // 控件有三种测量模式，精确测量（matchparent，具体的数值），
        // 匹配测量（warp_content），未定义测量(0dp)
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
       //获取当前的大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //测量之后的大小
        int width;
        int height;

        //测量宽度
        if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            width = widthSize;
        }else {//实现匹配测量warp_content
            width = num*2*r+(num-1)*offx;
        }
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = 2*r;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < num; i++) {
            if (i==currentPos){
                paint.setColor(Color.RED);
            }else{
                paint.setColor(Color.WHITE);
            }
            canvas.drawCircle(
                    x+(2*r+offx)*i,
                    y,
                    r,
                    paint);
        }

    }
}
