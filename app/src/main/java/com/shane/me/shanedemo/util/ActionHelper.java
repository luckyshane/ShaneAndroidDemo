package com.shane.me.shanedemo.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.shane.me.shanedemo.model.MainItem;

/**
 * Created by Asus on 2017/5/21.
 */

public class ActionHelper {

    public static void doAction(Context context, MainItem item) {
        if (item != null) {
            Intent intent = new Intent();

            if (!TextUtils.isEmpty(item.action)) {
                intent.setAction(item.action);
            }

            if (!TextUtils.isEmpty(item.componentName)) {
                intent.setClassName(context, item.componentName);
            }

            if (item.extras != null) {
                intent.putExtras(item.extras);
            }
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
