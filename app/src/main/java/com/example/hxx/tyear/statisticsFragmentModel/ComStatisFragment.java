package com.example.hxx.tyear.statisticsFragmentModel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hxx.tyear.R;
// Created by hxx on 2017/10/7.



public class ComStatisFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int ViewPagerCount = 3;
    private LayoutInflater layoutInflater;
    private String[] tabs = {"标签汇总", "图表统计", "总结报告"};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.com_statistics,container,false);

        super.onCreate(savedInstanceState);


        layoutInflater = LayoutInflater.from(getActivity());

        mSectionsPagerAdapter = new SectionsPagerAdapter(this.getChildFragmentManager());

        mViewPager = (ViewPager)view.findViewById(R.id.ucvp);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);//viepager与tab建立关系
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);//     //tab的字体选择器,默认黑色,选择时红色
   for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getTabItemView(i));
        }
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

        return view;
    }
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tabview, null);
        TextView textView = (TextView) view.findViewById(R.id.tabname);
        textView.setText(tabs[index]);
        return view;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = TablesumFragment.newInstance(12);
                    break;
                case 1:
               fragment = ChartFragment.newInstance(30);
                    break;
                case 2:
                    fragment = ReportFragment.newInstance(21);
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return ViewPagerCount;
        }
    }
}
