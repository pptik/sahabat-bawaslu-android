package id.pptik.ilham.sahabatbawaslu.features.signup;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.SignUpInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivitySignUpBinding;
import id.pptik.ilham.sahabatbawaslu.models.UserModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProvincesPOJO;
import id.pptik.ilham.sahabatbawaslu.view_models.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    ActivitySignUpBinding activitySignUpBinding;
    private RestServiceInterface restServiceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);

        setContentView(R.layout.activity_sign_up);

        activitySignUpBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        final UserViewModel userViewModel = new UserViewModel(new UserModel());
        activitySignUpBinding.setUser(userViewModel);

        //Call<ProvincesPOJO> callProvinces = restServiceInterface.provincesList();
        Call<ProvincesPOJO> callProvinces = restServiceInterface.provincesList();
        callProvinces.enqueue(new Callback<ProvincesPOJO>() {
            @Override
            public void onResponse(Call<ProvincesPOJO> call, Response<ProvincesPOJO> response) {
                Log.d("PROVINCES","PROVINCES: "+response.body().getResults());
            }

            @Override
            public void onFailure(Call<ProvincesPOJO> call, Throwable t) {
                Log.d("PROVINCES","GAGAL: "+t.toString());
            }
        });
        /*callProvinces.enqueue(new Callback<ProvincesPOJO>() {
            @Override
            public void onResponse(Call<List<ProvincesPOJO>> call, Response<List<ProvincesPOJO>> response) {
                Log.d("PROVINCES","PROVINCES: "+response.body());
            }

            @Override
            public void onFailure(Call<List<ProvincesPOJO>> call, Throwable t) {
                Log.d("PROVINCES","GAGAL: "+t.toString());
            }
        });*/

        activitySignUpBinding.setSignupevent(new SignUpInterface() {
            @Override
            public void onClickSignUp() {
                //Toast.makeText(SignUpActivity.this, "Yuhuuuuu", Toast.LENGTH_SHORT).show();
            }
        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.daftar_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
