package com.example.hxx.tyear.MineFragmentModel;

/**
 * Created by hxx on 2017/11/20.
 */


public class MineItem {
    MineItem reportIQuestiontem;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private int imageID;

    boolean Is_attention=true;


    public MineItem ( String name,int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public int getimageID () {
        return imageID;
    }

    public void setimageID(int id) {
        this.imageID = imageID;
    }
}
