package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckyshane on 2017/11/17.
 */

public class LoadingDemo2Activity extends BaseActivity {

    @BindView(R.id.loading_view)
    LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_demo2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_btn)
    void start() {
        loadingView.start();
    }

    @OnClick(R.id.stop_btn)
    void stop() {
        loadingView.stop();
    }



}
