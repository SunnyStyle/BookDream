package com.enjoylife.lookworld.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.bumptech.glide.Glide;
import com.enjoylife.bookdream.R;
import com.enjoylife.bookdream.databinding.HomeFootItemBinding;
import com.enjoylife.bookdream.databinding.HomeFragmentBinding;
import com.enjoylife.bookdream.databinding.HomeHeaderItemBinding;
import com.enjoylife.lookworld.http.RequestImpl;
import com.enjoylife.lookworld.http.cache.ACache;
import com.enjoylife.lookworld.model.HomeModel;
import com.enjoylife.lookworld.model.bean.AndroidBean;
import com.enjoylife.lookworld.model.bean.FrontpageBean;
import com.enjoylife.lookworld.ui.adapter.EmptyAdapter;
import com.enjoylife.lookworld.ui.adapter.HomeRvAdapter;
import com.enjoylife.lookworld.ui.baseView.BaseFragment;
import com.enjoylife.lookworld.ui.view.webview.WebViewActivity;
import com.enjoylife.lookworld.utils.CommonUtils;
import com.enjoylife.lookworld.utils.ConstantUtils;
import com.enjoylife.lookworld.utils.DebugUtil;
import com.enjoylife.lookworld.utils.GlideImageLoader;
import com.enjoylife.lookworld.utils.SPUtils;
import com.enjoylife.lookworld.utils.TimeUtil;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by wangfang on 2017/9/24.
 */

public class HomeFragment extends BaseFragment<HomeFragmentBinding> implements View.OnClickListener {


    private ACache maCache;
    private HomeModel homeModel;
    private ArrayList<String> mBannerImages;
    private HomeHeaderItemBinding headerItemBinding;
    private HomeFootItemBinding footItemBinding;
    private View mHeaderView;
    private View mFooterView;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;
    // 是否是上一天的请求
    private boolean isOldDayRequest;
    private RotateAnimation animation;
    // 记录请求的日期
    String year = getTodayTime().get(0);
    String month = getTodayTime().get(1);
    String day = getTodayTime().get(2);
    private ArrayList<List<AndroidBean>> mLists;
    private HomeRvAdapter mEverydayAdapter;

    @Override
    public int setContent() {
        return R.layout.home_fragment;
    }

    @Override
    protected void onInvisible() {
        // 不可见时轮播图停止滚动
        if (headerItemBinding != null && headerItemBinding.homeBanner != null) {
            headerItemBinding.homeBanner.stopAutoPlay();
        }
    }

    @Override
    protected void loadData() {
        // 显示时轮播图滚动
        if (headerItemBinding != null && headerItemBinding.homeBanner != null) {
            headerItemBinding.homeBanner.startAutoPlay();
            headerItemBinding.homeBanner.setDelayTime(4000);
        }

        if (!mIsVisible || !mIsPrepared) {
            return;
        }

        String oneData = SPUtils.getString("everyday_data", "2016-11-26");
        if (!oneData.equals(TimeUtil.getData())) {// 是第二天
            if (TimeUtil.isRightTime()) {//大于12：30,请求

                isOldDayRequest = false;
                homeModel.setData(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                showRotaLoading(true);
                //loadBannerPicture();
                showContentData();
            } else {// 小于，取缓存没有请求前一天

                ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                homeModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
                year = lastTime.get(0);
                month = lastTime.get(1);
                day = lastTime.get(2);

                isOldDayRequest = true;// 是昨天
                getACacheData();
            }
        } else {// 当天，取缓存没有请求当天

            isOldDayRequest = false;
            getACacheData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();

        bindingView.llLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setRepeatCount(10);
        bindingView.ivLoading.setAnimation(animation);
        animation.startNow();

        maCache = ACache.get(getContext());
        homeModel = new HomeModel();
        headerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.home_header_item,null,false);

        initMiddleClick();
        initRecyclerView();
        bindingView.toolbat.setVisibility(View.GONE);

        mIsPrepared = true;

        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();

    }

    private void initMiddleClick() {
        headerItemBinding.homeHotLayout.homeMiddleTimeLayout.setOnClickListener(this);
    }

    private void initRecyclerView() {
        bindingView.homeRecyclerview.setPullRefreshEnabled(false);
        //bindingView.homeRecyclerview.setLoadingMoreEnabled(false);
        if (mHeaderView == null) {
            mHeaderView = headerItemBinding.getRoot();
            //bindingView.homeRecyclerview.addHeaderView(mHeaderView);
            log.d("add headerview!!!!!!");
        }
        if (mFooterView == null) {
            footItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.home_foot_item, null, false);
            mFooterView = footItemBinding.getRoot();
            bindingView.homeRecyclerview.addFootView(mFooterView, true);
            bindingView.homeRecyclerview.noMoreLoading();
        }
        bindingView.homeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        // 需加，不然滑动不流畅
        bindingView.homeRecyclerview.setNestedScrollingEnabled(false);
        bindingView.homeRecyclerview.setHasFixedSize(false);
        bindingView.homeRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_middle_time_layout:
                break;
            case R.id.home_middle_choose_layout:
                break;
            case R.id.home_middle_book_layout:
                break;
            case R.id.home_middle_movie_layout:
                break;

        }
    }

    /**
     * 获取当天日期
     */
    private ArrayList<String> getTodayTime() {
        String data = TimeUtil.getData();
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        ArrayList<String> list = new ArrayList<>();
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }

    private void showRotaLoading(boolean isLoading) {
        if (isLoading) {
            bindingView.llLoading.setVisibility(View.VISIBLE);
            bindingView.homeRecyclerview.setVisibility(View.GONE);
            animation.startNow();
        } else {
            bindingView.llLoading.setVisibility(View.GONE);
            bindingView.homeRecyclerview.setVisibility(View.VISIBLE);
            animation.cancel();
        }
    }

    /**
     * 取缓存
     */
    private void getACacheData() {
        if (!mIsFirst) {
            return;
        }

        if (mBannerImages != null && mBannerImages.size() > 0) {
            headerItemBinding.homeBanner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
        } else {
            //loadBannerPicture();
        }
        mLists = (ArrayList<List<AndroidBean>>) maCache.getAsObject(ConstantUtils.HOME_CONTENT);
        if (mLists != null && mLists.size() > 0) {
            setAdapter(mLists);
        } else {
            showRotaLoading(true);
            showContentData();
        }
    }

    private void setAdapter(ArrayList<List<AndroidBean>> lists) {
        DebugUtil.debug("wang setAdapter lists = " + lists);
        if(lists == null || lists.size() == 0) return;
        showRotaLoading(false);
        if (mEverydayAdapter == null) {
            mEverydayAdapter = new HomeRvAdapter();
        } else {
            mEverydayAdapter.clear();
        }
        mEverydayAdapter.addAll(lists);
//        DebugUtil.error("----111111 ");
//        bindingView.xrvEveryday.setAdapter(mEverydayAdapter);
//        mEverydayAdapter.notifyDataSetChanged();
//        DebugUtil.error("----222222 ");
        maCache.remove(ConstantUtils.HOME_CONTENT);
        // 缓存三天，这样就可以取到缓存了！
        maCache.put(ConstantUtils.HOME_CONTENT, lists, 259200);

        if (isOldDayRequest) {
            ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
            SPUtils.putString("everyday_data", lastTime.get(0) + "-" + lastTime.get(1) + "-" + lastTime.get(2));
        } else {
            // 保存请求的日期
            SPUtils.putString("everyday_data", TimeUtil.getData());
        }
        mIsFirst = false;

        bindingView.homeRecyclerview.setAdapter(mEverydayAdapter);
        mEverydayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则RecyclerView第一个item会回到顶部
        bindingView.homeRecyclerview.setFocusable(false);
        DebugUtil.error("-----EverydayFragment----onResume()");
        // 开始图片请求
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        DebugUtil.error("-----EverydayFragment----onPause()");
        // 停止全部图片请求 跟随着Activity
        Glide.with(getActivity()).pauseRequests();

    }

    private void loadBannerPicture() {
        FrontpageBean bean = (FrontpageBean) maCache.getAsObject(ConstantUtils.HOME_FOCUS);
        if(bean != null){
            updateBanerPicture(bean);
        }else{
            homeModel.showBanncerPage(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    FrontpageBean bean = (FrontpageBean) object;
                    updateBanerPicture(bean);
                }

                @Override
                public void loadFailed() {

                }

                @Override
                public void addSubscription(Subscription subscription) {
                    HomeFragment.this.addSubscription(subscription);
                }
            });
        }

    }

    private void updateBanerPicture(FrontpageBean bean) {
        if (bean != null && bean.getResult() != null && bean.getResult().getFocus() != null && bean.getResult().getFocus().getResult() != null) {
            if (mBannerImages == null) {
                mBannerImages = new ArrayList<String>();
            } else {
                mBannerImages.clear();
            }
            final List<FrontpageBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX> result = bean.getResult().getFocus().getResult();
            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    //获取所有图片
                    mBannerImages.add(result.get(i).getRandpic());
                }
                headerItemBinding.homeBanner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
                headerItemBinding.homeBanner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        DebugUtil.debug("wangfang position = " + position);
                        position = position - 1;
                        // 链接没有做缓存，如果轮播图使用的缓存则点击图片无效
                        if (result.get(position) != null && result.get(position).getCode() != null
                                && result.get(position).getCode().startsWith("http")) {
                            WebViewActivity.loadUrl(getContext(), result.get(position).getCode(), "加载中...");
                        }
                    }
                });
                maCache.remove(ConstantUtils.HOME_FOCUS);
                maCache.put(ConstantUtils.HOME_FOCUS, bean, 30000);
            }
        }
    }

    /**
     * 加载正文内容
     */
    private void showContentData() {
        homeModel.showRecyclerViewData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mLists != null) {
                    mLists.clear();
                }
                mLists = (ArrayList<List<AndroidBean>>) object;
                if (mLists.size() > 0 && mLists.get(0).size() > 0) {
                    setAdapter(mLists);
                } else {
                    requestBeforeData();
                }
            }

            @Override
            public void loadFailed() {
                if (mLists != null && mLists.size() > 0) {
                    return;
                }
                showError();
            }

            @Override
            public void addSubscription(Subscription subscription) {
                HomeFragment.this.addSubscription(subscription);
            }
        });
    }

    /**
     * 没请求到数据就取缓存，没缓存一直请求前一天数据
     */
    private void requestBeforeData() {
        mLists = (ArrayList<List<AndroidBean>>) maCache.getAsObject(ConstantUtils.HOME_CONTENT);
        if (mLists != null && mLists.size() > 0) {
            setAdapter(mLists);
        } else {
            // 一直请求，知道请求到数据为止
            ArrayList<String> lastTime = TimeUtil.getLastTime(year, month, day);
            homeModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
            year = lastTime.get(0);
            month = lastTime.get(1);
            day = lastTime.get(2);
            showContentData();
        }
    }

    /**
     * 无数据返回时，暂时去掉
     */
    private void setEmptyAdapter() {
        showRotaLoading(false);

        EmptyAdapter emptyAdapter = new EmptyAdapter();
        ArrayList<String> list = new ArrayList<>();
        list.add(CommonUtils.getString(R.string.string_everyday_empty));
        emptyAdapter.addAll(list);
        bindingView.homeRecyclerview.setAdapter(emptyAdapter);

        // 保存请求的日期
        SPUtils.putString("everyday_data", TimeUtil.getData());

        mIsFirst = false;
    }
}
