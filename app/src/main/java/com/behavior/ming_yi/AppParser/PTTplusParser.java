package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

/**
 * Created by ming-yi on 2017/5/21.
 */

public class PTTplusParser extends AppTempleteParser {
    private String TAG = "App_PPT+";

    public PTTplusParser(Context context, String appname, String event) {
        super(context, appname, event);
    }

    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {

        if (mAccessibilityNodeInfo == null) return null;
        if (mAccessibilityNodeInfo.getChildCount() == 0) return null;
        StringBuilder data = new StringBuilder();
        AccessibilityNodeInfo ContentNode = SerachClassName(mAccessibilityNodeInfo,"android.view.View");
        if(ContentNode.getChildCount() < 3) return null;
        Log.e("APP_EE",Integer.toString(ContentNode.getChildCount()));
        AccessibilityNodeInfo ContentNextNode = ContentNode.getChild(0);
        AccessibilityNodeInfo ContentNextNode2 = ContentNode.getChild(2);


        Log.e("APP_EE",ContentNextNode2.toString());
        if(ContentNextNode.getChildCount() == 0) return null;
        for(int k = 0; k < ContentNextNode.getChildCount(); k++)
        {
            if(ContentNextNode.getChild(k).getContentDescription().equals(""))
            {
                if(ContentNextNode.getChild(k).getChildCount() > 0)
                {
                    for(int d = 0; d < ContentNextNode.getChild(k).getChildCount(); d++)
                    {
                        if(ContentNextNode.getChild(k).getChild(d).getContentDescription().toString().length() > 0)
                        {
                            data.append(ContentNextNode.getChild(k).getChild(d).getContentDescription().toString() + "\n");
                        }

                    }
                }
            }
            else
            {
                data.append(ContentNextNode.getChild(k).getContentDescription().toString() + "\n");
            }
        }

        if(ContentNextNode2.getChildCount() == 0) return null;
        for(int k = 0; k < ContentNextNode2.getChildCount(); k++)
        {
            if(ContentNextNode2.getChild(k).getContentDescription().equals(""))
            {
                if(ContentNextNode2.getChild(k).getChildCount() > 0)
                {
                    for(int d = 0; d < ContentNextNode2.getChild(k).getChildCount(); d++)
                    {
                        if(ContentNextNode2.getChild(k).getChild(d).getContentDescription().toString().length() > 0)
                        {
                            data.append(ContentNextNode2.getChild(k).getChild(d).getContentDescription().toString() + "\n");
                        }
                        else
                        {
                            if(ContentNextNode2.getChild(k).getChild(d).getChildCount() > 0)
                            {
                                for(int s = 0; s < ContentNextNode2.getChild(k).getChild(d).getChildCount(); s++)
                                {
                                    if(ContentNextNode2.getChild(k).getChild(d).getChild(s).getContentDescription().equals(""))
                                    {
                                        data.append(ContentNextNode2.getChild(k).getChild(d).getChild(s).getChild(0).getContentDescription().toString() + "\n");
                                        data.append(ContentNextNode2.getChild(k).getChild(d).getChild(s).getChild(1).getContentDescription().toString() + "\n");data.append(ContentNextNode2.getChild(k).getChild(d).getChild(s).getContentDescription().toString() + "\n");
                                    }
                                    else
                                    {
                                        data.append(ContentNextNode2.getChild(k).getChild(d).getChild(s).getContentDescription().toString() + "\n");
                                    }
                                }
                            }
                        }

                    }
                }
            }
            else
            {
                data.append(ContentNextNode2.getChild(k).getContentDescription().toString() + "\n");
            }
        }

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
