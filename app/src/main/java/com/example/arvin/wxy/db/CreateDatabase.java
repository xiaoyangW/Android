package com.example.arvin.wxy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by Arvin on 16/5/12.
 */
public class CreateDatabase {
    //第一种：上下文
    public SQLiteDatabase createDatabaseFromContext(Context context){
        SQLiteDatabase db =context.openOrCreateDatabase("test.db",Context.MODE_PRIVATE,null);
        return db;

    }
    //第二种：本身
    public SQLiteDatabase createDatabaseFromSQLiteDatabase(Context context){
        File file = new File("/data/data/"+context.getPackageName()+"/test.db");
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file,null);

        return db;
    }
}
