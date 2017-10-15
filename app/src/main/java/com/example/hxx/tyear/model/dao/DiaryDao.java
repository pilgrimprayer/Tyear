package com.example.hxx.tyear.model.dao;

import android.content.Context;

import com.example.hxx.tyear.model.bean.Diary;

import java.sql.SQLException;




public class DiaryDao extends DaoImpl<Diary> {//DaoImpl<Diary> 说明值对diary的操作 产生关联
//通过DAO对象对数据库数据操作
    //一个 表单对应一个DAO
    public DiaryDao(Context context) {
        super(context, Diary.class);
    }
//根据日期返回日记：用于点击日历时数据传输

    public Diary query(String date) {
        try {
            return (Diary) mDao.queryBuilder().where().eq("date", date).queryForFirst();
            //queryBuilder创建一个查询生成器:进行复杂查询

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
