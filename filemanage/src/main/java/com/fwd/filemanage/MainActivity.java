package com.fwd.filemanage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.fwd.filemanage.Adapter.FileAdapter;
import com.fwd.filemanage.Dao.FileDao;
import com.fwd.filemanage.entity.FileBean;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private TextView tv_path;
    private ListView lv_file;
    private FileAdapter fileAdapter;
    private SimpleAdapter gvAdapter;
    private GridView gv_menu;
    FileDao dao = new FileDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMenu();
        fileAdapter = new FileAdapter(dao.getCurrentList("/"),this);
        lv_file.setAdapter(fileAdapter);
        lv_file.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.得到选择的路径。
                FileBean fileBean = (FileBean) fileAdapter.getItem(position);
                String path = fileBean.getPath();
                File file = new File(path);
                if (file.canRead()){
                   /* //2.重新获取List<FileBean>
                    List<FileBean> fileList = dao.getCurrentList(path);
                    //3.刷新adapter
                    fileAdapter.setList(fileList);
                    fileAdapter.notifyDataSetChanged();
                    tv_path.setText(path);*/
                    if(file.isDirectory()) {
                        referesh(path);//调用刷新的方法
                    }else{
                        //复制、删除
                        createDialog(fileBean);
                    }
                }else {
                    Toast.makeText(MainActivity.this,"您还没有权限访问该文件夹",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 底部菜单栏适配器初始化
     */
    private void initView() {
        tv_path = (TextView) findViewById(R.id.tv_path);
        lv_file = (ListView) findViewById(R.id.lv_file);
        gv_menu = (GridView) findViewById(R.id.gv_menu);
    }
    private void initMenu(){
        List<Map<String,Object>> lists = new ArrayList<>();
        String menus[] ={"手机","SD卡","新建","粘贴","退出"};
        int icon[] = {R.drawable.menu_phone,R.drawable.menu_sdcard,R.drawable.menu_palse,R.drawable.menu_search,R.drawable.menu_exit};
        for (int i = 0; i < menus.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("icon",icon[i]);
            map.put("menus",menus[i]);
            lists.add(map);
        }
        gvAdapter = new SimpleAdapter(
                this,
                lists,
                R.layout.gv_item_menus,
                new String[]{"icon","menus"},
                new int[]{R.id.gv_item_image,R.id.gv_item_text}
        );
        gv_menu.setAdapter(gvAdapter);
        /**
         * 点击事件初始化
         */
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        dao.setSdcard(true);
                        referesh(dao.ROOT);
                        break;
                    case 1:
                        dao.setSdcard(false);
                        referesh(dao.getSdcard());
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,"新建",Toast.LENGTH_LONG).show();
                        createDialog();
                        break;
                    case 3:
                        try {
                            dao.copyFile();
                            Toast.makeText(MainActivity.this,"粘贴成功",Toast.LENGTH_LONG).show();
                            referesh(dao.getCurrentFilePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"粘贴失败",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 4:
                        finish();
                        break;
                }
            }
        });
    }

    /**
     * 刷新listView
     * @param path
     */
    private void referesh(String path){
        List<FileBean> newlists = dao.getCurrentList(path);
        //
        fileAdapter.setList(newlists);
        fileAdapter.notifyDataSetChanged();
        tv_path.setText(path);
        dao.setSdcard(true);
    }

    /**
     * 删除，复制，Dialog
     * @param fileBean
     */
    private void createDialog(final FileBean fileBean){
        String names[] = {"复制","删除"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择");
        builder.setItems(names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        dao.setCopyFile(fileBean);
                        Toast.makeText(MainActivity.this, fileBean.getPath(), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        String path = fileBean.getPath();
                        if (dao.deleteFile(path)){
                            Toast.makeText(MainActivity.this, "文件删除成功", Toast.LENGTH_SHORT).show();
                            referesh(tv_path.getText().toString().trim());
                        }else {
                            Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        }).show();
    }

    /**
     * 新建文件
     */
    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新建");
        View view =  View.inflate(this,R.layout.create_file_dialog,null);
        builder.setView(view);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final RadioGroup rg_select = (RadioGroup) view.findViewById(R.id.rg_select);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String path = tv_path.getText().toString().trim();
                String name = et_name.getText().toString().trim();
                switch (rg_select.getCheckedRadioButtonId()){
                    case R.id.rb_file:
                        File file  = new File(path+name);
                        try {
                            dao.createFile(file.getAbsolutePath());
                            referesh(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.rb_folder:
                        File file1 = new File(path+File.separator+name);
                        try {
                            dao.createFolder(file1.getAbsolutePath());
                            referesh(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
