package com.enjoylife.lookworld.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.enjoylife.lookworld.application.MyApplication;


/**
 * Created by wangfang on 2017/9/22.
 */

public class NetworkUtils {
    /**
     * 判断是否有网络连接
     * @return
     */

    public static boolean isNetworkConnected(){

        ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if (mNetworkInfo != null){
            return mNetworkInfo.isAvailable();
        }


        return false;
    }

    /**
     * 判断Wi-Fi网络是否有用
     * @param context
     * @return
     */
    public boolean isWifiConnected(Context context){
        if(context != null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWiFiNetworkInfo != null){
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
