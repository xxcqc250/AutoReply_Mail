package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by Ming-Yi on 2016/12/8.
 */

public class LineParser extends AppTempleteParser {
    private String TAG = "App_Line";
    public LineParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {

        int out = 0;

        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNodes1 = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("jp.naver.line.android:id/text_message_balloon_root");
        List<AccessibilityNodeInfo> WebNewsNode = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("jp.naver.line.android:id/channel_webview_parent");
        List<AccessibilityNodeInfo> talkWindow = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("jp.naver.line.android:id/message_text");


        if(CacheNodes1.size() > 0){
            for(AccessibilityNodeInfo CacheNode : CacheNodes1){
                if(CacheNode.getChildCount()!=0){
                    if(CacheNode.getChild(0).getContentDescription()!=null)
                        data.append(CacheNode.getChild(0).getContentDescription()+"\n");
                }
            }
        }
        else if(WebNewsNode.size() > 0){
            if(WebNewsNode.get(0).getChildCount() == 0) return null;
            if(WebNewsNode.get(0).getChild(0).getChildCount() == 0) return null;
            AccessibilityNodeInfo WebRoot = WebNewsNode.get(0).getChild(0).getChild(0);
            AccessibilityNodeInfo WebRoot2 = WebRoot.getChild(0).getChild(0);

            for(int i=0 ; i < WebRoot2.getChildCount();i++){
                if(WebRoot2.getChild(i).getClassName().equals("android.widget.ListView"))
                    break;
                if(WebRoot2.getChild(i).getContentDescription() != null)
                    data.append(WebRoot2.getChild(i).getContentDescription()+"\n");

                if(WebRoot2.getChild(i).getChildCount() > 0)
                {
                   for(int k = 0; k < WebRoot2.getChild(i).getChildCount(); k ++)
                   {
                       if(WebRoot2.getChild(i).getChild(k).getContentDescription()!=null)
                       {
                           data.append(WebRoot2.getChild(i).getChild(k)
                                   .getContentDescription()+"\n");

                       }

                       if(WebRoot2.getChild(i).getChild(k).getChildCount() > 0)
                       {
                           if(WebRoot2.getChild(i).getChild(k).getChild(0).getViewIdResourceName() != null)
                           {
                               if(WebRoot2.getChild(i).getChild(k).getChild(0).getViewIdResourceName().equals("article_see_original"))
                               {
                                   out = 1;
                                    break;
                               }
                           }

                           for(int d = 0; d < WebRoot2.getChild(i).getChild(k).getChildCount(); d++)
                           {
                               if(WebRoot2.getChild(i).getChild(k).getChild(d).getContentDescription()!=null)
                               {
                                   Log.e("APP_LLFF",WebRoot2.getChild(i).getChild(k).getChild(d).toString());
                                   data.append(WebRoot2.getChild(i).getChild(k).getChild(d)
                                           .getContentDescription()+"\n");

                               }
                           }
                       }




                   }
                }

                if(out == 1)
                    break;

            }

        }
        if(talkWindow.size()>0)
        {
            for(int i=0;i<talkWindow.size();i++)
            {
                data.append(talkWindow.get(i).getText());
            }

        }

        Log.i(TAG,data.toString());
        return data.toString();
    }
}