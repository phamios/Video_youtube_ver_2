package vn.admega.notification;

import admega.cartoon.movies.screen.category.controller.MainActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import admega.cartoon.movies.R;

public class AlarmReceiver extends BroadcastReceiver{
	@Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNM;
        mNM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher, "Test Alarm",
        System.currentTimeMillis());
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(context, context.getText(R.string.app_name), "This is a Test Alarm", contentIntent);
        // Send the notification.
        // We use a layout id because it is a unique number. We use it later to cancel.
        mNM.notify(R.string.app_name, notification);
    }

}
