package com.test.faceq.Interface;

/**
 * Created by acer1 on 2016/6/14.
 */

public interface MyOnClickListener {
    //Fragment与Activity传值
    /**
     * @param isBoy 男或女
     * @param faceType 类型
     * @param index 索引
     */
    public void facepartClick(boolean isBoy,int faceType,int index);
}
