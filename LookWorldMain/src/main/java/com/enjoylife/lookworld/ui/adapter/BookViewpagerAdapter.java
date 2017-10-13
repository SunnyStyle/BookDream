package com.enjoylife.lookworld.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.enjoylife.lookworld.ui.baseView.BaseFragment;
import com.enjoylife.lookworld.ui.fragment.bookmovienews.MovieFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfang on 2017/9/21.
 */

public class BookViewpagerAdapter extends FragmentPagerAdapter{
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> tabTitles = new ArrayList<>();

    public BookViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(BaseFragment fragment, String titles){
        fragmentList.add(fragment);
        tabTitles.add(titles);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null? 0:fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
