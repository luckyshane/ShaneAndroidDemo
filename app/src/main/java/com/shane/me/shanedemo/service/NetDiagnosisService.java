package com.shane.me.shanedemo.service;
/*
 * @author: Xian Jingxiong
 * @date: 2017/06/30
 */

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.shane.me.shanedemo.net.diagnosis.DNSTask;
import com.shane.me.shanedemo.net.diagnosis.EmptyTask;
import com.shane.me.shanedemo.net.diagnosis.PingTask;

public class NetDiagnosisService extends IntentService {

    public static final String ACTION_NET_DIAGNOSIS_RET = "com.shane.shanedemo.net_diagnosis_ret";


    public static final int TYPE_INVALID = 0;
    public static final int TYPE_PING = 1 << 0;
    public static final int TYPE_DNS = 1 << 1;
    public static final int TYPE_TRACE = 1 << 2;


    public NetDiagnosisService() {
        super("NetDiagnosisService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            int type = intent.getIntExtra("type", TYPE_INVALID);
            String host = intent.getStringExtra("host");

            if (type != TYPE_INVALID) {

                EmptyTask emptyTask = new EmptyTask();
                StringBuilder sb = new StringBuilder(512);

                if (containMask(type, TYPE_PING)) {
                    emptyTask.addNextTask(new PingTask(host));
                }

                if (containMask(type, TYPE_DNS)) {
                    emptyTask.addNextTask(new DNSTask(host));
                }

                emptyTask.exec(sb);
                postResult(sb);
            }
        }
    }

    private static boolean containMask(int target, int mask) {
        return (target & mask) > 0;
    }

    public void postResult(StringBuilder sb) {
        Intent intent = new Intent(ACTION_NET_DIAGNOSIS_RET)
                .putExtra("result", sb.toString());

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }





}
