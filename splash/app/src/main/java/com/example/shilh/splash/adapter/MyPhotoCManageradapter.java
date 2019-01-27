package com.example.shilh.splash.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import com.example.shilh.splash.OnePagerImageShowActivity;
import com.example.shilh.splash.R;

import java.util.ArrayList;
import java.util.List;

public class MyPhotoCManageradapter extends RecyclerView.Adapter<MyPhotoCManageradapter.ViewHolder> {

    private List<String> mlist;
    private Context mcontext;
    private boolean mshowCheckBox;
    private RelativeLayout mRlItem;
    private CheckBox mCbItem;
    private onItemClickListener onItemClickListener;
    private Button mrubbishbutton;
    private Button msharebuttton;
    private GridLayoutManager.LayoutParams mparams;
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout mRlItem;
        CheckBox mCbItem;
        ImageView imageView;
        Button mrubbishbutton;
        Button msharebuttton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.photocmanager_image);
            mCbItem = (CheckBox) itemView.findViewById(R.id.cb_item);
            mrubbishbutton=(Button) itemView.findViewById(R.id.rubbishbutton);
            msharebuttton=(Button)itemView.findViewById(R.id.sharebuttton);
            mRlItem=(RelativeLayout) itemView.findViewById(R.id.mRlItem);

        }


    }
    public MyPhotoCManageradapter(Context context,List<String> list){
        mlist=list;
        mcontext=context;

    }
    public boolean isShowCheckBox() {
        return mshowCheckBox;
    }
    public void setShowCheckBox(boolean showCheckBox) {
        this.mshowCheckBox = showCheckBox;
    }

    @NonNull
    @Override
    public MyPhotoCManageradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myphotocmanager,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyPhotoCManageradapter.ViewHolder viewHolder,final int i) {
        String p=mlist.get(i);
        //viewHolder.textView.setText(p.getFilename());
        viewHolder.mCbItem.setTag(i);
        if (mshowCheckBox) {
            viewHolder.mCbItem.setVisibility(View.VISIBLE);
            viewHolder.mCbItem.setChecked(mCheckStates.get(i, false));
        } else {
            viewHolder.mCbItem.setVisibility(View.GONE);
            //取消掉Checkbox后不再保存当前选择的状态
            viewHolder.mCbItem.setChecked(false);
            mCheckStates.clear();
        }
        viewHolder.mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mshowCheckBox) {
                    viewHolder.mCbItem.setChecked(!viewHolder.mCbItem.isChecked());
                }else{
                    Intent intent = new Intent(mcontext,OnePagerImageShowActivity.class);
                    intent.putStringArrayListExtra("image", (ArrayList<String>) mlist);
                    intent.putExtra("position",i+"");
                    mcontext.startActivity(intent);
                }
                onItemClickListener.onClick(view, i);
            }
        });
        viewHolder.mRlItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return onItemClickListener.onLongClick(view, i);

            }
        });
        viewHolder.mCbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                int pos = (int) compoundButton.getTag();
                if (b) {
                    mCheckStates.put(pos, true);
                } else {
                    mCheckStates.delete(pos);
                }
            }
        });
        Glide.with(mcontext).load(p).thumbnail(0.1f).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public interface onItemClickListener {
        void onClick(View view, int i);

        boolean onLongClick(View view, int i);
    }
    public void setOnItemClickListener(MyPhotoCManageradapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }



}
