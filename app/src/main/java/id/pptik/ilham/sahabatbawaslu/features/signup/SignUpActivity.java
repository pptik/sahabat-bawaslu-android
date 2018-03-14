package id.pptik.ilham.sahabatbawaslu.features.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.BoringLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.SignUpInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivitySignUpBinding;
import id.pptik.ilham.sahabatbawaslu.features.dashboard.DashboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.intro.IntroActivity;
import id.pptik.ilham.sahabatbawaslu.models.UserModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProvincesPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.SignUpPOJO;
import id.pptik.ilham.sahabatbawaslu.view_models.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    ActivitySignUpBinding activitySignUpBinding;
    private RestServiceInterface restServiceInterface;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    @BindView(R.id.LinearLayoutParent)LinearLayout linearLayout;
    Boolean referenceCodeStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        //setContentView(R.layout.activity_sign_up);
        activitySignUpBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        final UserViewModel userViewModel = new UserViewModel(new UserModel());
        activitySignUpBinding.setUser(userViewModel);

        progressDialog = new ProgressDialog(this);

        activitySignUpBinding.setSignupevent(new SignUpInterface() {
            @Override
            public void onClickSignUp() {
                /*Intent intent = new Intent(SignUpActivity.this, IntroActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.show();

                if(RestServiceClass.isNetworkAvailable(SignUpActivity.this)){
                    if (activitySignUpBinding.getUser().getReferenceCode() == null ||
                            activitySignUpBinding.getUser().getReferenceCode() == ""){

                        signup(activitySignUpBinding.getUser().getEmail(),activitySignUpBinding.getUser().getPassword(),
                                activitySignUpBinding.getUser().getUsername(),activitySignUpBinding.getUser().getPhoneNumber(),
                                activitySignUpBinding.getUser().getClassCode(),"",
                                false,1);

                    }else if(activitySignUpBinding.getUser().getReferenceCode() != null ||
                            activitySignUpBinding.getUser().getReferenceCode() != ""){
                        signup(activitySignUpBinding.getUser().getEmail(),activitySignUpBinding.getUser().getPassword(),
                                activitySignUpBinding.getUser().getUsername(),activitySignUpBinding.getUser().getPhoneNumber(),
                                activitySignUpBinding.getUser().getClassCode(),activitySignUpBinding.getUser().getReferenceCode(),
                                true,1);
                    }
                }else{
                    progressDialog.dismiss();
                    snackbar = Snackbar.make(linearLayout,R.string.pastikan_internet_label,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


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

    private void signup(final String email, final String password, final String username,
                        final String no_handphone, final String kode_referensi,
                        final Boolean status_referensi, final int signup_type){

        Call<SignUpPOJO> callSignUp = restServiceInterface.userSignUp(username,no_handphone,
                email,password,kode_referensi,status_referensi, signup_type);
        callSignUp.enqueue(new Callback<SignUpPOJO>() {

            @Override
            public void onResponse(Call<SignUpPOJO> call, Response<SignUpPOJO> response) {
                progressDialog.setProgress(100);
                progressDialog.dismiss();

                SignUpPOJO signUpPOJO = response.body();
                if (signUpPOJO != null){
                    if (signUpPOJO.getRc().toString().equals("0000")){
                        finish();
                        Intent intent = new Intent(SignUpActivity.this, IntroActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }else{
                        snackbar = Snackbar.make(linearLayout,signUpPOJO.getRm(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpPOJO> call, Throwable t) {

            }
        });

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
