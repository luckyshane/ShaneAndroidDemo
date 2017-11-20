package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.anim.AnimationsContainer;
import com.shane.me.shanedemo.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckyshane on 2017/11/17.
 */

public class LoadingDemoActivity extends BaseActivity {


    @BindView(R.id.loading_view)
    LoadingView loadingView;
    AnimationsContainer.FramesSequenceAnimation container;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_demo);
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
