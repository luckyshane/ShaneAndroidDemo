package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/05/25
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.Button;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.widget.AnimationDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupWindowDemoActivity extends BaseActivity {

    @BindView(R.id.btn_popup)
    Button popupBtn;

    @BindView(R.id.btn_popup_top)
    Button topBtn;

    @BindView(R.id.btn_popup_bottom)
    Button bottomBtn;


    AnimationDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation_dialog_demo);
        ButterKnife.bind(this);

        dialog = new AnimationDialog(this);
    }

    @OnClick(R.id.btn_popup)
    void onPopupBtnClick() {
        dialog.setAnimationStyle(R.style.popupwindow);
        dialog.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    @OnClick(R.id.btn_popup_top)
    void onPopupTopBtnClick() {
        dialog.show();
    }

    @OnClick(R.id.btn_popup_bottom)
    void onPopupBottomBtnClick() {
        dialog.showAtLocation(getWindow().getDecorView(),Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onBackPressed() {
        if (dialog.isShowing()) {
            dialog.dismiss();
            return;
        }
        super.onBackPressed();
    }



}
