package com.example.hxx.tyear.statisticsFragmentModel;

/**
 * Created by hxx on 2017/11/24.
 */

public class ChartPointItem {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;
    public ChartPointItem(String date, String content) {
        this.date=date;
        this.content=content;
    }
}
