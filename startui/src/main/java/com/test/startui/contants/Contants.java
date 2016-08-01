package com.test.startui.contants;

import com.test.startui.R;
import com.test.startui.fragment.NewsFragment;
import com.test.startui.fragment.OtherFragment;

/**
 * Created by acer1 on 2016/6/17.
 */

public class Contants {
    public static final String  TAG_IMAGE_ID = "imageid";
    public static final String TAG_NEWS_POS = "newspos";
    public static Class[] FRAG_CALSS={
            NewsFragment.class,
            OtherFragment.class,
            OtherFragment.class,
            OtherFragment.class
    };
    //tabspac背景数组
    public static final int[] SPEC_IDS = {
            R.drawable.news_bg,
            R.drawable.paper_bg,
            R.drawable.watch_bg,
            R.drawable.policy_bg
    };
    //声明文字的数组
    public static final String[] SPEC_NAMES = {
            "新闻",
            "湖南日报",
            "湖南观察",
            "政策"
    };
}
