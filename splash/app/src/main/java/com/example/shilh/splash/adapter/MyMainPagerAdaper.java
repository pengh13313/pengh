package com.example.shilh.splash.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

public class MyMainPagerAdaper extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titlelist;
    public MyMainPagerAdaper(FragmentManager fm, List<Fragment> fragmentList, List<String> titlelist) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titlelist=titlelist;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }
}
