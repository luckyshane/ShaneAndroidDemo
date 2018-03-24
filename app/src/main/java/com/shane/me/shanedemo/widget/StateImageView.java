package com.shane.me.shanedemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shane.me.shanedemo.R;

/**
 * Created by luckyshane on 2018/3/24.
 */

public class StateImageView extends View {
    private float alpha; // 0 ~ 1.0f
    private Paint paint;
    private Bitmap backgroundBitmap;
    private Bitmap foregroundBitmap;
    private ValueAnimator valueAnimator;

    public StateImageView(Context context) {
        super(context);
        initView(context, null);
    }

    public StateImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public StateImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StateImageView);
        int backgroundImageId = array.getResourceId(R.styleable.StateImageView_backgroundImageId, 0);
        int foregroundImageId = array.getResourceId(R.styleable.StateImageView_foregroundImageId, 0);
        array.recycle();

        if (backgroundImageId != 0) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(backgroundImageId);
            backgroundBitmap = bitmapDrawable.getBitmap();
        }
        if (foregroundImageId != 0) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(foregroundImageId);
            foregroundBitmap = bitmapDrawable.getBitmap();
        }

        if (backgroundBitmap == null) {
            throw new IllegalArgumentException("must provide background image resource");
        }
        if (foregroundBitmap == null) {
            throw new IllegalArgumentException("must provide foreground image resource");
        }
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (backgroundBitmap != null) {
            paint.setAlpha(getAlphaInt());
            canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        }
        if (foregroundBitmap != null) {
            paint.setAlpha(255 - getAlphaInt());
            canvas.drawBitmap(foregroundBitmap, 0, 0, paint);
        }
    }

    private int getAlphaInt() {
        return (int) (alpha * 255);
    }

    public void showForegroundImage() {
        showForegroundImage(0);
    }

    public void showBackgroundImage() {
        showBackgroundImage(0);
    }

    public void toggle() {
        if (alpha > 0.5) {
            showBackgroundImage(500);
        } else {
            showForegroundImage(500);
        }
    }

    public void showForegroundImage(int duration) {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        getValueAnimator().setDuration(duration);
        valueAnimator.start();
    }

    public void showBackgroundImage(int duration) {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        getValueAnimator().setDuration(duration);
        valueAnimator.reverse();
    }

    private ValueAnimator getValueAnimator() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }
        return valueAnimator;
    }




}
