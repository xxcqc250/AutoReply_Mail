package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/6.
 */

public class HTCBrowserParser extends AppTempleteParser {
    private String TAG="App_HTC_Browser";

    public HTCBrowserParser(Context context,String appname,String event) {
        super(context,appname,event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.android.browser:id/webview_wrapper");
        List<AccessibilityNodeInfo> url = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.android.browser:id/url");


        if(CacheNodes.size() == 0 || url.size() == 0) return null;
        AccessibilityNodeInfo CacheNode = CacheNodes.get(0);
        AccessibilityNodeInfo CacheUrl = url.get(0);


        if(CacheNode.getChild(0)!=null && CacheNode.getChild(0).getChild(0)!=null) {
            AccessibilityNodeInfo CacheTitle = CacheNode.getChild(0).getChild(0);
            if(CacheTitle.getContentDescription()!= null && CacheUrl.getText()!=null)
                data.append(CacheTitle.getContentDescription()+"\n"+CacheUrl.getText());
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }

    private void test(AccessibilityNodeInfo CacheNode,int hcount){
        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNodechildcount==0) {
            Log.i(TAG,"================"+String.valueOf(hcount)+"================");
            Log.i(TAG,CacheNode.toString());
        }

        for(int i=0; i<CacheNodechildcount ; i++){
            test(CacheNode.getChild(i),hcount+1);
        }
    }
}
