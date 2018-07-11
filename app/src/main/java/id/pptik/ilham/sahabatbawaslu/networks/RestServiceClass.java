package id.pptik.ilham.sahabatbawaslu.networks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ilham on 01/03/18.
 */

public class RestServiceClass {
    public static final String BASE_URL = "http://167.205.7.233:8183/";
    public static final String BASE_URL_MEDIA = "http://167.205.7.233:3077/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        /*java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress("cache.itb.ac.id", 8080));
        OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).build();*/

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient2() {
        /*java.net.Proxy proxy = new Proxy(Proxy.Type.HTTP,  new InetSocketAddress("cache.itb.ac.id", 8080));
        OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).build();*/

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_MEDIA)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
