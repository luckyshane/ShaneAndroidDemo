package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.widget.LiveRecordProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomProgressBarActivity extends BaseActivity {

    @BindView(R.id.progressbar_test)
    LiveRecordProgressBar progressBar;

    static final int MAX_COUNT = 10 * 1000;
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
                progressBar.update();

                if (count < MAX_COUNT) {
                    handler.postDelayed(this, DELAY);
                }
            }
        }, DELAY);
    }







}
