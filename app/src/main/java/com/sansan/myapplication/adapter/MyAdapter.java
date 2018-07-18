package com.sansan.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sansan.myapplication.R;
import com.sansan.myapplication.ui.activity.FlowLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/24 0024.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    Context context;
    List<String> data=new ArrayList<>();
    private int type;

    public interface OnItemClickLitener
    {
        void onItemClick(int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MyAdapter(Context context,List<String> data){
        this.context=context;
        this.data=data;
    }
    public void setData(List<String> data,int type){
        this.data=data;
        this.type=type;
        notifyDataSetChanged();
    }

    public void addData(List<String> dataList) {
        if (null != dataList) {
            this.data.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void setData(List<String> dataList) {
        if (null != dataList) {
            this.data.clear();
            this.data.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyAdapter.MyViewHolder holder = new MyAdapter.MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_home, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position)
    {
        holder.tv.setText(data.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(position);
//                if (type==0){
////                    Toast.makeText(context,"111",Toast.LENGTH_SHORT).show();
//                    mOnItemClickLitener.onItemClick(0);
//                }else{
////                    Toast.makeText(context,"222",Toast.LENGTH_SHORT).show();
//                    mOnItemClickLitener.onItemClick(1);
//                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }


}
