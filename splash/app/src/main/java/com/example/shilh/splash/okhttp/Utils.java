package com.example.shilh.splash.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
//判断网络是否连接
    public static boolean networkavailable(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo info=connectivityManager.getActiveNetworkInfo();
            if(info!=null&&info.isConnected()){
                return  true;
            }

        }
        return false;
    }
}
