package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/5/23.
 */

public class AppleMediaParser extends AppTempleteParser {
    private String TAG = "App_AppleMedia";

    public AppleMediaParser(Context context, String appname, String event){
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

//        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.nextmedia:id/fragment_article_details_scrollContainer");
//        if(CacheNodes.size() == 0) return null;
        List<AccessibilityNodeInfo> CacheTitleNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.nextmedia:id/tv_title");
        List<AccessibilityNodeInfo> CacheContentNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.nextmedia:id/ll_content");
//        if(CacheTitleNodes.size() > 0)
//            if(CacheTitleNodes.get(0).getText()!=null)
//                data.append(CacheTitleNodes.get(0).getText()+"\n");

//        List<AccessibilityNodeInfo> CacheContentNodes = CacheNodes.get(0).findAccessibilityNodeInfosByViewId("com.nextmedia:id/ll_content");
//        if(CacheContentNodes.size() == 0) return null;
        if(CacheTitleNodes.size() == 0 || CacheContentNodes.size() == 0) return null;

        AccessibilityNodeInfo CacheNode = CacheContentNodes.get(0);
//
//       for(int i=0;i<CacheContentNodes.get(0).getChildCount();i++){
//           if(CacheContentNodes.get(0).getChild(i).getText()!=null) {
//               data.append(CacheContentNodes.get(0).getChild(i).getText() + "\n");
//           }
//       }
        if(CacheTitleNodes.get(0).getText() != null) data.append(CacheTitleNodes.get(0).getText().toString());
        if(CacheNode.getChildCount() == 0) return null;
        for(int x=0 ; x < CacheNode.getChildCount() ; x++)
        {
            if(CacheNode.getChild(x).getText() != null)
            {
               data.append(CacheNode.getChild(x).getText()+"\n");
            }
        }
        Log.i(TAG,data.toString());
        return data.toString();
    }
}
