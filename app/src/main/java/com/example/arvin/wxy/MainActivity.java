package com.example.arvin.wxy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.arvin.wxy.tools.MyMap;
import com.example.arvin.wxy.tools.SystemBarTintManager;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn_add;
    private ListView lv_count;
    private ActionBar actionbar;
    public List<Map<String,Object>> list=new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使android工具栏与app统一风格
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
        }
        setContentView(R.layout.activity_main);
        initView();
        initActionBar();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,OtherActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }

    /**
     *工具栏统一风格
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    /**
     * 初始化按钮
     */
    private void initView() {
        btn_add= (Button) findViewById(R.id.btn_add);
        lv_count= (ListView) findViewById(R.id.lv_count);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //获得ActionBar
        actionbar = getSupportActionBar();
        //显示返回的箭头
        //actionbar.setDisplayHomeAsUpEnabled(true);
        MenuItem itemnext= menu.add(0,1,1,"添加");
        MenuItem itemExit= menu.add(0,2,2,"退出");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 1:
                Intent intent = new Intent(this,OtherActivity.class);
                //activity的启动模式
                startActivityForResult(intent,100);
                break;
            case 2:
                finish();
                break;
        }
        return true;
    }

    private void initActionBar(){
        actionbar= getSupportActionBar();
        //设置允许自定义
        actionbar.setDisplayShowCustomEnabled(true);
        //设置自定义布局
        actionbar.setCustomView(R.layout.title_item);
        //如果设置点击事件
        //1.找到布局
        View view= actionbar.getCustomView();
        ImageView image= (ImageView) view.findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100 && resultCode==200){
            Bundle bundle= data.getExtras();
            Map<String,Object> map=new ArrayMap();
            map.put("name",bundle.getString("name"));
            map.put("head",bundle.getInt("head"));
            map.put("count",bundle.getString("count"));
            list.add(map);
            simpleAdapter=new SimpleAdapter(
                    MainActivity.this,
                    list,
                    R.layout.listview_item,
                    new  String[]{"name", "head","count"},
                    new int[]{R.id.tv_item_name,R.id.iv_item_head,R.id.tv_item_count});
            lv_count.setAdapter(simpleAdapter);
        }
     }

}
