package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/8.
 */

public class FacebookParser extends AppTempleteParser {
    private String TAG = "App_Facebook";
    public FacebookParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;

        StringBuilder data = new StringBuilder();
        List<AccessibilityNodeInfo> CacheNews1Nodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/browser_chrome");
        List<AccessibilityNodeInfo> CacheNews2Nodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/recycler_view");
        List<AccessibilityNodeInfo> CacheVideoNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/feed_story_message");
        List<AccessibilityNodeInfo> CacheNews3Nodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.facebook.katana:id/webview_container");


        if(CacheNews1Nodes.size() > 0){
            List<AccessibilityNodeInfo> TitleNode = CacheNews1Nodes.get(0).findAccessibilityNodeInfosByViewId("com.facebook.katana:id/text_title");
            if(TitleNode.size()>0){
                if(TitleNode.get(0).getText() != null) data.append(TitleNode.get(0).getText());
            }
        }
        else if(CacheNews2Nodes.size() == 1){
            for(int i=0; i<CacheNews2Nodes.get(0).getChildCount();i++){
                List<AccessibilityNodeInfo> ContentNodes = CacheNews2Nodes.get(0).getChild(i).findAccessibilityNodeInfosByViewId("com.facebook.katana:id/richdocument_text");
                for(AccessibilityNodeInfo ContentNode : ContentNodes){
                    if(ContentNode.getText()!=null)
                        data.append(ContentNode.getText()+"\n");
                }
            }
        }
        else if(CacheVideoNodes.size()>0){
            AccessibilityNodeInfo VideoNode = CacheVideoNodes.get(0);
            if(VideoNode.getContentDescription()!=null)
                data.append(VideoNode.getContentDescription()+"\n");
        }

        if(CacheNews3Nodes.size()>0){
            AccessibilityNodeInfo node3=CacheNews3Nodes.get(0);

            data=SerachClassName(node3,"android.view.View",data);
//            Log.e("Aaaaaaaaaa",data.toString());
//            Log.e("xxxxx",Integer.toString(CacheNews3Nodes.get(0).getChildCount()));
//            Log.e("xxxxx",node3.getChild(0).getChild(0).toString());


//            if(node3.getChild(0).getContentDescription()!=null)
//                Log.e("YYYYYYY:",node3.getChild(0).getContentDescription().toString());
//            else
//                Log.e("YYYYYYY:","12312132");


//            List<AccessibilityNodeInfo> ContentNodes3 =null;
//            ContentNodes3 = SerachClassName(CacheNews3Nodes.get(0),"android.view.View",ContentNodes3);
//            if(ContentNodes3.size()!=0){
//                for(int i=0;i<ContentNodes3.size()-1;i++){
//                    if(ContentNodes3.get(i).getContentDescription()!=null){
//                        data.append(ContentNodes3.get(i).getContentDescription()+"\n");
//                    }
//                }
//            }
//            ContentNodes3.getContentDescription();
        }


        Log.i(TAG,data.toString());
        return data.toString();
    }





    private StringBuilder SerachClassName(AccessibilityNodeInfo CacheNode,String name,StringBuilder data){
//        AccessibilityNodeInfo data=null;

        int CacheNodechildcount = CacheNode.getChildCount();
//        Log.e("APP_POUTPUT:",Integer.toString(CacheNodechildcount));
        if(CacheNode.getClassName() != null && CacheNode.getClassName().equals(name)) {
            if(CacheNode.getContentDescription()!=null) {
                data.append(CacheNode.getContentDescription() + "\n");
            }
            if(CacheNodechildcount>0){
                for(int i=0;i<CacheNodechildcount;i++)
                {
                    data=SerachClassName(CacheNode.getChild(i),"android.view.View",data);
                }
            }
            else{
                return data;
            }
        }
        else{
            for(int i=0; i<CacheNodechildcount ; i++){
                data=SerachClassName(CacheNode.getChild(i),"android.view.View",data);
            }

        }
        return data;
    }

}
