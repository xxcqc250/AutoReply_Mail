package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/4/26.
 */

public class InstagramParser extends AppTempleteParser {
    private String TAG = "App_Instagram";

    public InstagramParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;

        StringBuilder data = new StringBuilder();
        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.instagram.android:id/row_feed_textview_comments");

        if(CacheNodes.size() == 0) return null;

        for(AccessibilityNodeInfo CacheNode : CacheNodes){
            if(CacheNode.getText() != null){
                String tmp = CacheNode.getText().toString().split("\n\n")[0];
                if (tmp.length() != 0)
                    tmp = tmp.substring(tmp.indexOf(" ")+1,tmp.length());
                data.append(tmp+"\n");
            }
        }
        Log.i(TAG,data.toString());
        return data.toString();
    }
}
