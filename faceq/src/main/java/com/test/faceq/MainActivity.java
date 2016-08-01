package com.test.faceq;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.faceq.Entity.Res;
import com.test.faceq.Fragment.MyFragment;
import com.test.faceq.Interface.MyOnClickListener;
import com.test.faceq.MyView.faceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 脸萌主界面Activity
 */
public class MainActivity extends FragmentActivity implements MyOnClickListener{
    private RadioGroup rg;
    private Button btn_save;
    private TextView line;
    private ViewPager vp;
    private HorizontalScrollView hsv;
    private RelativeLayout rl;
    private boolean isBoy;
    faceView faceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        isBoy = intent.getBooleanExtra("isBoy",false);
        initView();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int item = Integer.parseInt(findViewById(checkedId).getTag().toString());
                vp.setCurrentItem(item,true);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceView.setDrawingCacheEnabled(true);
                Bitmap bitmap = faceView.getDrawingCache(true);
                File file = new File(Environment.getExternalStorageDirectory()+"/temp.png");
                if(file.exists()){
                    file.delete();
                }
                FileOutputStream out;
                try{
                    out = new FileOutputStream(file);
                    if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
                    {
                        out.flush();
                        out.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        initViewPager();
        initLine();
    }

    private void initViewPager(){
        List<MyFragment> list = new ArrayList<>();
        for (int i = 0; i < Res.TYPE_COUNT; i++) {
            list.add(new MyFragment());
            //传参
            Bundle bundle = new Bundle();
            bundle.putBoolean(Res.EXTRA_ISBOY,isBoy);
            bundle.putInt(Res.EXTRA_TYEP,i);
            list.get(i).setArguments(bundle);
            list.get(i).setListener(this);
        }
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),list);
        vp.setAdapter(adapter);
    }

    @Override
    public void facepartClick(boolean isBoy, int faceType, int index) {
        faceView.updatePart(isBoy,faceType,index);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
         List<MyFragment> lists = new ArrayList<>();
         public MyFragmentPagerAdapter(FragmentManager fm,List<MyFragment> lists) {
             super(fm);
             this.lists = lists;
         }

         @Override
         public Fragment getItem(int position) {
             return lists.get(position);
         }

         @Override
         public int getCount() {
             return lists.size();
         }
     }
    /**
     * 控件初始化
     */
    private void initView() {
        btn_save = (Button) findViewById(R.id.btn_save);
        rg = (RadioGroup) findViewById(R.id.rg);
        vp = (ViewPager) findViewById(R.id.vp);
        line = (TextView) findViewById(R.id.line);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        rl = (RelativeLayout) findViewById(R.id.rl);
        faceView = new faceView(this,isBoy);
        rl.addView(faceView);
    }

    private void initLine(){
        final RadioButton rb0 = (RadioButton) findViewById(R.id.rb0);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //1.获取水平滑动条的当前位置
                int total = (int)((position + positionOffset)*rb0.getWidth());
                //2.获取按钮放在中间的位置 屏幕的一半减按钮的一半就是中间
                int centerX = (vp.getWidth()- rb0.getWidth())/2;
                //3.计算水平滚动条应该移动的距离 其实就是算超出屏的部分
                int scroll = total - centerX;
                //4.字永远在中间位置
                hsv.scrollTo(scroll,0);
                //5.线移动的实现
                lineMoveTo(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    float linex = 0;
    private void lineMoveTo(int position, float positionOffset) {
        //获得点的按钮
        RadioButton rb = (RadioButton) rg.getChildAt(position);
        //获得坐标
        int temp[] = new int[2];
        rb.getLocationInWindow(temp);
        //
        float dx = temp[0] + positionOffset*rb.getWidth();
        // 初始化位移动画，开始位置为fromX,结束位置为与选中按钮x坐标一致x1,x2,y1,y2
        TranslateAnimation animation = new TranslateAnimation(linex,dx, 0, 0);
        // 动画时间50ms
        animation.setDuration(50);
        // 动画完成保持完成后状态
        animation.setFillAfter(true);
        // 将本次动画结束位置设置为下次动画开始位置
        linex = dx;
        // 开启动画
        line.startAnimation(animation);
    }


}
