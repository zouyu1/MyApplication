package com.sansan.myapplication.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.DisplayMetrics;

public class DateUtil {
	private static final String TAG = DateUtil.class.getSimpleName();
	public static void setDate(Context context, int year, int month, int day,
			int hourOfDay, int minute) {
		LogUtils.d(TAG, "setDate1111 AlarmManager");
		Calendar c = Calendar.getInstance();

		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long when = c.getTimeInMillis();

		if (when / 1000 < Integer.MAX_VALUE) {
			((AlarmManager) context.getSystemService(Context.ALARM_SERVICE))
					.setTime(when);
			SystemClock.setCurrentTimeMillis(when);
		}
	}
	
	public static void timeUpdated(Context context) {
		Intent timeChanged = new Intent(Intent.ACTION_TIME_CHANGED);
		context.sendBroadcast(timeChanged);
	}
}
