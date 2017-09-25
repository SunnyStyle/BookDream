package com.enjoylife.lookworld.ui.baseView;


import android.support.v4.app.Fragment;

import com.enjoylife.lookworld.ui.IVew.MvpView;


/**
 * Created by wangfang on 2017/9/21.
 */

public class BaseFragment extends Fragment implements MvpView {
    @Override
    public void startLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }
}
