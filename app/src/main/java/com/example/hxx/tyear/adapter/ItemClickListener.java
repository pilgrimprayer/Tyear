package com.example.hxx.tyear.adapter;


import com.example.hxx.tyear.statisticsFragmentModel.TableNameItem;


public interface ItemClickListener {
    /**
     * 展开子Item
     * @param bean
     */
    void onExpandChildren(TableNameItem bean);

    /**
     * 隐藏子Item
     * @param bean
     */
    void onHideChildren(TableNameItem bean);
}
