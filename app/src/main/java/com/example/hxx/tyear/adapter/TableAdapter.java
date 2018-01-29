package com.example.hxx.tyear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.statisticsFragmentModel.TableGroupItem;
import com.example.hxx.tyear.statisticsFragmentModel.TableNameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxx on 2017/10/8.
 */

public class TableAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private List<TableNameItem> labelList;  //用户列表
    private ArrayList<TableGroupItem> labelGroupList;
    private Context context;
    private OnScrollListener mOnScrollListener;
    public TableAdapter(Context context,List<TableNameItem> list,ArrayList<TableGroupItem> labelGroupList){
        this.context = context;
        this.labelList = list;
        this.labelGroupList=labelGroupList;
    }



    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_table_item_parent, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
        View view = null;
        switch (viewType){//传入类型 判断
            case TableNameItem.PARENT_ITEM://父项目
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_table_item_parent, parent, false);
                return new ParentViewHolder(context, view);
            case TableNameItem.CHILD_ITEM:///子项目
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_table_item_child, parent, false);
                return new ChildViewHolder(context, view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_table_item_parent, parent, false);
                return new ParentViewHolder(context, view);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TableNameItem.PARENT_ITEM:
                ParentViewHolder parentViewHolder = (ParentViewHolder) holder;
                parentViewHolder.bindView(labelList.get(position), position, itemClickListener);
                break;
            case TableNameItem.CHILD_ITEM:
                ChildViewHolder childViewHolder = (ChildViewHolder) holder;
                childViewHolder.bindView(labelList.get(position), position);
                break;
        }
    }



    @Override
    public int getItemViewType(int position) {
        return labelList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }



    private ItemClickListener itemClickListener = new ItemClickListener() {//点击时
        @Override
        public void onExpandChildren(TableNameItem bean) {
            int position = getCurrentPosition(bean.getID());//确定当前点击的item位置
            int sum=bean.getTableGroupItemList().size();//最后一个bean位置
            int starttFromend=sum-1;
            boolean isNewDay=true;
            for(;starttFromend>=0;starttFromend--){//依次增加
                //遍历group得从后往前遍历 因为add是往前加
                TableGroupItem tableGroupItem = bean.getTableGroupItemList().get(starttFromend);
                TableNameItem children = getChildTableNameItem(bean);//获取要展示的子布局数据对象，注意区分onHideChildren方法中的getChildBean()。

                String date = new String(tableGroupItem.getDate());
                children.setChildTitle(tableGroupItem.getQuestion());//
                for(String conent:tableGroupItem.getContens()){
                    children.addContents(conent+"+");
                }
                String str=children.getContents();
                str=str.substring(0,str.length()-1);
                children.setContents( str);

                children.setDate(tableGroupItem.getDate());

              if(starttFromend==0||!tableGroupItem.getDate().equals(bean.getTableGroupItemList().get(starttFromend-1).getDate())){//若是最前一个或是比前一个日期不同

                    children.setChildType(1);
                }
                else{
                    children.setChildType(0);
                }
                if (children == null) {
                    return;
                }
                add(children, position + 1);//在当前的item下方插入：数据源数组增加 刷新list
            }
            if (position == labelList.size() - 2 && mOnScrollListener != null) { //如果点击的item为最后一个
                mOnScrollListener.scrollTo(position + 1);//向下滚动，使子布局能够完全展示
            }
        }

        @Override
        public void onHideChildren(TableNameItem bean) {
            int position = getCurrentPosition(bean.getID());//确定当前点击的item位置
            TableNameItem children = bean.getChildBean();//获取子布局对象
            if (children == null) {
                return;
            }
            int curPositon;
            curPositon=position;
            for(int i=0;i<bean.getTableGroupItemList().size();i++) {//加多个
                remove(curPositon + 1);//删除
              //  curPositon++;
            }
//            remove(position + 1);//删除
            if (mOnScrollListener != null) {
                mOnScrollListener.scrollTo(position);
            }
        }
    };

    /**
     * 在父布局下方插入一条数据
     * @param bean
     * @param position
     */
    public void add(TableNameItem bean, int position) {


            labelList.add(position, bean);

        notifyItemInserted(position);
    }

    /**
     *移除子布局数据
     * @param position
     */
    protected void remove(int position) {

        labelList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 确定当前点击的item位置并返回
     * @param uuid
     * @return
     */
    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < labelList.size(); i++) {
            if (uuid.equalsIgnoreCase(labelList.get(i).getID())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 封装子布局数据对象并返回
     * 注意，此处只是重新封装一个TableNameItem对象，为了标注Type为子布局数据，进而展开，展示数据
     * 要和onHideChildren方法里的getChildBean()区分开来
     * @param bean
     * @return
     */
    private TableNameItem getChildTableNameItem(TableNameItem bean){
        TableNameItem child = new TableNameItem(bean.getName(),bean.getSum());
        child.setType(1);

        return child;
    }

    /**
     * 滚动监听接口
     */
    public interface OnScrollListener{
        void scrollTo(int pos);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener){
        this.mOnScrollListener = onScrollListener;
    }
}

    

