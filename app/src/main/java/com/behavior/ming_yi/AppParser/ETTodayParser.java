package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;

/**
 * Created by ming-yi on 2017/5/24.
 */

public class ETTodayParser extends AppTempleteParser {
    private String TAG = "App_ETToday";

    public ETTodayParser(Context context, String appname, String event){
        super(context,appname,event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        StringBuilder data = new StringBuilder();

        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("net.ettoday.phone:id/et_wv_scrollview");
        if(CacheNodes.size() == 0) return null;

        AccessibilityNodeInfo FinalNode = SerachClassName(CacheNodes.get(0),"android.webkit.WebView");
        if(FinalNode == null) return null;
        if(FinalNode.getChildCount() == 0) return null;

//        if(FinalNode.getChild(0).getContentDescription()!=null)
//        {
//            data.append(FinalNode.getChild(0).getContentDescription());
//        }

        String vv = Integer.toString(FinalNode.getChild(0).getChildCount());

        for(int k=0 ; k < FinalNode.getChild(0).getChild(1).getChildCount() ; k++)
        {
            String x = FinalNode.getChild(0).getChild(1).getChild(k).getViewIdResourceName();
            if(x != null)
            {
                if (x.equals("next-prev"))
                {
                    break;
                }
            }

            if(FinalNode.getChild(0).getChild(1).getChild(k).getContentDescription()!=null)
            {
                if(FinalNode.getChild(0).getChild(1).getChild(k).getContentDescription().equals("點我參加"))
                    continue;
                if(FinalNode.getChild(0).getChild(1).getChild(k).getContentDescription().toString().contains("ETtoday東森新聞雲旗艦APP")||
                        FinalNode.getChild(0).getChild(1).getChild(k).getContentDescription().equals("推薦閱讀"))
                    break;
                data.append(FinalNode.getChild(0).getChild(1).getChild(k).getContentDescription()+"\n");

                if(FinalNode.getChild(0).getChild(1).getChild(k).getChildCount() != 0)
                {
                    for(int f = 0; f < FinalNode.getChild(0).getChild(1).getChild(k).getChildCount(); f++)
                    {
                        if(FinalNode.getChild(0).getChild(1).getChild(k).getChild(f).getContentDescription() != null)
                        {

                            if(FinalNode.getChild(0).getChild(1).getChild(k).getChild(f).getContentDescription().toString().contains("ETtoday東森新聞雲旗艦APP")||
                                    FinalNode.getChild(0).getChild(1).getChild(k).getChild(f).getContentDescription().equals("推薦閱讀"))
                                break;

                            data.append(FinalNode.getChild(0).getChild(1).getChild(k).getChild(f).getContentDescription()+"\n");

                        }
                    }
                }
            }
        }

//        for(int i=0 ; i<FinalNode.getChild(0).getChild(1).getChildCount() ; i++){
//            if(FinalNode.getChild(0).getChild(1).getChild(i).getContentDescription()!=null){
//                Log.e("APP_TTTT",FinalNode.getChild(0).getChild(1).getChild(i).toString());
//                if(FinalNode.getChild(0).getChild(1).getChild(i).getContentDescription().equals("點我參加"))
//                    continue;
//                if(FinalNode.getChild(0).getChild(1).getChild(i).getContentDescription().toString().contains("ETtoday東森新聞雲旗艦APP")||
//                        FinalNode.getChild(0).getChild(1).getChild(i).getContentDescription().equals("推薦閱讀 Heading"))
//                    break;
//
//                data.append(FinalNode.getChild(0).getChild(1).getChild(i).getContentDescription()+"\n");
//            }
//        }


        Log.i(TAG,data.toString());
        return data.toString();
    }

    private AccessibilityNodeInfo SerachClassName(AccessibilityNodeInfo CacheNode,String name){
        AccessibilityNodeInfo data = null;

        int CacheNodechildcount = CacheNode.getChildCount();

        if(CacheNode.getClassName() != null && CacheNode.getClassName().equals(name)) {
            return CacheNode;
        }

        for(int i=0; i<CacheNodechildcount ; i++){
            if(data!=null) break;
            data = SerachClassName(CacheNode.getChild(i),name);
        }

        return data;
    }
}
