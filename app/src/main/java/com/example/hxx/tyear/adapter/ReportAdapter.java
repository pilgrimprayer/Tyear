package com.example.hxx.tyear.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.statisticsFragmentModel.ReportIQuestiontem;

import java.util.List;

/**
 * Created by hxx on 2017/10/8.
 */
//todo//position位置混乱
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>  {

    private List<ReportIQuestiontem> mList;  //用户列表


    public ReportAdapter(List<ReportIQuestiontem> list){

        this.mList = list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_report_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }
    //内部类ViewHolder 传入构造参数View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textTitle;
        public final TextView textContent;
        public final ImageView imageView;
        public final View mView;
        public ReportIQuestiontem mItem;
        public  ViewHolder(View view) {
            super(view);
            mView = view;
            textTitle = (TextView) view.findViewById(R.id.report_title);//加载每个子项里的内容-题目！！
            textContent = (TextView) view.findViewById(R.id.report_content);//加载每个子项里的内容-题目！！
           imageView=(ImageView) view.findViewById(R.id.reportImage);//加载每个子项里的内容-内容！！

        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //对子项目赋值 子项滚动屏幕时执行
        holder.mItem = mList.get(position);
 /*       holder.textTitle.setText(mList.get(position).getName());
        holder.textContent.setText(mList.get(position).getSum()+"");*/
        holder.imageView.setImageResource(mList.get(position).getimageID());
        holder.textTitle.setText(mList.get(position).getName());
        if(position==0){
            holder.textContent.setText("百年孤独，Java并发编程实战");
        }
        if(position==3){
            holder.textContent.setText("情绪忽高忽低，和兴趣主义投机时，则热情奔放，消极时则垂头丧气,好高骛远,不肯埋头苦干");
        }

        //ReportIQuestiontem grid = mList.get(position);//返回指定数组元素
/*        holder.textView.setText(grid.getSubject());
        holder.contentView.setText(grid.getContent());*/
        //holder.textView.setText(titles[position]); //对textview控件设置内容 ：数组title
        //holder.contentView.setText("sd");

        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
