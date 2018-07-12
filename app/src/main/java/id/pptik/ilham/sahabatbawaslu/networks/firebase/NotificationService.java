package id.pptik.ilham.sahabatbawaslu.networks.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import id.pptik.ilham.sahabatbawaslu.networks.firebase.App;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;

/**
 * Created by hynra on 13/05/2018.
 */

public class NotificationService extends FirebaseMessagingService {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private NotificationUtils mNotificationUtils;
    //BroadcastManager broadcastManager = new BroadcastManager(App.getContext());

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.i(TAG, remoteMessage.getNotification().getBody());
        Log.i(TAG, remoteMessage.getNotification().getTitle());
        Log.i(TAG, remoteMessage.getNotification().getBody());
        //Log.i(TAG, remoteMessage.getNotification().getTag());
        //Log.i(TAG, remoteMessage.getMessageType());
        //Log.i(TAG, Integer.toString(remoteMessage.getTtl()));

        /*broadcastManager.sendBroadcastToUI(App.ACTION_NOTIF_INFO,
                remoteMessage.getData().get("trip_id"));
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(App.INTENT_ITEM_ID, remoteMessage.getData().get("task_id"));

        switch (remoteMessage.getData().get("type")) {
            case Prefs.NOTIFICATION_TYPE_ANGKOT_CHARTER_STATUS:
                broadcastManager.sendBroadcastToUI(Prefs.NOTIFICATION_TYPE_ANGKOT_CHARTER_STATUS,
                        remoteMessage.getData().get("trip_id"));
                intent = new Intent(getApplicationContext(), DetailImageActivity.class);
                intent.putExtra(Prefs.INTENT_TRIP_ID, remoteMessage.getData().get("trip_id"));
                break;
            case Prefs.NOTIFICATION_TYPE_ANGKOT_CHARTER_MESSAGE:
                broadcastManager.sendBroadcastToUI(Prefs.NOTIFICATION_TYPE_ANGKOT_CHARTER_MESSAGE,
                        remoteMessage.getData().get("trip_id"));
                intent = new Intent(getApplicationContext(), DetailImageActivity.class);
                //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra(Prefs.INTENT_TRIP_ID, remoteMessage.getData().get("trip_id"));
                intent.putExtra(Prefs.INTENT_ANGKOT_TRIP_WITH_MESSAGE, true);
        }*/


        /*if (App.getTinyDB().getBoolean(App.IS_LOGGED_IN)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                oreoNotif(remoteMessage, intent);
            } else showNotification(remoteMessage, intent);
        } else Log.i("FCM SERVICE", "USER IS NOT LOGGED IN");*/

    }


    private void showNotification(RemoteMessage remoteMessage, Intent intent) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent contentIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("Bin Client")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Notif");
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) Math.random(), b.build());
    }


    private void oreoNotif(RemoteMessage remoteMessage, Intent intent) {
        mNotificationUtils = new NotificationUtils(this);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent contentIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder nb = mNotificationUtils.getInfoChannelNotif(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(), contentIntent);
        mNotificationUtils.getManager().notify((int) Math.random(), nb.build());

    }
}