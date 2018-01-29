package com.example.hxx.tyear.MineFragmentModel;

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
import com.example.hxx.tyear.adapter.ReportAdapter;
import com.example.hxx.tyear.statisticsFragmentModel.ReportIQuestiontem;

import java.util.ArrayList;

/**
 * Created by hxx on 2017/10/7.
 */

public class ReportFragment extends Fragment {

    private ArrayList<ReportIQuestiontem> dataList;
    RecyclerView mTableList;
    ReportAdapter mAdapter;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    public ReportFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReportFragment newInstance(int columnCount) {
        ReportFragment fragment = new ReportFragment();
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
        View view = inflater.inflate(R.layout.diary_list_report, container, false);



        mTableList=(RecyclerView)view.findViewById(R.id.diary_card_list_re);
        mTableList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        /**
         * 数据源加载
         */
        dataList=new ArrayList<ReportIQuestiontem>();
        dataList.add(new ReportIQuestiontem(("想做的事情有去努力吗？"),R.drawable.ic_people_black_24dp));
        dataList.add(new ReportIQuestiontem(("这段时间过的开不开心？"),R.drawable.ic_business_center_black_24dp));
        dataList.add(new ReportIQuestiontem(("下一个阶段有什么目标吗?"),R.drawable.ic_school_black_24dp));
        dataList.add(new ReportIQuestiontem(("我的局限在哪里？"),R.drawable.ic_013_productivity));
        dataList.add(new ReportIQuestiontem(("怎样才能让自己变得更好？"),R.drawable.ic_043_medal));
        /**
         * 适配器加载
         */
        mAdapter = new ReportAdapter(dataList);
        mAdapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}

