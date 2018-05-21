package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2017/2/28.
 */

public class JPTTParser extends AppTempleteParser {
    private static String TAG = "App_JPTT";

    public JPTTParser(Context context, String appname, String event) {
        super(context,appname,event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> TitleNode = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.joshua.jptt:id/title_text");
        if(TitleNode.size() > 0){
            if(TitleNode.get(0).getText()!=null){
                data.append(TitleNode.get(0).getText()+"\n");
            }
        }
        List<AccessibilityNodeInfo> content_pager = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.joshua.jptt:id/content_pager");
        if(content_pager.size() == 0) return null;
        List<AccessibilityNodeInfo> ContentList = content_pager.get(0).findAccessibilityNodeInfosByViewId("com.joshua.jptt:id/intext_text");
        if (ContentList.size() == 0) return null;
        for(AccessibilityNodeInfo content : ContentList){
            if(content.getText() != null) data.append(content.getText()+"\n");
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }
}
