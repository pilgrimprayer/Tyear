package com.example.hxx.tyear.diaryFragmentModel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hxx.tyear.R;
import com.example.hxx.tyear.adapter.EdiQAdapter;
import com.example.hxx.tyear.model.bean.BaseQue;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hxx on 2017/11/20.
 */

public class QuestionEditAcitivity extends AppCompatActivity {
//
//    @BindView(R.id.spinner)    Spinner typean;
//    @BindView(R.id.type)
//    TextView type;
//    @BindView(R.id.biaoqian)
//    TextView biaoqian;
//    @BindView(R.id.ediradioButton)
//    Button r1;
//    @BindView(R.id.ediradioButton2)
//    Button r2;@BindView(R.id.ediradioButton3)
//    Button r3;@BindView(R.id.ediradioButton4)
//    Button r4;
//
//    @BindView(R.id.editt)
//    TextView editt;
//    @BindView(R.id.editText1)
//    EditText editttext; @BindView(R.id.radioGroup)
//    EditText radioGroup;
//    @BindView(R.id.text2)
//    TextView answer; @BindView(R.id.a1)
//    EditText a1; @BindView(R.id.a2)
//    EditText a2  ; @BindView(R.id.a3)
//    EditText a3;  @BindView(R.id.a4)
//    EditText a4;@BindView(R.id.a5)
//    EditText a5;
//    @BindView(R.id.back)
//    ImageView back;
//    @BindView(R.id.done)
//    ImageView done;
//    @BindView(R.id.toolbaredi)
//    Toolbar toolbar;
//    @BindView(R.id.titlenameedi)
//    TextView tt;
EdiQAdapter adapter;

    @BindView(R.id.add_q_list)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_quetion_list_form);
        ButterKnife.bind(this);
        List<BaseQue> orderList = new ArrayList<>();
        BaseQue baseQue=new BaseQue();
        orderList.add(baseQue);
        adapter = new EdiQAdapter(orderList);
        //MyAdapter2 adapter2 = new MyAdapter2(orderContents2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
