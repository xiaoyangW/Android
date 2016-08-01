package com.example.arvin.versions;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.arvin.versions.entity.VersionInfo;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;


import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String path = "http://192.168.1.48:8080/gogo/version.xml";
    private Button btn_delte;
    private TextView tv_version;
    String versionCode;//用来比对的——当前软件版本
    VersionInfo versionInfo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_delte= (Button) findViewById(R.id.btn_delte);
        tv_version= (TextView) findViewById(R.id.tv_version);
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute();
        tv_version.setText(getVersionCode());
        //卸载程序
        btn_delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uninstall();
            }
        });
    }

    /**
     * 获取服务器端的xml并解析
     * @param path
     * @return
     * @throws Exception
     */
    private VersionInfo getUpdate(String path) throws Exception {
        VersionInfo info=null;
        URL url=new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode()==HttpURLConnection.HTTP_OK){
            InputStream is = conn.getInputStream();
            //pull解析
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is,"utf-8");
            int eventtype = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT!=eventtype){
                switch (eventtype){
                    case XmlPullParser.START_TAG:
                        if ("version".equals(parser.getName())){
                            info = new VersionInfo();
                        }else if ("versioncode".equals(parser.getName())){
                            info.setVersioncode(parser.nextText());
                        }else if ("versioninfo".equals(parser.getName())){
                            info.setVersioninfo(parser.nextText());
                        }else if ("versionurl".equals(parser.getName())){
                            info.setVersionurl(parser.nextText());
                        }
                        break;
                }
                eventtype = parser.next();
            }
        }

        return info;
    }
    /**
     * 获取程序当前版本
     * @return
     */
    public String getVersionCode(){
        //versionCode 1 int
        //versionName "1.0" String
        //PackageManager——包的管理器
        PackageManager packageManager= getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageInfo.versionName;
    }

    /**
     * 安装程序
     * @param file
     */
    private void install(File file){
        //安装意图
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 卸载程序
     */
    private void uninstall(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:"+this.getPackageName()));
        startActivity(intent);
    }
    private void update() {
        //1.创建一个进度条
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        //2.下载
        //sdcard
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard获取路径
            File file = getExternalCacheDir();//6.0要这么做
            //文件存储
            File path = new File(file, "update.apk");
            Log.d("", path.getAbsolutePath());
            FinalHttp finalHttp = new FinalHttp();
            if (versionInfo != null)
                finalHttp.download(versionInfo.versionurl, path.getAbsolutePath(), new AjaxCallBack<File>() {
                    @Override
                    public void onLoading(long count, long current) {
                        super.onLoading(count, current);
                        dialog.setMax((int) count);//最大刻度
                        dialog.setProgress((int) current);//当前刻度

                    }

                    @Override
                    public void onSuccess(File file) {
                        super.onSuccess(file);
                        dialog.dismiss();//关掉
                        //安装
                        install(file);

                    }
                });
        }
    }

    private class MyAsyncTask extends AsyncTask<Void,Void,VersionInfo>{

        @Override
        protected VersionInfo doInBackground(Void... params) {
            try {
                versionInfo=getUpdate(path);
                System.out.print(versionInfo);
                return versionInfo;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(VersionInfo versionInfo) {
            if (versionInfo!=null&&!versionCode.equals(versionInfo.versioncode)){

                //构造一个对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage(versionInfo.versioninfo);
                builder.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //下载更新
                        update();
                    }
                });
                builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            //super.onPostExecute(versionInfo);
        }
    }
}
