package com.shane.me.shanedemo.widget;
/*
 * @author: Xian Jingxiong
 * @date: 2017/05/22
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;

import java.util.ArrayList;
import java.util.List;


public class LiveRecordProgressBar extends View {

    private static final int DEFAULT_AUTO_DRAW_INTERVAL = 16 * 2;   // 16ms更新一次则刷新率约为60，这里没必要更新那么频繁
    private static final long TWINKLE_INTERVAL_IN_MILLIS = 499;

    private static final float DEFAULT_TEXT_SIZE_IN_SP = 10;
    private static final float DEFAULT_PADDING_VERTICAL_IN_DP = 2;

    private static final float DEFAULT_CIRCLE_DOT_RADIUS_IN_DP = 5;
    private static final float DEFAULT_CIRCLE_DOT_MARGIN_IN_DP = 5;

    /**
     * 进度条中打点信息宽度，以dp为单位
     */
    private static final float DEFAULT_SLICE_WIDTH_IN_DP = 2;

    /**
     * 进度条总共分片数
     */
    private static final int SLICES_COUNT = 10000;
    /**
     * 进度条每一片的宽度，以px为单位
     */
    private float SLICE_WIDTH_IN_PX;


    private Context context;
    private String text;

    private int maxProgress;
    private int curProgress;
    private float curProgressRate;

    private List<Float> sliceRates;

    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint textPaint;
    private Paint circleDotPaint;
    private Paint slicePaint;

    private int circleDotColor;
    private int circleDotColorDark;
    private float circleDotRadiusInPx;

    private boolean twinkleCircleDotEnabled = true;

    private long t1;
    private int twinkleCount;


    private Handler handler = new Handler(Looper.getMainLooper());


    public LiveRecordProgressBar(Context context) {
        this(context, null);
    }

    public LiveRecordProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveRecordProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LiveRecordProgressBar);

        int backgroundColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_recordProgressBar_backgroundColor, Color.GRAY);
        int progressColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_recordProgressBar_progressColor, Color.CYAN);
        int progressSliceColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_recordProgressBar_progressSliceColor, Color.WHITE);
        circleDotColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_recordProgressBar_circleDotColor, Color.WHITE);
        circleDotColorDark = typedArray.getColor(R.styleable.LiveRecordProgressBar_recordProgressBar_circleDotColorDark, Color.DKGRAY);
        circleDotRadiusInPx = typedArray.getDimension(R.styleable.LiveRecordProgressBar_recordProgressBar_circleDotRadius, dip2px(DEFAULT_CIRCLE_DOT_RADIUS_IN_DP));
        float textSizeInPx = typedArray.getDimension(R.styleable.LiveRecordProgressBar_android_textSize, dip2px(DEFAULT_TEXT_SIZE_IN_SP));
        int textColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_android_textColor, Color.RED);
        text = typedArray.getString(R.styleable.LiveRecordProgressBar_android_text);
        float sliceWidth = typedArray.getDimension(R.styleable.LiveRecordProgressBar_recordProgressBar_sliceWidth, dip2px(DEFAULT_SLICE_WIDTH_IN_DP));

        typedArray.recycle();

        backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);

        progressPaint = new Paint();
        progressPaint.setColor(progressColor);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSizeInPx);
        textPaint.setStyle(Paint.Style.FILL);

        circleDotPaint = new Paint();
        circleDotPaint.setAntiAlias(true);
        circleDotPaint.setColor(circleDotColor);

        slicePaint = new Paint();
        slicePaint.setColor(progressSliceColor);
        slicePaint.setStrokeWidth(sliceWidth);

        sliceRates = new ArrayList<>();
    }

    public void startAutoDraw() {
        startAutoDraw(DEFAULT_AUTO_DRAW_INTERVAL);
    }

    /**
     * 启动自动更新，定时绘制UI。调用此方法后，外部在更新了设置后，不需要手动调用{@link #update()}方法
     * @param delayInMillis 刷新间隔，时间不建议超过1s
     */
    public void startAutoDraw(int delayInMillis) {
        if (delayInMillis < DEFAULT_AUTO_DRAW_INTERVAL) {
            delayInMillis = DEFAULT_AUTO_DRAW_INTERVAL;
        }
        final int delay = delayInMillis;
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoDraw();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void stopAutoDraw() {
        handler.removeCallbacksAndMessages(null);
    }

    public void enableTwinkleCircleDot(boolean enable) {
        twinkleCircleDotEnabled = enable;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMaxProgress(int maxProgress) {
        if (maxProgress <= 0)
            throw new IllegalArgumentException();

        this.maxProgress = maxProgress;
    }

    public void setCurProgress(int curProgress) {
        if (curProgress < 0) {
            curProgress = 0;
        } else if (curProgress > maxProgress) {
            curProgress = maxProgress;
        }
        this.curProgress = curProgress;

        if (maxProgress > 0) {
            curProgressRate = curProgress * 1.0f / maxProgress;
        } else {
            curProgressRate = 0;
        }
    }

    public int getCurProgress() {
        return curProgress;
    }

    public void setCurProgressRate(float rate) {
        this.curProgressRate = resolveRate(rate);

        if (maxProgress > 0) {
            this.curProgress = (int) (maxProgress * curProgressRate);
        }
    }

    public float getCurProgressRate() {
        return curProgressRate;
    }

    public void addSliceRate(float sliceRate) {
        this.sliceRates.add(resolveRate(sliceRate));
    }

    public void addSliceProgress(int progress) {
        if (maxProgress <= 0) {
            throw new IllegalStateException("must set max progress before this invoke !");
        }

        float rate = progress * 1.0f / maxProgress;
        this.sliceRates.add(resolveRate(rate));
    }

    private float resolveRate(float rate) {
        if (rate < 0) {
            return 0;
        } else if (rate > 1) {
            return 1;
        }
        return rate;
    }


    /**
     * 更新UI。在调用一些设置进度条参数方法（比如，{@link #setCurProgress(int)}）之后，必须调用此方法来更新
     */
    public void update() {
        invalidate();
    }

    private void autoDraw() {
        twinkleCircleDot();
        invalidate();
    }

    private void twinkleCircleDot() {
        if (twinkleCircleDotEnabled) {
            long t2 = System.currentTimeMillis();
            if (t2 - t1 >= TWINKLE_INTERVAL_IN_MILLIS) {
                if (twinkleCount % 2 == 0) {
                    circleDotPaint.setColor(circleDotColor);
                } else {
                    circleDotPaint.setColor(circleDotColorDark);
                }
                twinkleCount++;
                t1 = t2;
            }
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        SLICE_WIDTH_IN_PX = getWidth() * 1.0f / SLICES_COUNT;
    }

    private int measureWidth(int widthMeasureSpec) {
        return MeasureSpec.getSize(widthMeasureSpec);
    }

    private int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        int result;
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            result = calTextHeight() + dip2px(DEFAULT_PADDING_VERTICAL_IN_DP) * 2;

            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    private int calTextHeight() {
        Rect textBounds = new Rect();
        String testText = "xyz88";

        textPaint.getTextBounds(testText, 0, testText.length(), textBounds);
        return textBounds.height();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (getVisibility() != VISIBLE) {
            return;
        }
        drawBackground(canvas);
        drawProgress(canvas);
        drawProgressSlices(canvas);
        drawCircleDotAndText(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
    }

    /**
     * 绘制实际进度
     */
    private void drawProgress(Canvas canvas) {
        float right = curProgressRate * SLICES_COUNT * SLICE_WIDTH_IN_PX;
        canvas.drawRect(0, 0, right, getHeight(), progressPaint);
    }

    /**
     * 绘制进度条打点信息
     */
    private void drawProgressSlices(Canvas canvas) {
        for (float sliceRate : sliceRates) {
            float x = sliceRate * SLICES_COUNT * SLICE_WIDTH_IN_PX;
            float startY = 0;
            float endY = getHeight();
            canvas.drawLine(x, startY, x, endY, slicePaint);
        }
    }

    private void drawCircleDotAndText(Canvas canvas) {
        if (!TextUtils.isEmpty(text)) {
            // 仅仅当文本不为空的时候才绘制圆点

            Rect textBounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), textBounds);
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();

            float circleDotMarginPx = dip2px(DEFAULT_CIRCLE_DOT_MARGIN_IN_DP);

            // 文字和圆点以及其中间距的总长
            int totalWidth = (int) (circleDotRadiusInPx * 2 + circleDotMarginPx + textBounds.width());

            float circleDotX = (getWidth() - totalWidth) / 2 + circleDotRadiusInPx;
            float circleDotY = getHeight() / 2;

            canvas.drawCircle(circleDotX, circleDotY, circleDotRadiusInPx, circleDotPaint);

            float textLeft = circleDotX + circleDotRadiusInPx + circleDotMarginPx;
            float textBaseLine = (getHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;

            canvas.drawText(text, textLeft, textBaseLine, textPaint);
        }
    }

    private int dip2px(float dp) {
        return ContextUtil.dip2px(context, (int) dp);
    }

    private int dip2px(int dp) {
        return ContextUtil.dip2px(context, dp);
    }

    private int sp2px(float sp) {
        return ContextUtil.sp2px(context, sp);
    }






}
