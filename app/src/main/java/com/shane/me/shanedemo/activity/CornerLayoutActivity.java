package com.shane.me.shanedemo.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;
import com.shane.me.shanedemo.widget.RCRelativeLayout;

/**
 * Created by luckyshane on 2018/3/26.
 */

public class CornerLayoutActivity extends BaseActivity {

    RCRelativeLayout rcLayout;
    int collpaseMarginLeft;
    ObjectAnimator collpaseAnimator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corner_layout);

        rcLayout = findViewById(R.id.rc_layout);

        rcLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapseRcLayout(500);
                } else if (isCollapse()) {
                    expandRcLayout(500);
                }
            }
        });
        int peekWidth = ContextUtil.dip2px(this, 50);
        collpaseMarginLeft = ContextUtil.getScreenWidth(this) - peekWidth;
        collapseRcLayout(0);
    }

    private void expandRcLayout(int duration) {
        ObjectAnimator animator = getCollapseAnimator();
        animator.cancel();

        animator.setDuration(duration);
        animator.reverse();
    }

    private void collapseRcLayout(int duration) {
        ObjectAnimator animator = getCollapseAnimator();
        animator.cancel();

        animator.setDuration(duration);
        animator.start();
    }

    private ObjectAnimator getCollapseAnimator() {
        if (collpaseAnimator == null) {
            PropertyValuesHolder transHolder = PropertyValuesHolder.ofFloat("translationX", 0, collpaseMarginLeft);
            PropertyValuesHolder leftRaduisHolder = PropertyValuesHolder.ofFloat("leftRadius", 0, ContextUtil.dip2px(this, 18));
            collpaseAnimator = ObjectAnimator.ofPropertyValuesHolder(rcLayout, transHolder, leftRaduisHolder);
        }
        return collpaseAnimator;
    }

    private boolean isCollapse() {
        return rcLayout.getTranslationX() == collpaseMarginLeft;
    }

    private boolean isExpanded() {
        return rcLayout.getTranslationX() == 0;
    }



}
