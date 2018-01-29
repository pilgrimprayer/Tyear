package com.example.hxx.tyear.statisticsFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxx on 2017/10/8.
 */
//后期需要存入数据库 因为要做历史记录报告对吧
public class TableGroupItem {
    TableGroupItem tableNameItem;
    int id;
    private String label;
    private String question;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    List<String>  contens=new ArrayList<String>();
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public List<String> getContens() {
        return  contens;
    }

    public void setContens(String conten) {
         contens.add(conten);
    }

    int sum;
   boolean Is_attention=true;

    public String getChildTxt() {
        return childTxt;
    }

    public void setChildTxt(String childTxt) {
        this.childTxt = childTxt;
    }

    private String childTxt;
    public TableGroupItem(String label,String question,String date){
        this.label=label;
        this.question=question;
        this.date=date;
    }



    public boolean  getIs_attention(){
        return Is_attention;
    }
    public void  SetIs_attention(boolean i){
        this.Is_attention=i;
    }
    public TableGroupItem getT () {
        return tableNameItem;
    }

    public void setT (TableGroupItem t) {
        this.tableNameItem = t;
    }


    public int getSum () {
        return sum;
    }

    public void setSum(int id) {
        this.sum = sum;
    }
}
