package com.example.hxx.tyear.diaryFragmentModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.QGridyAdapter;

import java.util.ArrayList;

/**
 * Created by hxx on 2017/10/5.
 */
//问题列表布局-不需要 已在calanderfragment一起编写
public class CardListFragment extends Fragment {
    private ArrayList<RecycleViewItemData> dataList;
    RecyclerView mDiaryCardList;
    QGridyAdapter mAdapter;
    //数据初始化
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_list,container,false);

/*         mDiaryCardList=(RecyclerView)view.findViewById(R.id.diary_card_list);
        mDiaryCardList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));*/

        return view;
    }



    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*
        *//**
         * 数据源加载
         *//*


        *//**
         * 适配器加载
         *//*
        mAdapter = new QGridyAdapter(dataList);
        //长按事件
        mAdapter.setOnItemClickListener(new QGridyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click " + dataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnItemLongClickListener(new QGridyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"long click "+dataList.get(position),Toast.LENGTH_SHORT).show();
                showPopMenu(view,position);

            }
        });
        //动画效果
        mDiaryCardList.setItemAnimator(new DefaultItemAnimator());
        mDiaryCardList.setAdapter(mAdapter);*/
    }

    @Override
    public void onStart() {
        super.onStart();

    }


        /**
         * 弹窗 “编辑问题”与“删除问题”
         * @param view
         * @param pos
         */
    public void showPopMenu(View view,final int pos){
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        popupMenu.getMenuInflater().inflate(R.menu.popme,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.removeItem:
                        Toast.makeText(getActivity(),"remove",Toast.LENGTH_SHORT).show();
                        mAdapter.removeItem(pos);
                        break;
                    case R.id.editItem:
                        Toast.makeText(getActivity(),"edit",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }

                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getActivity(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }
}