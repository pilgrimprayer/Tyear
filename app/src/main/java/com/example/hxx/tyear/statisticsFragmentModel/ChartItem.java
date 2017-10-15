package com.example.hxx.tyear.statisticsFragmentModel;

/**
 * Created by hxx on 2017/10/8.
 */

public class ChartItem {
    ChartItem ChartItem;
    int id;
    private String name;
    private int imageID;
    boolean Is_attention=true;


    public ChartItem(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public int getimageID () {
        return imageID;
    }

    public void setimageID(int id) {
        this.imageID = imageID;
    }
}
