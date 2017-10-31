package com.example.hxx.tyear.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hxx.tyear.model.bean.BaseQue;
import com.example.hxx.tyear.model.bean.Content;
import com.example.hxx.tyear.model.bean.Diary;
import com.example.hxx.tyear.model.bean.Label;
import com.example.hxx.tyear.model.bean.SpecialQue;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangqi on 16/5/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
//用于创建数据库，此实例只用搞一个

    public static final String DB_NAME = "grid_diary.db";
    public static final int DB_VERSION = 13;
    private static DatabaseHelper mInstance;
    private Map<String, Dao> mDaoMaps = new HashMap<>();

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 单例获取Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        /*、为了线程安全，需要在方法前提供一个线程安全关键字synchronized
14      *  如果一个调用时，另一个就不允许调用
15 */
        /* 3、在方法中判断如果已经存在就不再创建，如果不存在再创建
12 *  这样保证永远只有一个DataBaseHelper对象*/
        if (mInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (mInstance == null)
                    mInstance = new DatabaseHelper(context);
            }
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            //数据库里创建表

            TableUtils.createTable(connectionSource, Diary.class);
            TableUtils.createTable(connectionSource, BaseQue.class);
            TableUtils.createTable(connectionSource, SpecialQue.class);
            TableUtils.createTable(connectionSource, Label.class);
            TableUtils.createTable(connectionSource, Content.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int
            i, int i1) {
        /*在数据库的版本发生变化时会被调用， 一般在软件升级时才需改变版本号，而数据库的版本是由程序员控制的，
        假设数据库现在的 版本是1，由于业务的变更，修改了数据库表结构，这时候就需要升级软件，升级软件时希望 更新用户手机里的数据库表结构，
        为了实现这一目的，可以把原来的数据库版本设置为2 或者其他与旧版本号不同的数字即可！*/
        try {
          //删除表

            TableUtils.dropTable(connectionSource, Diary.class,true);
            TableUtils.dropTable(connectionSource, BaseQue.class,true);
            TableUtils.dropTable(connectionSource, SpecialQue.class,true);
            TableUtils.dropTable(connectionSource, Label.class,true);
            TableUtils.dropTable(connectionSource, Content.class,true);

            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public SQLiteDatabase getWritableDatabase() {
//        super.getWritableDatabase();
//        return SQLiteDatabase.openDatabase(DiaryApplication.getInstance().getDbPath(),
//                null,
//                SQLiteDatabase.OPEN_READWRITE);
//    }
//
//    @Override
//    public SQLiteDatabase getReadableDatabase() {
//        super.getReadableDatabase();
//        return SQLiteDatabase.openDatabase(DiaryApplication.getInstance().getDbPath(),
//                null,
//                SQLiteDatabase.OPEN_READONLY);
//    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao<Class, Integer> dao = null;
        String className = clazz.getSimpleName();
        if (mDaoMaps.containsKey(className)) {
            dao = mDaoMaps.get(className);
        }

        if (dao == null) {
            dao = super.getDao(clazz);
            mDaoMaps.put(className, dao);
        }

        return dao;
    }

    @Override
    public void close() {
        super.close();

        for (String key : mDaoMaps.keySet()) {
            Dao dao = mDaoMaps.get(key);
            dao = null;
        }
    }

    public void close(Class clazz) {
        super.close();

        String className = clazz.getSimpleName();
        if (mDaoMaps.containsKey(className)) {
            Dao dao = mDaoMaps.get(className);
            dao = null;
        }
    }
}
