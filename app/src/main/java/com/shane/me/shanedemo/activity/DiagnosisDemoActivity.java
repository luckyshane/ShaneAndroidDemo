package com.shane.me.shanedemo.activity;
/*
 * @author: Xian Jingxiong
 * @date: 2017/06/30
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.service.NetDiagnosisService;
import com.shane.me.shanedemo.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiagnosisDemoActivity extends BaseActivity {

    @BindView(R.id.host_et)
    EditText hostEt;

    @BindView(R.id.content_tv)
    TextView contentTv;


    private String host;

    private NetDiagnosisResultReceiver resultReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_demo);
        ButterKnife.bind(this);
        registerReceiver();
    }

    @OnClick(R.id.go_btn)
    void onGoBtnClick() {
        if (!checkHost()) return;
        clearContent();
        startDiagnosis(NetDiagnosisService.TYPE_PING | NetDiagnosisService.TYPE_DNS | NetDiagnosisService.TYPE_TRACE);
    }


    @OnClick(R.id.ping_btn)
    void onPingBtnClick() {
        if (!checkHost()) return;
        clearContent();
        startDiagnosis(NetDiagnosisService.TYPE_PING);
    }


    @OnClick(R.id.dns_btn)
    void onDnsBtnClick() {
        if (!checkHost()) return;
        clearContent();
        startDiagnosis(NetDiagnosisService.TYPE_DNS);
    }


    private boolean checkHost() {
        host = hostEt.getText().toString();
        boolean valid = !TextUtils.isEmpty(host);
        if (!valid) {
            ToastUtil.shortToast(this, "请填入host");
        }
        return valid;
    }

    private void startDiagnosis(int type) {
        Intent intent = new Intent(this, NetDiagnosisService.class);
        intent.putExtra("type", type);
        intent.putExtra("host", host);
        startService(intent);
    }

    private void registerReceiver() {
        resultReceiver = new NetDiagnosisResultReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(resultReceiver, new IntentFilter(NetDiagnosisService.ACTION_NET_DIAGNOSIS_RET));
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(resultReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    private class NetDiagnosisResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra("result");

            contentTv.setText(result);

        }
    }

    private void clearContent() {
        contentTv.setText("");
    }










}
