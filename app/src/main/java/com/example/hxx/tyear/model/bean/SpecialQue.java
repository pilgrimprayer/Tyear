package com.example.hxx.tyear.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by hxx on 2017/10/13.
 */
@DatabaseTable(tableName = "special_que")
public class SpecialQue extends BaseEntity{

    @DatabaseField(generatedId = true)//主键
    private int id;

    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "content")
    private String content;



    @DatabaseField(canBeNull = false, foreign = true)
    private Diary diary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /////////////////
    public String getSitlet() {
        return title;
    }

    public void setTitlet(String subject) {
        this.title = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    @Override
    public String toString() {
        return "specialQuestion{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +

                '}';
    }
}
