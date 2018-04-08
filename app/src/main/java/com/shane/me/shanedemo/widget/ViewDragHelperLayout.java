package com.shane.me.shanedemo.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by luckyshane on 2018/4/3.
 */

public class ViewDragHelperLayout extends LinearLayout {
    private static final String TAG = ViewDragHelperLayout.class.getSimpleName();
    private ViewDragHelper viewDragHelper;
    private View viewOne;
    private View viewTwo;

    Point viewOneOriginPos = new Point();


    public ViewDragHelperLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - child.getWidth() - getPaddingRight();
                return Math.min(Math.max(leftBound, left), rightBound);
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                int topBound = getPaddingTop();
                int bottomBound = getHeight() - child.getHeight() - getPaddingBottom();
                return Math.min(Math.max(topBound, top), bottomBound);
            }

            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                viewOneOriginPos.x = capturedChild.getLeft();
                viewOneOriginPos.y = capturedChild.getTop();
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                //Toast.makeText(getContext(),"edgeTouched", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                viewDragHelper.captureChildView(viewTwo, pointerId);
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                Log.d(TAG, "onViewReleased");
                //Toast.makeText(getContext(),"onViewReleased", Toast.LENGTH_SHORT).show();
                View firstChild = getChildAt(0);
                if (firstChild == releasedChild) {
                    viewDragHelper.flingCapturedView(getPaddingLeft(), getPaddingTop(),
                            getWidth() - getPaddingRight() - firstChild.getWidth(),
                            getHeight() - getPaddingBottom() - firstChild.getHeight());
                } else {
                    viewDragHelper.settleCapturedViewAt(viewOneOriginPos.x, viewOneOriginPos.y);
                }
                invalidate();
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }
        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewOne = getChildAt(0);
        viewTwo = getChildAt(1);
    }

    @Override
    public void computeScroll() {
        //super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }


}
