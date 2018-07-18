package com.sansan.myapplication.popwindow;

/**
 * Created by Administrator on 2018/7/2 0002.
 */


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.sansan.myapplication.R;


/**
 * 学习PopupWindow
 * Created by 刘楠 on 2016/8/1 0001.17:42
 */
public class MyPopupWindow extends PopupWindow {

    Context mContext;
    private LayoutInflater mInflater;
    private View mContentView;


    public MyPopupWindow(Context context) {
        super(context);

        this.mContext = context;
        //打气筒
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //打气

        mContentView = mInflater.inflate(R.layout.layout_dialog, null);

        //设置View
        setContentView(mContentView);


        //设置宽与高
        setWidth(200);

        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);


        /**
         * 设置进出动画
         */
//        setAnimationStyle(R.style.MyPopupWindow);


        /**
         * 设置背景只有设置了这个才可以点击外边和BACK消失
         */
        setBackgroundDrawable(new ColorDrawable());


        /**
         * 设置可以获取集点
         */
        setFocusable(true);

        /**
         * 设置点击外边可以消失
         */
        setOutsideTouchable(true);

        /**
         *设置可以触摸
         */
        setTouchable(true);


        /**
         * 设置点击外部可以消失
         */

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                /**
                 * 判断是不是点击了外部
                 */
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    return true;
                }
                //不是点击外部
                return false;
            }
        });


        /**
         * 初始化View与监听器
         */
        initView();

        initListener();
    }


    private void initView() {

    }

    private void initListener() {

    }


}