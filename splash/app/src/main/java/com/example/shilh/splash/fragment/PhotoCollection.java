package com.example.shilh.splash.fragment;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shilh.splash.MainActivity;
import com.example.shilh.splash.R;
import com.example.shilh.splash.adapter.PhotoCollectionRecycleAdapter;
import com.example.shilh.splash.model.Photoc;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoCollection extends Fragment {

    private HashMap<String,List<String>> mgroupmap=new HashMap<String,List<String>>();
    private List<Photoc> mlist=new ArrayList<Photoc>();
    private final static int OK = 1;
    private PhotoCollectionRecycleAdapter madapter;
    private RecyclerView mrecycleview;
    //private Handler mhandler;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                    case OK:
                    madapter = new PhotoCollectionRecycleAdapter(getActivity(),subphotoc(mgroupmap));
                    mrecycleview.setAdapter(madapter);
                    default:


            }
        }
    };
    public PhotoCollection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_photo_collection,container,false);

        initphtotc();

     /*   mhandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case OK:
                        madapter = new PhotoCollectionRecycleAdapter(subphotoc(mgroupmap));
                        mrecycleview.setAdapter(madapter);

                }
            }
        }; */
        mrecycleview=(RecyclerView)view.findViewById(R.id.recycle_photoc);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mrecycleview.setLayoutManager(layoutManager);
    /*    PhotoCollectionRecycleAdapter adapter=new PhotoCollectionRecycleAdapter(mlist);
        mrecycleview.setAdapter(adapter);    */
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initphtotc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mimageurl= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mcontentresolver=getActivity().getContentResolver();
                //只查jpg和png图片
                Cursor mcursor=mcontentresolver.query(mimageurl,null,MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);
                if(mcursor==null){
                    return;
                }
                while(mcursor.moveToNext()){
                    String path=mcursor.getString(mcursor.getColumnIndex(MediaStore.Images.Media.DATA));//获取图片路径
                    String parentName = new File(path).getParentFile().getName();//获取图片父路径
                    if(!mgroupmap.containsKey(parentName)){
                        List<String> clist=new ArrayList<String>();
                        clist.add(path);
                        mgroupmap.put(parentName,clist);
                    }else{
                        mgroupmap.get(parentName).add(path);
                    }
                }
                Message message=Message.obtain();
                message.what=OK;
                mhandler.sendMessage(message);
                mcursor.close();

            }
        }).start();
    }
    private List<Photoc> subphotoc(HashMap<String,List<String>> mgroupmap){
        if(mgroupmap==null){
            return null;
        }
        List<Photoc> list=new ArrayList<Photoc>();
        Iterator<Map.Entry<String, List<String>>> it = mgroupmap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, List<String>> entry=it.next();
            Photoc pc=new Photoc();
            String key=entry.getKey();
            List<String> value=entry.getValue();
            pc.setFilename(key);
            pc.setMimageCounts(value.size());
            pc.setMtopimagepath(value.get(0));
            pc.setImageurl(value);
            list.add(pc);


        }
        return list;

    }
}
