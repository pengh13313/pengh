package com.example.shilh.splash.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shilh.splash.MainActivity;
import com.example.shilh.splash.MyphotoImageShowActivity;
import com.example.shilh.splash.R;
import com.example.shilh.splash.model.Photoc;

import java.util.ArrayList;
import java.util.List;

public class PhotoCollectionRecycleAdapter extends RecyclerView.Adapter<PhotoCollectionRecycleAdapter.ViewHolder> {

    private List<Photoc> mlist;
    private Context mcontext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.photoc_image);
            textView=(TextView)itemView.findViewById(R.id.photoc_name);
        }
    }
    public PhotoCollectionRecycleAdapter(Context context,List<Photoc> list){
        mlist=list;
        mcontext=context;

    }

    @NonNull
    @Override
    public PhotoCollectionRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photocollection_item,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
    /*    holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                List<String> childList = mlist.get(position).getImageurl();
                Intent intent = new Intent(, MyphotoImageShowActivity.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) childList);
                startActivity(intent);

            }
        });  */
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoCollectionRecycleAdapter.ViewHolder viewHolder, final int i) {
        Photoc photoc=mlist.get(i);
        viewHolder.textView.setText(photoc.getFilename());
        Glide.with(mcontext).load(photoc.getMtopimagepath()).thumbnail(0.1f).into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MyphotoImageShowActivity.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) mlist.get(i).getImageurl());
                intent.putExtra("name",mlist.get(i).getMtopimagepath());
                mcontext.startActivity(intent);
                MainActivity activity=(MainActivity)mcontext;
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    /*public interface Onclick{
        void inClick(int p, List list);

    } */
}
