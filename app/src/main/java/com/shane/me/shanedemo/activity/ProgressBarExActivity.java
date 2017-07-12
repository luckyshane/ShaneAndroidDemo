package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/07/11
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.widget.RecordProgressBarEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressBarExActivity extends BaseActivity {


    @BindView(R.id.test_progressbar)
    RecordProgressBarEx pb;

    Handler uiHandler = new Handler(Looper.getMainLooper());

    Runnable updateRunnable;

    int progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_progress_bar_ex_demo);
        ButterKnife.bind(this);

        pb.setMaxProgress(30000);
        pb.setSliceProgress(3000);
    }

    @OnClick(R.id.test_btn)
    void onTestBtnClick() {
        startUpdate();
    }


    private void startUpdate() {
        if (updateRunnable == null) {
            updateRunnable = new Runnable() {
                @Override
                public void run() {
                    progress += 200;

                    if (progress > 30000) {
                        progress = 30000;
                    }
                    pb.updateProgressTo(progress, 300);
                    pb.setText(millisToTimeDesc(progress));

                    if (progress < 30000 && !isFinishing()) {
                        uiHandler.postDelayed(this, 100);
                    } else {
                        pb.stopTwinkleDot();
                    }

                }
            };

            uiHandler.postDelayed(updateRunnable, 100);
            pb.startTwinkleDot();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (updateRunnable != null) {
            uiHandler.removeCallbacks(updateRunnable);
        }
    }


    /**
     * 将毫秒转化成"mm:ss"的格式
     */
    public static String millisToTimeDesc(int millis) {
        int seconds = (int) Math.floor(millis * 1.0f / 1000);
        int minutes = seconds / 60;
        int remainSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainSeconds);
    }



}
