package com.enjoylife.lookworld.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.enjoylife.bookdream.R;
import com.enjoylife.bookdream.databinding.BookNewsFragmentBinding;
import com.enjoylife.lookworld.model.entities.movieEntities.Movieinfo;
import com.enjoylife.lookworld.model.entities.usBoxEntities.UsBoxEntity;
import com.enjoylife.lookworld.ui.IVew.IMovieLoadData;
import com.enjoylife.lookworld.ui.adapter.BookViewpagerAdapter;
import com.enjoylife.lookworld.ui.baseView.BaseFragment;
import com.enjoylife.lookworld.ui.fragment.bookmovienews.MovieFragment;

/**
 * Created by wangfang on 2017/9/22.
 */

public class BookNewsFragment extends BaseFragment<BookNewsFragmentBinding> {
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public BookNewsFragment() {
    }

    @Override
    public int setContent() {
        return R.layout.book_news_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        showContentView();
    }

    private void initViews() {
        tabLayout = bindingView.booknewsTabLayout;
        viewPager = bindingView.booknewsContentVpager;

        String[] tabArray = getResources().getStringArray(R.array.tab_title);
        BookViewpagerAdapter adapter = new BookViewpagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragments(new MovieFragment(),tabArray[0]);
        adapter.addFragments(new HomeFragment(),tabArray[1]);
        /*adapter.addFragments(new MovieFragment(),tabArray[2]);
        adapter.addFragments(new MovieFragment(),tabArray[3]);
        adapter.addFragments(new MovieFragment(),tabArray[4]);*/
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.top_tab_text_color_selector));
        tabLayout.setSelectedTabIndicatorHeight(getResources().getDimensionPixelSize(R.dimen.tab_selected_height));

    }
}
