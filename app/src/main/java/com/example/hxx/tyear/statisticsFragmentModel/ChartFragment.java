package com.example.hxx.tyear.statisticsFragmentModel;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.ChartAdapter;
import com.example.hxx.tyear.model.bean.BaseQue;
import com.example.hxx.tyear.model.bean.Content;
import com.example.hxx.tyear.model.bean.Diary;
import com.example.hxx.tyear.model.dao.BaseQueDao;
import com.example.hxx.tyear.model.dao.DiaryDao;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by hxx on 2017/10/7.
 */

public class ChartFragment extends Fragment {
    private LineChartView lineChart;
    String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22"};//X轴的标注
    int[] score= {50,42,90,33,10,74,22};//图表的数据点
    int[] colorData = {
            Color.parseColor("#FFE3B7"),//白色
            Color.parseColor("#840404"),//暗红
            Color.parseColor("#CB0101"),//明红
            Color.parseColor("#FFAF00")};//黄色
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private LineChartView chartView;
    private LineChartView chartView2;
   private ArrayList<ChartItem> dataList;
//private ArrayList<MineItem> dataList;
    RecyclerView mTableList;
    ChartAdapter mAdapter;
    private LineChart mLineChart;
    public  TextView textTitle;
    List<BaseQue> quetionsList;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    public ChartFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChartFragment newInstance(int columnCount) {
        ChartFragment fragment = new ChartFragment();
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
        View view = inflater.inflate(R.layout.sta_chart_list, container, false);
//
//        chartView = (LineChartView)view.findViewById(R.id.chart);
//        chartView2=(LineChartView)view.findViewById(R.id.chart2);
//        initData2Chart();


        mTableList=(RecyclerView)view.findViewById(R.id.chart_list);
        mTableList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));





        /**
         * 数据源加载
         */
        //todo//目前选所有的日记
        DiaryDao mDiaryDao=new DiaryDao(getActivity());
        List<Diary> diaryList = mDiaryDao.queryAll();

        final Map<String, List<BaseQue>> map = new HashMap<String, List<BaseQue>>();
        BaseQueDao baseQueDao = new BaseQueDao(getActivity());
        List<BaseQue> baseQueList= baseQueDao.queryAllDin();//过滤重复项查询问题库里的所有问题
//
//        for(BaseQue baseQue:baseQueList){
//            if(baseQue.getType()!=0){
//                quetionsList.add(baseQue);//
//            }
//        }
        for(Diary diary:diaryList){
            List<BaseQue> diaryBaseQues = diary.getBaseQues();
            for(BaseQue baseQue:diaryBaseQues) {
                if(baseQue.getType()!=0) {//挑出非选
                    String title = baseQue.getTitle();
                    if (!map.containsKey(title)) {//若是第一次则初始值为零
                        List<BaseQue> pointBaseQueList = new ArrayList<BaseQue>();

                        pointBaseQueList.add(baseQue);
                        map.put(title, pointBaseQueList);//计数+1
                    } else {
                        List<BaseQue> pointBaseQueList = map.get(title);
                        pointBaseQueList.add(baseQue);
                        map.put(title, pointBaseQueList);//计数+1
                    }
                }
            }
        }
//题目,所有已在日记中的问题 但不代表都回答了

        dataList=new ArrayList<ChartItem>();
        for (Map.Entry<String, List<BaseQue>> entry: map.entrySet()) {
            //一个map键值对应一个view
                //一个view里有多个点
            int chartType=0;
            List<ChartPointItem> chartPointItemList=new ArrayList<ChartPointItem>();
            List<BaseQue> baseQueListIn=entry.getValue();//属于此问题题目 的所有已记录问题获取
            if(baseQueListIn.get(0).getType()==1){//单选
                 for(BaseQue baseQue:baseQueListIn){//遍历这些问题 获取点：日期，回答

                    chartType=0;
                    for(Content content:baseQue.getContent()) {
                        if (content.isChecked()==true){//若选择了则记录回答
                        //str=str.Substring(i);
                            ChartPointItem chartPointItem = new ChartPointItem(baseQue.getDiary().getDate().substring(5),content.getName());
                            chartPointItemList.add(chartPointItem);
                        }
                    }
                 }//最终获得这段日期内所有的点
                //单选加入
                // view创建元素：题目，线性/饼图，点List，纵坐标
                //todo//此时无横坐标 因为通过选择来定 此时默认为这一周
                ChartItem chartItem=new ChartItem(entry.getKey(),chartType,chartPointItemList,baseQueListIn.get(0).getContent());
                dataList.add(chartItem);//chartPointItemList.size()=0的为这一周没有一个回答的。所以可以move掉//todo
            }
            if(baseQueListIn.get(0).getType()==2){//多选
                chartType=1;
                final Map<String,Integer > checkMap = new LinkedHashMap<String,Integer >();

                for(BaseQue baseQue:baseQueListIn){//遍历这些问题 获取点：回答名字 总数

                    int ci=0;


                    for(Content content:baseQue.getContent()) {
                        if (checkMap.get(content.getName()) == null) {//若是第一次则初始值为零

                            checkMap.put(content.getName(), 0);
                        }
                        if (content.isChecked()==true){//若选择了则记录回答
                            checkMap.put(content.getName(), checkMap.get(content.getName()) + 1);//计数+1
//                            if (checkMap.get(content.getName()) == null) {//若是第一次则初始值为零
//
//                                checkMap.put(content.getName(), 0);
//                                checkMap.put(content.getName(), checkMap.get(content.getName()) + 1);//计数+1
//                            } else {
//                                checkMap.put(content.getName(), checkMap.get(content.getName()) + 1);//计数+1
//                            }
                        }
                    }
                }//最终checkMap 回答名，回答数量
                int ci=0;
                int j=0,doneSum=0,undoneSum;
                int needSum=baseQueListIn.get(0).getContent().size()*7;
         List<PieData> pieDataList = new ArrayList<>();
                for (Map.Entry<String,Integer> entryCheck: checkMap.entrySet()) {
                    PieData pieData=new PieData(entryCheck.getKey(),entryCheck.getValue(),colorData[ci]);
                    ci++;
                    pieData.setRate((float)entryCheck.getValue() / (float)needSum);
                    doneSum+=entryCheck.getValue();
                    pieDataList.add(pieData);
                }
                //最后一个是未完成的

                undoneSum=needSum -doneSum;//todo//有选日期的功能后总数要变
                float rate=(float)doneSum/(float)(undoneSum+doneSum);
                PieData pieData=new PieData("未完成",undoneSum,colorData[ci]);
                pieData.setRate((float)undoneSum / (float)needSum);
                pieDataList.add(pieData);
                // view创建元素：题目，线性/饼图，点List，纵坐标
                //todo//此时无横坐标 因为通过选择来定 此时默认为这一周
                ChartItem chartItem=new ChartItem(entry.getKey(),chartType,checkMap,pieDataList);
                chartItem.setRate(rate);//完成度
                dataList.add(chartItem);//checkmap.size()=0的为这一周没有一个回答的。所以可以move掉//todo
            }


   }
        List<Diary> diaryList2 = mDiaryDao.queryAll();
        for(BaseQue baseQue:baseQueList){
//            if(baseQue.getType()!=0){
//                quetionsList.add(baseQue);//
//            }
    }

//        dataList.add(new ChartItem(("提醒"),R.drawable.ic_timer_black_24dp,0));
//        dataList.add(new ChartItem(("密码锁"),R.drawable.ic_lock_black_24dp,1));

        /**
         * 适配器加载
         */
        mAdapter = new ChartAdapter(dataList,getActivity());
        mAdapter.setOnItemClickListener(new ChartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click " + dataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        mTableList.setAdapter(mAdapter);

        return view;  /* View view = inflater.inflate(R.layout.statistic_table_list, container, false);*/


    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables(){
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
      axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("dd");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }

    /**
     * 初始化数据到chart中
     */
    private void initData2Chart() {
        chartView.setOnValueTouchListener(listener);
        /**
         * 简单模拟的数据
         */
        List<PointValue> values = new ArrayList<>();
        //要点出的数据集

        values.add(new PointValue(1, 3));
        values.add(new PointValue(2, 1));
        values.add(new PointValue(3,2));
        values.add(new PointValue(4, 0));
        //setCubic(true),true是曲线型，false是直线连接
        Line line = new Line(values).setColor(Color.RED).setCubic(true);
        //一条折线加入折线集合
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        //折线集合
        LineChartData data = new LineChartData();
        data.setLines(lines);//因为可能有多条线 这里只有一条


        //坐标轴
        Axis axisX = new Axis();
        //setHasLines(true),设定是否有网格线
        Axis axisY = new Axis().setHasLines(false);
        //为两个坐标系设定名称
        axisX.setName("日期 ");
        axisY.setName("分值");
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        //设置图标所在位置
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        //将数据添加到View中
        chartView.setLineChartData(data);


        //第二个demo
        chartView2.setOnValueTouchListener(listener);
        /**
         * 简单模拟的数据
         */
        List<PointValue> values2 = new ArrayList<>();
        values2.add(new PointValue(1, 0));
        values2.add(new PointValue(2, 3));
        values2.add(new PointValue(3,1));
        values2.add(new PointValue(4, 2));
        //setCubic(true),true是曲线型，false是直线连接
        Line line2 = new Line(values).setColor(Color.RED).setCubic(true);
        //一条折线加入折线集合
        List<Line> lines2 = new ArrayList<>();
        lines2.add(line2);
        //折线集合
        LineChartData data2 = new LineChartData();
        data2.setLines(lines);
        //坐标轴
        Axis axisX2 = new Axis();
        //setHasLines(true),设定是否有网格线
        Axis axis2Y = new Axis().setHasLines(false);
        //为两个坐标系设定名称
        axisX2.setName("日期 ");
        axis2Y.setName("分值");
        //设置图标所在位置
        data2.setAxisXBottom(axisX2);
        data2.setAxisYLeft(axis2Y);
        //将数据添加到View中
        chartView2.setLineChartData(data2);


    }

    /**
     * 为每个点设置监听
     */
    private LineChartOnValueSelectListener listener = new LineChartOnValueSelectListener() {
        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "value" + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
        }
    };
    }

