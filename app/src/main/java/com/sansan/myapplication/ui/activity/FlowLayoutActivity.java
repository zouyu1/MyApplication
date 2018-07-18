package com.sansan.myapplication.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sansan.myapplication.R;
import com.sansan.myapplication.adapter.MyAdapter;
import com.sansan.myapplication.bean.SelectObject;
import com.sansan.myapplication.popwindow.CustomPopWindow;
import com.sansan.myapplication.popwindow.MyPopupWindow;
import com.sansan.myapplication.widget.CircularStatisticsView;
import com.sansan.myapplication.widget.RingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.name;

public class FlowLayoutActivity extends ActionBarActivity implements View.OnClickListener, MyAdapter.OnItemClickLitener {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
@BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    private MyAdapter mAdapter1;
    private CircularStatisticsView circularStatisticsView;
    private SelectObject selObj;
    private String name="aa";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        ButterKnife.bind(this);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mAdapter = new HomeAdapter(this,mDatas));
        mRecyclerView.setAdapter(mAdapter1 = new MyAdapter(this,mDatas));
        mAdapter1.setOnItemClickLitener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        initRingView();

        circularStatisticsView = (CircularStatisticsView) findViewById(R.id.circularStatisticsView);
        circularStatisticsView.setOnClickListener(this);
        circularStatisticsView.setReminderText("剩余");
        circularStatisticsView.setProgressText("已使用");
        circularStatisticsView.setProgress(15638,2546);
        circularStatisticsView.setReminderColor(Color.parseColor("#FF0000FF"));
        circularStatisticsView.setProgressColor(Color.parseColor("#FFFF0000"));
        circularStatisticsView.setCircleWidth(50);
    }

    private void initRingView() {
        RingView ringView = (RingView) findViewById(R.id.ringView);

        // 添加的是颜色
        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.color_ff3e60);
        colorList.add(R.color.color_ffa200);
        colorList.add(R.color.color_31cc64);
        colorList.add(R.color.color_3377ff);

        //  添加的是百分比
        List<Float> rateList = new ArrayList<>();
        rateList.add(20f);
        rateList.add(35f);
        rateList.add(25f);
        rateList.add(20f);
        ringView.setShow(colorList, rateList,true,true);
    }
//@OnClick(R.id.button1)
//public void onclick(View view){
//
//}


    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
        selObj=new SelectObject();
        selObj.setGlobal("global11");
        Log.d("zou","global befor="+selObj.getGlobal());
        changeGlabal(selObj);
        Log.d("zou","global after="+selObj.getGlobal());
    }

    private void changeGlabal(SelectObject sel) {
        sel.setGlobal("aaabbb");
        Log.d("zou","global ining ="+sel.getGlobal());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.button1:
        mDatas.clear();
        for (int i=0;i<6;i++) {
            mDatas.add("1111");
        }
        mAdapter1.setData(mDatas,0);
//        mAdapter.notifyDataSetChanged();
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
 // 获取锚点View在屏幕上的左上角坐标位置
        mButton2.getLocationOnScreen(anchorLoc);

        MyPopupWindow myPopupWindow = new MyPopupWindow(this);
        int width=mButton2.getWidth();
        int height=mButton2.getHeight();
        int width1=mButton2.getMeasuredWidth();
        int height1=mButton2.getMeasuredHeight();
        myPopupWindow.showAsDropDown(mButton2,width1-200,10);
//        myPopupWindow.showAtLocation(mButton2, Gravity.NO_GRAVITY,anchorLoc[0]+mButton2.getMeasuredWidth()-200,anchorLoc[1]+mButton2.getMeasuredHeight());
        break;
    case R.id.button2:
        mDatas.clear();
        for (int i=0;i<6;i++) {
            mDatas.add("2222");
        }
        mAdapter1.setData(mDatas,1);
//        mAdapter.notifyDataSetChanged();
        showPop();
        break;
    case R.id.button3:
        name=name+"aa";
        mButton2.setText(name);
        break;
}
    }

    private void showPop() {
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_layout1)//显示的布局，还可以通过设置一个View
                //     .size(600,400) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create()//创建PopupWindow
                .showAsDropDown(mButton2,0,10);//显示PopupWindow
    }

    @Override
    public void onItemClick(int position) {
        if (position==0){
            Toast.makeText(this,"click 111",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"click  222",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {
        Context context;
        List<String> data=new ArrayList<>();
        private int type;
    public HomeAdapter(Context context,List<String> data){
        this.context=context;
    this.data=data;
    }
        public void setData(List<String> data,int type){
            this.data=data;
            this.type=type;
            notifyDataSetChanged();
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    FlowLayoutActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(data.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type==0){
                        Toast.makeText(context,"111",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"222",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        class MyViewHolder extends ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }


    }

}
