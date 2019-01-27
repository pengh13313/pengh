package com.example.shilh.splash.adapter;
//史鹂鸿
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shilh.splash.model.Folder;

import java.util.List;
import com.example.shilh.splash.R;

public class SdFolderAdapter extends RecyclerView.Adapter<SdFolderAdapter.ViewHolder>{

    private List<Folder> folderList;
    private ViewHolder holder;
    public OnItemClickListener mOnItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView folderImage;
        TextView folderName;
        TextView folderData;
        View folderView;

        public ViewHolder(View view) {
            super(view);
            folderView = (RecyclerView)itemView.findViewById(R.id.recycle_sd);
            folderImage = (ImageView)itemView.findViewById(R.id.folder_image);
            folderName = (TextView)itemView.findViewById(R.id.folder_name);
            folderData = (TextView)itemView.findViewById(R.id.folder_date);
        }
    }

    public SdFolderAdapter(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.folder_item, viewGroup, false);
        holder = new ViewHolder(view);
        return holder;
    }

    //点击接口
    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //适配渲染数据到View中
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Folder folder = folderList.get(position);

        final ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.folderName.setText(folder.getName());
        mViewHolder.folderImage.setImageResource(folder.getImage());
        mViewHolder.folderData.setText(folder.getDate());

        if (mOnItemClickListener != null) {
            mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(mViewHolder.itemView, mViewHolder.getLayoutPosition());
                }
            });
        }
    }

    //这个方法类似于BaseAdapter的getCount方法，即总共有多少个条目。
    @Override
    public int getItemCount() {
        return folderList.size();
    }
}
