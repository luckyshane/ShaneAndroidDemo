package com.shane.me.shanedemo.util;
/*
 * @author: Xian Jingxiong
 * @date: 2017/07/21
 */

import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CancelableToastWrapper<T> {

    private static final String TAG = CancelableToastWrapper.class.getSimpleName();
    private List<WeakReference<T>> toastList;

    
    public void retainToast(T toast) {
        Log.d(TAG, "retain toast");

        if (toastList == null) {
            toastList = new ArrayList<>();
        }
        toastList.add(new WeakReference<T>(toast));
    }
    
    
    public void cancel() {
        Log.d(TAG, "cancel toast");

        if (toastList != null && toastList.size() > 0) {
            Iterator<WeakReference<T>> iterator = toastList.iterator();

            while (iterator.hasNext()) {
                WeakReference<T> toastWeakReference = iterator.next();
                T toast = toastWeakReference.get();
                if (toast != null) {
                    if (toast instanceof Toast) {
                        ((Toast) toast).cancel();
                    }
                    Log.d(TAG, "toast canceled");
                } else {
                    Log.d(TAG, "toast null");
                }
                iterator.remove();
            }
        }
    }
    
    
    
}
