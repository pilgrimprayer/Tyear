package com.example.hxx.tyear.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.diaryFragmentModel.RadioItem;
import com.example.hxx.tyear.diaryFragmentModel.RecycleViewItemData;
import com.example.hxx.tyear.diaryFragmentModel.TextItem;

import java.util.ArrayList;

/**
 * Created by hxx on 2017/10/5.
 */

public class QGridyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TEXT = 0;//编辑框
    private static final int TYPE_RADIO = 1;//按钮
    private static final int TYPE_CHECK = 2;//下拉列表
    private ArrayList<RecycleViewItemData> dataList;//数据集合

    /**
     * 数据源加载
     * @param dataList
     */
    public QGridyAdapter(ArrayList<RecycleViewItemData> dataList) {
        this.dataList = dataList;
    }

    /**
     * 判断类型 获取相应item
     * @param parent
     * @param viewType
     * @return
     */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //如果viewType是编辑框类型,则创建TextViewHolder型viewholder
        if (viewType == TYPE_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_text, parent, false);
            TextViewHolder viewHolder = new TextViewHolder(view);
            return viewHolder;
        }
        //如果viewType是按钮类型,则创建RadioViewHolder型viewholder
        if (viewType == TYPE_RADIO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_radio, parent, false);
            RadioViewHolder viewHolder = new RadioViewHolder(view);
            return viewHolder;
        }
        //如果viewType是下拉列表类型,则创建CheckHolder型viewholder
        if (viewType == TYPE_CHECK) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_check, parent, false);
            CheckHolder viewHolder = new CheckHolder(view);
            return viewHolder;
        }
        return null;
    }

    /**
     * 根据position对holder操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //如果holder是TextViewHolder的实例
        if (holder instanceof TextViewHolder) {
            
            TextItem mTextItem = (TextItem) dataList.get(position).getT();//从datalist获取一个TextItem实例 因为动态加载的数据 将从这个实例获得
           
            //xxx.setText-动态改控件 ！！！因为同一个xml要装不同数据
            //position==xxxx-不同textItem的放置位置
            
/*            if (position == 0) {//??重新获取
           
                *//*
                ((TextViewHolder) holder).mTextTitle.setText("* Holder Name");
                ((TextViewHolder) holder).mTextContent.setText("* Card Number");*//*
            } else if (position == 3) {
                ((TextViewHolder) holder).mTextTitle.setText("          * Street ");
                ((TextViewHolder) holder).mTextContent.setText("           * City  ");

            } else if (position == 5) {
                ((TextViewHolder) holder).mTextTitle.setText("                   Or ");
                ((TextViewHolder) holder).mTextContent.setText("           * Zipcode  ");
            }
            ((TextViewHolder) holder).mEditText1.setText(mTextItem.getText1());//
            ((TextViewHolder) holder).mEditText2.setText(mTextItem.getText2());*/

        }
        //如果holder是RadioViewHolder的实例
        if (holder instanceof RadioViewHolder) {
            //从数据集合中取出该项
            RadioItem mRadioItem = (RadioItem) dataList.get(position).getT();
            //设置选中的按钮-根据数据Position动态选中 
          /*  switch (mRadioItem.getPosition()) {
                case 0:
                    ((RadioViewHolder) holder).mRadioButton.setChecked(true);
                    break;
                case 1:
                    ((RadioViewHolder) holder).mRadioButton2.setChecked(true);
                    break;
                case 2:
                    ((RadioViewHolder) holder).mRadioButton3.setChecked(true);
//                    ((TextViewHolder) holder).mEditText1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                    break;
            }*/
        }
        //如果holder是CheckHolder的实例
        if (holder instanceof CheckHolder) {
        }

        //长短按 监听事件
        //判断是否设置了监听器
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
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 根据列表位置，从数据源dataList（列表位置与datalist的position一一对应），获取判断类型 应用在onCreateViewHolder
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {//？？？

        if (0 == dataList.get(position).getDataType()) {
            return TYPE_TEXT;// 编辑框
        } else if (1 == dataList.get(position).getDataType()) {
            return TYPE_RADIO;// 按钮
        } else if (2 == dataList.get(position).getDataType()) {
            return TYPE_CHECK;//下拉列表
        } else {
            return 0;
        }
    }
//新建两个内部接口：
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    /**
     * 长按设置
     */
    //新建两个私有变量用于保存用户设置的监听器及其set方法：
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    /**
     * 删除
     */
    public void removeItem(int pos){
        dataList.remove(pos);
        notifyItemRemoved(pos);
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextTitle;
        public TextView mTextContent;

        public TextViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextContent = (TextView) itemView.findViewById(R.id.text_content);
        }
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder {
        public RadioButton mRadioButton;
        public RadioButton mRadioButton2;
        public RadioButton mRadioButton3;

        public RadioViewHolder(View itemView) {
            super(itemView);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.radioButton1);
            mRadioButton2 = (RadioButton) itemView.findViewById(R.id.radioButton2);
            mRadioButton3 = (RadioButton) itemView.findViewById(R.id.radioButton3);
        }
    }

    public class CheckHolder extends RecyclerView.ViewHolder {
        public CheckBox mCheckBox;
        public CheckBox mCheckBox2;
        public CheckBox mCheckBox3;
        public CheckHolder(View itemView) {
            
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox1);
            mCheckBox2 = (CheckBox) itemView.findViewById(R.id.checkBox2);
            mCheckBox3 = (CheckBox) itemView.findViewById(R.id.checkBox3);
        }
    }
}
