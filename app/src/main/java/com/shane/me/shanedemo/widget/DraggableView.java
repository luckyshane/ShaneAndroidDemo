package com.shane.me.shanedemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luckyshane on 2018/3/1.
 */

public class DraggableView extends View {
    private int mLastX;
    private int mLastY;

    public DraggableView(Context context) {
        super(context);
    }

    public DraggableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                setTranslationX(getTranslationX() + deltaX);
                setTranslationY(getTranslationY() + deltaY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }


}
