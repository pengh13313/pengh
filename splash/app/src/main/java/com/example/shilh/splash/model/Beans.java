package com.example.shilh.splash.model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Beans {

    public String reason;
    public ResultBean result;
    public int error_code;

    public List<Beans> arrayBeansFromData1(String str) {

        Type listType = new TypeToken<ArrayList<Beans>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class ResultBean {
        public String stat;
        public List<DataBean> data;

        public static List<ResultBean> arrayResultBeanFromData2(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static class DataBean {
            public String uniquekey;
            public String title;
            public String date;
            public String category;
            public String author_name;
            public String url;
            public String thumbnail_pic_s;
            public String thumbnail_pic_s02;
            public String thumbnail_pic_s03;

            @RequiresApi(api = Build.VERSION_CODES.N)
            public static List<DataBean> arrayDataBeanFromData3(String str) {
                Gson gson=new Gson();
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();
                List<DataBean> list=new Gson().fromJson(str, listType);
                String a=list.get(1).thumbnail_pic_s;
                Log.e("a",a);
                return new Gson().fromJson(str, listType);
            }

        }
    }
}
