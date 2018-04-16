package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import com.shane.me.shanedemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckyshane on 2018/4/16.
 */

public class CheckInDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.check_in_btn)
    void onCheckInBtnClick() {
        final ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        final View layer = getLayoutInflater().inflate(R.layout.laoyut_check_in_sucess_show, null);
        decorView.addView(layer);
        View background = layer.findViewById(R.id.background);
        final View tvEarnings = layer.findViewById(R.id.tv_earnings);
        background.setAlpha(1f);

        //文字进入位移
        TranslateAnimation textTranslate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        textTranslate.setInterpolator(PathInterpolatorCompat.create(0.22f, 1f, 0.36f, 1f));
        textTranslate.setDuration(733);
        textTranslate.setStartOffset(150);
        //文字进入透明度
        AlphaAnimation textAlpha = new AlphaAnimation(0, 1);
        textAlpha.setStartOffset(150);
        textAlpha.setInterpolator(new LinearInterpolator());
        textAlpha.setDuration(100);
        //文字退出透明度
        AlphaAnimation textExitAlpha = new AlphaAnimation(1, 0);
        textExitAlpha.setStartOffset(1416);
        textExitAlpha.setInterpolator(new LinearInterpolator());
        textExitAlpha.setDuration(100);
        textExitAlpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvEarnings.setAlpha(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //背景进入动画
        AlphaAnimation backgroundAnim = new AlphaAnimation(0, 1.5f);
        backgroundAnim.setInterpolator(new LinearInterpolator());
        backgroundAnim.setFillAfter(true);
        backgroundAnim.setDuration(150);
        //背景退出动画
        AlphaAnimation backgroundExitAnim = new AlphaAnimation(0.4f, 0);
        backgroundExitAnim.setStartOffset(1516);
        backgroundExitAnim.setInterpolator(new LinearInterpolator());
        backgroundExitAnim.setDuration(150);
        backgroundExitAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束这个 View 就用不到了,直接移除
                decorView.removeView(layer);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AnimationSet textAnimSet = new AnimationSet(false);
        textAnimSet.addAnimation(textTranslate);
        textAnimSet.addAnimation(textAlpha);
        textAnimSet.addAnimation(textExitAlpha);

        AnimationSet bgAnimSet = new AnimationSet(false);
        bgAnimSet.addAnimation(backgroundAnim);
        bgAnimSet.addAnimation(backgroundExitAnim);

        //文字动画
        tvEarnings.startAnimation(textAnimSet);
        //背景动画
        background.startAnimation(bgAnimSet);
    }




}
