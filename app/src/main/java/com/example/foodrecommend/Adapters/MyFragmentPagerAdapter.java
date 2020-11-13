package com.example.foodrecommend.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.foodrecommend.RecommendFragment;
import com.example.foodrecommend.SquareFragment;
import com.example.foodrecommend.TypeFragment;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] tableTitle = new String[] {"推荐", "美食广场", "类别"};
    private List<Fragment> mFragmentTab;
    /*添加碎片*/
    private RecommendFragment recommendFragment;
    private SquareFragment squareFragment;
    private TypeFragment typeFragment;




    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragmentTab();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentTab.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tableTitle[position];
    }

    private void initFragmentTab() {
        recommendFragment = new RecommendFragment();
        squareFragment = new SquareFragment();
        typeFragment = new TypeFragment();
        mFragmentTab = new ArrayList<>();
        mFragmentTab.add(recommendFragment);
        mFragmentTab.add(squareFragment);
        mFragmentTab.add(typeFragment);

    }
}
