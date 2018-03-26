package com.shane.me.shanedemo.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.widget.StateImageView;

import static com.shane.me.shanedemo.R.id.test_iv3;

/**
 * Created by luckyshane on 2018/3/24.
 */

public class StateImageViewActivity extends BaseActivity {
    private AnimatorSet animatorSet;
    private boolean test2Show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_image_view);
        initView();
    }

    private void initView() {
        final View testIv = findViewById(R.id.test_iv);
        final View testIv2 = findViewById(R.id.test_iv2);

        final ValueAnimator alphaShow = ObjectAnimator.ofFloat(0, 1);
        alphaShow.setDuration(500);

        alphaShow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                testIv2.setAlpha(alpha);
                testIv.setAlpha(1 - alpha);
            }
        });

        findViewById(R.id.test_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test2Show) {
                    alphaShow.reverse();
                } else {
                    alphaShow.start();
                }
                test2Show = !test2Show;
            }
        });
        final StateImageView iv3 = findViewById(test_iv3);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iv3.getShowLayerIndex() == 0) {
                    iv3.showLayer(1, 500);
                } else {
                    iv3.showLayer(0, 500);
                }
            }
        });

    }




}
