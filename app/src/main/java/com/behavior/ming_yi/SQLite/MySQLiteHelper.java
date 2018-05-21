package com.behavior.ming_yi.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ming-Yi on 2016/12/6.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    // 資料庫名稱
    public static final String DATABASE_NAME = "mydata.db";
    // 資料庫版本
    public static final int VERSION = 3;

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 創建資料表
        db.execSQL(ItemSchema.CREATE_TABLE);
        db.execSQL(ItemSchema.CREATE_USER_TOKEN);
        db.execSQL("insert into "+ItemSchema.USER_TOKEN+"("+ItemSchema.USER_TOKEN+")"+"values('"+getToken()+"')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的資料表
        db.execSQL("DROP TABLE IF EXISTS " + ItemSchema.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemSchema.CREATE_USER_TOKEN);
        // 呼叫onCreate建立新版的資料表
        onCreate(db);
    }

    private String getToken(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        String dts = sdf.format(now)+String.valueOf((int)(Math.random()* 99999));
        return md5(dts);
    }

    private String md5(String str){
        String md5_str = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mdbytes = md.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            md5_str = sb.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md5_str == null) md5_str = str;
        return md5_str;
    }

}
