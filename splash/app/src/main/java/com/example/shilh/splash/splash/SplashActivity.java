package com.example.shilh.splash.splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.shilh.splash.MainActivity;
import com.example.shilh.splash.R;
import com.example.shilh.splash.model.Beans;
import com.example.shilh.splash.okhttp.Utils;
import com.example.shilh.splash.model.UrlSearch;
import com.example.shilh.splash.runnable.AderRunnable;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SplashActivity extends AppCompatActivity{

    private boolean flag_click = false;
    private boolean flag_ad = false;
    private Context context;
    private OkHttpClient client=new OkHttpClient();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = (ImageView)findViewById(R.id.image_ad);
        if (!Utils.networkavailable(SplashActivity.this))
        {

            imageView.setImageResource(R.drawable.ad);
            Toast.makeText(SplashActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();

        }else{
            String url=new UrlSearch().getUrl();
            handler=new myhandler();
            new Thread(new AderRunnable(handler,url,imageView)).start();
            //loaddata();

            //imageView.setImageResource(R.drawable.ad);
       /*     String url="http://v.juhe.cn/toutiao/index?type=keji&key=65d4c89f2460e131bd8b288f3f70bff6";
            final Gson gson =new Gson();
            final Request request=new Request.Builder().get().url(url).build();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Response response;
                    try {
                        response=client.newCall(request).execute();//启动
                        if(response.isSuccessful()){
                            String content=response.body().toString();
                            Beans beans=gson.fromJson(content,Beans.class);
                            String reason=beans.reason;
                            Beans.Second result=beans.result;
                            List<Beans.Second.Third> list=result.data;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Glide.with(context).load(url).into(imageView);
                        }
                    });
                }
            }).start();   */



        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_ad = true;
                Intent intent = new Intent(SplashActivity.this,AdvertActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final Button button=(Button)findViewById(R.id.button_count_down);

        final CountDownTimer timer = new CountDownTimer(6*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                button.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                finish();
                if(flag_click == false && flag_ad == false){
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_click = true;
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    class myhandler extends Handler{
        private ImageView mimageView;
        private List<Beans.ResultBean.DataBean> list;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mimageView=(ImageView)findViewById(R.id.image_ad);
            mimageView.setImageBitmap((Bitmap) msg.obj);




        }
    }
   /*
    private void loaddata(){
        String url="http://v.juhe.cn/toutiao/index?type=keji&key=65d4c89f2460e131bd8b288f3f70bff6";
        final Gson gson =new Gson();
        final Request request=new Request.Builder().get().url(url).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    response=client.newCall(request).execute();//启动
                    if(response.isSuccessful()){
                        String content=response.body().toString();
                        Beans beans= gson.fromJson(content,Beans.class);
                        List<Beans.ResultBean.DataBean> list= (List<Beans.ResultBean.DataBean>) beans;
                    /*    Log.e("SplashActibity",list.get(1).getResult().toString());
                        Message message=handler.obtainMessage();
                        message.obj=list;
                        handler.sendMessage(message);
                        */


   /*                  }
               } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }    */



}
