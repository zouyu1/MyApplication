package com.sansan.myapplication.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sansan.myapplication.R;
import com.sansan.myapplication.adapter.MyAdapter;
import com.sansan.myapplication.adapter.MyAdapter1;

import java.util.ArrayList;
import java.util.List;

public class ExpandActivity extends AppCompatActivity {
    TextView descriptionView;
    View layoutView ,expandView; //LinearLayout布局和ImageView
    int maxDescripLine = 3; //TextView默认最大展示行数

    // 定义
    private MyAdapter mAdapter;

    // 存储数据
    private List<String> dataList = new ArrayList<>();
    private XRecyclerView mRecyclerView;

    // 模拟接口返回的10条数据
    private List<String> testList() {
        List<String> mytest = new ArrayList<>();
        for(int i = 0 ; i < 14; i++) {
            mytest.add(" test:" + i);
        }
        return mytest;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
        initView();
        initData1();
    }

    // 模拟接口返回的10条数据
    private List<String> testList1() {
        List<String> mytest = new ArrayList<>();
        for(int i = 0 ; i < 2; i++) {
            mytest.add("loadmore:" + System.currentTimeMillis());
        }
        return mytest;
    }

    // 模拟接口返回的10条数据
    private List<String> testList2() {
        List<String> mytest = new ArrayList<>();
        for(int i = 0 ; i < 13; i++) {
            mytest.add("reflesh test" + System.currentTimeMillis());
        }
        return mytest;
    }

    private void initData1() {
        //设置文本
        descriptionView.setText(getText(R.string.content));

        //descriptionView设置默认显示高度
        descriptionView.setHeight(descriptionView.getLineHeight() * maxDescripLine);
        //根据高度来判断是否需要再点击展开
        descriptionView.post(new Runnable() {

            @Override
            public void run() {
                expandView.setVisibility(descriptionView.getLineCount() > maxDescripLine ? View.VISIBLE : View.GONE);
            }
        });

        layoutView.setOnClickListener(new View.OnClickListener() {
            boolean isExpand;//是否已展开的状态

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                descriptionView.clearAnimation();//清楚动画效果
                final int deltaValue;//默认高度，即前边由maxLine确定的高度
                final int startValue = descriptionView.getHeight();//起始高度
                int durationMillis = 350;//动画持续时间
                if (isExpand) {
                    /**
                     * 折叠动画
                     * 从实际高度缩回起始高度
                     */
                    deltaValue = descriptionView.getLineHeight() * descriptionView.getLineCount() - startValue;
                    RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                } else {
                    /**
                     * 展开动画
                     * 从起始高度增长至实际高度
                     */
                    deltaValue = descriptionView.getLineHeight() * maxDescripLine - startValue;
                    RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(durationMillis);
                    animation.setFillAfter(true);
                    expandView.startAnimation(animation);
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) { //根据ImageView旋转动画的百分比来显示textview高度，达到动画效果
                        descriptionView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }
                };
                animation.setDuration(durationMillis);
                descriptionView.startAnimation(animation);
            }
        });

    }

    private void initView() {
        layoutView = findViewById(R.id.description_layout);
        descriptionView = (TextView)findViewById(R.id.description_view);
        expandView = findViewById(R.id.expand_view);

        // 定义
        mRecyclerView = (XRecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new MyAdapter(this, dataList);

        // XRecyclerView的使用，和RecyclerView几乎一致
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        // 可以设置是否开启加载更多/下拉刷新
        mRecyclerView.setLoadingMoreEnabled(true);
        // 可以设置加载更多的样式，很多种
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        // 如果设置上这个，下拉刷新的时候会显示上次刷新的时间
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);  // make refresh time visible,false means hiding

        // 添加数据
        mAdapter.addData(testList());

        // 添加刷新和加载更多的监听
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                mAdapter.setData(testList2());
                initData();
                // 为了看效果，加了一个等待效果，正式的时候直接写mRecyclerView.refreshComplete();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
//                mAdapter.addData(testList1());
                addData();
                // 为了看效果，加了一个等待效果，正式的时候直接写mRecyclerView.loadMoreComplete();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.loadMoreComplete();
                    }
                }, 2000);
            }
        });

    }

    /**
     *上拉加载添加数据
     */
    private void addData() {
        for (int i=0;i<10;i++){
            Integer r= Integer.valueOf((int) (Math.random()*100));
            dataList.add(r+"");
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     *初始化数据
     */
    private void initData() {
        dataList.clear();
        for (int i=0;i<14;i++){
            Integer r= Integer.valueOf(i);
            dataList.add(r+"");
        }
        mAdapter.notifyDataSetChanged();
    }

}
