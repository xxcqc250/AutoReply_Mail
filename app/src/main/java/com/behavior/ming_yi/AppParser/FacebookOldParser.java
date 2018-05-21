package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/8.
 */

public class FacebookOldParser extends AppTempleteParser {
    private String TAG = "App_Facebook";
    public FacebookOldParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;

        StringBuilder data = new StringBuilder();
        List<AccessibilityNodeInfo> CacheTitleNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/header_view_title");
        List<AccessibilityNodeInfo> CacheSubTitleNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/header_view_sub_title");
        List<AccessibilityNodeInfo> CacheContentNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/feed_story_message");

        if(CacheTitleNodes.size() == 1 && CacheSubTitleNodes.size() == 1){
            if(CacheTitleNodes.get(0).getContentDescription()!=null){
                data.append(CacheTitleNodes.get(0).getContentDescription()+"\n");
            }
            if(CacheSubTitleNodes.get(0).getContentDescription()!=null){
                data.append(CacheSubTitleNodes.get(0).getContentDescription()+"\n");
            }
            for(AccessibilityNodeInfo CacheContentNode:CacheContentNodes){
                if(CacheContentNode.getContentDescription()!=null){
                    data.append(CacheContentNode.getContentDescription()+"\n");
                }
            }
        }
        else{
            return null;
        }



        Log.e("App_Name","Facebook");
        Log.i("App_Content",data.toString());
        return data.toString();
    }

    private String GetAllNodeText(AccessibilityNodeInfo CacheNode){
        StringBuilder data = new StringBuilder();

        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNode.getClassName().equals("android.widget.ListView")){
            if(CacheNode.getContentDescription()!=null)
                data.append(CacheNode.getContentDescription()+"\n");
        }
        for(int i=0; i<CacheNodechildcount ; i++){
            data.append(GetAllNodeText(CacheNode.getChild(i)));
        }
        return data.toString();
    }
}
