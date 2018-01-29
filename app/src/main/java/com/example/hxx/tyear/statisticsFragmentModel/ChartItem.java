package com.example.hxx.tyear.statisticsFragmentModel;

import com.example.hxx.tyear.model.bean.Content;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxx on 2017/10/8.
 */

public class ChartItem {
   // ChartItem ChartItem;
    int id;
    PieData pieData;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    float rate;

    public List<PieData> getPieDataList() {
        return pieDataList;
    }

    public void setPieDataList(List<PieData> pieDataList) {
        this.pieDataList = pieDataList;
    }
    public void addPieDataList(PieData pieDataList) {
        this.pieDataList .add( pieDataList);
    }


    List<PieData> pieDataList = new ArrayList<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private int imageID;

    public Map<String, Integer> getCheckMap() {
        return checkMap;
    }

    public void setCheckMap(Map<String, Integer> checkMap) {
        this.checkMap = checkMap;
    }

    Map<String,Integer > checkMap = new LinkedHashMap<String,Integer >();
    public List<ChartPointItem> getChartPointItems() {
        return chartPointItems;
    }

    public void setChartPointItems(List<ChartPointItem> chartPointItems) {
        this.chartPointItems = chartPointItems;
    }
    public void addChartPointItems(ChartPointItem chartPointItems) {
        this.chartPointItems .add(chartPointItems);
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
    public void addContents(String content) {
        this.contents.add(content);
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    List<Content> contentList;
    List<String> contents;
    List<ChartPointItem> chartPointItems;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    int type;
    boolean Is_attention=true;
//x轴-日期
    //y轴 问题
    //问题类型 问题选项 你的真实选项

    public ChartItem(String name, int type,List<ChartPointItem> chartPointItems,List<Content> contentList) {
        this.name = name;

        this.type=type;
        this.chartPointItems = chartPointItems;
        this.contentList=contentList;
    }
    public ChartItem(String name, int type,   Map<String,Integer > checkMap, List<PieData> pieDataList) {//PIE
        this.name = name;

        this.type=type;
       this.checkMap=checkMap;
        this.pieDataList=pieDataList;

    }


    public int getimageID () {
        return imageID;
    }

    public void setimageID(int id) {
        this.imageID = imageID;
    }
}
