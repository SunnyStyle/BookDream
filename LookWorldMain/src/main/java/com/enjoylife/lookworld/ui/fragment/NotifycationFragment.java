package com.enjoylife.lookworld.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.lookworld.ui.IVew.DataView;
import com.enjoylife.lookworld.ui.baseView.BaseFragment;

/**
 * Created by wangfang on 2017/9/24.
 */

public class NotifycationFragment extends BaseFragment implements DataView {
    @Override
    public void loadData(UsBoxEntity usBoxEntity, Movieinfo comingSoon, Movieinfo inThreatEntity) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
