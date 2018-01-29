package com.example.hxx.tyear.statisticsFragmentModel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.TableAdapter;
import com.example.hxx.tyear.model.bean.BaseQue;
import com.example.hxx.tyear.model.bean.Content;
import com.example.hxx.tyear.model.bean.Diary;
import com.example.hxx.tyear.model.dao.DiaryDao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxx on 2017/10/7.
 */

public class TablesumFragment extends Fragment {
    private ArrayList<TableNameItem> labelList;
 private ArrayList<TableGroupItem> labelGroupList;
    ArrayList<BaseQue> groupQue=new ArrayList<BaseQue>();
    RecyclerView mTableList;
    TableAdapter mAdapter;
    List<Integer> tableSum = new ArrayList<>();
   Map<String,Integer> lableSumMap ;
 //   Map<String, List<Integer>> lableSumMap = new HashMap<String, List<Integer>>();
        //数据标签
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
        lableSumMap = new LinkedHashMap<String,Integer>();
        DiaryDao mDiaryDao=new DiaryDao(getActivity());
        List<Diary> diaryList = mDiaryDao.queryAll();
        //0健康-1学习2心情3反思
        labelGroupList=new ArrayList<TableGroupItem>();
        //统计

        for(Diary diary:diaryList){
            List<BaseQue> diaryBaseQues = diary.getBaseQues();
            for (BaseQue baseQue:diaryBaseQues){
                if (baseQue.getType()==0){//文本类型
                    if(baseQue.getContent().size()!=0) {//若答案存在
                        Content content = baseQue.getContent().get(0);

                        if (baseQue.getContent().size() != 0 && content.getName() != null && !content.getName().equals("")) {//若已回答
                            String LabelName = baseQue.getLabel().get(0).getName();//todo//多标签
                            TableGroupItem tableGroupItem = new TableGroupItem(LabelName, baseQue.getTitle(), diary.getDate());
                            tableGroupItem.setContens(content.getName());
                            labelGroupList.add(tableGroupItem);//问题添加
                            //标签计数
                            if (lableSumMap.get(LabelName) == null) {//若是第一次则初始值为零

                                lableSumMap.put(LabelName, 0);
                                lableSumMap.put(LabelName, lableSumMap.get(LabelName) + 1);//计数+1
                            } else {
                                lableSumMap.put(LabelName, lableSumMap.get(LabelName) + 1);//计数+1
                            }

                        }

                    }
                }
                if (baseQue.getType()==1){//非文本类型只要有一个已经选上则计数
                   List<Content> contentList=baseQue.getContent();

                    for(Content content:contentList) {

                        if (content.isChecked()) {//若已勾选
                            String LabelName = baseQue.getLabel().get(0).getName();//todo//多标签
                            TableGroupItem tableGroupItem = new TableGroupItem(LabelName,baseQue.getTitle(),diary.getDate());
                            tableGroupItem.setContens(content.getName());
                            labelGroupList.add(tableGroupItem);//问题添加
                            if (lableSumMap.get(LabelName) == null) {//若是第一次则初始值为零

                                lableSumMap.put(LabelName, 0);
                                lableSumMap.put(LabelName, lableSumMap.get(LabelName) + 1);//计数+1
                            }
                            else{
                                lableSumMap.put(LabelName, lableSumMap.get(LabelName)+1);//计数+1
                            }
                           // noSelected=false;
                           break;//跳出！！

                        }
                        
                    }
                }//end 单选
                if (baseQue.getType()==2){//非文本类型只要有一个已经选上则计数
                    List<Content> contentList=baseQue.getContent();
                    TableGroupItem tableGroupItem=null;
                    boolean noSelected=true;
                    for(Content content:contentList) {

                        if (content.isChecked()) {//若已勾选则加入问题的回答（可能多个）并只计数一次
                            String LabelName = baseQue.getLabel().get(0).getName();//todo//多标签
                           if(tableGroupItem==null)
                            tableGroupItem = new TableGroupItem(LabelName,baseQue.getTitle(),diary.getDate());


                            if(noSelected) {//若还未计数则计数
                                // 1加入回答
                                tableGroupItem.setContens(content.getName());
                                //2计数
                                if (lableSumMap.get(LabelName) == null) {//若是第一次则初始值为零

                                    lableSumMap.put(LabelName, 0);
                                    lableSumMap.put(LabelName, lableSumMap.get(LabelName) + 1);//计数+1
                                } else {
                                    lableSumMap.put(LabelName, lableSumMap.get(LabelName) + 1);//计数+1
                                }
                                noSelected = false;
                            }//若已计数则不计数只加入问题
                            else{
                                // 1加入回答
                                tableGroupItem.setContens(content.getName());
                            }

                        }
                 }
                 if(tableGroupItem!=null)//若不为空
                    labelGroupList.add(tableGroupItem);//问题添加
                }//end 多选

            }
        }

        labelList=new ArrayList<TableNameItem>();
//
//        for (Map.Entry<String, Integer> entry : lableSumMap.entrySet()){
//            TableNameItem tableNameItem=new TableNameItem(entry.getKey(),entry.getValue());
//
//            labelList.add(tableNameItem);
//        }
        //父布局与子布局绑定
        int i=1;
        for (Map.Entry<String, Integer> entry : lableSumMap.entrySet()){
            TableNameItem tableNameItem=null;
            for(TableGroupItem tableGroupItem:labelGroupList){//遍历子布局 若标签名字==标签名字
                if(tableNameItem==null)
                { tableNameItem=new TableNameItem(entry.getKey(),entry.getValue());}
                if(tableGroupItem.getLabel().equals(entry.getKey())){//做问题标签名字等于计算标签名字
                    tableNameItem.addTableGroupItemList(tableGroupItem);//计算标签（主布局）绑定问题（子布局）
                }

            }
            tableNameItem.setID(i+"");//
            tableNameItem.setType(0);//
            tableNameItem.setChildBean(tableNameItem);//??父子相连 为了 展开后再点击收起来
            labelList.add(tableNameItem);
            i++;
        }

        /**
         * 适配器加载
         */
        mAdapter = new TableAdapter(getActivity(),labelList,labelGroupList);
        //滚动监听
        mTableList.setAdapter(mAdapter);
        mAdapter.setOnScrollListener(new TableAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mTableList.scrollToPosition(pos);
            }
        });
        //滚动监听
//        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
//            @Override
//            public void scrollTo(int pos) {
//                mRecyclerView.scrollToPosition(pos);
//            }
//        });



        //动画效果/*
/*        mTableList.setItemAnimator(new DefaultItemAnimator());*/

        
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

