package com.example.hxx.tyear.model;

import android.content.Context;
import android.util.Log;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.model.bean.BaseQue;
import com.example.hxx.tyear.model.bean.Content;
import com.example.hxx.tyear.model.bean.Diary;
import com.example.hxx.tyear.model.bean.Label;
import com.example.hxx.tyear.model.dao.BaseQueDao;
import com.example.hxx.tyear.model.dao.ContentDao;
import com.example.hxx.tyear.model.dao.DiaryDao;
import com.example.hxx.tyear.model.dao.LabelDao;

import java.util.ArrayList;
import java.util.List;


public class BaseQueManager {
    private Context mContext;
    List<BaseQue> baseQueList = new ArrayList<>();
    public BaseQueManager(Context context) {
        this.mContext = context;
    }

    public void init() {

        String[] textQuestionArray = mContext.getResources().getStringArray(R.array.radio_ansewer_list);//从本环境 的R文档里获得资源
//        if (!SpfUtils.getBoolean(mContext, SpfConfig.IS_INIT_BaseQue, false)) {
        //文本库建立
       // List<BaseTextQue> textQueList = getTextQueList();//生成文本问题列表
        getQueList(R.array.question_text_list,0);//生成文本问题列表
        getQueList(R.array.question_radio_list,1);//生成单选问题列表
        getQueList(R.array.question_check_list,2);//生成多选问题列表
        //List<BaseCheckQue> checkQueList = getCheckQueList();//生成单选问题列表

       
        //所有
        BaseQueDao baseQueDao = new BaseQueDao(mContext);//创建数据库操作对象-本环境下mContex 关联
    // if(baseQueDao.queryAll()==null){

 //baseQueDao.delelteAll();//删除数据库所有内容*/
        //每次初始化 数据库只有这10条 不删除会不断添加一模一样进库里


      // }
         baseQueDao.insert(baseQueList);//将问题们添加进数据库表格里
        List<BaseQue> list = baseQueDao.queryAll();

       Log.i("Test", "diary:" + list.toString());
        DiaryDao mDiaryDao=new DiaryDao(mContext);
        List<Diary> listd = mDiaryDao.queryAll();

        Log.i("Test", "diary:" + listd.toString());

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
        int CheckAnswerResourceList[]={
                R.array.check_Englishe_answer1,
                R.array.check_cs_answer2,


        };
        String[] AnswerArray;
        ContentDao contentDao = new ContentDao(mContext);//创建数据库操作对象-本环境下mContex 关联


   //     contentDao.delelteAll();//删除数据库所有内容
        //todo//每次都添加？
        int i=0,j=0;
        for (BaseQue baseQue : baseQueList) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。
            if(baseQue.getType()==1){//若类型为单选则绑定相应答案
              AnswerArray = mContext.getResources().getStringArray(RadioAnswerResourceList[i++]);//从本环境 的R文档里获得资源
                List<Content> AnswerList = getContent(AnswerArray);//回答问题列表
                for (Content content : AnswerList) {//遍历所有回答选项
                    //一个问题对应生成一个对象 BaseQue
                    content.setBaseQue(baseQue);//遍历radio问题数组
                }
                contentDao.insert(AnswerList);//将问题们添加进数据库表格里
            }
            if(baseQue.getType()==2){//若类型为多选则绑定相应答案

               AnswerArray = mContext.getResources().getStringArray(CheckAnswerResourceList[j++]);//从本环境 的R文档里获得资源
                List<Content> AnswerList = getContent(AnswerArray);//回答问题列表
                for (Content content : AnswerList) {//遍历所有回答选项
                    //一个问题对应生成一个对象 BaseQue
                    content.setBaseQue(baseQue);//遍历radio问题数组
               }
                contentDao.insert(AnswerList);//将问题们添加进数据库表格里
            }

        }
        //标签绑定

        String[] LabelArray = mContext.getResources().getStringArray(R.array.label_list);//0健康-1学习2心情3反思
        String[] LabelArrayToQue = new String[]{  LabelArray[2], LabelArray[3],  LabelArray[2],
                LabelArray[3],  LabelArray[0],  LabelArray[3],  LabelArray[0],  LabelArray[0],  LabelArray[1],  LabelArray[1]};

        LabelDao labelDao = new LabelDao(mContext);
        List<Label> labelList = new ArrayList<Label>();
        for(int p=0;p<10;p++){
            Label label = new Label();
            label.setName(LabelArrayToQue[p]);
            label.setBaseQue(baseQueList.get(p));
            labelList.add(label);
        }
        labelDao.insert(labelList);

        List<Label> tlist = labelDao.queryAll();
       Log.i("Test", "diary:" + tlist.toString());
//
//        List<Content> tlist = contentDao.queryAll();
//
//        Log.i("Test", "diary:" + tlist.toString());
//
//        List<BaseQue> mlist = baseQueDao.queryAll();
//
//        Log.i("Test", "diary:" + mlist.toString());
//
//        List<Content> t2list = contentDao.queryAll();
//
//        Log.i("Test", "diary:" + t2list.toString());


    }

    public void getQueList(int QueResource,int QueType) {

        String[] QuestionArray = mContext.getResources().getStringArray(QueResource);//从本环境 的R文档里获得资源
        String[] radioQuestionArray = mContext.getResources().getStringArray(R.array.question_radio_list);//从本环境 的R文档里获得资源
        String[] RadioAnswerArray = mContext.getResources().getStringArray(R.array.radio_exercise_answer1);//从本环境 的R文档里获得资源
        //先遍历textQuesion   获取问题入textQuestionArray
        //生成文本问题列表

        for (String question : QuestionArray) {//**for(String s:v)s是遍历后赋值的变量，v是要遍历的list。
            //一个问题对应生成一个对象 BaseQue
            BaseQue baseQue = new BaseQue();

            baseQue.setType(QueType);
            baseQue.setTitle(question);//加入问题题目
            baseQueList.add(baseQue);//加入对象集合
        }

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







    
}
