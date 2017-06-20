package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/06/20
 */

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PanelShowDemoActivity extends BaseActivity {

    @BindView(R.id.target_panel)
    View targetView;

    @BindView(R.id.invalid_tip_tv)
    View leftTextTv;

    @BindView(R.id.tip_bottom_tv)
    View bottomTipTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_show_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.show_panel_from_bottom_btn)
    void onShowFromBottomBtnClick() {
        if (targetView.getVisibility() != View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_bottom);
            targetView.startAnimation(animation);
            targetView.setVisibility(View.VISIBLE);
        } else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_bottom);
            targetView.startAnimation(animation);
            targetView.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.show_panel_from_left_btn)
    void onShowFromLeftBtnClick() {
        if (targetView.getVisibility() != View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left);
            targetView.startAnimation(animation);
            targetView.setVisibility(View.VISIBLE);
        } else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left);
            targetView.startAnimation(animation);
            targetView.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.show_invalid_tip_from_left_btn)
    void onShowLeftTipBtnClick() {
        if (leftTextTv.getVisibility() != View.VISIBLE) {
            // enter
            int oneDp = ContextUtil.dip2px(PanelShowDemoActivity.this, 1);

            PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                    Keyframe.ofFloat(0, -145 * oneDp),
                    Keyframe.ofFloat(0.167f, 10 * oneDp),
                    Keyframe.ofFloat(0.333f, oneDp),
                    Keyframe.ofFloat(0.5f, 8 * oneDp),
                    Keyframe.ofFloat(0.667f, 3 * oneDp),
                    Keyframe.ofFloat(0.833f, 6 * oneDp),
                    Keyframe.ofFloat(1.0f, 5 * oneDp)
            );

            ObjectAnimator.ofPropertyValuesHolder(leftTextTv, holder).setDuration(800).start();
            leftTextTv.setVisibility(View.VISIBLE);
        } else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left);
            leftTextTv.startAnimation(animation);
            leftTextTv.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.shake_bottom_tip_btn)
    void onShakeBottomTipBtnClick() {
        shakeBottomTip();
    }


    @OnClick(R.id.show_bottom_all_btn)
    void onShowBottomAllBtnClick() {
        if (targetView.getVisibility() != View.VISIBLE) {


            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_bottom);
            animation.setDuration(1000 / 6);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    shakeBottomTip();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            targetView.startAnimation(animation);
            bottomTipTv.startAnimation(animation);

            targetView.setVisibility(View.VISIBLE);
            bottomTipTv.setVisibility(View.VISIBLE);
        } else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_bottom);
            targetView.startAnimation(animation);
            targetView.setVisibility(View.INVISIBLE);
            bottomTipTv.setVisibility(View.INVISIBLE);
        }
    }


    private void shakeBottomTip() {
        Log.d(TAG, "shakeBottomTip");

        PropertyValuesHolder shakeHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0, -5),
                Keyframe.ofFloat(0.25f, 5),
                Keyframe.ofFloat(0.5f, -3),
                Keyframe.ofFloat(0.75f, 3),
                Keyframe.ofFloat(1.0f, 0)
        );
        int width = bottomTipTv.getWidth();
        int height = bottomTipTv.getHeight();

        bottomTipTv.setPivotX(width / 2);
        bottomTipTv.setPivotY(height);
        Log.d(TAG, "newPivotX: " + bottomTipTv.getPivotX() + ", newPivotY:  " + bottomTipTv.getPivotY());

        ObjectAnimator.ofPropertyValuesHolder(bottomTipTv, shakeHolder)
                .setDuration(533).start();
    }






}
