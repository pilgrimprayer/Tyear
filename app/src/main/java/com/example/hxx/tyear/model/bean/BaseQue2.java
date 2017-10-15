package com.example.hxx.tyear.model.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by hxx on 2017/10/13.
 */
@DatabaseTable(tableName = "base_que")
public class BaseQue2 extends BaseEntity{

    @DatabaseField(generatedId = true)//主键
    private int id;

    @DatabaseField(columnName = "title")
    private String title;
//内容
    //？？多选 单选 怎么设定
    //？？！设定成数组
/*    @DatabaseField(columnName = "content")
    private String content;*/
    @ForeignCollectionField(eager = false)
    private ForeignCollection<Content> content;

//类型
    @DatabaseField(columnName = "type")
    private int  Type;
    //012 文本 单选 多选


//监控开关
    @DatabaseField(columnName = "IsStatistics")
    private boolean  IsStatistics;

//标签

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Label> label;
//所属日记
    @DatabaseField(canBeNull = false, foreign = true)
    private Diary diary;

    public boolean isStatistics() {
        return IsStatistics;
    }

    public void setStatistics(boolean statistics) {
        IsStatistics = statistics;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String subject) {
        this.title = subject;
    }
/*
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/

    public ArrayList<Content> getContent() {
        ArrayList<Content> list = new ArrayList<>();
        if (content != null) {
            list.addAll(content);
            return list;
        }

        return list;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
    @Override
    public String toString() {
        return "BasicalQuestion{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +

                '}';
    }
}
