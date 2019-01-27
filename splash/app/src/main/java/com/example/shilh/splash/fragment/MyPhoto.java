package com.example.shilh.splash.fragment;
//史鹂鸿

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shilh.splash.adapter.MyPhotoAdapter;
import com.example.shilh.splash.R;
import com.example.shilh.splash.baidumap.MapActivity;
import com.example.shilh.splash.model.Photo;
import com.example.shilh.splash.OnePagerImageShowActivity;
import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyPhoto extends Fragment {

    public static List<Photo> photos;//这两个是可以在其他java都能访问到的。
    public static List<String> photoDates;

    ContentResolver resolver;//可以通过数据库拿文件的一个工具。
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");//把long转成String,指定格式
    MyPhotoAdapter adapter ;//适配器

    private RecyclerView recyclerView = null;
    private Button btnMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_photo, container, false);
        photos = new ArrayList<Photo>();
        photoDates = new ArrayList<String>();
        adapter = new MyPhotoAdapter(photos, photoDates, getActivity());
        resolver = getActivity().getContentResolver();//这个方法只能在有activity或者service的时候实现
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_photo);
        getPhotos();

        return view;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();//Bundle类用于activity之间传输数据
                    photos = (ArrayList<Photo>) bundle.getParcelableArrayList("photos").get(0);//从msg拿到两个list
                    photoDates = (ArrayList<String>) bundle.getParcelableArrayList("photos").get(1);

                    //adapter = new MyPhotoAdapter(photos, photoDates, getActivity());
                    adapter.setPhoto(photos);
                    adapter.setPhotoDates(photoDates);
                    adapter.notifyDataSetChanged();

                    GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);//一行3个
                    SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, manager);
                    manager.setSpanSizeLookup(lookup);//设置之后，一个位置占满一行
                    recyclerView.setLayoutManager(manager);//设recyclerView的布局
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                        break;
            }
        }
    };

    public void getPhotos() {
        new Thread() {
            @Override
            public void run() {
                List<Photo> list = new ArrayList<Photo>();
                List<String> list1 = new ArrayList<String>();//记得初始化
                super.run();
                Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DATE_TAKEN + " desc");
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        long date = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN));
                        long id=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                        String name=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                        String dec=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

                        Date date1 = new Date(date);
                        String sDate = format.format(date1);//将long变string

                        Photo photo = new Photo(path, sDate,id,name);//new一个新photo

                        if (!list1.contains(sDate)) {//判断日期的list是否已经有了这个照片的日期
                            list1.add(sDate);//这个是将日期添加到日期的list
                        }
                        list.add(photo);//这个是照片的list
                    } while (cursor.moveToNext());
                    cursor.close();
                    Message msg = new Message();
                    msg.what = 1;//这个是一个key，就是一个标记吧
                    Bundle bundle = new Bundle();
                    ArrayList Alist = new ArrayList();//待会以大的list传送数据
                    Alist.add(list);//大的list 加入之前的照片list
                    Alist.add(list1);//大的list加入日期的list

                    bundle.putParcelableArrayList("photos", Alist);//bundle加入大的list
                    msg.setData(bundle);//msg设置数据，数据是含有大list的bundle
                    handler.sendMessage(msg);//发送
                }
            }
        }.start();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnMap = (Button) getActivity().findViewById(R.id.bt_map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null){
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent);
                }
            }
        });

        if(adapter != null) {
            adapter.setOnItemClickListener(new MyPhotoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Intent intent = new Intent(getContext(),OnePagerImageShowActivity.class);
                    List<String> list=new ArrayList<String>();
                    for(Photo i:photos){
                        list.add(i.getPath());
                    }
                    intent.putStringArrayListExtra("image", (ArrayList<String>) list);
                    intent.putExtra("position",position+"");
                    getContext().startActivity(intent);
                }
            });
        }
    }
}
