package com.example.shilh.splash.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class OneViewPagerAdapter extends PagerAdapter{
    private List<String> mlist;
    private Context mcontext;
    private int mposition;
    private TextView mpagenow;

    public OneViewPagerAdapter(List<String> mlist, Context mcontext, int mposition) {
        this.mlist = mlist;
        this.mcontext = mcontext;
        this.mposition=mposition;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        Glide.with(mcontext).load(mlist.get(position)).into(photoView);

        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

}
