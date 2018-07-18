package com.sansan.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sansan.myapplication.MainActivity;
import com.sansan.myapplication.R;
import com.sansan.myapplication.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main1Activity extends AppCompatActivity implements MyAdapter.OnItemClickLitener {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<String> list=new ArrayList<>();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initView();
    }

    private void initView() {
        for (int i=0;i<10;i++){
            list.add("aa"+i);
        }
        //通过findViewById拿到RecyclerView实例
        mRecyclerView =  (RecyclerView) findViewById(R.id.recycleView);
//设置RecyclerView管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//初始化适配器
        mAdapter = new MyAdapter(this,list);
//设置添加或删除item时的动画，这里使用默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//设置适配器
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(this);

    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                   intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case 1:
                 intent=new Intent(this,NetworkActivity.class);
                startActivity(intent);
                break;
            case 2:
                 intent=new Intent(this,FlowLayoutActivity.class);
                startActivity(intent);
                break;
            case 3:
                 intent=new Intent(this,ExpandActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
