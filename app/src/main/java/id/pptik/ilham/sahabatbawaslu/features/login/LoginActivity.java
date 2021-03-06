package id.pptik.ilham.sahabatbawaslu.features.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.LoginInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.features.signup.SignUpActivity;
import id.pptik.ilham.sahabatbawaslu.models.UserModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import id.pptik.ilham.sahabatbawaslu.view_models.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;
    private RestServiceInterface restServiceInterface;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    @BindView(R.id.LinearLayoutParent)LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        //setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        ButterKnife.bind(this);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));


        progressDialog = new ProgressDialog(this);

        final UserViewModel userViewModel = new UserViewModel(new UserModel());
        //Binding View Model
        activityLoginBinding.setUser(userViewModel);
        //Binding Interface
        activityLoginBinding.setLoginevent(new LoginInterface() {
            @Override
            public void onClickLogin() {
                progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.show();

                if(RestServiceClass.isNetworkAvailable(LoginActivity.this)){
                    login(activityLoginBinding.getUser().getEmail(),activityLoginBinding.getUser().getPassword());
                }else{
                    progressDialog.dismiss();
                    snackbar = Snackbar.make(linearLayout,R.string.pastikan_internet_label,Snackbar.LENGTH_LONG);
                    snackbar.show();
                    //Toast.makeText(LoginActivity.this, R.string.pastikan_internet_label, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickSignUpPage() {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    private void login(final String field, final String password){
        Call<LoginPOJO> call1 = restServiceInterface.userLogin(field,true,password,4501);
        call1.enqueue(new Callback<LoginPOJO>() {
            @Override
            public void onResponse(Call<LoginPOJO> call, Response<LoginPOJO> response) {
                progressDialog.setProgress(100);
                progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);
                LoginPOJO loginPOJO = response.body();
                if (loginPOJO != null){
                    if (loginPOJO.getRc().toString().equals("0000")){
                        //Toast.makeText(LoginActivity.this, "Selamat datang "+loginPOJO.getResults().getName(), Toast.LENGTH_SHORT).show();
                        snackbar = Snackbar.make(linearLayout,"Selamat datang "+loginPOJO.getResults().getName(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }else{
                        snackbar = Snackbar.make(linearLayout,loginPOJO.getRm(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginPOJO> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "Maaf terjadi gangguan pada koneksi ke server.", Toast.LENGTH_LONG).show();
                snackbar = Snackbar.make(linearLayout,"Maaf terjadi gangguan pada koneksi ke server.",Snackbar.LENGTH_LONG);
                snackbar.show();
                call.cancel();
            }
        });

    }
}
