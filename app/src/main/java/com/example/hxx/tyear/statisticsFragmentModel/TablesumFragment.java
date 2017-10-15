package com.example.hxx.tyear.statisticsFragmentModel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.TableAdapter;

import java.util.ArrayList;

/**
 * Created by hxx on 2017/10/7.
 */

public class TablesumFragment extends Fragment {
    private ArrayList<TableNameItem> dataList;
    RecyclerView mTableList;
    TableAdapter mAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    public TablesumFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TablesumFragment newInstance(int columnCount) {
        TablesumFragment fragment = new TablesumFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.sta_table_list, container, false);

        mTableList=(RecyclerView)view.findViewById(R.id.table_list);
        mTableList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        /**
         * 数据源加载
         */
        dataList=new ArrayList<TableNameItem>();
        dataList.add(new TableNameItem(("生活"),8));
        dataList.add(new TableNameItem(("工作"),3));
        dataList.add(new TableNameItem(("学习"),5));
        /**
         * 适配器加载
         */
        mAdapter = new TableAdapter(dataList);
        mAdapter.setOnItemClickListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click " + dataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //动画效果/*
/*        mTableList.setItemAnimator(new DefaultItemAnimator());*/
        mTableList.setAdapter(mAdapter);
        
        // Set the adapter
     /*   if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
        }*/
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}

