package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/5/20.
 */

public class TVBSParser extends AppTempleteParser {
    private String TAG = "App_TVBS";

    public TVBSParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> Contentlist = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.tvbs.news:id/content_list_recyclerView");
        if(Contentlist.size() == 0) return null;

        List<AccessibilityNodeInfo> TitleNodes = Contentlist.get(0).findAccessibilityNodeInfosByViewId("com.tvbs.news:id/list_item_title");

        for(AccessibilityNodeInfo title : TitleNodes){
            if(title.getText()!=null){
                data.append(title.getText()+"\n");
            }
        }
        Log.i(TAG,data.toString());
        return data.toString();
    }
}
