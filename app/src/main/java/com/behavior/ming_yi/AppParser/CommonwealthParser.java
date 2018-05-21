package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/5/10.
 */

public class CommonwealthParser extends AppTempleteParser {
    private String TAG = "App_Commonwealth";

    public CommonwealthParser(Context context, String appname, String event){
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

//        Title
        List<AccessibilityNodeInfo> CacheTitleNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ingree.cwwebsite:id/text_title");
        if(CacheTitleNodes.size() == 0) return  null;
        if(CacheTitleNodes.get(0).getText() != null)
            data.append(CacheTitleNodes.get(0).getText()+"\n");


//        preface

        List<AccessibilityNodeInfo> CachePrefaceNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ingree.cwwebsite:id/text_preface");
        if(CachePrefaceNodes.size() == 0) return null;
        if(CachePrefaceNodes.get(0).getContentDescription()!=null)
            data.append(CachePrefaceNodes.get(0).getContentDescription()+"\n");

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ingree.cwwebsite:id/layout_content");
        if(CacheNodes.size() == 0) return null;

//        WebView
        List<AccessibilityNodeInfo> webNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ingree.cwwebsite:id/layout_web");
        if(webNodes.size() == 0)
            return null;
        else
        {
            AccessibilityNodeInfo FinalWebview = webNodes.get(0).getChild(0).getChild(0);
            for(int i=0;i<FinalWebview.getChildCount()-1;i++)
            {
                if(FinalWebview.getChild(i).getClassName().equals("android.view.View") && FinalWebview.getChild(i).getContentDescription()!=null){
                    data.append(FinalWebview.getChild(i).getContentDescription() + "\n" );
                }
            }
        }

        if(webNodes.get(0).getChildCount() == 0) return null;
        if(webNodes.get(0).getChild(0).getChildCount() == 0) return null;
        AccessibilityNodeInfo FinalWebview = webNodes.get(0).getChild(0).getChild(0);
        for(int i=0 ; i<FinalWebview.getChildCount() ;i++){
            if(FinalWebview.getChild(i).getContentDescription()!=null){
                if(FinalWebview.getChild(i).getContentDescription().toString().contains("【延伸閱讀】"))
                    break;
                data.append(FinalWebview.getChild(i).getContentDescription()+"\n");
            }
        }
        Log.i(TAG,data.toString());
        return data.toString();
    }



//    private StringBuilder SerachClassName(AccessibilityNodeInfo CacheNode,String name,StringBuilder data){
////        AccessibilityNodeInfo data=null;
//
//        int CacheNodechildcount = CacheNode.getChildCount();
////        Log.e("APP_POUTPUT:",Integer.toString(CacheNodechildcount));
//        if(CacheNode.getClassName() != null && CacheNode.getClassName().equals(name)) {
//            if(CacheNode.getContentDescription()!=null) {
//                data.append(CacheNode.getContentDescription() + "\n");
//            }
//            if(CacheNodechildcount>0){
//                for(int i=0;i<CacheNodechildcount-1;i++)
//                {
//                    data=SerachClassName(CacheNode.getChild(i),"android.view.View",data);
//                }
//            }
//            else{
//                return data;
//            }
//        }
//        else{
//            for(int i=0; i<CacheNodechildcount ; i++){
//                data=SerachClassName(CacheNode.getChild(i),"android.view.View",data);
//            }
//
//        }
//        return data;
//    }
}
