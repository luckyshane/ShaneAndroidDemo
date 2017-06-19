package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/06/19
 */

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shane.me.shanedemo.R;
import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RippleDemoActivity extends BaseActivity {

    @BindView(R.id.layout_ripple)
    RippleBackground rippleBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_ripple)
    void onRippleBtnClick() {
        if (rippleBackground.isRippleAnimationRunning()) {
            rippleBackground.stopRippleAnimation();
        } else  {
            rippleBackground.startRippleAnimation();
        }
    }

}
