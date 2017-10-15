package com.example.hxx.tyear.model;

import android.content.Context;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.model.bean.BaseCheckQue;
import com.example.hxx.tyear.model.bean.BaseRadioQue;
import com.example.hxx.tyear.model.bean.BaseTextQue;
import com.example.hxx.tyear.model.bean.Content;
import com.example.hxx.tyear.model.dao.BaseCheckQueDao;
import com.example.hxx.tyear.model.dao.BaseRadioQueDao;
import com.example.hxx.tyear.model.dao.BaseTextQueDao;
import com.example.hxx.tyear.model.dao.ContentDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangqi on 16/5/19.
 */
public class BaseQueManager {
    private Context mContext;

    public BaseQueManager(Context context) {
        this.mContext = context;
    }

    public void init() {

        String[] textQuestionArray = mContext.getResources().getStringArray(R.array.radio_ansewer_list);//从本环境 的R文档里获得资源
//        if (!SpfUtils.getBoolean(mContext, SpfConfig.IS_INIT_BaseQue, false)) {
        //文本库建立
        List<BaseTextQue> textQueList = getTextQueList();//生成文本问题列表
        List<BaseRadioQue> radioQueList = getRadioQueList();//生成单选问题列表
        List<BaseCheckQue> checkQueList = getCheckQueList();//生成单选问题列表
        //todo//耦合
        //文本
        BaseTextQueDao baseTextQueDao = new BaseTextQueDao(mContext);//创建数据库操作对象-本环境下mContex 关联
       if(baseTextQueDao.queryAll()==null){
           baseTextQueDao.delelteAll();//删除数据库所有内容
           baseTextQueDao.insert(textQueList);//将问题们添加进数据库表格里
       }
       //单选
        BaseRadioQueDao baseRadioQueDao = new BaseRadioQueDao(mContext);//创建数据库操作对象-本环境下mContex 关联
        if(baseRadioQueDao.queryAll()==null){
            baseRadioQueDao.delelteAll();//删除数据库所有内容
            baseRadioQueDao.insert(radioQueList);//将问题们添加进数据库表格里
        }
       //多选
        BaseCheckQueDao baseCheckQueDao = new BaseCheckQueDao(mContext);//创建数据库操作对象-本环境下mContex 关联
        if(baseCheckQueDao.queryAll()==null){
            baseCheckQueDao.delelteAll();//删除数据库所有内容
            baseCheckQueDao.insert(checkQueList);//将问题们添加进数据库表格里
        }



/**
 * 所有单选问题绑定
 */
//单选
        int RadioAnswerResourceList[]={
                R.array.radio_exercise_answer1,
                R.array.radio_cost_answer2,
                R.array.radio_rest_answer3,
                R.array.radio_status_answer4

        };
        int i=0;
        for (BaseRadioQue baseRadioQue : radioQueList) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。

            RadioAnswerToQue(RadioAnswerResourceList[i++],baseRadioQue);
        }

        /**
         * 所有多选问题绑定
         */

        int CheckAnswerResourceList[]={
                R.array.check_Englishe_answer1,
                R.array.check_cs_answer2,


        };
        int j=0;
        for (BaseCheckQue baseCheckQue : checkQueList) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。

            CheckAnswerToQue(CheckAnswerResourceList[j++],baseCheckQue);
        }

    }

    /**
     * 一个多选问题绑定
     * @param answeRresource
     * @param baseCheckQue
     */

    public void CheckAnswerToQue(int answeRresource, BaseCheckQue baseCheckQue){

        String[] AnswerArray = mContext.getResources().getStringArray(answeRresource);//从本环境 的R文档里获得资源
        List<Content> AnswerList = getContent(AnswerArray);//回答问题列表


        for (Content content : AnswerList) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。
            //一个问题对应生成一个对象 BaseRadioQue
            content.setBaseCheckQue(baseCheckQue);//遍历radio问题数组


        }
        ContentDao contentDao = new ContentDao(mContext);//创建数据库操作对象-本环境下mContex 关联
        if(contentDao.queryAll()==null){
            contentDao.delelteAll();//删除数据库所有内容//
            contentDao.insert(AnswerList);//将问题们添加进数据库表格里
        }

    }
    /**
     * 一个单选问题绑定，同时回答加入数据库表
     * @param answeRresource 回答选项string文件资源
     * @param baseRadioQue 目标问题-单选
     */
    public void RadioAnswerToQue(int answeRresource, BaseRadioQue baseRadioQue){

        String[] RadioAnswerArray1 = mContext.getResources().getStringArray(answeRresource);//从本环境 的R文档里获得资源
        List<Content> RadioAnswerList1 = getContent(RadioAnswerArray1);//回答问题列表

        ContentDao contentDao = new ContentDao(mContext);//创建数据库操作对象-本环境下mContex 关联
        contentDao.delelteAll();//删除数据库所有内容
        contentDao.insert(RadioAnswerList1);//将问题们添加进数据库表格里

            for (Content content : RadioAnswerList1) {//遍历所有回答选项
                //一个问题对应生成一个对象 BaseRadioQue
                content.setBaseRadioQue(baseRadioQue);//遍历radio问题数组


        }
    }

    //->返回问题对象列表
    //TODO//抽象 TEXT RADIO CHECK 出 BASEQUE
    public List<BaseTextQue> getTextQueList() {
        String[] textQuestionArray = mContext.getResources().getStringArray(R.array.question_text_list);//从本环境 的R文档里获得资源
        String[] RadioQuestionArray = mContext.getResources().getStringArray(R.array.question_radio_list);//从本环境 的R文档里获得资源
        String[] CheckQuestionArray = mContext.getResources().getStringArray(R.array.question_check_list);//从本环境 的R文档里获得资源
        //先遍历textQuesion   获取问题入textQuestionArray
        //生成文本问题列表
        List<BaseTextQue> BaseTextQueList = new ArrayList<>();

        for (String question : textQuestionArray) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。
            //一个问题对应生成一个对象 BaseTextQue
            BaseTextQue baseTextQue = new BaseTextQue();

            baseTextQue.setType(0);
            baseTextQue.setTitle(question);//加入问题题目
            BaseTextQueList.add(baseTextQue);//加入对象集合
        }

        return BaseTextQueList;
    }

    public List<Content> getContent(String[] AnswerArray) {
        String[] textQuestionArray = mContext.getResources().getStringArray(R.array.question_text_list);//从本环境 的R文档里获得资源
        String[] RadioQuestionArray = mContext.getResources().getStringArray(R.array.question_radio_list);//从本环境 的R文档里获得资源
        String[] CheckQuestionArray = mContext.getResources().getStringArray(R.array.question_check_list);//从本环境 的R文档里获得资源


        //先遍历textQuesion   获取问题入textQuestionArray
        //生成文本问题列表
        List<Content> contentList = new ArrayList<>();

        for (String answer : AnswerArray) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。
            //一个问题对应生成一个对象 BaseTextQue
            Content content = new Content();


            content.setName(answer);//加入问题题目
            contentList.add(content);//加入对象集合
        }

        return contentList;
    }

    /**
     * 生成单选问题列表
     *
     * @return
     */
    public List<BaseRadioQue> getRadioQueList() {
        String[] radioQuestionArray = mContext.getResources().getStringArray(R.array.question_radio_list);//从本环境 的R文档里获得资源
        String[] RadioAnswerArray = mContext.getResources().getStringArray(R.array.radio_exercise_answer1);//从本环境 的R文档里获得资源
        //先遍历textQuesion   获取问题入textQuestionArray
        //生成文本问题列表
        List<BaseRadioQue> baseRadioQueList = new ArrayList<>();

        for (String question : radioQuestionArray) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。
            //一个问题对应生成一个对象 BaseRadioQue
            BaseRadioQue baseRadioQue = new BaseRadioQue();

            //todo//耦合时setTYpe
            baseRadioQue.setType(1);
            baseRadioQue.setTitle(question);//加入问题题目
            baseRadioQueList.add(baseRadioQue);//加入对象集合
        }
        return baseRadioQueList;
    }
        /**
         * 生成多选问题列表
         * @return
         */
        public List<BaseCheckQue> getCheckQueList () {
            String[] checkoQuestionArray = mContext.getResources().getStringArray(R.array.question_check_list);//从本环境 的R文档里获得资源
            //String[] RadioAnswerArray = mContext.getResources().getStringArray(R.array.radio_exercise_answer1);//从本环境 的R文档里获得资源
            //先遍历textQuesion   获取问题入textQuestionArray
            //生成文本问题列表
            List<BaseCheckQue> baseCheckQueList = new ArrayList<>();

            for (String question : checkoQuestionArray) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。
                //一个问题对应生成一个对象 BaseRadioQue
                BaseCheckQue baseCheckQue = new BaseCheckQue();
                //todo//耦合时setTYpe
                baseCheckQue.setType(2);
                baseCheckQue.setTitle(question);//加入问题题目
                baseCheckQueList.add(baseCheckQue);//加入对象集合
            }

            return baseCheckQueList;
        }

    
}
