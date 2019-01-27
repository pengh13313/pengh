package com.example.shilh.splash.runnable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.shilh.splash.model.Beans;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AderRunnable implements Runnable {
    private Handler mHandler ;
    private String sUrl;
    private ImageView im;
    private OkHttpClient client=new OkHttpClient();
    public AderRunnable(Handler h, String str, ImageView imageView)
    {
        mHandler = h;
        sUrl = str;
        imageView=im;
    }
    @Override
    public void run() {
        Bitmap bit=imagenetwork();
        Message message=Message.obtain();
        message.obj=bit;
        mHandler.sendMessage(message);

    }
    private Bitmap imagenetwork(){

                try {
                    String murl="http://v.juhe.cn/toutiao/index?type=keji&key=65d4c89f2460e131bd8b288f3f70bff6";
                    final Gson gson = new Gson();
                    final Request request = new Request.Builder().get().url(murl).build();
                    Response response;
                    response = client.newCall(request).execute();//获取数据
                    if(response == null) {
                        return null;
                    }
                    if(response.isSuccessful())
                    {
                        String content = response.body().string();//这里一定是.string如果是tostring接受不到还很难查到
                        if(content == null) {
                            return null;
                        }
                        Log.e("content",content);
                        Beans b=gson.fromJson(content,Beans.class);
                        sUrl=b.result.data.get(1).thumbnail_pic_s;

                        Log.e("sUrl",sUrl);

                    }
            URL purl=new URL(sUrl);
            URLConnection connection= purl.openConnection();
            InputStream stream=connection.getInputStream();
    /*        byte[] data=readStream(stream);//重复获取bitmap时使用方法
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts. inJustDecodeBounds = false;
            opts.inSampleSize=100;
            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length,opts); */
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
/*
    private byte[] readStream(InputStream stream)throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=stream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        stream.close();
        return outStream.toByteArray();
    }
    */


}
