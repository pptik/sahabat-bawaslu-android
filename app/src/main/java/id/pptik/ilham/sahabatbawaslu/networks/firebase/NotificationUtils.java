package id.pptik.ilham.sahabatbawaslu.networks.firebase;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import id.pptik.ilham.sahabatbawaslu.R;

//import id.enibbe.client.R;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String INFO_CHANNEL_ID = "info.channel";
    public static final String INFO_CHANNEL_NAME = "INFORMATION CHANNEL";

    @TargetApi(Build.VERSION_CODES.O)
    public NotificationUtils(Context base) {
        super(base);
        createChannels();

        String groupId = "group_id_101";
        CharSequence groupName = "Channel Name";
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel androidChannel = new NotificationChannel(INFO_CHANNEL_ID,
                INFO_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(true);
        androidChannel.setLightColor(Color.GREEN);
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(androidChannel);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getInfoChannelNotif(String title, String body, PendingIntent contentIntent) {
        return new Notification.Builder(getApplicationContext(), INFO_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);
    }


    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void deleteNotificationChannel(String channelId) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.deleteNotificationChannel(channelId);
    }
}
