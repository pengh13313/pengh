package com.example.shilh.splash.adapter;
//史鹂鸿

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.shilh.splash.R;
import com.example.shilh.splash.fragment.MyPhoto;
import com.example.shilh.splash.model.Photo;
import com.example.shilh.splash.model.SquareImg;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//利用SectionedRecyclerViewAdapter实现分组列表的recyclerView
public class MyPhotoAdapter extends SectionedRecyclerViewAdapter<MyPhotoAdapter.MyHeadView, MyPhotoAdapter.MyViewHolder, MyPhotoAdapter.MyFootView> {

    public List<Photo> photos;
    public List<String> photoDates;
    protected Context context;
    public static int sectionCount;
    protected String[][] path = new String[1000][1000];//path[date][image]

    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;

    public MyPhotoAdapter(List<Photo> photos, List<String> photoDates, Context context) {
        this.photos = photos;
        this.context = context;
        this.photoDates = photoDates;
        sectionCount = photoDates.size();
        getPath();
        //Log.i("tag:", "MyPhotoAdapter: " + photos.size()+"---"+photoDates.size());
    }

    public void setPhoto(List<Photo> photos) {
        this.photos = photos;
        getPath();
    }

    public void setPhotoDates(List<String> photoDates) {
        this.photoDates = photoDates;
        getPath();
    }

    @Override//添加content
    protected MyFootView onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override//添加时
    protected MyHeadView onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent, false);
        return new MyHeadView(view);
    }

    @Override//添加主体图片
    protected MyViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        //  System.out.println("photoSize: "+photos.size()+"\n"+"photoDates: "+photoDates.size());
        return new MyViewHolder(view);
    }

    @Override//绑定数据，也就是绑定照片
    public void onBindItemViewHolder(MyViewHolder holder, final int section, int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        Glide.with(context).load(new File(path[section][position]))
                .into(viewHolder.img);
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, viewHolder.getAdapterPosition() - section - 1);//-1是因为从0开始的，第一个位置就要变成第0个；
            }
        });
    }

    @Override
    protected void onBindSectionFooterViewHolder(MyFootView holder, int section) {
    }

    @Override
    protected void onBindSectionHeaderViewHolder(MyHeadView holder, int section) {
        MyHeadView viewHolder = (MyHeadView) holder;
        viewHolder.textDate.setText(photoDates.get(section));
    }//必须继承，绑定头部时间

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override//一共有多少个日期。一天就是一个，100天就是一百个
    public int getSectionCount() {
        return MyPhoto.photoDates.size();
    }

    @Override//一个有多少个item，有天数的和照片个数的
    public int getItemCount() {
        return photos.size() + photoDates.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return getCountInsection(section);
    }

    public void setOnItemClickListener(OnItemClickListener itemClick) {
        this.onItemClickListener = itemClick;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClick) {
        this.onItemLongClickListener = itemLongClick;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    //获得某一天里面有多少张照片。
    public int getCountInsection(int section) {
        List<Photo> sectionPhotos = new ArrayList<>();
        for (int i = 0; i < MyPhoto.photos.size(); i++) {
            if (MyPhoto.photoDates.get(section).equals(MyPhoto.photos.get(i).getDate())) {
                sectionPhotos.add(MyPhoto.photos.get(i));
            }
        }
        return sectionPhotos.size();
    }

    //!!!ij边界!!! count数值!!!
    public void getPath() {
        int count = 0;
        for (int i = 0; i < photoDates.size(); i++) {
            for (int j = 0; j < getCountInsection(i); j++) {
                path[i][j] = photos.get(count).getPath();
                count++;
            }
        }
    }

    class MyHeadView extends RecyclerView.ViewHolder {
        TextView textDate;

        public MyHeadView(View itemView) {
            super(itemView);
            textDate = (TextView) itemView.findViewById(R.id.text_date);
        }
    }

    class MyFootView extends RecyclerView.ViewHolder {
        public MyFootView(View itemView) {
            super(itemView);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        SquareImg img;

        public MyViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_view);
            img = (SquareImg) itemView.findViewById(R.id.square_image);
        }
    }

}
