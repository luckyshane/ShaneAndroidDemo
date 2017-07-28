package com.shane.me.shanedemo.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shane.me.shanedemo.R;

import java.lang.reflect.Field;
import java.security.PrivilegedAction;

/**
 * Created by Asus on 2017/6/18.
 */

public class ToastUtil {

    private static CancelableToastWrapper<Toast> toastWrapper = new CancelableToastWrapper<>();



    public static void shortToast(Context context, String text) {
        Toast toast = new Toast(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.toast_top, null);
        TextView msgTv = (TextView) rootView.findViewById(R.id.toast_text_tv);
        msgTv.setText(text);


        toast.setView(rootView);
        initToast(toast, context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);


        toast.show();

        toastWrapper.retainToast(toast);
    }

    private static void initToast(Toast toast, Context context) {
        try {
            Field mTN = toast.getClass().getDeclaredField("mTN");
            mTN.setAccessible(true);
            Object mTNObj = mTN.get(toast);

            Field mParams = mTNObj.getClass().getDeclaredField("mParams");
            mParams.setAccessible(true);
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams.get(mTNObj);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;

            params.height = getStatusBarHeight(context) + ContextUtil.dip2px(context, 35);

            params.windowAnimations = R.style.toast;
            params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void cancel() {
        toastWrapper.cancel();
    }




}
