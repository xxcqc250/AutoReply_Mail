package com.behavior.ming_yi.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/6.
 */

public class Database implements ItemDao<Item> {
    // 資料庫物件
    private SQLiteDatabase db;


    public Database(Context context) {
//        DatabaseContext DbContext = new DatabaseContext(context);
        MySQLiteHelper DbHelper = new MySQLiteHelper(context);
        this.db = DbHelper.getWritableDatabase();
    }

    @Override
    public Item insert(Item item) {
        // 使用ContentValues物件來包裝資料
        ContentValues cv = new ContentValues();

        cv.put(ItemSchema.DATETIME_COLUMN, item.time);
        cv.put(ItemSchema.TEXT_COLUMN, item.data);
        cv.put(ItemSchema.APP_COLUMN, item.appname);
        cv.put(ItemSchema.EVENT_COLUMN, item.event);

        item.id = db.insert(ItemSchema.TABLE_NAME, null, cv);
        return item;
    }

    @Override
    public boolean update(Item item) {
        ContentValues cv = new ContentValues();

        cv.put(ItemSchema.DATETIME_COLUMN, item.time);
        cv.put(ItemSchema.TEXT_COLUMN, item.data);
        cv.put(ItemSchema.APP_COLUMN, item.appname);
        cv.put(ItemSchema.EVENT_COLUMN, item.event);


        String where = ItemSchema.KEY_ID + "=" + item.id;

        return db.update(ItemSchema.TABLE_NAME, cv, where, null) > 0;
    }

    @Override
    public List<Item> getAll() {
        List<Item> data = new ArrayList<>();
        Cursor cursor = db.query(ItemSchema.TABLE_NAME, null, null, null, null, null, ItemSchema.KEY_ID+" DESC");

        while (cursor.moveToNext()) {
            data.add(RecordParser(cursor));
        }

        cursor.close();
        return data;
    }

    @Override
    public boolean delete(long id) {
        // 設定刪除條件，格式為「欄位名稱=資料」
        String where = ItemSchema.KEY_ID + "=" + id;
        return db.delete(ItemSchema.TABLE_NAME, where , null) > 0;
    }

    @Override
    public void deleteAll() {
        db.delete(ItemSchema.TABLE_NAME,null,null);
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public int getRecordNumber() {
        int RecordNumber = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + ItemSchema.TABLE_NAME, null);

        if (cursor.moveToNext()) {
            RecordNumber = cursor.getInt(0);
        }
        cursor.close();

        return RecordNumber;
    }

    public String checkUploadData() {
        int AllRecordNumber = 0;
        int alreadyNumber = 0;
        Cursor cursor = null;

        cursor = db.query(ItemSchema.TABLE_NAME, null, null, null, null, null, null);
        AllRecordNumber = cursor.getCount();

        String whereClause = ItemSchema.STATE_COLUMN + " = ?";
        String [] whereArgs = {"1"};
        cursor = db.query(ItemSchema.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        alreadyNumber = cursor.getCount();

        cursor.close();
        return String.valueOf(alreadyNumber)+"/"+String.valueOf(AllRecordNumber);
    }

    public List<Item> SqlitetoUpload(){
        List<Item> data = new ArrayList<>();
        String whereClause = ItemSchema.STATE_COLUMN + " = ?";
        String [] whereArgs = {"0"};
        Cursor cursor = db.query(ItemSchema.TABLE_NAME, null, whereClause, whereArgs, null, null, ItemSchema.KEY_ID+" ASC");
        while (cursor.moveToNext()) {
            data.add(RecordParser(cursor));
        }
        cursor.close();
        return data;
    }

    public String getToken(){
        String token = null;
        Cursor cursor = db.query(ItemSchema.USER_TOKEN, null, null, null, null, null, null);
        cursor.moveToNext();
        token = cursor.getString(0);
        cursor.close();
        return token;
    }

    public Item getLastRecord(){
        Cursor cursor = db.query(ItemSchema.TABLE_NAME, null, null, null, null, null, ItemSchema.KEY_ID+" DESC");
        if(cursor.getCount() == 0) return new Item();
        cursor.moveToFirst();
        return RecordParser(cursor);
    }

    public boolean UpdateUploadState(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(ItemSchema.STATE_COLUMN, "1");
        String where = ItemSchema.KEY_ID + "=" + item.id;
        return db.update(ItemSchema.TABLE_NAME, cv, where, null) > 0;
    }

    // 解析Cursor的回傳資料
    public Item RecordParser(Cursor cursor) {
        Item item = new Item();
        item.id = (cursor.getLong(0));
        item.time = (cursor.getString(1));
        item.data = (cursor.getString(2));
        item.appname = (cursor.getString(3));
        item.event = (cursor.getString(4));
        item.uploadstate = (cursor.getLong(5));

        return item;
    }
}
