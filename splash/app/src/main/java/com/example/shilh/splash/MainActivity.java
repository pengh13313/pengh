package com.example.shilh.splash;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.shilh.splash.adapter.MyMainPagerAdaper;
import com.example.shilh.splash.fragment.MyPhoto;
import com.example.shilh.splash.fragment.PhotoCollection;
import com.example.shilh.splash.fragment.SdCardFiles;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private MyMainPagerAdaper adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        tablayout=(TabLayout)findViewById(R.id.tablelayout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        tablayout.setSelectedTabIndicatorColor(Color.YELLOW);
        List<Fragment> fragmentList=new ArrayList<>();
        List<String> titleList=new ArrayList<>();
        titleList.add("我的照片");
        titleList.add("所有图集");
        titleList.add("SD卡文件");
        fragmentList.add(new MyPhoto());
        fragmentList.add(new PhotoCollection());
        fragmentList.add(new SdCardFiles());
        adapter = new MyMainPagerAdaper(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
