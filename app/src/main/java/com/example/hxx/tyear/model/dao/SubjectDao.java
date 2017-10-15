package com.example.hxx.tyear.model.dao;

import android.content.Context;

import com.example.hxx.tyear.model.bean.Subject;

/**
 * Created by tangqi on 16/5/19.
 */
public class SubjectDao extends DaoImpl<Subject> {

    public SubjectDao(Context context) {
        super(context, Subject.class);
    }
}
