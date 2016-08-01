package com.test.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView iv_test;
    private EditText et_txt;
    Bitmap bitmap;
    private Button btn_add,btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_txt.getText().toString().trim();
                if (text.equals("")){
                    Toast.makeText(MainActivity.this, "请输入要添加的水印文字", Toast.LENGTH_SHORT).show();
                }else {
                    bitmap = drawTextToBitmap(MainActivity.this, R.drawable.login, text);
                    iv_test.setImageBitmap(bitmap);
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmap(bitmap,"temp.png");
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        et_txt = (EditText) findViewById(R.id.et_txt);
        iv_test = (ImageView) findViewById(R.id.iv_test);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    /**
     * 图片添加文字水印的方法
     * @param gContext
     * @param gResId
     * @param gText
     * @return
     */
    public Bitmap drawTextToBitmap(Context gContext, int gResId, String gText) {
        Resources resources = gContext.getResources();
        //获取屏幕参数 density（屏幕的密度，类似分辨率，）
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        bitmap = scaleWithWH(bitmap, 300*scale, 300*scale);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.BLUE);
        paint.setTextSize((int) (20 * scale));
        paint.setDither(true); //获取跟清晰的图像采样
        paint.setFilterBitmap(true);//过滤一些
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = 100;
        int y = 100;
        canvas.drawText(gText, x * scale, y * scale, paint);
        return bitmap;
    }

    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    /**
     * 将bitmap保存在本地
     * @param bitmap
     * @param bitName
     */
    private void saveBitmap(Bitmap bitmap,String bitName){
        File file = new File(Environment.getExternalStorageDirectory()+"/"+bitName);
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
}
