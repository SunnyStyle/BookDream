package com.enjoylife.lookworld.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.bookdream.R;
import com.enjoylife.lookworld.application.Log;
import com.enjoylife.bookdream.databinding.MovieFragmentBinding;
import com.enjoylife.lookworld.presenter.MoviePresenterImpl;
import com.enjoylife.lookworld.ui.IVew.DataView;
import com.enjoylife.lookworld.ui.adapter.MovieRecyclerviewAdapter;
import com.enjoylife.lookworld.ui.baseView.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfang on 2017/9/21.
 */

public class MovieFragment extends BaseFragment implements DataView {

    RecyclerView movieRecyclerview;
    MovieFragmentBinding movieFragmentBinding;
    MoviePresenterImpl moviePresenter;


    List<Object> lists = new ArrayList<>();
    Log log = Log.YLog();

    @Override
    public void loadData(UsBoxEntity usBoxEntity, Movieinfo comingSoon, Movieinfo inThreatEntity) {
        lists.add(0,inThreatEntity);
        lists.add(1,comingSoon);
        lists.add(2,usBoxEntity);
        log.d("inThreatEntity = " + inThreatEntity.getCount() + "， comingSoon = " + comingSoon.getCount());
        String[] movieTitle = getActivity().getResources().getStringArray(R.array.movie_title);
        log.d("movieRecyclerview 111111= " + movieRecyclerview);
        MovieRecyclerviewAdapter movieRecyclerviewAdapter = new MovieRecyclerviewAdapter(getActivity(),movieTitle,lists);
        movieRecyclerview.setAdapter(movieRecyclerviewAdapter);
        log.d("movieRecyclerviewAdapter 222222= " + movieRecyclerviewAdapter);
        //movieFragmentBinding.setRvAdapter(new MovieRecyclerviewAdapter(getActivity(),movieTitle,lists));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        movieFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.movie_fragment, container, false);
        movieRecyclerview = movieFragmentBinding.movieFragmentRecycler;
        movieRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //movieFragmentBinding.setRvLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        moviePresenter = new MoviePresenterImpl(getActivity());
        moviePresenter.attachView(this);
        moviePresenter.loadData();

        return movieFragmentBinding.getRoot();

    }
}
