package com.example.test.langlarm.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.test.langlarm.activity.WakeUpActivity;

public class AlarmService {

    public static void startAlarm(Context context, Alarm alarm){
        Intent intent = new Intent(context, WakeUpActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am =  (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }
}
