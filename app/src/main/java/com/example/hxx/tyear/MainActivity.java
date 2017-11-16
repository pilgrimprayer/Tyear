package com.example.hxx.tyear;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.hxx.tyear.diaryFragmentModel.ComDiaryFragment;
import com.example.hxx.tyear.statisticsFragmentModel.ComStatisFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentPagerAdapter mModeladapter;
    final ArrayList<Fragment> fraglist=new ArrayList<>(3);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_diary:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_stat:
               viewPager.setCurrentItem(1);
                  //  Intent intent = new Intent(MainActivity.this, ComStatisActivity.class);
                   // startActivity(intent);

                    return true;
                case R.id.navigation_mine:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//防止edittext键盘出现也把底部导航栏也顶上去
        setContentView(R.layout.activity_main);
    //模块viwpager
        viewPager=(ViewPager)findViewById(R.id.vp_container);

        fraglist.add(new ComDiaryFragment());
        fraglist.add(new ComStatisFragment());


        mModeladapter=new ModelPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mModeladapter);
        //导航栏初始化
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

/*        *//**
         * 数据库问题表生成
         *//*
        BaseQueManager subjectManager = new BaseQueManager(MainActivity.this);
        subjectManager.init();
        //数据库之<问题>表生成*/
    }
//    @Override
//    public void SaveEdit(int position, String string) {
//        Toast.makeText( this,"click 回答问题了吧"+string + position, Toast.LENGTH_SHORT).show();//position从0开始传
//    }

    /**
     * 模块fragment适配器
     */
    private class ModelPagerAdapter extends FragmentPagerAdapter {
        public ModelPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fraglist.get(position);
        }

        @Override
        public int getCount() {
            return fraglist.size();
        }
    }
}
