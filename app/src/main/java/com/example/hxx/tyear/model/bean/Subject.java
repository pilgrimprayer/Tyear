package com.example.hxx.tyear.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 主题库
 * Created by tangqi on 16/5/18.
 */
@DatabaseTable(tableName = "subject")
/*
    数据库ORM框架
    首先在User类上添加@DatabaseTable(tableName = "tb_user")，标明这是数据库中的一张表，标明为tb_user*/
public class Subject extends BaseEntity {


    /*    然后分别在属性上添加@DatabaseField(columnName = "name") ，columnName的值为该字段在数据中的列名
    @DatabaseField(generatedId = true) ，generatedId 表示id为主键且自动生成*/
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "subject")
    private String subject;

    @DatabaseField(columnName = "reserve")
    private String reserve;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String question) {
        this.subject = question;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", reserve='" + reserve + '\'' +
                '}';
    }
}
