package com.test.startui.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池-单例模式
 * Created by WXY on 2016/6/29.
 */

public class ThreadPoolManager {
    ExecutorService service;
    private static  ThreadPoolManager instance;
    //单例
    public static ThreadPoolManager getInstance(){
        if (instance == null){
            instance = new ThreadPoolManager();
        }
        return instance;
    }

    private ThreadPoolManager(){
        int nums = Runtime.getRuntime().availableProcessors();
        //创建线程池
        service = Executors.newFixedThreadPool(nums);
    }

    public void execute(Runnable runnable){
        service.execute(runnable);
    }
}
