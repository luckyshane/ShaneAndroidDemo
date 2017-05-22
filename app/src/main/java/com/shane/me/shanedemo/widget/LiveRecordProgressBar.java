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
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ContextUtil;


public class LiveRecordProgressBar extends View {

    private static final float DEFAULT_TEXT_SIZE = 12;
    private static final float DEFAULT_PADDING_VERTICAL = 2;

    private static final float DEFAULT_CIRCLE_DOT_RADIUS = 5;
    private static final float DEFAULT_CIRCLE_DOT_MARGIN = 5;

    private Context context;

    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint textPaint;
    private Paint circleDotPaint;


    private int backgroundColor;
    private int progressColor;
    private int circleDotColor;
    private int circleDotColorDark;
    private int progressSliceColor;
    private int textColor;
    private float textSize;
    private float circleDotRadius;


    private String text;


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

        backgroundColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_backgroundColor, Color.CYAN);
        progressColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_progressColor, Color.CYAN);
        progressSliceColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_progressSliceColor, Color.WHITE);
        circleDotColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_circleDotColor, Color.WHITE);
        circleDotColorDark = typedArray.getColor(R.styleable.LiveRecordProgressBar_circleDotColorDark, Color.GRAY);
        circleDotRadius = typedArray.getDimension(R.styleable.LiveRecordProgressBar_circleDotRadius, dip2px(DEFAULT_CIRCLE_DOT_RADIUS));
        textSize = typedArray.getDimension(R.styleable.LiveRecordProgressBar_textSize, dip2px(DEFAULT_TEXT_SIZE));
        textColor = typedArray.getColor(R.styleable.LiveRecordProgressBar_textColor, Color.RED);
        text = typedArray.getString(R.styleable.LiveRecordProgressBar_text);

        typedArray.recycle();

        backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);

        progressPaint = new Paint();
        progressPaint.setColor(progressColor);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(ContextUtil.sp2px(context, (int) textSize));
        textPaint.setStyle(Paint.Style.FILL);

        circleDotPaint = new Paint();
        circleDotPaint.setColor(circleDotColor);

    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
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
            result = calTextHeight() + dip2px(DEFAULT_PADDING_VERTICAL) * 2;

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
        super.onDraw(canvas);

        drawBackground(canvas);
        drawProgress(canvas);
        drawCircleDotAndText(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
    }

    private void drawProgress(Canvas canvas) {


    }

    private void drawCircleDotAndText(Canvas canvas) {
        if (!TextUtils.isEmpty(text)) {
            // 仅仅当文本不为空的时候才绘制圆点

            Rect textBounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), textBounds);
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();

            // 文字和圆点以及其中间距的总长
            float circleDotMarginPx = dip2px(DEFAULT_CIRCLE_DOT_MARGIN);

            int totalWidth = (int) (circleDotRadius * 2 + circleDotMarginPx + textBounds.width());

            float circleDotX = (getWidth() - totalWidth) / 2 + circleDotRadius;
            float circleDotY = getHeight() / 2;

            canvas.drawCircle(circleDotX, circleDotY, circleDotRadius, circleDotPaint);

            int textHeight = textBounds.height();

            Log.d("drawCircleDotAndText", "height: " + getHeight() + ", textHeight: " + textHeight);


            float textLeft = circleDotX + circleDotRadius + circleDotMarginPx;
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
