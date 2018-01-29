package com.example.hxx.tyear.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.statisticsFragmentModel.ChartItem;
import com.example.hxx.tyear.statisticsFragmentModel.ChartPointItem;
import com.example.hxx.tyear.statisticsFragmentModel.PieData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

import static com.example.hxx.tyear.R.id.pie_chart;


/**
 * Created by hxx on 2017/10/8.
 */

public class ChartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private static final int TYPE_LINE = 0;//编辑框
    private static final int TYPE_PIE = 1;//按钮



    String[] stateChar = {"报警", "故障", "离线", "正常"};
    int[] dataPie = {21, 20, 9, 2};
    String[] date = {"11-20","11-21","11-22","11-23","11-24","11-25","11-26"};//X轴的标注
    //todo//date
    String[] datey = {"开心","不开心","很普通","桑心"};//X轴的标注


//    private PieChartData pieChardata;
    int[] score= {50,42,90,33,10,74,22};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<Integer> color ;

    private List<AxisValue> mAxisXValues ;
    private List<AxisValue> mAxisYValues ;
    private List<ChartItem> dataList;  //用户列表
    private static Context mContext;

    public ChartAdapter(List<ChartItem> list,Context mContext){

        this.dataList = list;
        this.mContext=mContext;
//        colorData[0]=mContext.getResources().getColor(R.color.colorPrimary);
//        colorData[1]=mContext.getResources().getColor(R.color.pie_Chart2);
//        colorData[1]=mContext.getResources().getColor(R.color.pie_Chart3);
//        colorData[1]=mContext.getResources().getColor(R.color.);
    }



    @Override
    public   RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LINE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_chart_line_item, parent, false);
            LineViewHolder viewHolder = new LineViewHolder(view);
            return viewHolder;
        }
        if (viewType == TYPE_PIE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_chart_pie_item, parent, false);
            PieViewHolder viewHolder = new PieViewHolder(view);
            return viewHolder;
        }
        return null;
    }



    //内部类ViewHolder 传入构造参数View
 //   public class RadioViewHolder extends RecyclerView.ViewHolder {
    public static class LineViewHolder extends RecyclerView.ViewHolder {
        public final TextView textTitle;

        public LineChartView chartView;

        public final View mView;
        public ChartItem mItem;
        public  LineViewHolder(View view) {
            super(view);
            mView = view;
            chartView = (LineChartView)view.findViewById(R.id.line_chart);

            textTitle = (TextView) view.findViewById(R.id.chart_line_title);//加载每个子项里的内容-题目！！
        }
    }
    public static class PieViewHolder extends RecyclerView.ViewHolder {
        public final TextView textTitle;

        public PieChartView chartView;
        private PieChartData pieChardata;
        public final View mView;
        public ChartItem mItem;
        private int[] colorData = {
                Color.parseColor("#FFE3B7"),//白色
                Color.parseColor("#840404"),//暗红
                Color.parseColor("#CB0101"),//明红
                Color.parseColor("#FFAF00")};//黄色

        private String[] stateChar = {"报警", "故障", "离线", "正常"};
        private int[] dataPie = {21, 20, 9, 2};
        public  PieViewHolder(View view) {
            super(view);
            mView = view;
            chartView = (PieChartView)view.findViewById(pie_chart);

            textTitle = (TextView) view.findViewById(R.id.chart_pie_title);//加载每个子项里的内容-题目！！
            //颜色

            pieChardata = new PieChartData();
            pieChardata.setHasLabels(true);//显示表情
            pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
            pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
            pieChardata.setHasCenterCircle(true);//是否是环形显示

            List<SliceValue> values = new ArrayList<SliceValue>();
            for (int i = 0; i < dataPie.length; ++i) {
                SliceValue sliceValue = new SliceValue((float) dataPie[i], colorData[i]);
                values.add(sliceValue);
            }
            pieChardata.setValues(values);//填充数据
            pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
            pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别
            
            chartView.setOnValueTouchListener(
             new PieChartOnValueSelectListener() {

                @Override
                public void onValueDeselected() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onValueSelected(int arg0, SliceValue value) {
                    // TODO Auto-generated method stub
                    pieChardata.setCenterText1(stateChar[arg0]);//
                    pieChardata.setCenterText1Color(colorData[arg0]);
                    pieChardata.setCenterText1FontSize(10);//块块上面的字

                    pieChardata.setCenterText2(value.getValue() + "（" + "%"+ ")");//最中间
                  //  Toast.makeText(mContext, "Selected: " + value.getValue(), Toast.LENGTH_SHORT).show();
                }
            });//设置点击事件监听
        }

    }
    @Override
    public int getItemViewType(int position) {

        if (0 == dataList.get(position).getType()) {
            return TYPE_LINE;//
        } else if (1 == dataList.get(position).getType()) {
            return TYPE_PIE;//

        }
        else {


            return 0;
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //对子项目赋值 子项滚动屏幕时执行
       // holder. chartView.setOnValueTouchListener(listener);//每个点设置监听
        if (holder instanceof LineViewHolder) {
            //获取纵坐标 从数据源中取
            ArrayList<String> dataY = new ArrayList<String>();
            //为了加点准备，y
            Map<String, Integer> contentMap = new HashMap<>();//使y轴上String与Int成映射关系
            for(int i=0;i<dataList.get(position).getContentList().size();i++) {
                String content=dataList.get(position).getContentList().get(i).getName();
                if(content.length()>5){
                    //
                    // String str1 = "我java";
//                    StringBuilder sb = new StringBuilder(content);//构造一个StringBuilder对象
//                    sb.insert(7, "\n");//在指定的位置1，插入指定的字符串
//                    content = sb.toString();
                    //System.out.println(str1);
                    content=   content.substring(0,3)+"…";
                }
                dataY.add(content);//为了标签名附上准备
                contentMap.put(dataList.get(position).getContentList().get(i).getName(),i);
            }
            //todo//??map明明按顺序put但其实它是乱放的 是否也与写日记模块那个有关
            //加点准备,x
            Map<String, Integer> dateMap = new HashMap<>();//使y轴上String与Int成映射关系
            for(int i=0;i<date.length;i++) {

                dateMap.put(date[i],i);
            }
            //准备完毕 加载七天的点
            List<PointValue> values = new ArrayList<>();
          int i=0;
            List<ChartPointItem> chartPointItemList= dataList.get(position).getChartPointItems();
            Collections.sort(chartPointItemList, new SortByDate());
            for(ChartPointItem chartPointItem:chartPointItemList){
                float x,y;
                y=(float)contentMap.get(chartPointItem.getContent());
               x=(float)dateMap.get(chartPointItem.getDate());
                PointValue pointValue=new PointValue(x, y);
                pointValue.setLabel(chartPointItem.getContent());//点击点出现的标签信息
                values.add(pointValue);
            }

            Line line = new Line(values).setColor(mContext.getResources().getColor(R.color.colorPrimary)).setCubic(true);
            //一条折线加入折线集合
            line.setHasLabelsOnlyForSelected(true);
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
//        axisX.setName("日期 ");
//        axisY.setName("分值");
            //为两个坐标轴数值点加上名称
            getAxisXLables();//获取坐标名称
            axisX.setValues(mAxisXValues);  //填充X轴的坐标名称

            getAxisYLables(dataY);
            axisY.setMaxLabelChars(5);
            axisY.setValues(mAxisYValues);  //填充X轴的坐标名称
          //  axisY.setInside(true);


            //最多几个X坐标轴
            //axisX.setMaxLabelChars(7);
            //设置图标所在位置
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
            //将数据添加到View中
            //设置行为属性，支持缩放、滑动以及平移

            ((LineViewHolder) holder).chartView.setInteractive(true);
            ((LineViewHolder) holder).chartView.setZoomType(ZoomType.HORIZONTAL);
            ((LineViewHolder) holder).chartView.setMaxZoom((float) 2);//最大方法比例 双击会放大
            ((LineViewHolder) holder).chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);//设置是否允许图表在父容器中滑动。
            ((LineViewHolder) holder).chartView.setLineChartData(data);
            ((LineViewHolder) holder).chartView.startDataAnimation();

            ((LineViewHolder) holder).textTitle.setText(dataList.get(position).getName());//标题 ==问题
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                ((LineViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                    }
                });
            }
        }
        if (holder instanceof PieViewHolder) {

            ((PieViewHolder) holder).textTitle.setText(dataList.get(position).getName());//标题 ==问题
            ((PieViewHolder) holder). pieChardata = new PieChartData();
            ((PieViewHolder) holder). pieChardata.setHasLabels(true);//显示标签
            ((PieViewHolder) holder). pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
            ((PieViewHolder) holder). pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
            ((PieViewHolder) holder). pieChardata.setHasCenterCircle(true);//是否是环形显示

            //加载数据源
            ChartItem chartItem=dataList.get(position);
            final Map<String,Integer > checkMap = chartItem.getCheckMap();
            List<PieData> pieDataList = chartItem.getPieDataList();
            List<SliceValue> values = new ArrayList<SliceValue>();
            for(PieData pieData:pieDataList){
                SliceValue sliceValue = new SliceValue((float)pieData.getSum(), pieData.getColor());//数据和颜色
                sliceValue.setLabel(pieData.getName());
                values.add(sliceValue);
            }
            ((PieViewHolder) holder). pieChardata.setCenterText1(" 完成度 ");//
            ((PieViewHolder) holder). pieChardata.setCenterText1Color(Color.BLACK);
            ((PieViewHolder) holder). pieChardata.setCenterText1FontSize(19);//块块上面的字??
            DecimalFormat decimalFormat=new DecimalFormat(".00");
            String p=decimalFormat.format(dataList.get(position).getRate()*100);

             ((PieViewHolder) holder). pieChardata.setCenterText2(p+"%");
//            int j=0,doneSum=0,undoneSum;
//            for (Map.Entry<String,Integer> entry: checkMap.entrySet()) {
//                SliceValue sliceValue = new SliceValue((float)entry.getValue(), colorData[j]);//数据和颜色
//                values.add(sliceValue);
//                doneSum+=entry.getValue();
//                j++;
//            }
//            //最后一个是未完成的
//            undoneSum= chartItem.getContentList().size()*7-doneSum;//todo//有选日期的功能后总数要变
//            SliceValue sliceValue = new SliceValue((float)undoneSum, colorData[j]);//数据和颜色
//            values.add(sliceValue);
//            for (int i = 0; i < dataPie.length; ++i) {
//                SliceValue sliceValue = new SliceValue((float) dataPie[i], colorData[i]);//数据和颜色
//                values.add(sliceValue);
//            }
              ((PieViewHolder) holder). pieChardata.setValues(values);//填充数据
              ((PieViewHolder) holder). pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
              ((PieViewHolder) holder). pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别


            ((PieViewHolder) holder). chartView.setOnValueTouchListener(
                    new PieChartOnValueSelectListener() {

                        @Override
                        public void onValueDeselected() {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onValueSelected(int arg0, SliceValue value) {
                            // TODO Auto-generated method stub

                              ((PieViewHolder) holder). pieChardata.setCenterText1( dataList.get(position).getPieDataList().get(arg0).getName() );//
                              ((PieViewHolder) holder). pieChardata.setCenterText1Color(dataList.get(position).getPieDataList().get(arg0).getColor());
                              ((PieViewHolder) holder). pieChardata.setCenterText1FontSize(19);//块块上面的字??
                            ((PieViewHolder) holder).  pieChardata.setCenterText2FontSize(12);
                            DecimalFormat decimalFormat=new DecimalFormat(".00");
                            String p=decimalFormat.format(dataList.get(position).getPieDataList().get(arg0).getRate()*100);
                              ((PieViewHolder) holder). pieChardata.setCenterText2((int)value.getValue() + "（" +p+"%"+ ")");//最中间
                            //  Toast.makeText(mContext, "Selected: " + value.getValue(), Toast.LENGTH_SHORT).show();
                        }
                    });//设置点击事件监听
            ((PieViewHolder) holder).chartView.setPieChartData(   ((PieViewHolder) holder).pieChardata);
            ((PieViewHolder) holder).chartView.setValueSelectionEnabled(true);//选择饼图某一块变大
            ((PieViewHolder) holder).chartView.setAlpha(0.9f);//设置透明度
            ((PieViewHolder) holder).chartView.setCircleFillRatio(1f);//设置饼图大小


        }
    }

    private void getAxisXLables(){
     mAxisXValues = new ArrayList<AxisValue>();

        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }
    private void getAxisYLables( ArrayList<String> yData){

        mAxisYValues = new ArrayList<AxisValue>();
        for (int i = 0; i < yData.size(); i++) {
            mAxisYValues.add(new AxisValue(i).setLabel(yData.get(i)));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    private class SortByDate implements Comparator {
        public int compare(Object o1, Object o2) {
            ChartPointItem s1 = (ChartPointItem) o1;
            ChartPointItem s2 = (ChartPointItem) o2;
            return s1.getDate().compareTo(s2.getDate());
        }
    }
}
