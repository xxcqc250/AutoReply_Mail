package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/4/27.
 */

public class ChromeOldParser extends AppTempleteParser {
    private String TAG = "App_Old_Chrome";

    public ChromeOldParser(Context context, String appname, String event) {
        super(context, appname, event);
    }
    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.android.chrome:id/compositor_view_holder");
        List<AccessibilityNodeInfo> url = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar");

        if(CacheNodes.size() == 0 || url.size() == 0) return null;
        AccessibilityNodeInfo CacheNode = CacheNodes.get(0);
        AccessibilityNodeInfo CacheUrl = url.get(0);

        if(CacheNode.getChild(0)!=null && CacheNode.getChild(0).getChild(0)!=null) {
            AccessibilityNodeInfo CacheTitle = CacheNode.getChild(0).getChild(0);
            if(CacheTitle.getContentDescription()!= null &&
                    CacheTitle.getContentDescription().toString().length() !=0 &&
                    CacheUrl.getText()!=null){
                if(CacheTitle.getContentDescription().equals("Redirecting...")) return null;
                data.append(CacheTitle.getContentDescription()+"\n"+CacheUrl.getText());
            }
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }
}
