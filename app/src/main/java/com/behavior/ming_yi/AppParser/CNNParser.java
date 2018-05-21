package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/6/13.
 */

public class CNNParser extends AppTempleteParser {
    private String TAG = "App_CNN";

    public CNNParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;

        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.cnn.mobile.android.phone:id/article_item_container");

        for(AccessibilityNodeInfo CacheNode:CacheNodes){
            data.append(GetAllNodeText(CacheNode)+"\n");
        }
//        GetAllNodeText(CacheNodes.get(0));
        Log.i(TAG,data.toString());
        return data.toString();
    }

    private String GetAllNodeText(AccessibilityNodeInfo CacheNode){
        StringBuilder data = new StringBuilder();

        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNode.getClassName().equals("android.widget.TextView")){
            if(CacheNode.getText()!=null)
                data.append(CacheNode.getText()+"\n");
        }
        for(int i=0; i<CacheNodechildcount ; i++){
            data.append(GetAllNodeText(CacheNode.getChild(i)));
        }
        return data.toString();
    }
}
