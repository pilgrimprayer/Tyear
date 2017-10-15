package com.example.hxx.tyear.statisticsFragmentModel;

/**
 * Created by hxx on 2017/10/8.
 */

public class TableNameItem {
    TableNameItem tableNameItem;
    int id;
    private String name;
    int sum;
   boolean Is_attention=true;
    public TableNameItem(){}


    public TableNameItem ( String name,int sum) {
        this.name = name;
        this.sum = sum;
    }
    public boolean  getIs_attention(){
        return Is_attention;
    }
    public void  SetIs_attention(boolean i){
        this.Is_attention=i;
    }
    public TableNameItem getT () {
        return tableNameItem;
    }

    public void setT (TableNameItem t) {
        this.tableNameItem = t;
    }

    public String getName () {
        return name;
    }

    public void setName (int id) {
        this.name = name;
    }

    public int getSum () {
        return sum;
    }

    public void setSum(int id) {
        this.sum = sum;
    }
}
