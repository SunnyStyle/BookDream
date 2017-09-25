package com.enjoylife.lookworld.presenter;


import com.enjoylife.lookworld.ui.IVew.MvpView;

/**
 * Created by wangfang on 2017/9/21.
 */

public class BasePresenter<T extends MvpView> implements Presenter<T>{
    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {

    }

    public T getMvpView() {
        return mMvpView;
    }

}
