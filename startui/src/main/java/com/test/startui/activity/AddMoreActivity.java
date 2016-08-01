package com.test.startui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.test.startui.R;
import com.test.startui.view.FlowLayout;

public class AddMoreActivity extends AppCompatActivity {
    public static  String[] IN_TEXT = {
            "头条",
            "大湘江",
            "经济",
            "深度",
            "视觉",
            "湖南印象",
            "观点",
            "解放",
            "LOL",
            "湘西"
    };
    FlowLayout flowlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);
        flowlayout = (FlowLayout) findViewById(R.id.flowlayout);
        for (int i = 0; i < IN_TEXT.length; i++) {
            CheckBox cb = new CheckBox(this);
            cb.setText(IN_TEXT[i]);
            flowlayout.addView(cb);
        }


    }

    public void back(View view) {
        finish();
    }
}
