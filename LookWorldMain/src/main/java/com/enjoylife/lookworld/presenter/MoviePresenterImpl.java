package com.enjoylife.lookworld.presenter;

import android.content.Context;

import com.enjoylife.lookworld.http.cache.ACache;
import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.lookworld.http.retrofit.InitRetrofit;
import com.enjoylife.lookworld.application.Log;
import com.enjoylife.lookworld.application.NetService;
import com.enjoylife.lookworld.ui.IVew.IMovieLoadData;
import com.enjoylife.lookworld.utils.ConstantUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangfang on 2017/9/21.
 */

public class MoviePresenterImpl extends BasePresenter<IMovieLoadData>{

    Log log = Log.YLog();
    Context context;

    private boolean USBOX_FINISHED = false;
    private boolean HOTMO_FINISHED = false;
    private boolean RECENT_FINISHED = false;
    private boolean TOP250_FINISHED = false;

    UsBoxEntity usBoxentity;
    Movieinfo comingSoonEntity,inThreatentity,top250;
    private ACache maCache;

    public MoviePresenterImpl(Context context) {
        this.context = context;
        maCache = ACache.get(context);
    }

    @Override
    public void attachView(IMovieLoadData mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadData() {
        getMvpView().startLoading();
        top250 = (Movieinfo) maCache.getAsObject(ConstantUtils.MOVIE_TOP250);
        inThreatentity = (Movieinfo) maCache.getAsObject(ConstantUtils.MOVIE_INTHREAT);
        comingSoonEntity = (Movieinfo) maCache.getAsObject(ConstantUtils.MOVIE_COMINGSOON);
        usBoxentity = (UsBoxEntity) maCache.getAsObject(ConstantUtils.MOVIE_USBOX);
        log.d("top250 = " + top250);
        log.d("inThreatentity = " + inThreatentity);
        log.d("comingSoonEntity = " + comingSoonEntity);
        log.d("usBoxentity = " + usBoxentity);
        if(top250 != null && inThreatentity != null && comingSoonEntity != null && usBoxentity != null){
            USBOX_FINISHED = true;
            RECENT_FINISHED = true;
            HOTMO_FINISHED = true;
            TOP250_FINISHED = true;
            update();
        }else{
            usBoxMovie();
            loadUsBox();
            recentMovie();
            Top250Movie();
        }

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
                maCache.remove(ConstantUtils.MOVIE_USBOX);
                maCache.put(ConstantUtils.MOVIE_USBOX,usBoxentity,30000);
            }
        });
    }


    private void update() {
        if (USBOX_FINISHED && RECENT_FINISHED && HOTMO_FINISHED && TOP250_FINISHED){
            android.util.Log.d("wangfang","usBoxentity = " + usBoxentity);
            //android.util.Log.d("wangfang","comingSoonEntity = " + comingSoonEntity.toString());
            android.util.Log.d("wangfang","inThreatentity = " + inThreatentity);
            getMvpView().loadData(top250,inThreatentity,usBoxentity,comingSoonEntity);
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
                        maCache.remove(ConstantUtils.MOVIE_INTHREAT);
                        maCache.put(ConstantUtils.MOVIE_INTHREAT,inThreatentity,30000);

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
                        maCache.remove(ConstantUtils.MOVIE_TOP250);
                        maCache.put(ConstantUtils.MOVIE_COMINGSOON,comingSoonEntity,30000);
                    }
                });
    }

    private void Top250Movie() {
        InitRetrofit.createApi(NetService.class)
                .getTop250()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movieinfo>() {
                    @Override
                    public void onCompleted() {
                        log.d("finished");
                        TOP250_FINISHED = true;
                        update();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(null);
                        TOP250_FINISHED = true;
                        log.e(e.toString());
                    }

                    @Override
                    public void onNext(Movieinfo t250) {
                        top250 = t250;
                        maCache.remove(ConstantUtils.MOVIE_TOP250);
                        maCache.put(ConstantUtils.MOVIE_TOP250,top250,30000);
                    }
                });
    }

}
