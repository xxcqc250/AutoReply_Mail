package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/5.
 */

public class MessengerParser extends AppTempleteParser {
    private String TAG = "App_Messenger";

    public MessengerParser(Context context,String appname,String event) {
        super(context,appname,event);
    }


    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
//        if (!mAccessibilityNodeInfo.getClassName().toString().equals("android.widget.ListView")) return null;

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.orca:id/message_text");
        if (CacheNodes.size() <= 0) return null;

        StringBuilder data = new StringBuilder();

        for(AccessibilityNodeInfo CacheNode : CacheNodes){
            data.append(CacheNode.getText()+"\n");
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
