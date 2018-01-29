package com.example.hxx.tyear.model.dao;

import android.content.Context;

import com.example.hxx.tyear.model.bean.Content;

import java.sql.SQLException;


public class ContentDao extends DaoImpl<Content> {

    public ContentDao(Context context) {
        super(context, Content.class);
    }
    public Content query(int contentId) {
        try {
            return (Content) mDao.queryBuilder().where().eq("id", contentId).queryForFirst();
            //queryBuilder创建一个查询生成器:进行复杂查询

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Content queryByQue(int qId) {
        try {
            return (Content) mDao.queryBuilder().where().eq("id", qId).queryForFirst();
            //queryBuilder创建一个查询生成器:进行复杂查询

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
