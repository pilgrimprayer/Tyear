package com.example.hxx.tyear.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hxx on 2017/10/13.
 */

//标签 名称 所属基本问题 
    //待考虑 建立时间
@DatabaseTable(tableName = "label")
public class Label extends BaseEntity{

    @DatabaseField(generatedId = true)//主键
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(canBeNull = true, foreign = true)
    private BaseQue baseQue;





    public BaseQue getBaseQue() {
        return baseQue;
    }

    public void setBaseQue(BaseQue baseQue) {
        this.baseQue = baseQue;
    }






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
