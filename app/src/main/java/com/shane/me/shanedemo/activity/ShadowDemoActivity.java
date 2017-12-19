package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luckyshane on 2017/10/27.
 */

public class ShadowDemoActivity extends BaseActivity {

    @BindView(R.id.shadow_btn)
    View shadowBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_demo);
        ButterKnife.bind(this);

        ViewCompat.setElevation(shadowBtn, ContextUtil.dip2px(this, 4));
    }



}
