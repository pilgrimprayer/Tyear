package com.example.hxx.tyear.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.statisticsFragmentModel.TableNameItem;

import static com.example.hxx.tyear.R.id.tvDot;
import static com.example.hxx.tyear.R.id.tvTopLine;


/**
 * Created by hbh on 2017/4/20.
 * 子布局ViewHolder
 */

public class ChildViewHolder extends BaseViewHolder {

    private Context mContext;
    private View view;
    private TextView childLeftText;
    private TextView childRightText;
    TextView date;
    TextView qTitle;

    TextView qContent;

    TextView topLine;

    TextView dov;
    TextView textSum;
    ImageView imageView;
    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final TableNameItem dataBean, final int pos){
        date = (TextView) view.findViewById(R.id.date);//加载每个子项里的内容-题目！！
        qTitle = (TextView) view.findViewById(R.id.q_title);
        qContent = (TextView) view.findViewById(R.id.q_content);
        topLine = (TextView) view.findViewById(tvTopLine);
        dov = (TextView) view.findViewById(tvDot);
        if(dataBean.getChildType()==1){
        //  date.setTextColor(0xfFF5252);
            date.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

            dov.setBackgroundResource(R.drawable.timelline_dot_first);
          topLine.setVisibility(View.INVISIBLE);
            date.setText(dataBean.getDate());
            qTitle.setText(dataBean.getChildTitle());
            qContent.setText(dataBean.getContents());
        }
        else{
            date.setVisibility(View.INVISIBLE);
            qTitle.setText(dataBean.getChildTitle());
            qContent.setText(dataBean.getContents());
        }
//        textSum = (TextView) view.findViewById(R.id.table_sum_c);//加载每个子项里的内容-题目！！
//        imageView=(ImageView) view.findViewById(R.id.table_open_c);//加载每个子项里的内容-内容！！
       // textName.setText(dataBean.getTableGroupItemList().get(0).getQuestion());//todo!!nb
//        holder.textName.setText(labelList.get(position).getName());
//        holder.textSum.setText(labelList.get(position).getSum()+"");
//        childLeftText.setText(dataBean.getChildLeftTxt());
//        childRightText.setText(dataBean.getChildRightTxt());

    }
}
