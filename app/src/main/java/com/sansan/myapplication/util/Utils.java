package com.sansan.myapplication.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.RemoteException;
import android.util.DisplayMetrics;

public class Utils {
	   // 两次点击按钮之间的点击间隔不能少于1000毫秒
 private static final int MIN_CLICK_DELAY_TIME = 300;
 private static long lastClickTime;

 public static boolean isFastClick() {
     boolean flag = false;
     long curClickTime = System.currentTimeMillis();
     if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
         flag = true;
     }
     lastClickTime = curClickTime;
     return flag;
 }
	public static void updateLanguage(Locale locale) {
		try {

			Object objIActMag, objActMagNative;
			Class clzIActMag = Class.forName("android.app.IActivityManager");
			Class clzActMagNative = Class
					.forName("android.app.ActivityManagerNative");
			Method mtdActMagNative$getDefault = clzActMagNative
					.getDeclaredMethod("getDefault");
			// IActivityManager iActMag = ActivityManagerNative.getDefault();
			objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);
			// Configuration config = iActMag.getConfiguration();
			Method mtdIActMag$getConfiguration = clzIActMag
					.getDeclaredMethod("getConfiguration");
			Configuration config = (Configuration) mtdIActMag$getConfiguration
					.invoke(objIActMag);
			config.locale = locale;
			// iActMag.updateConfiguration(config);
			// 此处需要声明权限:android.permission.CHANGE_CONFIGURATION
			// 会重新调用 onCreate();
			Class[] clzParams = { Configuration.class };
			Method mtdIActMag$updateConfiguration = clzIActMag
					.getDeclaredMethod("updateConfiguration", clzParams);
			mtdIActMag$updateConfiguration.invoke(objIActMag, config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String date = formatter.format(curDate);
		return date;

	}

	public void switchLanguage(Context ctx, Locale locale) {
		Resources resources = ctx.getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
		config.locale = locale; // 简体中文
		resources.updateConfiguration(config, dm);
	}
}
