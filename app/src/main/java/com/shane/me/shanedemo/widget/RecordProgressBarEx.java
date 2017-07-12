package com.shane.me.shanedemo.widget;
/*
 * @author: Xian Jingxiong
 * @date: 2017/07/11
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shane.me.shanedemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordProgressBarEx extends RelativeLayout {

    private static final String TAG = RecordProgressBarEx.class.getSimpleName();


    @BindView(R.id.progress_view)
    View progressView;

    @BindView(R.id.slice_view)
    View sliceView;

    @BindView(R.id.progress_text)
    TextView progressText;

    @BindView(R.id.dot_iv)
    View dotView;

    private Context context;

    private int maxProgress;
    private int curProgress;



    public RecordProgressBarEx(Context context) {
        this(context, null);
    }

    public RecordProgressBarEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);

        View rootView = inflater.inflate(R.layout.layout_progress_bar, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(rootView, layoutParams);

        ButterKnife.bind(this, rootView);
    }

    public void setMaxProgress(int maxProgress) {
        if (maxProgress <= 0)
            throw new IllegalArgumentException();
        this.maxProgress = maxProgress;
    }

    public void setSliceProgress(final int sliceProgress) {
        if (maxProgress <= 0)
            throw new IllegalStateException("must set max progress before this");

        if (sliceProgress < 0 || sliceProgress > maxProgress) {
            sliceView.setVisibility(INVISIBLE);
        } else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    int xPos = calProgressPos(sliceProgress);
                    RelativeLayout.LayoutParams params = (LayoutParams) sliceView.getLayoutParams();
                    params.leftMargin = xPos;
                    sliceView.setLayoutParams(params);

                    Log.d(TAG, "setSliceProgress sliceProgress: " + sliceProgress + ", xPos: " + xPos);

                    sliceView.setVisibility(VISIBLE);
                    sliceView.requestLayout();
                }
            });
        }
    }



    public void setText(String text) {
        progressText.setText(text);
    }

    public void updateProgressTo(int progress, long durationInMillis) {
        if (maxProgress <= 0) {
            throw new IllegalStateException("must set maxProgress before update Progress");
        }

        if (progress < 0) {
            progress = 0;
        } else if (progress > maxProgress) {
            progress = maxProgress;
        }
        curProgress = progress;
        int width = calProgressPos(curProgress);

        updateProgressSizeTo(width, durationInMillis);
    }

    private void updateProgressSizeTo(int width, long durationInMillis) {
        if (durationInMillis > 0) {
            ValueAnimator animator = ValueAnimator.ofInt(progressView.getWidth(), width);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int w =(Integer)animation.getAnimatedValue();
                    updateProgressSizeTo(w);
                }
            });

            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(durationInMillis);
            animator.start();
        } else {
            updateProgressSizeTo(width);
        }
    }

    public void startTwinkleDot() {
        AlphaAnimation hideAnimation = new AlphaAnimation(1.0f, 0);
        hideAnimation.setDuration(500);
        hideAnimation.setRepeatCount(Animation.INFINITE);
        hideAnimation.setRepeatMode(Animation.REVERSE);

        dotView.startAnimation(hideAnimation);
    }

    public void stopTwinkleDot() {
        dotView.clearAnimation();
    }

    private void updateProgressSizeTo(int width) {
        int visibility = width <= 0 ? INVISIBLE : VISIBLE;

        dotView.setVisibility(visibility);
        progressText.setVisibility(visibility);

        progressView.getLayoutParams().width = width;
        progressView.requestLayout();
    }

    private int calProgressPos(int progress) {
        float rate = progress * 1.0f / maxProgress;
        int width = getWidth();

        Log.d(TAG, "calProgressPos: " + progress + ", width: " + width);

        return (int) (rate * getWidth());
    }







}
