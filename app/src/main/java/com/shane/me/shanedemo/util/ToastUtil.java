package com.shane.me.shanedemo.util;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shane.me.shanedemo.R;

import java.lang.reflect.Field;

/**
 * Created by Asus on 2017/6/18.
 */

public class ToastUtil {

    public static void shortToast(Context context, String text) {
        Toast toast = new Toast(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.toast_top, null);
        TextView msgTv = (TextView) rootView.findViewById(R.id.toast_text_tv);
        msgTv.setText(text);

        initToast(toast);

        toast.setView(rootView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 之上才支持沉浸模式，这样才能让Toast显示到状态栏
            toast.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        toast.show();
    }

    private static void initToast(Toast toast) {
        try {
            Field mTN = toast.getClass().getDeclaredField("mTN");
            mTN.setAccessible(true);
            Object mTNObj = mTN.get(toast);

            Field mParams = mTNObj.getClass().getDeclaredField("mParams");
            mParams.setAccessible(true);
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams.get(mTNObj);
            params.width = -1; //ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = -2; //ViewGroup.LayoutParams.WRAP_CONTENT;
            params.windowAnimations = R.style.toast;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
