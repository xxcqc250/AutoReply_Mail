package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/5/9.
 */

public class OperaParser extends AppTempleteParser {
    private static String TAG = "App_Opera";
    private boolean state = false;

    public OperaParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.opera.browser:id/browser_fragment");
        List<AccessibilityNodeInfo> url = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.opera.browser:id/url_field");

        if(CacheNodes.size() == 0 || url.size() == 0) return null;

        String Title = SerachClassName(CacheNodes.get(0),"android.webkit.WebView");
        String Url = ""; if(url.get(0).getText()!=null) Url = url.get(0).getText().toString();
        if(Title.length()!=0 && Url.length()!=0) data.append(Title+"\n"+Url);

        Log.i(TAG,data.toString());
        return data.toString();
    }

    private String SerachClassName(AccessibilityNodeInfo CacheNode,String name){
        String data = "";
        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNode.getClassName() != null && CacheNode.getClassName().equals(name)) {
            state = true;
            if(CacheNode.getContentDescription() != null)
                return CacheNode.getContentDescription().toString();
        }

        for(int i=0; i<CacheNodechildcount ; i++){
            if(state) break;
            data += SerachClassName(CacheNode.getChild(i),name);
        }
        return data;
    }
}
