package id.pptik.ilham.sahabatbawaslu.networks.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

//import id.enibbe.client.App;

public class BroadcastManager {
    private Context context;
    private boolean isSubsribing = false;

    public interface UIBroadcastListener{
        public void onMessageReceived(String type, String msg);
    }

    BroadcastReceiver uiReceiver;

    public BroadcastManager(Context ctx){
        context = ctx;
    }


    public void sendBroadcastToUI(String broadcastType, String msg){
        Intent intent = new Intent();
        switch (broadcastType){
            case App.ACTION_NOTIF_INFO:
                intent.setAction(App.ACTION_NOTIF_INFO);
                intent.putExtra(App.INTENT_ITEM_ID, msg);
                break;
        }
        context.sendBroadcast(intent);
    }

    public boolean isSubsribed(){
        return isSubsribing;
    }

    public void subscribeToUi(final UIBroadcastListener listener){
        isSubsribing = true;
        IntentFilter intentFilter = new IntentFilter(App.ACTION_NOTIF_INFO);
        uiReceiver = new BroadcastReceiver() {
            String resType = "";
            String _msg = "";
            @Override
            public void onReceive(Context context, Intent intent) {
                resType = intent.getStringExtra(App.BROADCAST_TYPE);
                _msg = intent.getStringExtra(App.BROADCAST_MSG);
                listener.onMessageReceived(resType, _msg);
            }
        };
        context.registerReceiver(uiReceiver, intentFilter);
    }


    public void unSubscribeToUi(){
        context.unregisterReceiver(uiReceiver);
        Log.i(this.getClass().getSimpleName(), "Unsubscribe from UI");
    }
}
