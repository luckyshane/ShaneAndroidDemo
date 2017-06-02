package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.widget.LiveRecordProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

public class CustomProgressBarActivity extends BaseActivity {

    private static final int STATE_NORMAL = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_RECORDING = 2;

    @BindView(R.id.progressbar_test)
    LiveRecordProgressBar progressBar;

    @BindView(R.id.btn_press)
    View pressBtn;

    boolean isRecording;
    boolean isLongClick;


    static final int MAX_COUNT = 30 * 1000;
    int count;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar);
        ButterKnife.bind(this);

        progressBar.setMaxProgress(MAX_COUNT);
        progressBar.addSliceProgress(3 * 1000);
        testProgressCount();
    }

    private void testProgressCount() {
        final int DELAY = 16;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count += DELAY;
                progressBar.setText(String.format("%.0f%%", progressBar.getCurProgressRate() * 100));

                progressBar.setCurProgress(count);
                if (count < MAX_COUNT) {
                    handler.postDelayed(this, DELAY);
                }
            }
        }, DELAY);
    }


    @OnClick(R.id.btn_press)
    void onBtnClick() {
        isLongClick = false;
    }

    @OnLongClick(R.id.btn_press)
    boolean onLongClick() {
        isLongClick = true;
        return true;
    }

    @OnTouch(R.id.btn_press)
    boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isLongClick = false;
                setButtonState(STATE_PRESSED);

                isRecording = !isRecording;
                break;
            case MotionEvent.ACTION_UP:
                if (isLongClick) {
                    setButtonState(STATE_NORMAL);
                    if (isRecording) {
                        isRecording = false;
                    }
                } else {
                    setButtonState(isRecording ? STATE_RECORDING : STATE_NORMAL);
                }
                break;
        }

        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.startAutoDraw();
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.stopAutoDraw();
    }


    void setButtonState(int state) {
        switch (state) {
            case STATE_NORMAL:
                pressBtn.setBackgroundResource(R.drawable.button_record_normal);
                break;
            case STATE_PRESSED:
                pressBtn.setBackgroundResource(R.drawable.button_record_pressed);
                break;
            case STATE_RECORDING:
                pressBtn.setBackgroundResource(R.drawable.button_recording);
                break;
            default:
                pressBtn.setBackgroundResource(R.drawable.button_record_normal);
        }
    }







}
