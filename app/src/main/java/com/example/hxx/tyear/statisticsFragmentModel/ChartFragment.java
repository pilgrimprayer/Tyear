package com.example.hxx.tyear.statisticsFragmentModel;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.ChartAdapter;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

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
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private LineChartView chartView;

    private ArrayList<ChartItem> dataList;
    RecyclerView mTableList;
    ChartAdapter mAdapter;
    private LineChart mLineChart;
    public  TextView textTitle;
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
        View view = inflater.inflate(R.layout.sta_hellochart_item, container, false);

        chartView = (LineChartView)view.findViewById(R.id.chart);

        initData2Chart();



 /*       lineChart = (LineChartView)view.findViewById(R.id.chart);
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化*/



/*
        //1.从xml中获取linechart的引用
        mLineChart = (LineChart)view. findViewById(R.id.lineChart);
        textTitle = (TextView) view.findViewById(R.id.chart_text_title);
        //2.创建一个List集合，用来存放一条折线上的所有点
        List<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(10,20));
        entries.add(new Entry(20,30));
        entries.add(new Entry(30,15));
        entries.add(new Entry(40,50));

        //3.将entries设置给LineDataSet数据集
        LineDataSet dataSet = new LineDataSet(entries, "Label");

        //4.将上面创建的LineDataSet对象设置给LineData
        LineData lineData = new LineData(dataSet);

        //5.把lineData设置给lineChart就可以显示出来折线图了，就像把adapter设置给listview一样
        mLineChart.setData(lineData);

        //mTableList=(RecyclerView)view.findViewById(R.id.table_list);
       // mTableList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));*/
        /**
         * 数据源加载
         */
/*
//设置x轴的数据
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            xValues.add("" + i);
        }
        LineChart mLineChart = (LineChart) view.findViewById(R.id.lineChart);

        //设置y轴的数据
        ArrayList<Entry> yValue = new ArrayList<>();
        yValue.add(new Entry(13, 1));
        yValue.add(new Entry(6, 2));
        yValue.add(new Entry(3, 3));
        yValue.add(new Entry(7, 4));
        yValue.add(new Entry(2, 5));
        yValue.add(new Entry(5, 6));
        yValue.add(new Entry(12, 7));
        //设置折线的名称
        LineChartManager.setLineName("当月值");
*/

/*
        dataList=new ArrayList<ChartItem>();
        dataList.add(new ChartItem(("生活"),R.drawable.ic_people_black_24dp));
        dataList.add(new ChartItem(("工作"),R.drawable.ic_business_center_black_24dp));
        dataList.add(new ChartItem(("学习"),R.drawable.ic_school_black_24dp));
        *//**
         * 适配器加载
         *//*
        mAdapter = new ChartAdapter(dataList);
        mAdapter.setOnItemClickListener(new ChartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click " + dataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //动画效果*//*
*//*        mTableList.setItemAnimator(new DefaultItemAnimator());*//*
        mTableList.setAdapter(mAdapter);*/

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
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis();
        //setHasLines(true),设定是否有网格线
        Axis axisY = new Axis().setHasLines(false);
        //为两个坐标系设定名称
        axisX.setName("日期 ");
        axisY.setName("分值");
        //设置图标所在位置
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        //将数据添加到View中
        chartView.setLineChartData(data);
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

