package com.shane.me.shanedemo.widget;

import android.animation.ObjectAnimator;
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
    private static final int MAX_LAYER_COUNT = 3;

    private float alpha = 1.0f; // 0 ~ 1.0f
    private Paint paint;
    private ObjectAnimator valueAnimator;
    private Bitmap[] bitmaps;
    private int showLayerIndex;

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
        bitmaps = new Bitmap[MAX_LAYER_COUNT];
        int[] ids = new int[MAX_LAYER_COUNT];

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StateImageView);
        int layer0Id = array.getResourceId(R.styleable.StateImageView_layer0, 0);
        int layer1Id = array.getResourceId(R.styleable.StateImageView_layer1, 0);
        int layer2Id = array.getResourceId(R.styleable.StateImageView_layer2, 0);

        ids[0] = layer0Id;
        ids[1] = layer1Id;
        ids[2] = layer2Id;
        array.recycle();

        for (int i = 0; i < ids.length; i++) {
            Bitmap tmpBitmap = getBitmap(ids[i]);
            if (tmpBitmap != null) {
                bitmaps[i] = tmpBitmap;
                showLayerIndex = i;
            }
        }
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < bitmaps.length; i++) {
            if (i == showLayerIndex) {
                paint.setAlpha(getAlphaInt());
            } else {
                paint.setAlpha(255 - getAlphaInt());
            }
            Bitmap bitmap = bitmaps[i];
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, paint);
            }
        }
    }

    private int getAlphaInt() {
        return (int) (alpha * 255);
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        invalidate();
    }

    public void showLayer(int layerIndex, int duration) {
        if (layerIndex < 0 || layerIndex >= MAX_LAYER_COUNT) {
            throw new IllegalArgumentException("invalid layerIndex");
        }
        showLayerIndex = layerIndex;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        getAnimator().setDuration(duration);
        valueAnimator.start();
    }

    public void showLayer(int layerIndex) {
        showLayer(layerIndex, 0);
    }

    private ObjectAnimator getAnimator() {
        if (valueAnimator == null) {
            valueAnimator = ObjectAnimator.ofFloat(this, "alpha", 0, 1);
        }
        return valueAnimator;
    }

    private Bitmap getBitmap(int id) {
        if (id != 0) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(id);
            if (bitmapDrawable != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        return null;
    }

    public int getShowLayerIndex() {
        return showLayerIndex;
    }



}
