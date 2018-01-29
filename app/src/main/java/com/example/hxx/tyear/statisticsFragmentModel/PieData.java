package com.example.hxx.tyear.statisticsFragmentModel;

/**
 * Created by hxx on 2017/11/25.
 */

public   class PieData{
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    float rate;
    int sum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        color = color;
    }

    int color;
    public PieData(String name,int sum,int color) {
        this.name=name;
        this.sum=sum;
        this.color=color;
                    }
}