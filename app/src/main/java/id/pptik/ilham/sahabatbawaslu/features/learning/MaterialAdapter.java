package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import id.pptik.ilham.sahabatbawaslu.R;
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
                switch (materialDetailPOJO.getResults().getType()){
                    case 0://video

                        break;
                    case 1://suplemen
                        Intent intent = new Intent(getApplicationContext(), SuplemenMaterialDetailActivity.class);
                        intent.putExtra("materialId",contentId);
                        startActivity(intent);

                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                    case 2://kasus
                        break;
                }
            }

            @Override
            public void onFailure(retrofit2.Call<MaterialDetailPOJO> call, Throwable t) {
                Log.e("ERROR",t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
