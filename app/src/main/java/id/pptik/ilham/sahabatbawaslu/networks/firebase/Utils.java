package id.pptik.ilham.sahabatbawaslu.networks.firebase;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;

//import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/*
import id.enibbe.client.App;

import id.enibbe.client.features._common.model.CommonResponse;
import id.enibbe.client.features.login.LoginActivity;
import id.enibbe.client.features.login.model.LoginResponse;

import id.enibbe.client.networking.NetworkService;
import id.enibbe.client.networking.RestService;
*/
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by hynra on 13/05/2018.
 */

public class Utils {
    public static void updateFcm() {
        /*Retrofit restService = RestServiceClass.getRetroftInstance();

        String accessToken = App.getTinyDB().getString(App.ACCESS_TOKEN);
        String fcmToken = App.getTinyDB().getString(App.FCM_TOKEN);
        if (accessToken.equals("") || fcmToken.equals("")) {
            Log.i("FCM", "access token or fcm token empty");
        } else {
            restService.newBuilder()
                    .client(RestService.getClient())
                    .build()
                    .create(NetworkService.class)
                    .updateFcm(accessToken, fcmToken)
                    .enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Log.i("fcm", response.body().getRm());
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            Log.e("fcm", t.getLocalizedMessage());
                        }
                    });
        }*/
    }

    public static final int VIDEO_TYPE = 1;
    public static final int IMAGE_TYPE = 0;
    public static final int SOUND_TYPE = 2;

    private static String[] imageTypes = {"JPG", "PNG", "JPEG"};
    private static String[] videoTypes = {"MP4", "MKV", "3GP", "3GPP"};
    private static String[] audioTypes = {"MP3", "AMR"};


    public static String getFileExtension(String path) {
        String filename = path;
        String filenameArray[] = filename.split("\\.");
        return filenameArray[filenameArray.length - 1];
    }

    public static int getFileType(String path) {
        int type;
        path = path.toUpperCase();
        if (Arrays.asList(audioTypes).contains(getFileExtension(path))) {
            type = IMAGE_TYPE;
        } else if (Arrays.asList(videoTypes).contains(getFileExtension(path))) {
            type = VIDEO_TYPE;
        } else type = IMAGE_TYPE;

        return type;
    }




    /*public static String convertMongoDate(String val) {
        ISO8601DateFormat df = new ISO8601DateFormat();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(val);
            String finalStr = outputFormat.format(d);
            val = finalStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return val;
    }*/


    /*public static String convertMongoDateToAgo(String val) {
        ISO8601DateFormat df = new ISO8601DateFormat();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(val);
            String finalStr = outputFormat.format(d);
            val = getTimeAgo(finalStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return val;
    }*/


    public static String getTimeAgo(String dateInput) {
        String timeformat = dateInput;
        try {
            long now = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date convertedDate = dateFormat.parse(dateInput);
            CharSequence relavetime1 = DateUtils.getRelativeTimeSpanString(
                    convertedDate.getTime(),
                    now,
                    DateUtils.SECOND_IN_MILLIS);
            timeformat = String.valueOf(relavetime1);
        } catch (Exception e) {
            e.printStackTrace();
            return timeformat;
        }

        return timeformat;
    }


    /*public static void logout(Activity activity){
        App.getTinyDB().putBoolean(App.IS_LOGGED_IN, false);
        Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
    }*/





}
