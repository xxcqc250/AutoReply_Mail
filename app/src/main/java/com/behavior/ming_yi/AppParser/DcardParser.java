package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/5.
 */
/*
 * Event : AccessibilityEvent.TYPE_VIEW_SCROLLED
 */
public class DcardParser extends AppTempleteParser {
    private static String TAG="App_Dard";

    public DcardParser(Context context,String appname,String event) {
        super(context,appname,event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;

        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.sparkslab.dcardreader:id/recycler_post");
        List<AccessibilityNodeInfo> CacheMessageNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.sparkslab.dcardreader:id/layout_message");

        if(CacheNodes.size() == 1){
            if (!mAccessibilityNodeInfo.toString().contains("ACTION_SCROLL_BACKWARD")) return null;
            if(CacheNodes.get(0).getChildCount() == 0) return null;
            if(!CacheNodes.get(0).getChild(0).getClassName().equals("android.widget.LinearLayout")) return null;

            List<AccessibilityNodeInfo> CacheContentNodes = CacheNodes.get(0).getChild(0).findAccessibilityNodeInfosByViewId("com.sparkslab.dcardreader:id/layout_content");
            if(CacheContentNodes.size() == 0) return null;

            List<AccessibilityNodeInfo> CacheTitleNodes = CacheNodes.get(0).getChild(0).findAccessibilityNodeInfosByViewId("com.sparkslab.dcardreader:id/text_title");
            if(CacheTitleNodes.size() == 0) return null;

            if(CacheTitleNodes.get(0).getText()!=null){
                data.append(CacheTitleNodes.get(0).getText()+"\n");
            }

            for(AccessibilityNodeInfo CacheContentNode:CacheContentNodes){
                for(int i=0; i<CacheContentNode.getChildCount(); i++){
                    if(CacheContentNode.getChild(i).getText()!=null)
                        data.append(CacheContentNode.getChild(i).getText()+"\n");
                }
            }
        }else if(CacheMessageNodes.size() > 0){
            for(AccessibilityNodeInfo CacheMessageNode:CacheMessageNodes){
                for(int i=0 ; i<CacheMessageNode.getChildCount() ;i++){
                    if(CacheMessageNode.getChild(i).getText()!=null)
                        data.append(CacheMessageNode.getChild(i).getText()+"\n");
                }
            }
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }
}
