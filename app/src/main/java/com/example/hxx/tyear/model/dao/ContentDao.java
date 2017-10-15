package com.example.hxx.tyear.model.dao;

import android.content.Context;

import com.example.hxx.tyear.model.bean.Content;

/**
 * Created by tangqi on 16/5/18.
 */
public class ContentDao extends DaoImpl<Content> {

    public ContentDao(Context context) {
        super(context, Content.class);
    }
}
