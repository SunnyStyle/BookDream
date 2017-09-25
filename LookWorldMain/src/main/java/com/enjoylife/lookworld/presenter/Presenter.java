package com.enjoylife.lookworld.presenter;


import com.enjoylife.lookworld.ui.IVew.MvpView;

/**
 * Created by wangfang on 2017/9/21.
 */

public interface Presenter<T extends MvpView> {

    void attachView(T mvpView);

    void detachView();
}
