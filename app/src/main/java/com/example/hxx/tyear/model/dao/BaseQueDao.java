package com.example.hxx.tyear.model.dao;

import android.content.Context;

import com.example.hxx.tyear.model.bean.BaseQue;

import java.sql.SQLException;
import java.util.List;

public class BaseQueDao extends DaoImpl<BaseQue> {

    public BaseQueDao(Context context) {
        super(context, BaseQue.class);
    }
    public BaseQue queryLast() {
        try {
            //select * from tableName where id=            (select max(id) from tablename);
            //return (Diary) mDao.queryBuilder().where().eq("date", date).queryForFirst();
            return (BaseQue)  mDao.queryBuilder().orderBy("id", false).queryForFirst();//降序 最后一条为最新一条
            //queryBuilder创建一个查询生成器:进行复杂查询

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<BaseQue> queryAllDin() {
        try {

           //mDao.queryBuilder().selectColumns("City").distinct().query();
          //  mDao.queryBuilder().selectColumns("title").distinct().query();//只显示题目其他不会显示
            return    mDao.queryBuilder().selectColumns("title","type").distinct().query();//降序 最后一条为最新一条
            //queryBuilder创建一个查询生成器:进行复杂查询

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
