package com.shane.me.shanedemo.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimDemoActivity extends BaseActivity {

    @BindView(R.id.btn_target)
    Button targetBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_show)
    void onShow() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(targetBtn, "alpha", 0, 1.0f);
        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(targetBtn, "translationY", ContextUtil.dip2px(this, 20), 0);

        AnimatorSet set = new AnimatorSet();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                targetBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        set.playTogether(alphaAnimator, transAnimator);
        set.setDuration(500);
        set.start();
    }

    @OnClick(R.id.btn_translate)
    void onTranslate() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetBtn, "translationY", 10, -200);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }

    @OnClick(R.id.btn_scale)
    void onScale() {
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(targetBtn, "scaleX", 0, 1.2f, 1);
        scaleAnimator.setDuration(3000);
        scaleAnimator.start();
    }

    @OnClick(R.id.btn_alpha)
    void onAlpha() {
        ObjectAnimator alpahAnimator = ObjectAnimator.ofFloat(targetBtn, "alpha", 0, 1.0f);
        alpahAnimator.setDuration(3000);
        alpahAnimator.start();
    }


    @OnClick(R.id.btn_all)
    void onAllAnim() {
        AnimatorSet showAnimSet = new AnimatorSet();

        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(targetBtn, "translationY", 0, -200);
        //translateAnimator.setInterpolator(new AccelerateInterpolator());
        translateAnimator.setDuration(1000);

        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(targetBtn, "scaleX", 0, 1);
        scaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimator.setDuration(1000);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(targetBtn, "scaleY", 0, 1);
        scaleYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimator.setDuration(1000);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(targetBtn, "alpha", 0, 1.0f);
        //alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimator.setDuration(500);

        showAnimSet.playTogether(translateAnimator, scaleAnimator, scaleYAnimator, alphaAnimator);

        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(targetBtn, "translationY",
                -200,
                -220,
                -180,
                -210,
                -190,
                -200);
        //shakeAnimator.setRepeatCount(1);
        shakeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        shakeAnimator.setDuration(1000);


        AnimatorSet allSet = new AnimatorSet();
        allSet.playSequentially(showAnimSet, shakeAnimator);
        allSet.setInterpolator(new LinearInterpolator());

        allSet.start();
    }







}
