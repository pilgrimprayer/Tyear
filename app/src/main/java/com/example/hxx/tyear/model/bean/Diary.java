package com.example.hxx.tyear.model.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by hxx on 2017/10/13.
 */

@DatabaseTable(tableName = "diary")//表名
public class Diary extends BaseEntity {

    @DatabaseField(generatedId = true)//主键
    private int id;

    // 创建日期
    @DatabaseField(columnName = "date")//riqi
    private String date;

    @DatabaseField(columnName = "modifyTime")
    private long modifyTime;


    // 问题卡片
    @ForeignCollectionField(eager = false)
    private ForeignCollection<BaseRadioQue> baseRadioQues;

    @ForeignCollectionField(eager = false)
    private SpecialQue specialQue;


    public int getId() {
        return id;
    }   // 对应字段的set 和 get 方法

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public void setModifyDate(long modifyDate) {
        this.modifyTime = modifyDate;
    }

    public long getModifyDate() {
        return modifyTime;
    }

    public ArrayList<BaseRadioQue> getBaseRadioQues() {
        ArrayList<BaseRadioQue> list = new ArrayList<>();
        if (baseRadioQues != null) {
            list.addAll(baseRadioQues);
            return list;
        }

        return list;
    }

    public SpecialQue getSpecialQue() {
        return specialQue;
    }

    public void setSpecialQue(SpecialQue specialQue) {
        this.specialQue = specialQue;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", date=" + date +
                ", modifyTime=" + modifyTime +
                      ", specialQue='" + getSpecialQue() + '\'' +
                ", baseRadioQues=" + getBaseRadioQues() +
                '}';

    }

}
