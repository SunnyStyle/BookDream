package com.enjoylife.lookworld.ui.IVew;

/**
 * Created by wangfang on 2017/9/21.
 */

public interface MvpView {

    void startLoading();

    void hideLoading();

    void showError(String msg);
}
