package com.sansan.myapplication.util;

import android.content.Context;
import android.widget.Toast;

public class MToast {
	private static String mLastMsg;
	protected static Toast mToast = null;
	private static long mLastTime = 0;
	private static long mCurTime = 0;

	public static void makeText(Context context, String s,int time) {
		if (mToast == null) {
			mToast = Toast.makeText(context, s, time);
			mToast.show();
			mLastTime = System.currentTimeMillis();
		} else {
			mCurTime = System.currentTimeMillis();
			if (s.equals(mLastMsg)) {
				if (mCurTime - mLastTime > time) {
					mToast.show();
				}
			} else {
				mLastMsg = s;
				mToast.setText(s);
				mToast.show();
			}
		}
		mLastTime = mCurTime;
	}
	
	public static void makeText(Context context, int resId,int time) {
		if (mToast == null) {
			mToast = Toast.makeText(context, resId, time);
			mToast.show();
			mLastTime = System.currentTimeMillis();
		} else {
			mCurTime = System.currentTimeMillis();
			if (context.getString(resId).equals(mLastMsg)) {
				if (mCurTime - mLastTime > time) {
					mToast.show();
				}
			} else {
				mLastMsg = context.getString(resId);
				mToast.setText(mLastMsg);
				mToast.show();
			}
		}
		mLastTime = mCurTime;
	}
}
