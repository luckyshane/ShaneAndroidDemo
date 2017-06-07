package com.shane.me.shanedemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.service.MyService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceLifeCycleDemoActivity extends BaseActivity {

    private boolean isBound;


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_start_bind)
    void startBind() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


    @OnClick(R.id.btn_start_service)
    void startService() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @OnClick(R.id.btn_stop_service)
    void stopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    @OnClick(R.id.btn_bind_service)
    void bindService() {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_unbind_service)
    void unBindService() {
        if (isBound) {
            unbindService(conn);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unBindService();
    }




}
