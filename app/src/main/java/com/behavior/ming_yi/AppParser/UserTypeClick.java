package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.Set;

/**
 * Created by ming-yi on 2017/6/27.
 */

public class UserTypeClick extends AppTempleteParser {
    private String TAG = "App_User";
    String app = null;
    String event = null;
    public UserTypeClick(Context context, String appname, String event) {
        super(context, appname, event);
        this.app = appname;
        this.event = event;
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        StringBuilder data = new StringBuilder();

        Log.e(TAG,this.app);
        Log.e(TAG,this.event);
//        TYPE_VIEW_TEXT_CHANGED
//        TYPE_VIEW_TEXT_SELECTION_CHANGED
        if(this.event.equals("TYPE_VIEW_FOCUSED")){
            if(mAccessibilityNodeInfo.isFocusable() && mAccessibilityNodeInfo.isFocused()){
                Log.i(TAG,"not need");
                return null;
            }
            if(mAccessibilityNodeInfo.getText()!=null && mAccessibilityNodeInfo.getText().length()!=0){
                data.append(mAccessibilityNodeInfo.getText()+"\n");
        //            Log.e(TAG,mAccessibilityNodeInfo.getText().toString());
            }
            else if(mAccessibilityNodeInfo.getContentDescription()!=null && mAccessibilityNodeInfo.getContentDescription().length()!=0){
                data.append(mAccessibilityNodeInfo.getContentDescription()+"\n");
        //            Log.e(TAG,mAccessibilityNodeInfo.getContentDescription().toString());
            }
        }
        else
        {
            for(String tmp : GetAllNodeText(mAccessibilityNodeInfo))
            {
                data.append(tmp+" ");
            }
        }

        if(data.toString().length() == 0) return null;
        Log.e(TAG,mAccessibilityNodeInfo.toString());
        Log.i(TAG,data.toString());
        return data.toString();
    }

    private Set<String> GetAllNodeText(AccessibilityNodeInfo CacheNode){
        Set<String> data = new ArraySet<String>();

        if(CacheNode.getText()!= null && CacheNode.getText().length() > 0)
        {
            data.add(CacheNode.getText()+"\n");
        }
        else if(CacheNode.getContentDescription()!= null && CacheNode.getContentDescription().length() > 0)
        {
            data.add(CacheNode.getContentDescription()+"\n");
        }

        for(int i=0; i < CacheNode.getChildCount() ; i++){
            data.addAll(GetAllNodeText(CacheNode.getChild(i)));
        }

        return data;
    }

}
