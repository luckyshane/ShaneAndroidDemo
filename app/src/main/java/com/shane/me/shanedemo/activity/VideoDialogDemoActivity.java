package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/07/05
 */

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.widget.VideoViewDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoDialogDemoActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview_dialog_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.show_dialog_btn)
    void onShowDialogBtnClick() {
        new VideoViewDialog(this).show();
    }








}
