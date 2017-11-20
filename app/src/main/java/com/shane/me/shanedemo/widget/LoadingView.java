package com.shane.me.shanedemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.anim.AnimationsContainer;


/**
 * Created by luckyshane on 2017/11/9.
 */

public class LoadingView extends LinearLayout {
    private ImageView loadingIv;
    private AnimationDrawable animationDrawable;
    private AnimationsContainer.FramesSequenceAnimation framesSequenceAnimation;

    public LoadingView(Context context) {
        super(context);
        initView(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this);
        loadingIv = (ImageView) findViewById(R.id.loading_iv);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
            int loadingDrawableId = a.getResourceId(R.styleable.LoadingView_loadingDrawable, 0);
            int frameArrayId = a.getResourceId(R.styleable.LoadingView_loadingFrameArray, 0);

            if (loadingDrawableId != 0) {
                loadingIv.setImageResource(loadingDrawableId);
                animationDrawable = (AnimationDrawable) loadingIv.getDrawable();
            } else if (frameArrayId != 0) {
                int fps = a.getInteger(R.styleable.LoadingView_loadingFps, 15);
                framesSequenceAnimation = new AnimationsContainer(context, frameArrayId, fps).createProgressDialogAnim(loadingIv);
            }
            a.recycle();
        }
    }

    public void start() {
        if (animationDrawable != null) {
            animationDrawable.start();
        } else if (framesSequenceAnimation != null) {
            framesSequenceAnimation.start();
        }
    }

    public void stop() {
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        if (framesSequenceAnimation != null) {
            framesSequenceAnimation.stop();
        }
    }


}
