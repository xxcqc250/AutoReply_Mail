package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/8.
 */

public class FacebookNewsParser extends AppTempleteParser {
    private String TAG = "App_Facebook";
    public FacebookNewsParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;

        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNewsNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/recycler_view");

        if(CacheNewsNodes.size() == 1){
            for(AccessibilityNodeInfo CacheNewsNode:CacheNewsNodes){
//                if(!CacheNews2Node.toString().contains("ACTION_SCROLL_BACKWARD")) continue;
                for(int i=0; i<CacheNewsNode.getChildCount();i++){
                    List<AccessibilityNodeInfo> ContentNodes = CacheNewsNode.getChild(i).findAccessibilityNodeInfosByViewId("com.facebook.katana:id/richdocument_text");
                    Log.i(TAG,"L:"+String.valueOf(ContentNodes.size()));
                    for(AccessibilityNodeInfo ContentNode : ContentNodes){
                        if(ContentNode.getText()!=null)
                            data.append(ContentNode.getText()+"\n");
                    }
                }
            }
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }

}
