package id.pptik.ilham.sahabatbawaslu.networks.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.firebase.App;
import id.pptik.ilham.sahabatbawaslu.networks.firebase.Utils;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.UpdateFCMIdPOJO;
import retrofit2.Call;

import static id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity.SessionPengguna;

public class TokenInstanceService extends FirebaseInstanceIdService {

    private final String TAG = this.getClass().getSimpleName();
    //private Context context;
    private SharedPreferences sharedPreferences;
    public RestServiceInterface restServiceInterface;

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //context = getApplicationContext();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        sharedPreferences = getSharedPreferences(SessionPengguna, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fcmId", refreshedToken);
        editor.commit();



        Utils.updateFcm();
    }

}
