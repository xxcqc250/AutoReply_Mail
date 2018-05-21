package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ming-yi on 2017/5/4.
 */

public class ChromeNewParser extends AppTempleteParser {
    private String TAG = "App_New_Chrome";
    private boolean state = false;

    public ChromeNewParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheTitleNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.android.chrome:id/title_bar");
        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("android:id/content");
        List<AccessibilityNodeInfo> url = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar");
        String Title = "";
        String Url = "";

//        List<AccessibilityNodeInfo> contexts = FindClassName(mAccessibilityNodeInfo, "android.webkit.WebView");
//        if(contexts.size() > 0){
//            StringBuilder tmp = new StringBuilder();
//            for(String text :GetAllNodeText(contexts.get(0))){
//                tmp.append(text+"\n");
//            }
//            Log.e(TAG,tmp.toString());
//        }

        if(CacheTitleNodes.size() > 0){
            if(CacheTitleNodes.get(0).getText() != null)
                Title = CacheTitleNodes.get(0).getText().toString();
        }else if(CacheNodes.size()> 0){
            Title = SerachClassName(CacheNodes.get(0),"android.webkit.WebView");
        }

        if(url.size() > 0){
            if(url.get(0).getText()!=null)
                Url = url.get(0).getText().toString();
        }


        if(Title.length()!=0 && Url.length()!=0)
            data.append(Title+"\n"+Url);

        Log.i(TAG,data.toString());
        return data.toString();
    }

    private String SerachClassName(AccessibilityNodeInfo CacheNode,String name){
        String data = "";
        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNode.getClassName() != null && CacheNode.getClassName().equals(name)) {
            state = true;
            if(CacheNode.getContentDescription() != null)
                return CacheNode.getContentDescription().toString();
        }

        for(int i=0; i<CacheNodechildcount ; i++){
            if(state) break;
            data += SerachClassName(CacheNode.getChild(i),name);
        }
        return data;
    }

    private List<AccessibilityNodeInfo> FindClassName(AccessibilityNodeInfo CacheNode, String name){
        List<AccessibilityNodeInfo> data = new ArrayList<>();
        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNode.getClassName().equals(name)) {
            data.add(CacheNode);
        }

        for(int i=0; i<CacheNodechildcount ; i++){
            List<AccessibilityNodeInfo> tmp = FindClassName(CacheNode.getChild(i),name);
            if(tmp.size()!=0)
                data.addAll(tmp);
        }
        return data;
    }

    private Set<String> GetAllNodeText(AccessibilityNodeInfo CacheNode){
        Set<String> data = new ArraySet<String>();

        if(CacheNode.getClassName().equals("android.view.View") && CacheNode.getText()!= null) {
            if(CacheNode.getText().length()>0)
                data.add(CacheNode.getText()+"\n");
        }else if(CacheNode.getClassName().equals("android.view.View") && CacheNode.getContentDescription()!= null){
            if(CacheNode.getContentDescription().length()>0)
                data.add(CacheNode.getContentDescription()+"\n");
        }

        for(int i=0; i < CacheNode.getChildCount() ; i++){
            data.addAll(GetAllNodeText(CacheNode.getChild(i)));
        }

        return data;
    }
}