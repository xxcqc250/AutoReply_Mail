package com.behavior.ming_yi.SQLite;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ming-Yi on 2016/12/6.
 */

public class DatabaseContext extends ContextWrapper {

    /**
     * 建構子
     * @param    context
     */
    public DatabaseContext(Context context) {
        super(context);
    }

    @Override
    public File getDatabasePath(String name) {
        // 判斷是否有SD卡
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());

        if(!sdExist){
            Log.e("SDcard：", "SD卡不存在，請插入SD卡");
            return null;
        }
        else{
            // 取得SD卡資訊
            String dbDir=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

            // 取得PackageName當成資料夾名稱
            dbDir += "/"+this.getPackageName().toString();

            // 設定SQLite資料庫檔案路徑
            String dbPath = dbDir+"/"+name;

            // 判斷資料夾是否存在，不存在於創建資料夾
            File dirFile = new File(dbDir);
            if(!dirFile.exists())
                dirFile.mkdirs();

            // 資料庫是否創建成功
            boolean isFileCreateSuccess = false;

            // 判斷資料庫檔案是否存在，不存在於創建該資料庫檔案
            File dbFile = new File(dbPath);
            if(!dbFile.exists()){
                try {
                    // 創建資料庫檔案
                    isFileCreateSuccess = dbFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else
                isFileCreateSuccess = true;

            // 回傳資料庫物件
            if(isFileCreateSuccess)
                return dbFile;
            else
                return null;
        }
    }
    /**
     * 重載這個方法，是用來打開SD卡上的資料庫，android 2.3及以下會調用這些方法。
     *
     * @param    name
     * @param    mode
     * @param    factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }

    /**
     * Android 4.0會調用此方法獲取資料庫。
     *
     * @see ContextWrapper#openOrCreateDatabase(String, int,
     *              SQLiteDatabase.CursorFactory,
     *              DatabaseErrorHandler)
     * @param    name
     * @param    mode
     * @param    factory
     * @param     errorHandler
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }
}
