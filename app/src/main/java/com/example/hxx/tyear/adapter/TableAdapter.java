package com.example.hxx.tyear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.statisticsFragmentModel.TableNameItem;

import java.util.List;

/**
 * Created by hxx on 2017/10/8.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>  {

    private List<TableNameItem> mList;  //用户列表
    private Context mContext;

    public TableAdapter(List<TableNameItem> list){

        this.mList = list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_table_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }
    //内部类ViewHolder 传入构造参数View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textName;
        public final TextView textSum;
        public final ImageView imageView;
        public final View mView;
        public TableNameItem mItem;
        public  ViewHolder(View view) {
            super(view);
            mView = view;
            textName = (TextView) view.findViewById(R.id.table_name);//加载每个子项里的内容-题目！！
            textSum = (TextView) view.findViewById(R.id.table_sum);//加载每个子项里的内容-题目！！
           imageView=(ImageView) view.findViewById(R.id.table_open);//加载每个子项里的内容-内容！！

        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //对子项目赋值 子项滚动屏幕时执行
        holder.mItem = mList.get(position);
        holder.textName.setText(mList.get(position).getName());
        holder.textSum.setText(mList.get(position).getSum()+"");

        //TableNameItem grid = mList.get(position);//返回指定数组元素
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
