package com.shane.me.shanedemo.widget;
/*
 * @author: Xian Jingxiong
 * @date: 2017/05/25
 */

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationDialog extends PopupWindow {

    private Activity context;

    @BindView(R.id.panel_share)
    View sharePanel;

    @BindView(R.id.btn_dialog_test)
    Button testBtn;

    @BindView(R.id.btn_cancel)
    View cancelBtn;


    public AnimationDialog(Activity context) {
        super(context);
        init(context);
    }

    private void init(Activity context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_animation_dialog, null);

        ButterKnife.bind(this, rootView);

        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        int height = context.getWindowManager().getDefaultDisplay().getHeight();

        setWidth(width);
        setHeight(height);
        setContentView(rootView);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        setBackgroundDrawable(dw);
        update();

        setOutsideTouchable(false);
        setTouchable(true);

    }

    public void show() {
        this.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_top);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cancelBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        sharePanel.startAnimation(animation);
    }

    @OnClick(R.id.btn_dialog_test)
    public void test() {
        ToastUtil.shortToast(context, "You click test button");
    }

    @OnClick(R.id.btn_cancel)
    public void cancel() {
        cancelBtn.setVisibility(View.INVISIBLE);
        exit();
    }

    private void exit() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_to_bottom);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationDialog.this.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        sharePanel.startAnimation(animation);
    }



}
