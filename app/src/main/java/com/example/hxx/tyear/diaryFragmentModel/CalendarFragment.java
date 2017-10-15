package com.example.hxx.tyear.diaryFragmentModel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.QGridyAdapter;
import com.example.hxx.tyear.model.bean.BaseRadioQue;
import com.example.hxx.tyear.model.bean.Diary;
import com.example.hxx.tyear.model.bean.Subject;
import com.example.hxx.tyear.model.dao.DiaryDao;
import com.example.hxx.tyear.model.dao.SubjectDao;
import com.example.hxx.tyear.support.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hxx on 2017/10/6.
 */

public class CalendarFragment extends Fragment {
    private ArrayList<RecycleViewItemData> dataList;
    RecyclerView mDiaryCardList;
    QGridyAdapter mAdapter;

    private Date mDate;

    private String mDiaryDate = null;
    private DiaryDao mDiaryDao;
    private Diary mDiary;

    private BaseRadioQue mBaseRadioQue;
    private ArrayList<BaseRadioQue> mBaseRadioQueList;


    private  MaterialCalendarView calendarView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment,container,false);

        calendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        calendarView.state().edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        Date mDate = new Date(System.currentTimeMillis());//获取当前时间
        calendarView.setSelectedDate(mDate);

        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       mDiaryCardList=(RecyclerView)getActivity().findViewById(R.id.diary_card_list);
        mDiaryCardList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



        /**
         * 数据源加载-默认为今天
         */
        mDate = new Date(System.currentTimeMillis());//今天
        mDiaryDate = DateUtils.formatDate(mDate);//转化为string类型

        // 判断是否为新日记,应用场景：客户在一天内重启程序多次
        mDiaryDao = new DiaryDao(getActivity());//日记操作对象-Diary
        mDiary = mDiaryDao.query(mDiaryDate);//根据时间来查询今天的日记
        if (mDiary == null) {//若未找到此条记录==当前是新的日记
            {
                mDiary = new Diary();//创建新日记
                mDiary.setDate(mDiaryDate);//添加日期字段信息
            }

            mGridList = mDiary.getGrids();//获取此条记录中的grids字段 ：grid类集合：gird 也是一张数据的表

            mBaseRadioQueList = mDiary.getBaseRadioQues();//创建问题表数据源。此时mBaseQueList无

        SubjectDao subjectDao = new SubjectDao(getActivity());
        List<Subject> subjects = subjectDao.queryAll();//查询表中所有的记录：问题//todo//问题还有类型 重新修改managerment


//TODO 如何加入默认数据源 如何把值存入各个表里 。不如一开始就把表里设为默认值
        dataList=new ArrayList<RecycleViewItemData>();
        dataList.add(new RecycleViewItemData(new TextItem( ),0));
        dataList.add(new RecycleViewItemData(new RadioItem(1),1));
        dataList.add(new RecycleViewItemData(new CheckItem( ),2));
        dataList.add(new RecycleViewItemData(new CheckItem( ),2));
        dataList.add(new RecycleViewItemData(new CheckItem( ),2));

        /**
         * 适配器加载
         */
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
        mDiaryCardList.setAdapter(mAdapter);
        /**
         * 日历选择监听
         */

        //start-日历点击 列表内容变化
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
           /*     Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra(KeyConfig.CALENDAR_DAY, date);
                startActivity(intent);

*/           dataList.add(new RecycleViewItemData(new TextItem( ),0));
                dataList.add(new RecycleViewItemData(new TextItem( ),0));
                dataList.add(new RecycleViewItemData(new TextItem( ),0));
                dataList.add(new RecycleViewItemData(new TextItem( ),0));
         /*       mDiaryCardList.scrollToPosition(1);*/
                mAdapter.notifyDataSetChanged();

            }
        });
    }

    //TODO
    /*  new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());//日历样式激活*/


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
