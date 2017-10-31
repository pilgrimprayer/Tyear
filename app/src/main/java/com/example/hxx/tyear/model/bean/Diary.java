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
    private ForeignCollection<BaseQue> baseQues;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<SpecialQue> specialQue;


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

    public ArrayList<BaseQue> getBaseQues() {
        ArrayList<BaseQue> list = new ArrayList<>();
        if (baseQues != null) {
            list.addAll(baseQues);
            return list;
        }

        return list;
    }

    public ArrayList<SpecialQue> getSpecialQue() {
        ArrayList<SpecialQue> list = new ArrayList<>();
        if (specialQue != null) {
            list.addAll(specialQue);
            return list;
        }

        return list;
    }

/*    public void setSpecialQue(SpecialQue specialQue) {
        this.specialQue = specialQue;
    }*/

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", date=" + date +
                ", modifyTime=" + modifyTime +
                      ", specialQue='" + getSpecialQue() + '\'' +
                ", baseQues=" + getBaseQues() +
                '}';

    }

}
