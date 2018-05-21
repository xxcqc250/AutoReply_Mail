package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/6/4.
 */

public class PittParser extends AppTempleteParser {
    private static String TAG = "App_Pitt";

    public PittParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> cacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ihad.ptt:id/articleContentNormalRecyclerView");
        if(cacheNodes.size() == 0)return null;
        List<AccessibilityNodeInfo> TitleNodes = cacheNodes.get(0).findAccessibilityNodeInfosByViewId("com.ihad.ptt:id/articleContentTitle");
        if(TitleNodes.size() > 0){
            if (TitleNodes.get(0).getText()!=null){
                data.append(TitleNodes.get(0).getText()+"\n");
            }
        }

        List<AccessibilityNodeInfo> contentNodes = cacheNodes.get(0).findAccessibilityNodeInfosByViewId("com.ihad.ptt:id/normalText");
        for(AccessibilityNodeInfo contentNode :contentNodes){
            if(contentNode.getText()!=null){
                data.append(contentNode.getText()+"\n");
            }
        }
        Log.i(TAG, data.toString());
        return data.toString();
    }
}
