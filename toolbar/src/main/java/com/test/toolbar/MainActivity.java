package com.test.toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class MainActivity extends Activity{

    public Toolbar toolbar;
    public SearchView searchView;
    public Toast toast;
    public long Ctime,Ltime;//点击时间和结束时间;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * toolbar与actionbar类似，但比actionbar更灵活，可以调用style,menu等，
         * 可以放在界面的任意位置
         */
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar");//设置工具栏标题
        toolbar.setLogo(android.R.drawable.title_bar);//图标
        //toolbar.setBackgroundColor(Color.parseColor("#ff37474f"));//背景色

        /**
         * searchview
         */
        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("输入信息");
        String []str={"11","22","33","44","55","66","77","88"};
        searchView.setIconified(false);//设置不显示大的查询图标
        searchView.setSubmitButtonEnabled(false);//不显示查询小图标
        toast=new Toast(this);

        //设置输入监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                /**
                 * 自定义Toast显示时间
                 */

                return false;
            }

        });
    }





}

