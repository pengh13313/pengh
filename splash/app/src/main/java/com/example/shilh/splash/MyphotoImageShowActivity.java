package com.example.shilh.splash;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.shilh.splash.adapter.MyPhotoCManageradapter;

import java.util.ArrayList;
import java.util.List;

public class MyphotoImageShowActivity extends AppCompatActivity {
    private List<String> mlist=new ArrayList<String>();
    private final static int OK = 1;
    private MyPhotoCManageradapter madapter = null;
    private RecyclerView mrecycleview;
    private Button mrubbishbutton;
    private Button minformattionbutton;
    private Button msharebuttton;
    private Button mbackbutton;
    private CheckBox mcheck;
    private boolean isShowCheck;
    private Context mcontext;
    private List<String> checkList;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case OK:
                    mrecycleview=(RecyclerView)findViewById(R.id.photocmanager);
                    madapter=new MyPhotoCManageradapter(MyphotoImageShowActivity.this,mlist);
                    mrecycleview.setAdapter(madapter);
                default:


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myphoto_image_show);
        mrubbishbutton=(Button)findViewById(R.id.rubbishbutton);
        minformattionbutton=(Button)findViewById(R.id.informattionbutton);
        msharebuttton=(Button)findViewById(R.id.sharebuttton);
        mbackbutton=(Button)findViewById(R.id.back);
        mcheck=(CheckBox)findViewById(R.id.cb_item);
        mrubbishbutton.setClickable(false);
        minformattionbutton.setClickable(false);
        msharebuttton.setClickable(false);
        mlist=getIntent().getStringArrayListExtra("data");
        String parentname=getIntent().getStringExtra("name");
        checkList = new ArrayList<>();
        mrecycleview=(RecyclerView)findViewById(R.id.photocmanager);
        madapter = new MyPhotoCManageradapter(this,mlist);
        mrecycleview.setAdapter(madapter);
        mrecycleview.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        initListener();
        mrubbishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyphotoImageShowActivity.this);
                builder.setMessage("确定删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Thread threaddelete=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                deletePic(checkList);
                                Message message=new Message();
                                message.what=OK;
                                mhandler.sendMessage(message);
                            }
                        });

                        threaddelete.start();


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MyphotoImageShowActivity.this, "成功取消" , Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        mbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Intent intent=new Intent(MyphotoImageShowActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destory","destory");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause","pause");
    }

    private void refreshUI() {
        ArrayList<String> list=getIntent().getStringArrayListExtra("data");
        if (madapter == null) {
            madapter = new MyPhotoCManageradapter(MyphotoImageShowActivity.this,list);
            mrecycleview.setAdapter(madapter);
        } else {
            madapter.notifyDataSetChanged();
        }
    }

    private void initListener() {
        madapter.setOnItemClickListener(new MyPhotoCManageradapter.onItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                if(isShowCheck) {
                    ArrayList<String> list=getIntent().getStringArrayListExtra("data");
                    if (checkList.contains(list.get(i))) {
                        checkList.remove(list.get(i));
                    } else {
                        checkList.add(list.get(i));
                    }
                }
            }


            @Override
            public boolean onLongClick(View view, int i) {
                if (isShowCheck) {
                    mrubbishbutton.setClickable(false);
                    mrubbishbutton.setBackgroundResource(R.drawable.titlewhite);
                    msharebuttton.setClickable(false);
                    msharebuttton.setBackgroundResource(R.drawable.titlewhite);
                    madapter.setShowCheckBox(false);
                    refreshUI();

                    checkList.clear();
                } else {
                    madapter.setShowCheckBox(true);
                    mrubbishbutton.setClickable(true);
                    mrubbishbutton.setBackgroundResource(R.drawable.delete);
                    msharebuttton.setClickable(true);
                    msharebuttton.setBackgroundResource(R.drawable.share);
                    refreshUI();
                }
                isShowCheck = !isShowCheck;
                return false;
            }
        });
    }
    private void deletePic(List<String> list){
        for(String path:list){
            if(!TextUtils.isEmpty(path)) {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = MyphotoImageShowActivity.this.getContentResolver();

                String url = MediaStore.Images.Media.DATA + "='" + path + "'";
                //删除图片
                contentResolver.delete(uri, url, null);
                mlist.remove(path);
            }

        }

    }



}
