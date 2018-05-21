package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/5.
 */
/*
 * Event : AccessibilityEvent.TYPE_VIEW_SCROLLED
 */
public class MoPTTParser extends AppTempleteParser {
    private static String TAG = "App_MoPTT";

    public MoPTTParser(Context context,String appname,String event) {
        super(context,appname,event);
    }

    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo){
            if (mAccessibilityNodeInfo == null) return null;
            StringBuilder data = new StringBuilder();
            List<AccessibilityNodeInfo> CacheRootNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("mong.moptt:id/containerWrapper");
            if(CacheRootNodes.size()==0) return null;
            List<AccessibilityNodeInfo> CacheNextNodes = CacheRootNodes.get(0).findAccessibilityNodeInfosByViewId("mong.moptt:id/container");
            if(CacheNextNodes.size()==0) return null;
            List<AccessibilityNodeInfo> CacheTitleNode = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("mong.moptt:id/title");
            if(CacheTitleNode.size()>0){
                if(CacheTitleNode.get(0).getText()!=null) data.append(CacheTitleNode.get(0).getText()+"\n");
            }
            AccessibilityNodeInfo FinalNode = CacheNextNodes.get(0);
            for(int i=0;i<FinalNode.getChildCount();i++){
                if(FinalNode.getChild(i).getText()!=null)
                    data.append(FinalNode.getChild(i).getText()+"\n");
            }
            Log.i(TAG,data.toString());
            return data.toString();
    }
}
