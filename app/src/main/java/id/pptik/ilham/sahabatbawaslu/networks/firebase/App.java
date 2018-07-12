package id.pptik.ilham.sahabatbawaslu.networks.firebase;

import android.app.Application;
import android.content.Context;


//import id.enibbe.client.storage.TinyDB;


/**
 * Created by hynra on 12/05/2018.
 */

public class App extends Application {


    public static final String IS_LOGGED_IN = "is.logged.in";
    public static final String ACCESS_TOKEN = "access.token";
    public static final String USER_NAME = "user.name";
    public static final String USER_ID = "user.id";
    public static final String FCM_TOKEN = "fcm.token";
    public static final String INTENT_ITEM_DETAIL = "intent.item.detail";
    public static final String INTENT_ITEM_ID = "intent.item.id";

    public static final String ACTION_NOTIF_INFO = "broadcast.notif.info";
    public static final String BROADCAST_TYPE = "broadcast.type";
    public static final String BROADCAST_MSG = "broadcast.msg";

    private static Application sApplication;
    private static TinyDB tinyDB;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static TinyDB getTinyDB() {
        return tinyDB;
    }


    /*
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        tinyDB = new TinyDB(sApplication);
    }
}
