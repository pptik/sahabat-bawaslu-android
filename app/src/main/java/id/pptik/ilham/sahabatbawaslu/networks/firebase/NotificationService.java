package id.pptik.ilham.sahabatbawaslu.networks.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
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


import id.pptik.ilham.sahabatbawaslu.features.dashboard.DashboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.learning.FileManagerDownloadActivity;
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
        Log.i("Tipe notif", remoteMessage.getData().get("type"));
        showNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(),
                remoteMessage.getData().get("type"));

    }


    private void showNotification(String title, String content, String type) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        PendingIntent pi = null;

        if(type.equals("Berita")){
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            intent.putExtra("tabPosition", 0); //tab position to open
            pi = PendingIntent.getActivity(this, 0, intent, 0);
        }else if(type.equals("Materi")){
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            intent.putExtra("tabPosition", 1); //tab position to open
            pi = PendingIntent.getActivity(this, 0, intent, 0);
        }else if(type.equals("Forum")){
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            intent.putExtra("tabPosition", 2); //tab position to open
            pi = PendingIntent.getActivity(this, 0, intent, 0);
        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(pi)
                .setAutoCancel(true); // clear notification after click

        mNotificationManager.notify(0, mBuilder.build());

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