package com.antocistudios.uv.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.antocistudios.moviecatalog.R;
import com.antocistudios.uv.domain.MainActivityModule;
import com.antocistudios.uv.presenter.MainActivityPresenter;
import com.antocistudios.uv.presenter.view.MainActivityView;
import com.antocistudios.uv.ui.adapter.SlidePagerAdapter;
import com.antocistudios.uv.ui.base.BaseActivity;
import com.antocistudios.uv.ui.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adrian Antoci.
 *
 */
public class MainActivity extends BaseActivity implements MainActivityView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    public MainActivityPresenter mPresenter;

    @Bind(R.id.toolBar) Toolbar mToolBar;
    @Bind(R.id.viewPager) ViewPager mViewPager;

    private SlidePagerAdapter mPagerAdapter;
    private SlidingTabLayout mPagerTabStrip;

    public MainActivity() {
        super(TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupView();

        mPresenter.setView(this);
        mPresenter.onViewAttached();
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new MainActivityModule());
        return modules;
    }

    private void setupView() {
        setSupportActionBar(mToolBar);

        mPagerAdapter = new SlidePagerAdapter(this, getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mPagerTabStrip = (SlidingTabLayout) findViewById(R.id.pager_title_strip);
        mPagerTabStrip.setDistributeEvenly(true);
        mPagerTabStrip.setSelectedIndicatorColors(getResources().getColor(R.color.md_white_1000));

        mPagerTabStrip.setViewPager(mViewPager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDetached();
    }

    @Override
    public void displayInfoMessage(String msg) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_action_try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.onTryAgain();
                    }
                })
                .show();
    }

    @Override
    public void displayWelcomeMessage(String msg) {
        //
    }


}
