package com.behavior.ming_yi.AppTemplete;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by Ming-Yi on 2016/12/5.
 */

public class AppNameList {
    // Support AppName List
    public final static String HTCBrowser = "com.android.browser";
    public final static String ASUSBrowser = "com.asus.browser";
    public final static String Chrome = "com.android.chrome";
    public final static String Dcard = "com.sparkslab.dcardreader";
    public final static String NewDcard = "com.sparkslab.dcard";
    public final static String Line = "jp.naver.line.android";
    public final static String MoPTT = "mong.moptt";
    public final static String Facebook = "com.facebook.katana";
    public final static String Messenger = "com.facebook.orca";
    public final static String JPTT = "com.joshua.jptt";
    public final static String Instagram = "com.instagram.android";
    public final static String Opera = "com.opera.browser";
    public final static String EBCnews = "com.ebc.news";
    public final static String ETToday = "net.ettoday.phone";
    public final static String Applenews = "com.nextmediatw";
    public final static String AppleMedia = "com.nextmedia";
    public final static String AppleMediaMagazine = "tw.com.nextmedia.magazine";
    public final static String Commonwealth = "com.ingree.cwwebsite";
    public final static String TVBS = "com.tvbs.news";
    public final static String PTTplus = "com.pttplus";
    public final static String Cnyes = "com.cnyes.android";
    public final static String PiTT = "com.ihad.ptt";
    public final static String CNN = "com.cnn.mobile.android.phone";

    public static String getName(String PackageName){
        String AppNameClass = null;
        switch (PackageName){
            case HTCBrowser:
                AppNameClass = "HTCBrowser";
                break;

            case Chrome:
                AppNameClass = "Chrome";
                break;

            case CNN:
                AppNameClass = "CNN";
                break;

            case NewDcard:
            case Dcard:
                AppNameClass = "Dcard";
                break;

            case Line:
                AppNameClass = "Line";
                break;

            case MoPTT:
                AppNameClass = "MoPTT";
                break;

            case Facebook:
                AppNameClass = "Facebook";
                break;

            case JPTT:
                AppNameClass = "JPTT";
                break;

            case Instagram:
                AppNameClass = "Instagram";
                break;

            case Opera:
                AppNameClass = "Opera";
                break;

            case EBCnews:
                AppNameClass = "EBCnews";
                break;

            case ETToday:
                AppNameClass = "ETToday";
                break;

            case Applenews:
                AppNameClass = "Applenews";
                break;

            case AppleMedia:
                AppNameClass = "AppleMedia";
                break;

            case AppleMediaMagazine:
                AppNameClass = "AppleMediaMagazine";
                break;

            case Commonwealth:
                AppNameClass = "Commonwealth";
                break;

            case TVBS:
                AppNameClass = "TVBS";
                break;

            case PTTplus:
                AppNameClass = "PTTplus";
                break;

            case Cnyes:
                AppNameClass = "Cnyes";
                break;

            case PiTT:
                AppNameClass = "PiTT";
                break;

            default:
                AppNameClass = PackageName;
        }
        return AppNameClass;
    }
}
