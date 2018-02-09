package com.shane.me.shanedemo.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;
import com.shane.me.shanedemo.widget.LockableBottomSheetBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luckyshane on 2018/2/8.
 */

public class BottomSheetActivity2 extends BaseActivity {
    @BindView(R.id.bottom_sheet_view)
    View bottomSheetView;
    @BindView(R.id.bottom_sheet_content)
    View bottomSheetContent;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.scale_view)
    View scaleView;
    @BindView(R.id.scale_bg)
    View scaleBg;
    @BindView(R.id.top_panel)
    View topPanel;
    @BindView(R.id.scale_status_tv)
    View scaleStatusTv;

    boolean isExpand;
    LockableBottomSheetBehavior bottomSheetBehavior;

    private int scaleToWidth;
    private int scaleToHeight;
    private int defaultWidth;
    private int defaultHeight;
    private int translateDeltaY;
    private int scaleToTop;
    private int scaleViewDefaultTop;
    private ObjectAnimator objectAnimator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet2);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        scaleToWidth = ContextUtil.dip2px(this, 50);
        scaleToHeight = ContextUtil.dip2px(this, 50);
        defaultWidth = ContextUtil.dip2px(this, 100);
        defaultHeight = ContextUtil.dip2px(this, 100);
        translateDeltaY = ContextUtil.dip2px(this, 150);
        scaleToTop = ContextUtil.dip2px(this, 92);

        bottomSheetBehavior = (LockableBottomSheetBehavior) BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setLocked(true);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide slideOffset: " + slideOffset);

            }
        });

        scaleView.post(new Runnable() {
            @Override
            public void run() {
                scaleViewDefaultTop = scaleView.getTop() + scaleView.getHeight() / 2;
                Log.e(TAG, "scaleView top: " + scaleViewDefaultTop);
                initObjectAnimator();
            }
        });

        bottomSheetContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheetView.getLayoutParams();
                    layoutParams.topMargin = ContextUtil.dip2px(BottomSheetActivity2.this, 180);
                    bottomSheetView.setLayoutParams(layoutParams);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    objectAnimator.start();
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    objectAnimator.reverse();
                }
            }
        });
    }

    private void initObjectAnimator() {
        translateDeltaY = scaleToTop - scaleViewDefaultTop;

        Log.d(TAG, "translateDeltaY: " + translateDeltaY);

        PropertyValuesHolder scaleWith = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.5f);
        PropertyValuesHolder scaleHeight = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f);
        PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY", 0, translateDeltaY);

        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(scaleView, scaleWith, scaleHeight, translateY);
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (scaleBg.getVisibility() == View.VISIBLE) {
                    scaleBg.setVisibility(View.INVISIBLE);
                    topPanel.setVisibility(View.VISIBLE);
                    scaleStatusTv.setVisibility(View.VISIBLE);
                } else {
                    scaleBg.setVisibility(View.VISIBLE);
                    topPanel.setVisibility(View.INVISIBLE);
                    scaleStatusTv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }




}
