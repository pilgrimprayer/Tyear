package com.example.hxx.tyear.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hxx on 2017/10/13.
 */

//标签 名称 所属基本问题 
    //待考虑 建立时间
@DatabaseTable(tableName = "content")
public class Content extends BaseEntity{

    @DatabaseField(generatedId = true)//主键
    private int id;

    @DatabaseField(columnName = "name")
    private String name;


    public BaseRadioQue getBaseRadioQue() {
        return baseRadioQue;
    }

    public void setBaseRadioQue(BaseRadioQue baseRadioQue) {
        this.baseRadioQue = baseRadioQue;
    }

    public BaseCheckQue getBaseCheckQue() {
        return baseCheckQue;
    }

    public void setBaseCheckQue(BaseCheckQue baseCheckQue) {
        this.baseCheckQue = baseCheckQue;
    }

    @DatabaseField(canBeNull = true, foreign = true)
    private BaseRadioQue baseRadioQue;


    @DatabaseField(canBeNull = true, foreign = true)
    private BaseCheckQue baseCheckQue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String subject) {
        this.name = subject;
    }




    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
          
                '}';
    }
}
