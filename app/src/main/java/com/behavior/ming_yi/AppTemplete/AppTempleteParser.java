package com.behavior.ming_yi.AppTemplete;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.SQLite.Database;
import com.behavior.ming_yi.SQLite.Item;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ming-Yi on 2016/12/6.
 */

public abstract class AppTempleteParser extends AsyncTask<AccessibilityNodeInfo,Void,String> {
    private String AppName = null;
    private String Event = null;
    private Context context = null;

    public void setAppName(String name){
        this.AppName = name;
    }

    public void setEvent(String event){
        this.Event = event;
    }

    public AppTempleteParser(Context context,String appname,String event){
        this.context = context;
        this.AppName = appname;
        this.Event = event;
    }

    public AppTempleteParser(Context context,String appname){
        this.context = context;
        this.AppName = appname;
    }

    public abstract String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo);

    @Override
    protected String doInBackground(AccessibilityNodeInfo... params) {
        try{
            return this.AppTempleteParser(params[0]);
        }catch (NullPointerException e){
            return null;
        }

    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        if(data != null && data.trim().length() > 0) insertSQL(data,AppName);
    }

    public void insertSQL(String text,String appname){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        String dts = sdf.format(now);

        if(this.AppName == null || this.Event == null){
            Log.e("Error","請設定AppName,Event");
            return;
        }

        Database db = new Database(this.context);
        Item lastitem = db.getLastRecord();
        Item insertitem = new Item(dts,text.replace("\"","\\\""),this.AppName,this.Event);
        if(!lastitem.data.equals(insertitem.data))
            db.insert(insertitem);
        db.close();
    }

}
