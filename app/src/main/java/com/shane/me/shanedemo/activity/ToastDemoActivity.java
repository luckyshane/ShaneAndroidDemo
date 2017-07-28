package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/07/03
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToastDemoActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_demo);

        ButterKnife.bind(this);


        for (int i = 0; i < 5; i++) {
            ToastUtil.shortToast(this, "这是测试文本： " + i);
        }
    }


    @OnClick(R.id.show_toast_btn)
    void onShowToastBtnClick() {
        ToastUtil.shortToast(this, "哈哈，这是测试文本");
    }


    @Override
    protected void onPause() {
        super.onPause();

        ToastUtil.cancel();
    }
}
