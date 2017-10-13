package com.enjoylife.lookworld.ui.fragment.bookmovienews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.enjoylife.lookworld.http.cache.ACache;
import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.bookdream.R;
import com.enjoylife.lookworld.application.Log;
import com.enjoylife.bookdream.databinding.MovieFragmentBinding;
import com.enjoylife.lookworld.presenter.MoviePresenterImpl;
import com.enjoylife.lookworld.ui.IVew.IMovieLoadData;
import com.enjoylife.lookworld.ui.adapter.movie.MovieRvAdapter;
import com.enjoylife.lookworld.ui.baseView.BaseFragment;
import com.enjoylife.lookworld.ui.view.xrecycleview.XRecyclerView;
import com.enjoylife.lookworld.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfang on 2017/9/21.
 */

public class MovieFragment extends BaseFragment<MovieFragmentBinding> implements IMovieLoadData {

    RecyclerView movieRecyclerview;
    MovieFragmentBinding movieFragmentBinding;
    MoviePresenterImpl moviePresenter;

    List<Object> lists = new ArrayList<>();
    Log log = Log.YLog();

    MovieRvAdapter movieRecyclerviewAdapter;

    @Override
    public void loadData(Movieinfo top250,Movieinfo inThreatEntity,UsBoxEntity usBoxEntity, Movieinfo comingSoon) {
        //movieRecyclerview.refreshComplete();
        lists.add(0,top250);//今日推荐
        lists.add(1,null);
        lists.add(2,inThreatEntity);//影院热映
        lists.add(3,comingSoon);//院线即将上映
        lists.add(4,usBoxEntity);//豆瓣热门

        String[] movieTitle = getActivity().getResources().getStringArray(R.array.movie_title);
        log.d("movieRecyclerview 111111= " + movieRecyclerview);
        //MovieRecyclerviewAdapter movieRecyclerviewAdapter = new MovieRecyclerviewAdapter(getActivity(),movieTitle,lists);

        if(movieRecyclerviewAdapter == null){
            movieRecyclerviewAdapter = new MovieRvAdapter();
        }else{
            movieRecyclerviewAdapter.clear();
        }

        movieRecyclerviewAdapter.addAll((List)lists);
        movieRecyclerview.setAdapter(movieRecyclerviewAdapter);
        //movieFragmentBinding.setRvAdapter(new MovieRecyclerviewAdapter(getActivity(),movieTitle,lists));
    }

    @Override
    public int setContent() {
        return R.layout.movie_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieRecyclerview = bindingView.movieFragmentRecycler;
        movieRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //movieFragmentBinding.setRvLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        /*movieRecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                DebugUtil.debug("MovieFragment onRefresh()");
                moviePresenter.loadData();
            }

            @Override
            public void onLoadMore() {
                DebugUtil.debug("MovieFragment onLoadMore()");
            }
        });*/

        moviePresenter = new MoviePresenterImpl(getActivity());
        moviePresenter.attachView(this);
        moviePresenter.loadData();
        showContentView();
    }

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
