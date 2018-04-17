package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.CommentsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialDetailPOJO;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialAdapter extends AppCompatActivity {
    String contentId;
    RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;
    String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        contentId = bundle.getString("materialId");

        sharedPreferences = this.getSharedPreferences("User",Context.MODE_PRIVATE);
        access_token = sharedPreferences.getString("accessToken","abcde");

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        retrofit2.Call<MaterialDetailPOJO> materialDetail = restServiceInterface.materialDetail(contentId,access_token);
        materialDetail.enqueue(new Callback<MaterialDetailPOJO>() {
            @Override
            public void onResponse(retrofit2.Call<MaterialDetailPOJO> call, Response<MaterialDetailPOJO> response) {
                MaterialDetailPOJO materialDetailPOJO = response.body();
                //Log.d("MATERIAL",Integer.toString(materialDetailPOJO.getResults().getType()));
                Log.d("MATERIAL",materialDetailPOJO.getRm());
            }

            @Override
            public void onFailure(retrofit2.Call<MaterialDetailPOJO> call, Throwable t) {
                Log.e("MATERIAL",t.getLocalizedMessage());
            }
        });
    }
}
