package com.enjoylife.lookworld.application;

import android.app.Application;

import com.enjoylife.lookworld.http.httpUtils.HttpUtils;
import com.enjoylife.lookworld.model.DownLoad.OkHttp3Downloader;
import com.enjoylife.lookworld.utils.CommonUtils;
import com.enjoylife.lookworld.utils.DebugUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by wangfang on 2017/9/21.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private static Picasso mPicasso;
    Log log = Log.YLog();

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        Fresco.initialize(this);
        setUpPicasso();

    }

    private void setUpPicasso(){
        Picasso picasso = new Picasso.Builder(instance)
                .downloader(new OkHttp3Downloader(new OkHttpClient()))
                .build();
        Picasso.setSingletonInstance(picasso);
        mPicasso = picasso;
    }

    public static Picasso getPicasso(){
        return mPicasso;
    }
}
