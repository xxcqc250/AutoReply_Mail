package com.behavior.ming_yi.SQLite;

/**
 * Created by Ming-Yi on 2016/11/28.
 */

public class Item {
    public long id;
    public String data;
    public String time;
    public String appname;
    public String event;
    public long uploadstate;

    public Item(String time, String data, String appname, String event)
    {
        this.data = data;
        this.time = time;
        this.appname = appname;
        this.event = event;
    }

    public Item(String time, String data, String appname, String event,long uploadstate)
    {
        this.data = data;
        this.time = time;
        this.appname = appname;
        this.event = event;
        this.uploadstate = uploadstate;
    }



    public Item()
    {
        this.data = "";
        this.time = "";
        this.appname = "";
        this.event = "";
    }
}

