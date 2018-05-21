package com.behavior.ming_yi.SQLite;

/**
 * Created by Ming-Yi on 2016/12/6.
 */

public interface ItemSchema {
    // 資料表名稱
    public static final String TABLE_NAME = "records";
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String DATETIME_COLUMN = "datetime";
    public static final String TEXT_COLUMN = "text";
    public static final String APP_COLUMN = "app";
    public static final String EVENT_COLUMN = "event";
    public static final String STATE_COLUMN = "uploadstate";

    public static final String USER_TOKEN = "token";
    public static final String TOKEN_COLUMN = "token";

    // 建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DATETIME_COLUMN + " TEXT NOT NULL," +
                    TEXT_COLUMN + " TEXT NOT NULL," +
                    APP_COLUMN + " TEXT NOT NULL," +
                    EVENT_COLUMN + " TEXT NOT NULL," +
                    STATE_COLUMN + " INTEGER NOT NULL DEFAULT '0')";

    // 建立表格的SQL指令
    public static final String CREATE_USER_TOKEN =
            "CREATE TABLE " + USER_TOKEN + " (" +
                    TOKEN_COLUMN + " TEXT NOT NULL)";
}
