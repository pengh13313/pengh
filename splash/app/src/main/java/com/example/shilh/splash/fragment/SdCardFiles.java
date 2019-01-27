package com.example.shilh.splash.fragment;
//史鹂鸿

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shilh.splash.R;
import com.example.shilh.splash.adapter.SdFolderAdapter;
import com.example.shilh.splash.model.Folder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SdCardFiles extends Fragment {

    private List<Folder> itemList = new ArrayList<>();
    private SdFolderAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnNewFolder;
    private TextView textView;
    private ImageView imageView;
    private EditText editText;
    private String curPath;
    private Stack<String> pathHistory = null;

    public SdCardFiles() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sdcardfiles, container, false);
        pathHistory = new Stack<String>();

        initFolder();
        textView = (TextView)view.findViewById(R.id.tv_path);
        editText = (EditText)view.findViewById(R.id.et_search);
        imageView = (ImageView)view.findViewById(R.id.iv_search);
        //纵向文件夹列表（图片+文件名+日期）
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_sd);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new SdFolderAdapter(itemList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initFolder() {
        //先判断SD卡是否存在，存在再读取
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(sdCardExist) {
            File SDFile = Environment.getExternalStorageDirectory();
            curPath = SDFile.getAbsolutePath() + "/";
            itemList = getData(curPath);
        } else {
            Toast.makeText(getContext(),"未插入SD卡!",Toast.LENGTH_SHORT).show();
        }
    }

    private List<Folder> getData(String path) {
        List<Folder> list = new ArrayList<Folder>();
        File file = new File(path);
        if (file.listFiles().length > 0) {
            for (File f : file.listFiles()) {
                Folder folder = new Folder();
                String name = f.getName();
                if (f.isDirectory()) { //如果是一个目录，返回true
                    name += "/";
                }
                folder.setName(name);
                folder.setImage(R.drawable.folder);
                folder.setDate(getLastTime(path));
                list.add(folder);
            }
        }
        return list;
    }

    private List<String> getPath(String path) {
        List<String> list = new ArrayList<String>();

        return list;
    }

    //获取文件最后编辑的时间
    private String getLastTime(String path){
        String result = null;
        File f=new File(path);
        //Date time=new Date(f.lastModified());//两种方法都可以
        if(f.exists()){
            long time=f.lastModified();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result=formatter.format(time);
        }
        return result;
    }

    //更新RecyclerView列表
    private void updateList(String path) {
        itemList.clear();
        itemList = getData(path);
        adapter = new SdFolderAdapter(itemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();//通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
    }

    public void searchFolder(String string) {
        String path = curPath + "/" +string;
        File f = new File(path);
        if(f.exists()) {
            updateList(path);
            curPath = path;
            textView.setText(textView.getText() + ">" +string);
            imageView.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(getActivity(),"您查找的文件不存在!", Toast.LENGTH_SHORT).show();
        }

    }

    //fragment里在此方法下实现点击事件
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = editText.getText().toString();
                String path = curPath + string;
                File file = new File(path);
                if (file.isDirectory()) {
                    searchFolder(string);
                } else {
                    Toast.makeText(getActivity(), "该文件夹为空文件夹", Toast.LENGTH_SHORT) .show();
                }
            }
        });

        //点击某一个文件夹
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recycle_sd);
        adapter.setOnItemClickListener(new SdFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),"点击文件夹",Toast.LENGTH_SHORT).show();
                String path;
                if (position > 0) {
                    pathHistory.push(curPath);
                    path = curPath + itemList.get(position).getName();
                } else {
                    if (!pathHistory.empty())
                        path = pathHistory.pop();
                    else
                        path = curPath;
                }
                File file = new File(path);
                if (file.isDirectory()) {
                    curPath = path;
                    updateList(path);
                } else {
                    Toast.makeText(getActivity(), "该文件夹为空文件夹", Toast.LENGTH_SHORT) .show();
                }
            }
        });

        //点击新建文件夹按钮
        btnNewFolder = (Button)getActivity().findViewById(R.id.btn_new_folder);
        btnNewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(curPath + "新建文件夹+++++++++++++++++");
                if(file.exists()){
                    file.delete();
                }
                if (!file.exists()) {
                    //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
                    file.mkdirs();
                    updateList(curPath);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
