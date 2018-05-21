package com.behavior.ming_yi.Accessibility;

/**
 * Created by Ming-Yi on 2016/11/28.
 */

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppParser.AppleMediaMagazineParser;
import com.behavior.ming_yi.AppParser.AppleMediaParser;
import com.behavior.ming_yi.AppParser.ApplenewsParser;
import com.behavior.ming_yi.AppParser.CNNParser;
import com.behavior.ming_yi.AppParser.ChromeNewParser;
import com.behavior.ming_yi.AppParser.ChromeOldParser;
import com.behavior.ming_yi.AppParser.CnyesParser;
import com.behavior.ming_yi.AppParser.CommonwealthParser;
import com.behavior.ming_yi.AppParser.DcardParser;
import com.behavior.ming_yi.AppParser.EBCnewsParser;
import com.behavior.ming_yi.AppParser.ETTodayParser;
import com.behavior.ming_yi.AppParser.FacebookParser;
import com.behavior.ming_yi.AppParser.HTCBrowserParser;
import com.behavior.ming_yi.AppParser.InstagramParser;
import com.behavior.ming_yi.AppParser.JPTTParser;
import com.behavior.ming_yi.AppParser.LineParser;
import com.behavior.ming_yi.AppParser.MessengerParser;
import com.behavior.ming_yi.AppParser.MoPTTParser;
import com.behavior.ming_yi.AppParser.NewDcardParser;
import com.behavior.ming_yi.AppParser.OperaParser;
import com.behavior.ming_yi.AppParser.PTTplusParser;
import com.behavior.ming_yi.AppParser.PittParser;
import com.behavior.ming_yi.AppParser.TVBSParser;
import com.behavior.ming_yi.AppParser.UserTypeClick;
import com.behavior.ming_yi.AppParser.UserTypeClickNew;
import com.behavior.ming_yi.AppTemplete.AppNameList;

import java.util.List;

public class MyAccessibility extends AccessibilityService {
    private static final String TAG = "MyAccessibility";
    int DeviceVersion = Character.getNumericValue(android.os.Build.VERSION.RELEASE.charAt(0));

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "config success!");
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        accessibilityServiceInfo.notificationTimeout = 100;
        setServiceInfo(accessibilityServiceInfo);
    }

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub
        int eventType = event.getEventType();

        if(event == null) return;

        final AccessibilityNodeInfo mAccessibilityNodeInfo = event.getSource();
        if (mAccessibilityNodeInfo == null) return;

        String PackageName = null;
        try {
            PackageName = mAccessibilityNodeInfo.getPackageName().toString();
        }catch (NullPointerException e){
            return;
        }

        String eventText = GetAccessibilityEvent(eventType);
        if(eventText == null) return;

        Log.e(TAG,eventText);
        Log.e(TAG,mAccessibilityNodeInfo.toString());
        Log.e(TAG,"====================================================");


//        if(eventText == "TYPE_VIEW_CLICKED" || eventText == "TYPE_VIEW_TEXT_CHANGED" || eventText == "TYPE_VIEW_FOCUSED" || eventText == "TYPE_VIEW_TEXT_SELECTION_CHANGED"){
//            UserTypeClick UTC = new UserTypeClick(MyAccessibility.this, AppNameList.getName(PackageName), eventText);
//            UTC.execute(mAccessibilityNodeInfo);
//        }else{
//            ApplicationExecutor(PackageName,eventText,mAccessibilityNodeInfo);
//        }

        if(eventText == "TYPE_VIEW_CLICKED" || eventText == "TYPE_VIEW_TEXT_CHANGED" || eventText == "TYPE_VIEW_FOCUSED" || eventText == "TYPE_VIEW_TEXT_SELECTION_CHANGED")
        {
            UserTypeClickNew UTC2 = new UserTypeClickNew(MyAccessibility.this, AppNameList.getName(PackageName), eventText);
            UTC2.execute(mAccessibilityNodeInfo);
        }
        else
        {
            ApplicationExecutor(PackageName,eventText,mAccessibilityNodeInfo);
        }
    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub
    }



    private void ApplicationExecutor(String PackageName, String eventText, AccessibilityNodeInfo mAccessibilityNodeInfo){
        switch (PackageName){
            case AppNameList.Dcard:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                DcardParser dcard = new DcardParser(MyAccessibility.this, "Dcard", eventText);
                dcard.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.NewDcard:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                NewDcardParser newdcard = new NewDcardParser(MyAccessibility.this, "Dcard", eventText);
                newdcard.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.JPTT:
                if (eventText != "TYPE_WINDOW_CONTENT_CHANGED") break;
                JPTTParser JPTT = new JPTTParser(MyAccessibility.this, "JPTT", eventText);
                JPTT.execute(getRootInActiveWindow());
                break;

            case AppNameList.HTCBrowser:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                HTCBrowserParser htcbrowser = new HTCBrowserParser(MyAccessibility.this, "HTCBrowser", eventText);
                htcbrowser.execute(getRootInActiveWindow());
                break;

            case AppNameList.Line:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                LineParser line = new LineParser(MyAccessibility.this, "Line", eventText);
                line.execute(getRootInActiveWindow());
                break;

            case AppNameList.Facebook:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                FacebookParser facebook = new FacebookParser(MyAccessibility.this, "Facebook", eventText);

                List<AccessibilityNodeInfo> checkView = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.facebook.katana:id/browser_chrome");
                if(checkView.size()>0)
                    facebook.execute(getRootInActiveWindow());
                else
                    facebook.execute(mAccessibilityNodeInfo);

                break;

            case AppNameList.Chrome:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                if(DeviceVersion <= 4){
                    ChromeOldParser chrome = new ChromeOldParser(MyAccessibility.this, "Chrome", eventText);
                    chrome.execute(getRootInActiveWindow());
                }
                else{
                    ChromeNewParser chrome = new ChromeNewParser(MyAccessibility.this, "Chrome", eventText);
                    chrome.execute(getRootInActiveWindow());
                }
                break;

            case AppNameList.Opera:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                OperaParser opera = new OperaParser(MyAccessibility.this, "Opera", eventText);
                opera.execute(getRootInActiveWindow());
                break;

            case AppNameList.MoPTT:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                MoPTTParser moptt = new MoPTTParser(MyAccessibility.this, "MoPTT", eventText);
                moptt.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.Instagram:
                if (eventText != "TYPE_WINDOW_CONTENT_CHANGED") break;
                InstagramParser instagram = new InstagramParser(MyAccessibility.this, "Instagram", eventText);
                instagram.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.Messenger:
                if (eventText != "TYPE_WINDOW_CONTENT_CHANGED") break;
                MessengerParser messenger = new MessengerParser(MyAccessibility.this,"Messenger",eventText);
                messenger.execute(getRootInActiveWindow());
                break;

            case AppNameList.EBCnews:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                EBCnewsParser ebc = new EBCnewsParser(MyAccessibility.this,"EBCNews",eventText);
                ebc.execute(getRootInActiveWindow());
                break;

            case AppNameList.Applenews:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                ApplenewsParser apple = new ApplenewsParser(MyAccessibility.this,"AppleNews",eventText);
                apple.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.Commonwealth:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                CommonwealthParser commonwealth = new CommonwealthParser(MyAccessibility.this,"Commonwealth",eventText);
                commonwealth.execute(getRootInActiveWindow());
                break;

            case AppNameList.TVBS:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                TVBSParser TVBS = new TVBSParser(MyAccessibility.this,"TVBS",eventText);
                TVBS.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.PTTplus:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                PTTplusParser pptplus = new PTTplusParser(MyAccessibility.this,"PTTplus",eventText);
                pptplus.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.AppleMedia:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                AppleMediaParser AppleMedia = new AppleMediaParser(MyAccessibility.this,"AppleMedia",eventText);
                AppleMedia.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.AppleMediaMagazine:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                AppleMediaMagazineParser AppleMediaMagazine = new AppleMediaMagazineParser(MyAccessibility.this,"AppleMedia",eventText);
                AppleMediaMagazine.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.ETToday:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                ETTodayParser ETToday = new ETTodayParser(MyAccessibility.this,"AppleMedia",eventText);
                ETToday.execute(getRootInActiveWindow());
                break;

            case AppNameList.Cnyes:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                CnyesParser Cnyes = new CnyesParser(MyAccessibility.this,"Cnyes",eventText);
                Cnyes.execute(mAccessibilityNodeInfo);
                break;

            case AppNameList.PiTT:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                PittParser Pitt = new PittParser(MyAccessibility.this,"Cnyes",eventText);
                Pitt.execute(getRootInActiveWindow());
                break;

            case AppNameList.CNN:
                if (eventText != "TYPE_VIEW_SCROLLED") break;
                CNNParser cnn = new CNNParser(MyAccessibility.this,"cnn",eventText);
                cnn.execute(getRootInActiveWindow());


        }
    }

    private String GetAccessibilityEvent(int eventType){
        String eventText = null;
        switch (eventType) {
//            //點擊的東西
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "TYPE_VIEW_CLICKED";
                break;
//
//            // Edittext原本字串
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                break;
//
//            // 改變Edittext內容時
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                break;


//            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
//                eventText = "TYPE_VIEW_LONG_CLICKED";
//                break;
//
//            case AccessibilityEvent.TYPE_VIEW_SELECTED:
//                eventText = "TYPE_VIEW_SELECTED";
//                break;
//
//            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
//                eventText = "TYPE_WINDOW_STATE_CHANGED";
//                break;
//
//            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
//                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
//                break;
//
//            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
//                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
//                break;
//            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
//                eventText = "TYPE_ANNOUNCEMENT";
//                break;
//
//            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
//                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
//                break;
//
//            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
//                eventText = "TYPE_VIEW_HOVER_ENTER";
//                break;
//
//            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
//                eventText = "TYPE_VIEW_HOVER_EXIT";
//                break;

            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                break;

            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
        }
        return eventText;
    }

}