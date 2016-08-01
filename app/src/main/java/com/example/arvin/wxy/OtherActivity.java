package com.example.arvin.wxy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arvin.wxy.tools.MyMap;
import com.example.arvin.wxy.tools.SystemBarTintManager;

import java.util.Map;

/**
 * Created by acer1 on 2016/5/17.
 */
public class OtherActivity extends AppCompatActivity{
    private  ActionBar actionBar;
    private Button  btn_photo;
    private ImageView iv_head;
    private EditText et_name;
    private EditText et_count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使android工具栏与app统一风格
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
        }
        setContentView(R.layout.activity_other);
        initView();
        //View view=actionBar.getCustomView();
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle= new Bundle();
                bundle.putInt("head",iv_head.getImageAlpha());
                bundle.putString("name",et_name.getText().toString());
                bundle.putString("count",et_count.getText().toString());
                intent.putExtras(bundle);
                setResult(200,intent);
                startActivity(intent);
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
    private void initView() {
        iv_head= (ImageView) findViewById(R.id.iV_head);
        et_count= (EditText) findViewById(R.id.et_count);
        et_name= (EditText) findViewById(R.id.et_name);
        btn_photo= (Button) findViewById(R.id.btn_photo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //获得ActionBar
        actionBar = getSupportActionBar();
        //显示返回的箭头
        actionBar.setDisplayHomeAsUpEnabled(true);
        MenuItem itemext= menu.add(0,1,1,"返回");
        MenuItem itemExit= menu.add(0,2,2,"退出");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 1:
                Intent intent = new Intent(this,MainActivity.class);
                //activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case 2:
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * 重写系统自带点击事件(返回键)
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Toast.makeText(OtherActivity.this,"返回",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OtherActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle= new Bundle();
//            MyMap mymap=new MyMap();
//            Map<String,Object> map= new ArrayMap<>();
            bundle.putInt("head",iv_head.getImageAlpha());
            bundle.putString("name",et_name.getText().toString());
            bundle.putString("count",et_count.getText().toString());

            intent.putExtras(bundle);
            setResult(200,intent);
//            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
