package com.example.foodrecommend.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.foodrecommend.UserFoodFragment;
import com.example.foodrecommend.UserHistoryFragment;
import com.example.foodrecommend.UserTagFragment;

import java.util.ArrayList;
import java.util.List;

public class UserDetailFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] tableTitle = new String[]{"美食", "标签", "历史"};
    private List<Fragment> mFragmentTab;
    /*添加碎片*/
    private UserHistoryFragment historyFragment;
    private UserFoodFragment foodFragment;
    private UserTagFragment tagFragment;


    public UserDetailFragmentPagerAdapter(FragmentManager fm) {
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
        historyFragment = new UserHistoryFragment();
        tagFragment = new UserTagFragment();
        foodFragment = new UserFoodFragment();
        mFragmentTab = new ArrayList<>();
        mFragmentTab.add(foodFragment);
        mFragmentTab.add(tagFragment);
        mFragmentTab.add(historyFragment);

    }
}
