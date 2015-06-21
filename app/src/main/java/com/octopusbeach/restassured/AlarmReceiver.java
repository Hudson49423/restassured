package com.octopusbeach.restassured;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hudson on 6/21/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_action_done, context.getString(R.string.app_name), System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, HomeActivity.class), 0);
        notification.setLatestEventInfo(context, context.getText(R.string.alarm_service_label), intent.getStringExtra("title"), pendingIntent);
        nm.notify(R.string.alarm_service_label, notification);
    }
}
