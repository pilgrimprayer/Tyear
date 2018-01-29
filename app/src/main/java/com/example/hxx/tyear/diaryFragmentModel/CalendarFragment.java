package com.example.hxx.tyear.diaryFragmentModel;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hxx.tyear.MainActivity;
import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.QGridyAdapter;
import com.example.hxx.tyear.model.BaseQueManager;
import com.example.hxx.tyear.model.bean.BaseQue;
import com.example.hxx.tyear.model.bean.Content;
import com.example.hxx.tyear.model.bean.Diary;
import com.example.hxx.tyear.model.bean.Label;
import com.example.hxx.tyear.model.dao.BaseQueDao;
import com.example.hxx.tyear.model.dao.ContentDao;
import com.example.hxx.tyear.model.dao.DiaryDao;
import com.example.hxx.tyear.model.dao.LabelDao;
import com.example.hxx.tyear.support.DateUtils;
import com.example.hxx.tyear.support.KeyConfig;
import com.example.hxx.tyear.view.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by hxx on 2017/10/6.
 */

public class CalendarFragment extends Fragment  implements View.OnClickListener {
    private ArrayList<RecycleViewItemData> dataList;
    RecyclerView mDiaryCardList;
    QGridyAdapter mAdapter;
    FloatingActionButton addFab;
    ImageView save;
    ImageView back;

    final Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    // final Content checkContent[][];
    final List<Map<Integer, String>> saveCheckList = new ArrayList<>();
    private Date mDate;
   private boolean isNewDay=false;
    private String mDiaryDate = null;
    private DiaryDao mDiaryDao;
    private Diary mDiary;
    private BaseQueDao baseQueDao;
    private BaseQue mBaseQue;
   // ContentDao  contentDao;
    private List<BaseQue> mBaseQueList=new ArrayList<>();
    private  List<BaseQue> initBaseQueList;

    private  MaterialCalendarView calendarView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment,container,false);

        calendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)//第一天为星期一
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
//        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//        Date myDate1=null;
//        try {
//           myDate1 = dateFormat1.parse("2017-11-24");
//        } catch (ParseException e) {
//
//        }
        CalendarDay calendarDay = getActivity().getIntent().getParcelableExtra(KeyConfig.CALENDAR_DAY);
        Date newDate = (Date) getActivity().getIntent().getSerializableExtra(KeyConfig.DATE);

        if (newDate != null) {
            mDate = newDate;
        } else if (calendarDay != null) {
            mDate = calendarDay.getDate();//获取点击日历传来的时间
        } else {
            mDate = new Date(System.currentTimeMillis());//获取当前时间

        }

    calendarView.setSelectedDate(mDate);
//        addFab=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseQueDao=new BaseQueDao(getActivity());
       mDiaryCardList=(RecyclerView)getActivity().findViewById(R.id.diary_card_list);

        mDiaryCardList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        addFab=(FloatingActionButton)getActivity().findViewById(R.id.floatingActionButton);
     getActivity().findViewById(R.id.diary_back).setOnClickListener(this);;
       getActivity().findViewById(R.id.diary_done).setOnClickListener(this);;
        /**
         * 数据源加载-系统默认问题加入库:问题项表 baseque 回答content表
         */
        //可能从日历页面下获得 CalendarDay
        CalendarDay calendarDay = getActivity().getIntent().getParcelableExtra(KeyConfig.CALENDAR_DAY);


        BaseQueManager baseQueManager = new BaseQueManager(getActivity());
//        if(baseQueDao.queryAll()==null)
        baseQueManager.init();


        /**
         * 数据源加载-默认为今天
         */


      //  mDate = new Date(System.currentTimeMillis());//获取当前时间

        mDiaryDate = DateUtils.formatDate(mDate);//转化为string类型

        // 判断是否为新日记,应用场景：客户在一天内重启程序多次
        mDiaryDao = new DiaryDao(getActivity());//日记操作对象-Diary
       // mDiaryDao.delete("2017-11-30");
        mDiary = mDiaryDao.query(mDiaryDate);//根据时间来查询今天的日记
        if (mDiary == null) //若未找到此条记录==当前是新的日记
            {
                mDiary = new Diary();//创建新日记
                mDiary.setDate(mDiaryDate);//添加日期字段信息
                isNewDay=true;
            }

            //sart----获取基本问题列表
        //todo//
        List<BaseQue> tBaseQueList= baseQueDao.queryAll();
      initBaseQueList= new ArrayList<>();
        for(int i=0;i<10;i++){
            initBaseQueList.add(tBaseQueList.get(i));
        }
        mBaseQueList=mDiary.getBaseQues();//获取当天问题



            //if mBaseQueList为空说明为新日记，则数据源为最新一天
            if(mBaseQueList.size()==0) {
//                Calendar calendar = new GregorianCalendar();
//                calendar.setTime(mDate);
//                calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
//                Date date = calendar.getTime();   //这个时间就是日期往后推一天的结果
//
//                String oDiaryDate = DateUtils.formatDate(date);//转化为string类型
//                Diary oDiary = mDiaryDao.query(oDiaryDate);//根据时间来查询今天的日记
                Diary oDiary = mDiaryDao.queryLast();

                if (oDiary != null) {//最新一天存在//todo//有问题需要先加入数据库 才能连接多表
                    for (BaseQue addQue : oDiary.getBaseQues()) {//mBaseQueList 为点击日历已有-当天问题 未有-初始化问题
                        //   点击日历-指向那一天的问题 已有-那一天问题 未有-初始化问题
                        BaseQue newBaseQue = new BaseQue();
                        newBaseQue.setType(addQue.getType());
                        newBaseQue.setTitle(addQue.getTitle());
                     //   newBaseQue.setDiary(mDiary);
                        baseQueDao.insert(newBaseQue);//先放入数据库 这样答案才能绑定上问题
                        // 绑定标签
                        List<Label> newLabelList = new ArrayList<>();
                        for (Label label : addQue.getLabel()) {//遍历所有回答选项
                            //一个问题对应生成一个对象 BaseQue
                            Label newlabel = new Label();
                            newlabel.setName(label.getName());
                            //newContent.setIsChecked(content.isChecked());//
                            newlabel.setBaseQue(newBaseQue);
                            newLabelList.add(newlabel);
                        }
                        LabelDao labelDao = new LabelDao(getActivity());
                        labelDao.insert(newLabelList);
                        // 答案绑定  START--
                        List<Content> newContentList = new ArrayList<>();
                        if (addQue.getType() == 0)//text类型
                        {

                            Content newContent = new Content();
                            newContent.setName(null);
                            // newContent.setIsChecked(content.isChecked());
                            newContent.setBaseQue(newBaseQue);//绑定问题
                            newContentList.add(newContent);//增加数据库新数据

                        }
                        if (addQue.getType() != 0) {
                            for (Content content : addQue.getContent()) {
                                //一个问题对应生成一个对象 BaseQue
                                Content newContent = new Content();
                                // newContent.setName(content.getName());
                                newContent.setName(content.getName());
                                newContent.setIsChecked(false);

                                newContent.setBaseQue(newBaseQue);
                                newContentList.add(newContent);
                            }
                        }


                        ContentDao contentDao = new ContentDao(getActivity());
                        contentDao.insert(newContentList);//将问题们添加进数据库表格里
                        //end----答案绑定
                        //  isNewDay=false;//问题列表已于日记绑定 不需要再新建和此日记绑定问题列表 而直接对它们更新即可

                        mBaseQueList.add(baseQueDao.queryLast());//更新数据
                    }
                }
              if(mBaseQueList.size()==0){//如果前一天也没有==从原始数据获取//所以每次重置数据库都能成功get

                    mBaseQueList=initBaseQueList;


                }


            }





        /**
         * 适配器加载
         */


        //if(isNewDay==false) {

            //文本到单选问题复制
            for(int j=0,k=0;j<mBaseQueList.size();j++){
                //文本复制到map
                if(mBaseQueList.get(j).getType()==0)
                    if(mBaseQueList.get(j).getContent().size()!=0)
                    map.put(j, mBaseQueList.get(j).getContent().get(0).getName());
                //单选复制到map
                if(mBaseQueList.get(j).getType()==1){//单选文本下
                    for(Content content:mBaseQueList.get(j).getContent()){
                        if(content.isChecked()==true)//对的话
                            map.put(j, content.getName());
                    }
                }
                //多选复制到saveCheckListlist
                if(mBaseQueList.get(j).getType()==2){
                    Map checkBox;
                    Map checkContent = new LinkedHashMap();
                    int p=0;
                    for(Content content:mBaseQueList.get(j).getContent()){


                        if(content.isChecked()==true)//对的话
                        {
                            checkContent.put(p, content.getName());
                        }
                        else {
                            checkContent.put(p,null);
                        }
                        p++;
                    }
                    saveCheckList.add(checkContent);

                }
          //  }
            //多选问题复制

        }
        mAdapter = new QGridyAdapter(mBaseQueList,getActivity(),map,saveCheckList);
//监听输入文本
        mAdapter.setSaveEditListenerr(new QGridyAdapter.SaveEditListener() {
            @Override
            public void SaveEdit(int position, String string) {
               // Toast.makeText(getActivity(), "填写内容 " +string+ position, Toast.LENGTH_SHORT).show();
                 mAdapter.changeLockList.set(position,false);

                map.put(position,string);
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
         *
         * 跳转监听
         */
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
               Intent intent = new Intent(getActivity(), QuestionEditAcitivity.class);

                getActivity().startActivity(intent);
               // Snackbar.make(mCoordinateLayout,"click me", Snackbar.LENGTH_SHORT).show();
            }
        });

//
//

        /**
         * 日历选择监听
         */

        //start-日历点击 列表内容变化
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //start---跳转前 存入数据库
//                      Intent intent = new Intent(getActivity(), MainActivity.class);
//              intent.putExtra(KeyConfig.CALENDAR_DAY, date);
//                startActivity(intent);
                NoSave();
            //todo//1116BUG recylerlist位置紊乱  solve1 gettag而不是position solve2 数据库更新 而不是用map
                for(int i=0;i< mAdapter.changeLockList.size();i++)
                    mAdapter.changeLockList.set(i,true);
              List<BaseQue> testBaseQueList = new ArrayList<BaseQue>();
            //发送


                //todo//清零map
                mDiaryDate = DateUtils.formatDate(date.getDate());//转化为string类型
                mDate=date.getDate();//转化为Date 方便INtent传输
                // 判断是否为新日记,应用场景：客户在一天内重启程序多次
                mDiaryDao = new DiaryDao(getActivity());//日记操作对象-Diary
                mDiary = mDiaryDao.query(mDiaryDate);//根据时间来查询今天的日记--日历点击天
                if (mDiary == null) //若未找到此条记录==当前是新的日记
                {
                    isNewDay = true;
                    mDiary = new Diary();//创建新日记
                    mDiary.setDate(mDiaryDate);//添加日期字段信息
                  //  mDiaryDao.insert(mDiary);//todo//为了后面问题绑定 直接通过日记日期搜索出问题??
                } else {
                    isNewDay = false;
                }

                //List<BaseQue> tmBaseQueList = new ArrayList<BaseQue>();
                testBaseQueList= mDiary.getBaseQues();
                if (testBaseQueList.size() == 0) {//从当天无日记记录==新的一天
                    isNewDay=true;
//                    Calendar calendar = new GregorianCalendar();
//                    calendar.setTime(date.getDate());
//                    calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
//                    Date ndate = calendar.getTime();   //这个时间就是日期往后推一天的结果
//
//                    String oDiaryDate = DateUtils.formatDate(ndate);//转化为string类型
//                    Diary oDiary = mDiaryDao.query(oDiaryDate);//根据时间来查询今天的日记

                    Diary    oDiary = mDiaryDao.queryLast();//todo!!!最新一天
                    //todo//心日记应等于最近最新的一天 而不仅仅是前一天 修正sql语句！！ 所以除了第一次打开用原始数据外 其他都是最新数据
                    if (oDiary != null) {//前一天存在
                      // mDiaryDao.insert(mDiary);//1116-1057 显示为选择的那天
                        for (BaseQue addQue : oDiary.getBaseQues()) {//mBaseQueList 为点击日历已有-当天问题 未有-初始化问题
                            //   点击日历-指向那一天的问题 已有-那一天问题 未有-初始化问题
                            BaseQue newBaseQue = new BaseQue();
                            newBaseQue.setType(addQue.getType());
                            newBaseQue.setTitle(addQue.getTitle());
                            newBaseQue.setDiary(mDiary);
                            baseQueDao.insert(newBaseQue);//先放入数据库 这样答案才能绑定上问题
                            // 绑定标签
                            List<Label> newLabelList = new ArrayList<>();
                            for (Label label : addQue.getLabel()) {//遍历所有回答选项
                                //一个问题对应生成一个对象 BaseQue
                                Label newlabel = new Label();
                                newlabel.setName(label.getName());
                                //newContent.setIsChecked(content.isChecked());//
                                newlabel.setBaseQue(newBaseQue);
                                newLabelList.add(newlabel);
                            }
                            LabelDao labelDao = new LabelDao(getActivity());
                            labelDao.insert(newLabelList);
                            // 答案绑定  START--
                            List<Content> newContentList = new ArrayList<>();
                            if (addQue.getType() == 0)//text类型
                            {

                                Content newContent = new Content();
                                newContent.setName(null);
                                // newContent.setIsChecked(content.isChecked());
                                newContent.setBaseQue(newBaseQue);//绑定问题
                                newContentList.add(newContent);//增加数据库新数据

                            }
                            if (addQue.getType() != 0) {
                                for (Content content : addQue.getContent()) {
                                    //一个问题对应生成一个对象 BaseQue
                                    Content newContent = new Content();
                                    // newContent.setName(content.getName());
                                    newContent.setName(content.getName());
                                    newContent.setIsChecked(false);

                                    newContent.setBaseQue(newBaseQue);
                                    newContentList.add(newContent);
                                }
                            }


                            ContentDao contentDao = new ContentDao(getActivity());
                            contentDao.insert(newContentList);//将问题们添加进数据库表格里
                            //end----答案绑定
                          //  isNewDay=false;//问题列表已于日记绑定 不需要再新建和此日记绑定问题列表 而直接对它们更新即可

                            testBaseQueList.add(baseQueDao.queryLast());//更新数据
                        }
                     //   mDiary = mDiaryDao.query(mDiaryDate);//再次根据时间来查询当天的日记
                      //  mBaseQueList = mDiary.getBaseQues();


                      //  testBaseQueList = mDiary.getBaseQues();//再次获取日记
                        if (testBaseQueList.size() == 0) {//如果前一天存在但也没有==从原始数据获取
                           // testBaseQueList = initBaseQueList;
                            for (BaseQue addQue : initBaseQueList)//从前天日记获取问题数据，放入mBaseQueList筐
                            {
                                BaseQue newBaseQue = new BaseQue();
                                newBaseQue.setType(addQue.getType());
                                newBaseQue.setTitle(addQue.getTitle());
                               // newBaseQue.setDiary(mDiary);
                                baseQueDao.insert(newBaseQue);//先放入数据库 这样答案才能绑定上问题!!入库-QUE
                                // 绑定答案
                                List<Content> newContentList = new ArrayList<>();
                                for (Content content : addQue.getContent()) {//遍历所有回答选项
                                    //一个问题对应生成一个对象 BaseQue
                                    Content newContent = new Content();
                                    newContent.setName(content.getName());
                                    //newContent.setIsChecked(content.isChecked());//
                                    newContent.setBaseQue(newBaseQue);
                                    newContentList.add(newContent);
                                }
                                ContentDao contentDao = new ContentDao(getActivity());
                                contentDao.insert(newContentList);//添加进数据库表格里！！入库-  cont
                                // 绑定标签
                                List<Label> newLabelList = new ArrayList<>();
                                for (Label label : addQue.getLabel()) {//遍历所有回答选项
                                    //一个问题对应生成一个对象 BaseQue
                                    Label newlabel = new Label();
                                    newlabel.setName(label.getName());
                                    //newContent.setIsChecked(content.isChecked());//
                                    newlabel.setBaseQue(newBaseQue);
                                    newLabelList.add(newlabel);
                                }
                                LabelDao labelDao = new LabelDao(getActivity());
                                labelDao.insert(newLabelList);//!!入库 label

                                testBaseQueList.add( baseQueDao.queryLast());
                            }
                            //入完库后再从库取出来的问题 一定是与label和content绑定的
//                            mDiary = mDiaryDao.query(mDiaryDate);//再次根据时间来查询当天的日记
//                               testBaseQueList = mDiary.getBaseQues();//再次获取日记
                        }
                    }
                    else{//前一天不存在==从最新数据获取 odiary==null
                        //testBaseQueList=initBaseQueList;
                        for (BaseQue addQue : initBaseQueList)//从前天日记获取问题数据，放入mBaseQueList筐
                        {
                            BaseQue newBaseQue = new BaseQue();
                            newBaseQue.setType(addQue.getType());
                            newBaseQue.setTitle(addQue.getTitle());
                           // newBaseQue.setDiary(mDiary);
                            baseQueDao.insert(newBaseQue);//先放入数据库 这样答案才能绑定上问题!!入库-QUE
                            // 绑定答案
                            List<Content> newContentList = new ArrayList<>();
                            for (Content content : addQue.getContent()) {//遍历所有回答选项
                                //一个问题对应生成一个对象 BaseQue
                                Content newContent = new Content();
                                newContent.setName(content.getName());
                                //newContent.setIsChecked(content.isChecked());//
                                newContent.setBaseQue(newBaseQue);
                                newContentList.add(newContent);
                            }
                            ContentDao contentDao = new ContentDao(getActivity());
                            contentDao.insert(newContentList);//添加进数据库表格里！！入库-  cont
                            // 绑定标签
                            List<Label> newLabelList = new ArrayList<>();
                            for (Label label : addQue.getLabel()) {//遍历所有回答选项
                                //一个问题对应生成一个对象 BaseQue
                                Label newlabel = new Label();
                                newlabel.setName(label.getName());
                                //newContent.setIsChecked(content.isChecked());//
                                newlabel.setBaseQue(newBaseQue);
                                newLabelList.add(newlabel);
                            }
                            LabelDao labelDao = new LabelDao(getActivity());
                            labelDao.insert(newLabelList);//!!入库 label

                            testBaseQueList.add( baseQueDao.queryLast());//从数据库中才能获取和content和label绑定的问题
                        }
                        //入完库后再从库取出来的问题 一定是与label和content绑定的
//                        mDiary = mDiaryDao.query(mDiaryDate);//再次根据时间来查询当天的日记
//                        testBaseQueList = mDiary.getBaseQues();//再次获取日记
                    }
                }
            //    mAdapter = new QGridyAdapter(mBaseQueList,getActivity(),map,saveCheckList);
                int sum=mAdapter.getItemCount();
                for(int i=0;i<sum;i++) {
                    mBaseQueList.remove(0);
                }
                for(BaseQue nque:testBaseQueList){
                    mBaseQueList.add(nque);
                }
               // map= new HashMap<Integer, String>();
                int i=0;
                //map 若未旧的一天 map初始化为那一天的信息

                    //清除文本输入区 因为view要根据map显示数据
                
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        map.put(entry.getKey(), null);
                        i++;
                    }
                    //清除多选区
            //    saveCheckList = new ArrayList<Map>();
                
                for(int j=0;j<saveCheckList.size();j++){
                    int k=0;
                    for (Map.Entry<Integer, String> entry: saveCheckList.get(j).entrySet()) {
                        entry.setValue(null);  //entry是key
                        k++;
                    }
                }
                if(isNewDay==false) {//旧的一天 复制数据给map和checklist

                    //文本到单选问题复制
                    for(int j=0,k=0;j<mBaseQueList.size();j++){
                        //文本复制到map
                        if(mBaseQueList.get(j).getType()==0)
                            if(mBaseQueList.get(j).getContent().size()!=0)
                            map.put(j, mBaseQueList.get(j).getContent().get(0).getName());
                        //单选复制到map
                        if(mBaseQueList.get(j).getType()==1){//单选文本下
                            for(Content content:mBaseQueList.get(j).getContent()){
                                if(content.isChecked()==true)//对的话
                                    map.put(j, content.getName());
                            }
                        }
                        //多选复制到saveCheckListlist 1117 成功都根据mBaseQueList初始化map
                        if(mBaseQueList.get(j).getType()==2){
                            Map checkBox; int p=0;
                            for(Content content:mBaseQueList.get(j).getContent()){

                                if(content.isChecked()==true)//对的话
                                {
                                    saveCheckList.get(k).put(p, content.getName());
                                }
                                else{//不然去除 否则会数据紊乱
                                    saveCheckList.get(k).put(p, null);
                                }
                                p++;
                            }
                            k++;
                        }
                    }
                    //多选问题复制
                   
                }
                mAdapter.notifyDataSetChanged();
                mDiaryCardList.scrollToPosition(0);
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
                       //mBaseQueList.remove(pos);
               /*         BaseQueDao baseQueDao = new BaseQueDao(getActivity());//创建数据库操作对象-本环境下mContex 关联
                        mBaseQueList.get(pos).setDelete(true);
                        baseQueDao.update(mBaseQueList.get(pos));//更新数据库*/
                        mAdapter.removeItem(pos);//但是没有效果
                        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.diary_back:
                intent = new Intent(getActivity(), MainActivity.class);
            //  intent.putExtra(KeyConfig.DATE, mDate);
                NoSave();

                break;

            case R.id.diary_done:
                intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra(KeyConfig.DIARY, mDiary);
            intent.putExtra(KeyConfig.DATE, mDate);
               Save();
                break;



            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void Save() {//根据map和checkList获取

        List<BaseQue> testBaseQueList = new ArrayList<BaseQue>();
        if (isNewDay) {
            mDiaryDao.insert(mDiary);
            List<BaseQue> newBaseQueList = new ArrayList<>();
            int num = 0, k = 0, checkSize;
            checkSize = saveCheckList.size();
            for (BaseQue addQue : mBaseQueList) {//mBaseQueList 为点击日历已有-当天问题 未有-初始化问题
                //   点击日历-指向那一天的问题 已有-那一天问题 未有-初始化问题
                BaseQue newBaseQue = new BaseQue();
                newBaseQue.setType(addQue.getType());
                newBaseQue.setTitle(addQue.getTitle());
                newBaseQue.setDiary(mDiary);
                baseQueDao.insert(newBaseQue);//先放入数据库 这样答案才能绑定上问题
                // 绑定标签
                List<Label> newLabelList = new ArrayList<>();
                for (Label label : addQue.getLabel()) {//遍历所有回答选项
                    //一个问题对应生成一个对象 BaseQue
                    Label newlabel = new Label();
                    newlabel.setName(label.getName());
                    //newContent.setIsChecked(content.isChecked());//
                    newlabel.setBaseQue(newBaseQue);
                    newLabelList.add(newlabel);
                }
                LabelDao labelDao = new LabelDao(getActivity());
                labelDao.insert(newLabelList);
                // 答案绑定问题

                List<Content> newContentList = new ArrayList<>();
                if (addQue.getType() == 0)//text类型
                {

                    // String key=String.valueOf(num);
                    if (map.containsKey(num)) {//若已输入 更新内容 list与map位置一一对应
                        //for (Content content : addQue.getContent()) {//遍历所有回答选项//text只有一个
                        //一个问题对应生成一个对象 BaseQue
                        Content newContent = new Content();
                        newContent.setName((String) map.get(num));
                        // newContent.setIsChecked(content.isChecked());
                        newContent.setBaseQue(newBaseQue);//绑定问题
                        newContentList.add(newContent);//增加数据库新数据
                    }

                }
                if (addQue.getType() == 1) {//单选问题 -每一个回答都得加上 只需标记已选的即可
                    for (Content content : addQue.getContent()) {//遍历所有回答选项//text只有一个
                        //一个问题对应生成一个对象 BaseQue
                        Content newContent = new Content();
                        // newContent.setName(content.getName());
                        newContent.setName(content.getName());

                        if (map.containsKey(num) && content.getName().equals(map.get(num))) {
                            newContent.setIsChecked(true);//
                        }


                        newContent.setBaseQue(newBaseQue);
                        newContentList.add(newContent);
                    }

                }

                if (addQue.getType() == 2) {//多选问题 -每一个回答都得加上 遍历checkmap 标记多个
                    Map checkMap = null;
                    if (checkSize > 0) {
                        checkMap = saveCheckList.get(k++);
                        checkSize--;
                    }
                    for (int i = 0; i < addQue.getContent().size(); i++) {//遍历所有回答选项//text只有一个
                        //一个问题对应生成一个对象 BaseQue
                        //Content content : addQue.getContent()
                        Content content = addQue.getContent().get(i);
                        // newContent.setName(content.getName());
                        Content newContent = new Content();

                        newContent.setName(content.getName());


                        if (checkMap != null && checkMap.containsKey(i) && content.getName().equals(checkMap.get(i))) {
                            newContent.setIsChecked(true);//
                        }

                        newContent.setBaseQue(newBaseQue);
                        newContentList.add(newContent);
                    }

                }
                ContentDao contentDao = new ContentDao(getActivity());
                contentDao.insert(newContentList);//将问题们添加进数据库表格里
                //end----答案绑定


                num++;
            }

//                    baseQueDao.insert(newBaseQueList);

            ContentDao contentDao = new ContentDao(getActivity());
            List<Content> t2list = contentDao.queryAll();

            Log.i("Test", "diary:" + t2list.toString());//只能看到content对应的Qid但title什么的都没
            mDiary = mDiaryDao.query(mDiaryDate);//根据时间来查询今天的日记//看到Q Q对应的content
            mBaseQueList = mDiary.getBaseQues();

        } else {//已有 更新操作  //todo//mBaseQueList一定存了修改的东西吗  更新的话还是得从map中读取
            int num = 0, k = 0, checkSize;
            checkSize = saveCheckList.size();
            for (BaseQue addQue : mBaseQueList) {
                // addQue.setDiary(mDiary);//与当天日记绑定 基本问题
                //遍历所有回答选项//text只有一个

                Content content ;
                ContentDao contentDao = new ContentDao(getActivity());
                if (addQue.getType() == 0)//text类型
                {


                    // String key=String.valueOf(num);
                    if (map.containsKey(num)) {//若已输入 更新内容 list与map位置一一对应
                        if(addQue.getContent().size()!=0) {//若已存在此回答 则更新
                            int contentID =addQue.getContent().get(0).getId();
                            content = contentDao.query(contentID);
                            content.setName((String) map.get(num));
                            contentDao.insert(content);//content19-bq11 20-13
                        }
                        else{//若不存在则创建
                            content = new Content();
                            content.setName((String) map.get(num));
                            // newContent.setIsChecked(content.isChecked());
                            content.setBaseQue(addQue);//绑定问题
                            contentDao.insert(content);
                        }
                    }//若不包括说明已经删除操作
                    else{
                        if(addQue.getContent().size()!=0) {//若已存在此回答 则更新
                            int contentID =addQue.getContent().get(0).getId();
                            content = contentDao.query(contentID);
                            content.setName(null);
                            contentDao.insert(content);//content19-bq11 20-13
                        }
                        //若不存在 则一直保留Null
                    }
                }
                if (addQue.getType() == 1) {//单选问题 -每一个回答都得加上 只需标记已选的即可
                    for (int i = 0; i < addQue.getContent().size(); i++) {
                        int contentID =addQue.getContent().get(i).getId();
                        content = contentDao.query(contentID);
                        if (map.containsKey(num) && content.getName().equals(map.get(num))) {
                            content.setIsChecked(true);//
                            contentDao.insert(content);
                        }
                        //     else if(map.containsKey(num) && !content.getName().equals(map.get(num))){//checkMap无 即无任何勾选 或当期回答不在checkmap一一对应位置 说明肯定没选上
                        else {
                            content.setIsChecked(false);//
                            contentDao.insert(content);
                        }
                    }
                }
                if (addQue.getType() == 2) {//多选问题 -每一个回答都得加上 遍历checkmap 标记多个
                    Map checkMap = null;
                    if (checkSize > 0) {
                        checkMap = saveCheckList.get(k++);
                        checkSize--;
                    }
                    for (int i = 0; i < addQue.getContent().size(); i++) {
                        int contentID =addQue.getContent().get(i).getId();
                        content = contentDao.query(contentID);

                        if (checkMap != null && checkMap.containsKey(i) && content.getName().equals(checkMap.get(i))) {
                            content.setIsChecked(true);//
                            contentDao.insert(content);
                        }
                        else if(checkMap==null||checkMap.get(i)==null){//checkMap无 即无任何勾选 或当期回答不在checkmap一一对应位置 说明肯定没选上
                            content.setIsChecked(false);//
                            contentDao.insert(content);
                        }
                    }

                }
                baseQueDao.update(addQue);
                num++;
            }
            mDiaryDao.update(mDiary);
        }

        // List<BaseQue>  testeList ;
        //   testeList = mDiary.getBaseQues();//创建问题表数据源。此时mBaseQueList无

        //mDiaryDao.insert(mDiary);
        List<Diary> mlist = mDiaryDao.queryAll();///diary 没有显示绑定的que

        Log.i("Test", "diary:" + mlist.toString());//更新后的日记
        //end-----------存入数据库

    }

    private void NoSave() {
        if(isNewDay==true){
            DiaryDao diaryDao = new DiaryDao(getActivity());
            diaryDao.delete(mDiary);
        }
    }


    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {


@Override
protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }    ArrayList<CalendarDay> dates = new ArrayList<>();
    List<String> dateStringList = new ArrayList<>();
    List<Diary> mlist = mDiaryDao.queryAll();///diary 没有显示绑定的que
    for(Diary diary:mlist){
        String dateS = diary.getDate();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(dateS));
         //  date = sdf.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }



//        calendar.setTime(date);
        CalendarDay day = CalendarDay.from(calendar);
        dates.add(day);


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
