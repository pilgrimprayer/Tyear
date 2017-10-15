package com.example.hxx.tyear.diaryFragmentModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hxx.tyear.R;

/**
 * Created by hxx on 2017/10/6.
 */

public class ComDiaryFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_fragment,container,false);

        return view;
    }
}
