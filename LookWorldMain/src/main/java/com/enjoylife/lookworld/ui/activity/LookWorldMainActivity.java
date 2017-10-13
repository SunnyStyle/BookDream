package com.enjoylife.lookworld.ui.activity;

import android.app.Application;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.enjoylife.bookdream.R;
import com.enjoylife.bookdream.databinding.ActivityLookWorldMainBinding;
import com.enjoylife.lookworld.application.Log;
import com.enjoylife.lookworld.application.MyApplication;
import com.enjoylife.lookworld.ui.adapter.MainViewPagerAdapter;
import com.enjoylife.lookworld.ui.fragment.BookNewsFragment;
import com.enjoylife.lookworld.ui.fragment.DiscoveryFragment;
import com.enjoylife.lookworld.ui.fragment.HomeFragment;
import com.enjoylife.lookworld.ui.fragment.HomeMineFragment;
import com.enjoylife.lookworld.ui.fragment.NotifycationFragment;
import com.enjoylife.lookworld.ui.view.NotScrollViewpager;
import com.enjoylife.lookworld.ui.view.statusbar.StatusBarUtil;
import com.enjoylife.lookworld.ui.view.statusbar.StatusBarUtilFont;
import com.enjoylife.lookworld.utils.CommonUtils;
import com.enjoylife.lookworld.utils.ConstantUtils;

import java.lang.reflect.Field;

public class LookWorldMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Log log = Log.YLog();

    private ActivityLookWorldMainBinding activityLookWorldMainBinding;
    private BottomNavigationView bottomNavigationView;
    private NotScrollViewpager viewpager;
    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLookWorldMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_look_world_main);

        initStatusView();

        initToolbar();
        initDrawerlayout();
        initNavigationView();

        initBottomView();

        setupViewpager();
        //StatusBarUtil.setColorNoTranslucentForDrawerLayout(LookWorldMainActivity.this, drawer,
        //getResources().getColor(R.color.colorTheme));

    }

    private void initBottomView() {
        bottomNavigationView = activityLookWorldMainBinding.appBarMain.bottomNavigation;
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationItemListener);
    }

    private void initView() {

    }

    private void initNavigationView() {
        NavigationView navigationView = activityLookWorldMainBinding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    private void initDrawerlayout() {
        drawer = activityLookWorldMainBinding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initToolbar() {
        /*Toolbar toolbar = activityLookWorldMainBinding.appBarMain.toolbar;
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }*/
    }

    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = activityLookWorldMainBinding.appBarMain.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        activityLookWorldMainBinding.appBarMain.viewStatus.setLayoutParams(layoutParams);
    }

    private void setupViewpager() {
        viewpager = activityLookWorldMainBinding.appBarMain.appMainActivityVpager;
        viewpager.setNoScroll(true);
        MainViewPagerAdapter viewMainAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewMainAdapter.addFragment(new HomeMineFragment());
        viewMainAdapter.addFragment(new BookNewsFragment());
        viewMainAdapter.addFragment(new DiscoveryFragment());
        viewMainAdapter.addFragment(new NotifycationFragment());
        viewpager.setAdapter(viewMainAdapter);
    }

    BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationItemListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            log.d("navigation clicked");
            switch (itemId){
                case R.id.navigation_home:
                    ConstantUtils.toolbarSearchType = 0;
                    StatusBarUtilFont.StatusBarLightMode(LookWorldMainActivity.this, false);
                    StatusBarUtilFont.setStatusBarColor(LookWorldMainActivity.this, getResources().getColor(R.color.colorPrimary_home), false);
                    viewpager.setCurrentItem(0);
                    break;
                case R.id.navigation_book_world:
                    ConstantUtils.toolbarSearchType = 1;
                    StatusBarUtilFont.setStatusBarColor(LookWorldMainActivity.this, getResources().getColor(R.color.colorPrimary), false);
                    StatusBarUtilFont.StatusBarLightMode(LookWorldMainActivity.this, true);
                    viewpager.setCurrentItem(1);
                    break;
                case R.id.navigation_discovery:
                    ConstantUtils.toolbarSearchType = 2;
                    StatusBarUtilFont.StatusBarLightMode(LookWorldMainActivity.this, true);
                    viewpager.setCurrentItem(2);
                    break;
                case R.id.navigation_notifications:
                    ConstantUtils.toolbarSearchType = 3;
                    StatusBarUtilFont.StatusBarLightMode(LookWorldMainActivity.this, true);
                    viewpager.setCurrentItem(3);
                    break;
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = activityLookWorldMainBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_look_world_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie) {
            // Handle the camera action
        } else if (id == R.id.nav_book) {

        } else if (id == R.id.nav_soap) {

        } else if (id == R.id.nav_city) {

        } else if (id == R.id.nav_music) {

        } else if (id == R.id.nav_marker) {

        } else if (id == R.id.nav_acount) {

        } else if (id == R.id.nav_weather) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
