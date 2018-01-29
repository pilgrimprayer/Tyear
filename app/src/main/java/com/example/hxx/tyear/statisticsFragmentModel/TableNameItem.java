package com.example.hxx.tyear.statisticsFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxx on 2017/10/8.
 */
//后期需要存入数据库 因为要做历史记录报告对吧
public class TableNameItem {
    public static final int PARENT_ITEM = 0;//父布局
    public static final int CHILD_ITEM = 1;//子布局

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;// 显示类型

    public String getContents() {
        return contents;
    }

    public void addContents(String contents) {
        this.contents += contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    String contents="";
    int childType;//0是普通 1是开头
    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String ID;
    private boolean isExpand;// 是否展开
    TableNameItem tableNameItem;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public TableNameItem getChildBean() {
        return childBean;
    }

    public void setChildBean(TableNameItem childBean) {
        this.childBean = childBean;
    }

    TableNameItem childBean;
    List<TableGroupItem> tableGroupItemList=new ArrayList<TableGroupItem>();

    private String  parentname;
    int  parentsum;

    public String getChildTitle() {
        return childTitle;
    }

    public void setChildTitle(String childTitle) {
        this.childTitle = childTitle;
    }

    String childTitle;

    public String getChildContent() {
        return childContent;
    }

    public void setChildContent(String childContent) {
        this.childContent = childContent;
    }

    String childContent;
    public List<TableGroupItem> getTableGroupItemList() {
        return tableGroupItemList;
    }

    public void addTableGroupItemList(TableGroupItem tableGroupItem) {
        //this.tableGroupItemList = tableGroupItemList;
        tableGroupItemList.add(tableGroupItem);
    }

    public void setTableGroupItemList(List<TableGroupItem> tableGroupItemList) {
        this.tableGroupItemList = tableGroupItemList;
    }
    public int getChildType() {
        return childType;
    }

    public void setChildType(int childType) {
        this.childType = childType;
    }

   boolean Is_attention=true;





    public TableNameItem ( String  parentname,int  parentsum) {
        this. parentname =  parentname;
        this. parentsum =  parentsum;
    }
    public boolean  getIs_attention(){
        return Is_attention;
    }
    public void  SetIs_attention(boolean i){
        this.Is_attention=i;
    }
    public TableNameItem getT () {
        return tableNameItem;
    }

    public void setT (TableNameItem t) {
        this.tableNameItem = t;
    }

    public String getName () {
        return  parentname;
    }

    public void setName (int id) {
        this. parentname =  parentname;
    }

    public int getSum () {
        return  parentsum;
    }

    public void setSum(int id) {
        this. parentsum =  parentsum;
    }
}
