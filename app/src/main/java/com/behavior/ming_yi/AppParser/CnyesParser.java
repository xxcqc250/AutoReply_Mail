package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ming-yi on 2017/6/1.
 */

public class CnyesParser extends AppTempleteParser {
    private String TAG = "App_Cnyes";

    public CnyesParser(Context context, String appname, String event) {
        super(context, appname, event);
    }


    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> tmp = FindClassName(mAccessibilityNodeInfo,"android.widget.ScrollView");

        if(tmp.size()!=0){
            AccessibilityNodeInfo tmpNode = tmp.get(0);
            for(int i=0;i<tmpNode.getChildCount();i++){
                if(tmpNode.getChild(i).getText()!=null){
                    data.append(tmpNode.getChild(i).getText()+"\n");
                }
            }
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }

    private List<AccessibilityNodeInfo> FindClassName(AccessibilityNodeInfo CacheNode, String name){
        List<AccessibilityNodeInfo> data = new ArrayList<>();
        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNode.getClassName().equals(name)) {
            data.add(CacheNode);
        }

        for(int i=0; i<CacheNodechildcount ; i++){
            List<AccessibilityNodeInfo> tmp = FindClassName(CacheNode.getChild(i),name);
            if(tmp.size()!=0)
                data.addAll(tmp);
        }

        return data;
    }
}
