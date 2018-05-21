package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;


/**
 * Created by ming-yi on 2017/5/10.
 */

public class EBCnewsParser extends AppTempleteParser {
    private String TAG = "App_EBCnews";

    public EBCnewsParser(Context context, String appname, String event){
        super(context,appname,event);
    }


    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        StringBuilder data = new StringBuilder();
        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ebc.news:id/coordinatorLayout");

        if(CacheNodes.size()==0)return null;

        AccessibilityNodeInfo bodyNode=CacheNodes.get(0);
        data=SearchClassName(bodyNode,"android.view.View",data);

        Log.i(TAG,data.toString());
        return data.toString();
    }

    private StringBuilder SearchClassName(AccessibilityNodeInfo CacheNode,String name,StringBuilder data){
        if(CacheNode.getClassName().equals(name) && CacheNode.getContentDescription()!=null)
        {
            data.append(CacheNode.getContentDescription().toString());
        }

        if(CacheNode.getChildCount()>0)
        {
            for(int i=0;i<CacheNode.getChildCount();i++)
            {
                data=(SearchClassName(CacheNode.getChild(i),name,data));
            }
        }
        return data;
    }

}
