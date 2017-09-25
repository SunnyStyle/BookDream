package com.enjoylife.lookworld.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enjoylife.bookdream.R;
import com.enjoylife.bookdream.databinding.BookNewsFragmentBinding;
import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.lookworld.ui.IVew.DataView;
import com.enjoylife.lookworld.ui.adapter.BookViewpagerAdapter;
import com.enjoylife.lookworld.ui.baseView.BaseFragment;

/**
 * Created by wangfang on 2017/9/22.
 */

public class BookNewsFragment extends BaseFragment implements DataView {
    private BookNewsFragmentBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public BookNewsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_news_fragment,container,false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        tabLayout = binding.booknewsTabLayout;
        viewPager = binding.booknewsContentVpager;

        String[] tabArray = getResources().getStringArray(R.array.tab_title);
        BookViewpagerAdapter adapter = new BookViewpagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragments(new MovieFragment(),tabArray[0]);
        adapter.addFragments(new MovieFragment(),tabArray[1]);
        adapter.addFragments(new MovieFragment(),tabArray[2]);
        adapter.addFragments(new MovieFragment(),tabArray[3]);
        adapter.addFragments(new MovieFragment(),tabArray[4]);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.top_tab_text_color_selector));
        tabLayout.setSelectedTabIndicatorHeight(getResources().getDimensionPixelSize(R.dimen.tab_selected_height));

    }

    @Override
    public void loadData(UsBoxEntity usBoxEntity, Movieinfo comingSoon, Movieinfo inThreatEntity) {

    }

    @Override
    public void startLoading() {
        super.startLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
    }
}
