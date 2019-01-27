package com.example.shilh.splash;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shilh.splash.adapter.OneViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnePagerImageShowActivity extends AppCompatActivity {

    private List<String> mlist=new ArrayList<String>();
    private int mposition;
    private Button mback;
    private Button minformationbutton;
    private TextView mpagenow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onephoto_image_show);
        ViewPager viewPager = findViewById(R.id.viewpager);
        mback=(Button)findViewById(R.id.mback);
        minformationbutton=(Button)findViewById(R.id.informattionb);
        mpagenow=(TextView) findViewById(R.id.pagenow);
        mlist=getIntent().getStringArrayListExtra("image");
        mposition= Integer.parseInt(getIntent().getStringExtra("position"));
        viewPager.setAdapter(new OneViewPagerAdapter(mlist,OnePagerImageShowActivity.this,mposition));
        viewPager.setCurrentItem(mposition);
        mpagenow.setText((mposition+1)+"/"+(mlist.size()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mpagenow.setText((i+1)+"/"+(mlist.size()));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        minformationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(OnePagerImageShowActivity.this,OnePageInformationActivity.class);
                intent.putExtra("onepage",mlist.get(mposition));
                startActivity(intent);

            }
        });
    }
}
