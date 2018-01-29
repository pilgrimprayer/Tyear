package com.example.hxx.tyear.MineFragmentModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.MineAdapter;

import java.util.ArrayList;

/**
 * Created by hxx on 2017/10/6.
 */

public class ComMineFragment extends Fragment {

    private ArrayList<MineItem> dataList;
    RecyclerView mTableList;
    MineAdapter mAdapter;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.com_mine,container,false);



        mTableList=(RecyclerView)view.findViewById(R.id.mine_list);
        mTableList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        /**
         * 数据源加载
         */
        dataList=new ArrayList<MineItem>();
        dataList.add(new MineItem(("提醒"),R.drawable.ic_timer_black_24dp));
        dataList.add(new MineItem(("密码锁"),R.drawable.ic_lock_black_24dp));
        dataList.add(new MineItem(("导出"),R.drawable.ic_folder_special_black_24dp));
        dataList.add(new MineItem(("设置"),R.drawable.ic_settings_black_24dp));
        /**
         * 适配器加载
         */
        mAdapter = new MineAdapter(dataList);
        mAdapter.setOnItemClickListener(new MineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click " + dataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //动画效果/*
/*        mTableList.setItemAnimator(new DefaultItemAnimator());*/
        mTableList.setAdapter(mAdapter);

        return view;
    }
}
