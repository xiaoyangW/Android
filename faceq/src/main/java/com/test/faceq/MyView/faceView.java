package com.test.faceq.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import com.test.faceq.Entity.Res;

/**
 * Created by acer1 on 2016/6/14.
 */

public class faceView extends View {
    boolean isBoy = false; //性别
    Integer[] img_ids; //人物形象图片ID数组
    Bitmap[] bms;     //人物形象图片数组

    public faceView(Context context, boolean isBoy) {
        super(context);

        //取默认值
        if(isBoy){
            img_ids = Res.getBoyDefault();
        }else{
            img_ids = Res.getGirlDefault();
        }
        //读取所有图片
        bms = new Bitmap[Res.TYPE_COUNT];
        for (int i = 0; i < bms.length; i++) {
            bms[i] = BitmapFactory.decodeResource(this.getResources(), img_ids[i]);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //得到容器的宽高
        int width = this.getWidth();
        int height = this.getHeight();
        //缩放要画的图片
        bms[0] = Bitmap.createScaledBitmap(bms[0], 2*width/3, 2*width/3, false);
        bms[1] = Bitmap.createScaledBitmap(bms[1], 2*width/3, 2*width/3, false);
        bms[2] = Bitmap.createScaledBitmap(bms[2], width/3, width/3, false);
        bms[3] = Bitmap.createScaledBitmap(bms[3], 3*width/10, 3*width/10, false);
        bms[4] = Bitmap.createScaledBitmap(bms[4], width/5, width/5, false);
        bms[5] = Bitmap.createScaledBitmap(bms[5], width/2,width/2, false);
        bms[6] = Bitmap.createScaledBitmap(bms[6], width/3, width/3, false);
        bms[7] = Bitmap.createScaledBitmap(bms[7], 5*width/10, 5*width/8, false);
        bms[10] = Bitmap.createScaledBitmap(bms[10], width, height, false);

        //正式绘制形象
        canvas.drawColor(Color.WHITE); //先画个白色的背景
        //画背景
        canvas.drawBitmap(bms[10], 0, 0, null);
//衣服
        canvas.drawBitmap(bms[7],(width-bms[7].getWidth())/2, 3*(height-bms[7].getHeight())/4,null);
        //脸型
        canvas.drawBitmap(bms[1],(width-bms[1].getWidth())/2, (height-bms[1].getHeight())/2, null);

        //眉毛
        canvas.drawBitmap(bms[2],(width-bms[2].getWidth())/2, (height-bms[2].getHeight())/2, null);
        //眼睛
        canvas.drawBitmap(bms[3],(width-bms[3].getWidth())/2, (height-bms[2].getHeight())/2+height/15, null);
        //嘴巴
        canvas.drawBitmap(bms[4],(width-bms[4].getWidth())/2, 11*height/20, null);

        //特征
        canvas.drawBitmap(bms[5],(width-bms[5].getWidth())/2, (height-bms[5].getHeight())/2, null);
        //眼睛
        canvas.drawBitmap(bms[6],(width-bms[6].getWidth())/2, (height-bms[6].getHeight())/2+height/20, null);
        //发型
        canvas.drawBitmap(bms[0],(width-bms[0].getWidth())/2, (height-bms[0].getHeight())/2, null);

        super.onDraw(canvas);
    }

    //更新表情部件
    public void updatePart(boolean isBoy2, int faceType, int index) {
        //先改ID
        img_ids[faceType] = Res.getRealRes(isBoy2, faceType, index);
        //再加载图片
        bms[faceType] = BitmapFactory.decodeResource(this.getResources(), img_ids[faceType]);
        //更新界面
        this.invalidate(); //重绘 调onDraw
    }

}
