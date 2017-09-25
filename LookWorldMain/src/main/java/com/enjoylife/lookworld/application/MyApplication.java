package com.enjoylife.lookworld.application;

import android.app.Application;

import com.enjoylife.lookworld.model.DownLoad.OkHttp3Downloader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by wangfang on 2017/9/21.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private static Picasso mPicasso;

    public static Application getApplication()
    {
        if (instance == null)
            initialize();
        return instance;
    }

    private static void initialize()
    {
        instance = new MyApplication();
        instance.onCreate();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpPicasso();
        Fresco.initialize(this);
    }

    private void setUpPicasso(){

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(new OkHttpClient()))
                .build();
        Picasso.setSingletonInstance(picasso);
        mPicasso = picasso;
    }

    public static Picasso getPicasso(){
        return mPicasso;
    }
}
