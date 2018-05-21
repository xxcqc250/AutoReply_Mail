package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/5/10.
 */

public class ApplenewsParser extends AppTempleteParser {
    private String TAG = "App_Applenews";

    public ApplenewsParser(Context context, String appname, String event){
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheTitleNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.nextmediatw:id/tv_title");
        List<AccessibilityNodeInfo> CacheContentNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.nextmediatw:id/ll_content");


        if(CacheTitleNodes.size() == 0 || CacheContentNodes.size() == 0) return null;
        AccessibilityNodeInfo CacheNode = CacheContentNodes.get(0);

        StringBuilder Title = new StringBuilder();
        StringBuilder Content = new StringBuilder();

        if(CacheTitleNodes.get(0).getText() != null) Title.append(CacheTitleNodes.get(0).getText().toString());
        if(CacheNode.getChildCount() == 0) return null;
        for(int x=0 ; x < CacheNode.getChildCount() ; x++)
        {
            if(CacheNode.getChild(x).getText() != null)
            {
                Content.append(CacheNode.getChild(x).getText()+"\n");
            }
        }

        if(Title !=null && Content != null){
            data.append(Title+"\n");
            data.append(Content);
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }

}
