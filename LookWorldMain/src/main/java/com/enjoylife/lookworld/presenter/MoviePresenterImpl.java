package com.enjoylife.lookworld.presenter;

import android.content.Context;

import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.lookworld.application.InitRetrofit;
import com.enjoylife.lookworld.application.Log;
import com.enjoylife.lookworld.application.NetService;
import com.enjoylife.lookworld.ui.IVew.DataView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangfang on 2017/9/21.
 */

public class MoviePresenterImpl extends BasePresenter<DataView>{

    Log log = Log.YLog();
    Context context;

    private boolean USBOX_FINISHED = false;
    private boolean HOTMO_FINISHED = false;
    private boolean RECENT_FINISHED = false;

    UsBoxEntity usBoxentity;
    Movieinfo comingSoonEntity,inThreatentity;

    public MoviePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(DataView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadData() {
        getMvpView().startLoading();
        usBoxMovie();
        loadUsBox();
        recentMovie();
    }

    private void usBoxMovie() {
        InitRetrofit.createApi(NetService.class)
        .getHotMovie()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<UsBoxEntity>() {
            @Override
            public void onCompleted() {
                HOTMO_FINISHED = true;
                log.d("finished");
                update();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(null);
                HOTMO_FINISHED = true;
                log.e(e.toString());
            }

            @Override
            public void onNext(UsBoxEntity usBoxEntity) {
                usBoxentity = usBoxEntity;
                log.d(usBoxEntity.getTitle()+"");
            }
        });
    }


    private void update() {
        if (USBOX_FINISHED && RECENT_FINISHED && HOTMO_FINISHED){
            android.util.Log.d("wangfang","usBoxentity = " + usBoxentity);
            //android.util.Log.d("wangfang","comingSoonEntity = " + comingSoonEntity.toString());
            android.util.Log.d("wangfang","inThreatentity = " + inThreatentity);
            getMvpView().loadData(usBoxentity,comingSoonEntity,inThreatentity);
            getMvpView().hideLoading();
        }
    }

    private void loadUsBox() {
        InitRetrofit.createApi(NetService.class)
                .getInTheaters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movieinfo>() {
                    @Override
                    public void onCompleted() {
                        USBOX_FINISHED = true;
                        log.d("finished");
                        update();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(null);
                        USBOX_FINISHED = true;
                        log.e(e.toString());
                    }

                    @Override
                    public void onNext(Movieinfo inThreatEntity ) {
                        inThreatentity = inThreatEntity;

                    }
                });

    }

    private void recentMovie() {
        InitRetrofit.createApi(NetService.class)
                .getComingSoon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movieinfo>() {
                    @Override
                    public void onCompleted() {
                        log.d("finished");
                        RECENT_FINISHED = true;
                        update();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(null);
                        RECENT_FINISHED = true;
                        log.e(e.toString());
                    }

                    @Override
                    public void onNext(Movieinfo comingSoon) {
                        comingSoonEntity = comingSoon;
                    }
                });
    }

}
