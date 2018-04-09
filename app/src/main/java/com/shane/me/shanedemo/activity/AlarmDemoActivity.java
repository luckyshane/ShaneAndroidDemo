package com.shane.me.shanedemo.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.util.AlarmUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckyshane on 2018/4/8.
 */

public class AlarmDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_btn)
    void onStartBtnClick() {
        AlarmUtil.setAlarmDelayInSeconds(this, 10);
        //makeNotify(this);
    }

    @OnClick(R.id.cancel_btn)
    void onCancelBtnClick() {

    }


    private void makeNotify(Context ctx) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx, "ChannelID")
                        .setSmallIcon(R.drawable.icon_account_logo)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(ctx, AlarmDemoActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(AlarmDemoActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        //resultPendingIntent = PendingIntent.getActivity(ctx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }





}
