package com.enjoylife.lookworld.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enjoylife.bookdream.R;
import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.lookworld.ui.IVew.IMovieLoadData;
import com.enjoylife.lookworld.ui.baseView.BaseFragment;

/**
 * Created by wangfang on 2017/9/24.
 */

public class DiscoveryFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int setContent() {
        return R.layout.discovery_fragment;
    }

}
