package com.example.arvin.wxy.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //单例模式：只有一份数据库
    //1.构造方法私有
    //2.声明一个静态的本类
    private static DatabaseHelper DatabaseHelper;
    //3.提供一个公共的静态方法实例化本类
    public static DatabaseHelper getDatabaseHelperInstance(Context context, int version){
        if(DatabaseHelper==null){
            DatabaseHelper = new DatabaseHelper(context,"bank.db",null,version);
        }
        return DatabaseHelper;
    }
    //指定数据库名，版本
    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表 第一列只能用_id，只识别这个，如果别人表不是_id 你得起个别名 _id
        String sql = "create table account(_id Integer primary key autoincrement,card_id text,name text,balance real check(balance>=0))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库升级
    }


}
