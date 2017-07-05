package com.shane.me.shanedemo.widget;
/*
 * @author: Xian Jingxiong
 * @date: 2017/07/05
 */

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.shane.me.shanedemo.R;

public class VideoViewDialog {
    Context context;
    Dialog dialog;
    View rootView;

    public VideoViewDialog(Context context) {
        this.context = context;
        makeDialog();
    }

    private void makeDialog() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.videoDialog);
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_videoview_dialog, null);
        //builder.setView(rootView);
        //dialog = builder.create();

        dialog = new Dialog(context, R.style.videoDialog);
        //dialog.setContentView(rootView);

        dialog.getWindow().setWindowAnimations(R.style.record_share_dialog_animation);

        WindowManager m = dialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        ViewGroup.LayoutParams rootParams = new ViewGroup.LayoutParams(d.getWidth(), d.getHeight());

        dialog.setContentView(rootView, rootParams);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }



}
