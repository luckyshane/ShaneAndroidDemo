package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/07/14
 */

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WindowManagerDemoActivity extends BaseActivity {

    private WindowManager windowManager;

    private View pushView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_window_manger);

        ButterKnife.bind(this);

        windowManager = getWindowManager();

        pushView = LayoutInflater.from(this).inflate(R.layout.toast_top, null);
    }


    @OnClick(R.id.go_btn)
    void onGoBtnClick() {
        Log.d(TAG, "onGoBtnClick");
        showView();

        //HeadsUp.notify(this, "Hello");
    }

    private void showView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = -ToastUtil.getStatusBarHeight(this);

        params.type = WindowManager.LayoutParams.TYPE_TOAST;

        windowManager.addView(pushView, params);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        windowManager.removeView(pushView);
                    }
                });
            }
        }, 3000);
    }











}
