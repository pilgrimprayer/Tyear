package com.example.hxx.tyear.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.statisticsFragmentModel.ChartItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxx on 2017/10/8.
 */

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder>  {

    private List<ChartItem> mList;  //用户列表


    public ChartAdapter(List<ChartItem> list){

        this.mList = list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sta_report_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }
    //内部类ViewHolder 传入构造参数View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textTitle;
   /*     public final TextView textContent;*/
        public LineChart mLineChart;

   //     public final ImageView imageView;
        public final View mView;
        public ChartItem mItem;
        public  ViewHolder(View view) {
            super(view);
            mView = view;

            mLineChart = (LineChart) view.findViewById(R.id.lineChart);
            textTitle = (TextView) view.findViewById(R.id.chart_text_title);//加载每个子项里的内容-题目！！
          //  textContent = (TextView) view.findViewById(R.id.report_content);//加载每个子项里的内容-题目！！
     //      imageView=(ImageView) view.findViewById(R.id.reportImage);//加载每个子项里的内容-内容！！

        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //对子项目赋值 子项滚动屏幕时执行

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
        holder.mLineChart.setData(lineData);


        holder.mItem = mList.get(position);

      /*
        holder.mLineChart.setDrawGridBackground(false);

        Description description = new Description();
        description.setText("what");
        holder.mLineChart.setDescription(description);
        // 没有数据的时候，显示“暂无数据”
        holder.mLineChart.setNoDataText("暂无数据");

        // 不显示表格颜色
        holder.mLineChart.setDrawGridBackground(false);
        // 不显示y轴右边的值
        holder.mLineChart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = holder.mLineChart.getLegend();
        legend.setEnabled(false);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        holder.mLineChart.setExtraLeftOffset(-15);
        XAxis xAxis = holder.mLineChart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        // 设置x轴数据偏移量
        xAxis.setYOffset(-12);

        YAxis yAxis = holder.mLineChart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(30);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);  holder.mLineChart.invalidate();
*/
        //Matrix matrix = new Matrix();
        // x轴缩放1.5倍
        //matrix.postScale(1.5f, 1f);
        // 在图表动画显示之前进行缩放
        //chart.getViewPortHandler().refresh(matrix, chart, false);
    // holder.mLineChart.animateXY(3000,3000);


 /*       holder.textTitle.setText(mList.get(position).getName());
        holder.textContent.setText(mList.get(position).getSum()+"");*/
       // holder.imageView.setImageResource(mList.get(position).getimageID());
        //ChartItem grid = mList.get(position);//返回指定数组元素
/*        holder.textView.setText(grid.getSubject());
        holder.contentView.setText(grid.getContent());*/
        //holder.textView.setText(titles[position]); //对textview控件设置内容 ：数组title
        //holder.contentView.setText("sd");

        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public static void setChartData(LineChart chart, List<Entry> values) {
        LineDataSet lineDataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "");
            // 设置曲线颜色
            lineDataSet.setColor(Color.parseColor("#FFFFFF"));
            // 设置平滑曲线
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 不显示坐标点的小圆点
            lineDataSet.setDrawCircles(false);
            // 不显示坐标点的数据
            lineDataSet.setDrawValues(false);
            // 不显示定位线
            lineDataSet.setHighlightEnabled(false);

            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.invalidate();
        }
    }



}
