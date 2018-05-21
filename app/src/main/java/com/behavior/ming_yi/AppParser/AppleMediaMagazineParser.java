package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/5/24.
 */

public class AppleMediaMagazineParser extends AppTempleteParser {
    private String TAG = "App_AppleMediaMagazine";

    public AppleMediaMagazineParser(Context context, String appname, String event){
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

//        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("tw.com.nextmedia.magazine:id/fragment_article_details_scrollContainer");
//        if(CacheNodes.size() == 0) return null;

//        List<AccessibilityNodeInfo> CacheTitleNodes = CacheNodes.get(0).findAccessibilityNodeInfosByViewId("tw.com.nextmedia.magazine:id/tv_title");
        List<AccessibilityNodeInfo> CacheTitleNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("tw.com.nextmedia.magazine:id/tv_title");
        List<AccessibilityNodeInfo> CacheContentNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("tw.com.nextmedia.magazine:id/ll_content");

        if(CacheTitleNodes.size() == 0 || CacheContentNodes.size() == 0) return null;
        AccessibilityNodeInfo CacheNode = CacheContentNodes.get(0);

//        if(CacheTitleNodes.size() > 0)
//            if(CacheTitleNodes.get(0).getText()!=null)
//                data.append(CacheTitleNodes.get(0).getText()+"\n");

//        List<AccessibilityNodeInfo> CacheContentNodes = CacheNodes.get(0).findAccessibilityNodeInfosByViewId("tw.com.nextmedia.magazine:id/ll_content");
//          if(CacheContentNodes.size() == 0) return null;
        if(CacheNode.getChildCount() == 0) return null;
        if(CacheTitleNodes.get(0).getText() != null) data.append(CacheTitleNodes.get(0).getText().toString());

//        for(int i=0;i<CacheContentNodes.get(0).getChildCount();i++){
//            if(CacheContentNodes.get(0).getChild(i).getText()!=null) {
//                data.append(CacheContentNodes.get(0).getChild(i).getText() + "\n");
//            }
//        }

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