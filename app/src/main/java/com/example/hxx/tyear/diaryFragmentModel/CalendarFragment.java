package com.example.hxx.tyear.diaryFragmentModel;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.QGridyAdapter;
import com.example.hxx.tyear.model.BaseQueManager;
import com.example.hxx.tyear.model.bean.BaseQue;
import com.example.hxx.tyear.model.bean.Content;
import com.example.hxx.tyear.model.bean.Diary;
import com.example.hxx.tyear.model.dao.BaseQueDao;
import com.example.hxx.tyear.model.dao.ContentDao;
import com.example.hxx.tyear.model.dao.DiaryDao;
import com.example.hxx.tyear.support.DateUtils;
import com.example.hxx.tyear.support.KeyConfig;
import com.example.hxx.tyear.view.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by hxx on 2017/10/6.
 */

public class CalendarFragment extends Fragment {
    private ArrayList<RecycleViewItemData> dataList;
    RecyclerView mDiaryCardList;
    QGridyAdapter mAdapter;

    private Date mDate;
   private boolean isNewDay=false;
    private String mDiaryDate = null;
    private DiaryDao mDiaryDao;
    private Diary mDiary;
    private BaseQueDao baseQueDao;
    private BaseQue mBaseQue;
    private List<BaseQue> mBaseQueList;
    private  List<BaseQue> initBaseQueList;

    private  MaterialCalendarView calendarView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment,container,false);

        calendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)//第一天为星期一
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
         * 数据源加载-系统默认问题加入库:问题项表 baseque 回答content表
         */
        //可能从日历页面下获得 CalendarDay
        CalendarDay calendarDay = getActivity().getIntent().getParcelableExtra(KeyConfig.CALENDAR_DAY);


        BaseQueManager baseQueManager = new BaseQueManager(getActivity());
        baseQueManager.init();

        /**
         * 数据源加载-默认为今天
         */

 /*       if (calendarDay != null) {//若有==从日历除今天外日期跳转
            mDate = calendarDay.getDate();//通过calenderday获取mdate
        } else {//都没有==新的一篇日记==本界面！！！一个新的对象==表里一条新纪录
            mDate = new Date(System.currentTimeMillis());//获取当前时间

        }*/

        mDate = new Date(System.currentTimeMillis());//获取当前时间

        mDiaryDate = DateUtils.formatDate(mDate);//转化为string类型

        // 判断是否为新日记,应用场景：客户在一天内重启程序多次
        mDiaryDao = new DiaryDao(getActivity());//日记操作对象-Diary
        mDiary = mDiaryDao.query(mDiaryDate);//根据时间来查询今天的日记
        if (mDiary == null) //若未找到此条记录==当前是新的日记
            {
                mDiary = new Diary();//创建新日记
                mDiary.setDate(mDiaryDate);//添加日期字段信息
                isNewDay=true;
            }

            //todo//加载场景1 新日记 加载场景2 已有日记
            //sart----获取基本问题列表
        List<BaseQue> tBaseQueList= baseQueDao.queryAll();
      initBaseQueList= new ArrayList<>();
        for(int i=0;i<10;i++){
            initBaseQueList.add(tBaseQueList.get(i));
        }
        //todo//???重启一次 数据库都清零了
            mBaseQueList = mDiary.getBaseQues();//创建问题表数据源。此时mBaseQueList无
            //if mBaseQueList为空说明为新日记，则数据源为默认值
            if(mBaseQueList.size()==0){
               baseQueDao = new BaseQueDao(getActivity());
                mBaseQueList =  initBaseQueList;//查询表中所有的记录：问题
                //todo// 需要在新日记显示的记录 不等于问题表所有记录 。应等于 前一天的记录
            }
            //end----queList






        /**
         * 适配器加载
         */

                mAdapter = new QGridyAdapter(mBaseQueList);
       // mAdapter = new QGridyAdapter(dataList);
        //长按事件
        mAdapter.setOnItemClickListener(new QGridyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click " + mBaseQueList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnItemLongClickListener(new QGridyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"long click "+mBaseQueList.get(position),Toast.LENGTH_SHORT).show();
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
               //start---跳转前 存入数据库
                //todo//永远是表中前1-10个问题存入
                //新日记
                if(isNewDay) {
                    List<BaseQue> newBaseQueList = new ArrayList<>();
                    for (BaseQue addQue : mBaseQueList) {//mBaseQueList 为点击日历已有-当天问题 未有-初始化问题
                        //   点击日历-指向那一天的问题 已有-那一天问题 未有-初始化问题
                        BaseQue newBaseQue = new BaseQue();
                        newBaseQue.setType(addQue.getType());
                        newBaseQue.setTitle(addQue.getTitle());
                        // 绑定答案
                        List<Content> newContentList = new ArrayList<>();
                        for (Content content : addQue.getContent()) {//遍历所有回答选项
                            //一个问题对应生成一个对象 BaseQue
                            Content newContent = new Content();
                            newContent.setName(content.getName());
                            newContent.setIsChecked(content.isChecked());//todo//不同日记不同保存
                            newContent.setBaseQue(newBaseQue);
                            newContentList.add(newContent);
                        }
                        ContentDao contentDao = new ContentDao(getActivity());
                        contentDao.insert(newContentList);//将问题们添加进数据库表格里
                        //end----答案绑定
                        newBaseQue.setDiary(mDiary);

                        newBaseQueList.add(newBaseQue);

              /*      addQue.setDiary(mDiary);//与当天日记绑定 基本问题
                    baseQueDao.update(addQue);*/
                    }

                    baseQueDao.insert(newBaseQueList);

                    mDiaryDao.insert(mDiary);
                }
                else {//已有 更新操作  //todo//答案设置
                    for (BaseQue addQue : mBaseQueList) {
                        // addQue.setDiary(mDiary);//与当天日记绑定 基本问题
                        baseQueDao.update(addQue);
                    }

                    mDiaryDao.update(mDiary);
                }
                List<BaseQue> mmlist = baseQueDao.queryAll();

                Log.i("Test", "diary:" + mmlist.toString());
             // List<BaseQue>  testeList ;
             //   testeList = mDiary.getBaseQues();//创建问题表数据源。此时mBaseQueList无

                //mDiaryDao.insert(mDiary);
                List<Diary> mlist = mDiaryDao.queryAll();

                Log.i("Test", "diary:" + mlist.toString());
                //end-----------存入数据库

                //活动跳转法==但是会调频
         /*       Intent intent = new Intent(getActivity(), MainActivity.class);
              intent.putExtra(KeyConfig.CALENDAR_DAY, date);
                startActivity(intent);*/
//start-----------展示
            //不如直接搜索数据库刷新
                mDiaryDate = DateUtils.formatDate(date.getDate());//转化为string类型

                // 判断是否为新日记,应用场景：客户在一天内重启程序多次
                mDiaryDao = new DiaryDao(getActivity());//日记操作对象-Diary
                mDiary = mDiaryDao.query(mDiaryDate);//根据时间来查询今天的日记
                if (mDiary == null) //若未找到此条记录==当前是新的日记
                    {
                        isNewDay=true;
                        mDiary = new Diary();//创建新日记
                        mDiary.setDate(mDiaryDate);//添加日期字段信息

                    }
                    else{
                    isNewDay=false;
                }
                    //todo//加载场景1 新日记 加载场景2 已有日记



                    mBaseQueList = mDiary.getBaseQues();//创建问题表数据源。此时mBaseQueList无
                    //if mBaseQueList为空说明为新日记，则数据源为默认值
                    if (mBaseQueList.size() == 0) {
                        //todo//应该查询前10个问题或者是查询无归属日记的问题
                        //
                        mBaseQueList = initBaseQueList;
                      // mBaseQueList = baseQueDao.queryAll();//查询表中所有的记录：问题
                        //todo// 需要在新日记显示的记录 不等于问题表所有记录 。应等于 前一天的记录
                    }
               // mBaseQueList.add(bas)
                mAdapter.notifyDataSetChanged();
                mDiaryCardList.scrollToPosition(1);


            }
        });

        //TODO//日历样式
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());//日历样式激活
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
                        BaseQueDao baseQueDao = new BaseQueDao(getActivity());//创建数据库操作对象-本环境下mContex 关联
                        mBaseQueList.get(pos).setDelete(true);
                        baseQueDao.update(mBaseQueList.get(pos));//更新数据库

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
    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
/*
        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            //TODO//bug:点击其他月份的日期后又跳回原来月份的日历界面
            //已写过日记的加上标记
            ArrayList<CalendarDay> dates = new ArrayList<>();
            List<Diary> list = mDiaryDao.queryAll();
            for (Diary nDiary : list) {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date sdt=java.sql.Date.valueOf(nDiary.getDate());
                calendar.setTime(sdt);

                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }



            return dates;
        }*/
@Override
protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -2);//隔两个月选入
    ArrayList<CalendarDay> dates = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
        CalendarDay day = CalendarDay.from(calendar);
        dates.add(day);
        calendar.add(Calendar.DATE, 5);//这个月中的每隔五天选入
    }

    return dates;
}
        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (getActivity().isFinishing()) {
                return;
            }

            calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }

}
