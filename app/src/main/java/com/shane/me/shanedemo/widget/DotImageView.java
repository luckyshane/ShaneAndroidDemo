package com.shane.me.shanedemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;

/**
 * Created by luckyshane on 2018/4/9.
 */

public class DotImageView extends ImageView {
    private int dotColor;
    private int dotRadiusInPixel;
    private boolean isShowDot;
    private Paint paint;

    public DotImageView(Context context) {
        this(context, null);
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DotImageView);
        dotColor = a.getColor(R.styleable.DotImageView_dot_color, Color.parseColor("#F84B30"));
        dotRadiusInPixel = a.getDimensionPixelSize(R.styleable.DotImageView_dot_radius, ContextUtil.dip2px(context, 6));
        isShowDot = a.getBoolean(R.styleable.DotImageView_is_show_dot, false);
        a.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(dotColor);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowDot) {
            int width = getWidth();
            canvas.drawCircle(width - dotRadiusInPixel, dotRadiusInPixel, dotRadiusInPixel, paint);
        }
    }

    public void showDot(boolean isShowDot) {
        this.isShowDot = isShowDot;
        invalidate();
    }




}
