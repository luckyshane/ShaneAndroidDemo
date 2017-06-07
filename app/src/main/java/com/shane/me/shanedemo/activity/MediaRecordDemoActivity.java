package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.shane.me.shanedemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaRecordDemoActivity extends BaseActivity {


    @BindView(R.id.btn_record)
    Button recordBtn;

    private boolean isRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_record_demo);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_record)
    void onRecordBtnClick() {
        if (isRecording) {
            stopRecord();
        } else {
            startRecord();
        }
    }

    private void stopRecord() {
        isRecording = false;
        updateRecordState(isRecording);
    }

    private void startRecord() {
        isRecording = true;

        updateRecordState(isRecording);
    }

    private void updateRecordState(boolean isRecording) {
        recordBtn.setText(isRecording ? "stopRecord" : "startRecord");
    }





}
