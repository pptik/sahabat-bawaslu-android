package id.pptik.ilham.sahabatbawaslu.features.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.UserInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.features.signup.SignUpActivity;
import id.pptik.ilham.sahabatbawaslu.features.ui.CircleView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        //setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        progressDialog = new ProgressDialog(this);

        final UserViewModel userViewModel = new UserViewModel(new UserModel());
        //Binding View Model
        activityLoginBinding.setUser(userViewModel);
        //Binding Interface
        activityLoginBinding.setLoginevent(new UserInterface() {
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
                    Toast.makeText(LoginActivity.this, R.string.pastikan_internet_label, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickSignUpPage() {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    private void login(final String field, final String password){
        //final LoginPOJO login = new LoginPOJO(field, password, true, 4501);
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
                        Toast.makeText(LoginActivity.this, "Selamat datang "+loginPOJO.getResults().getName(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, loginPOJO.getRm(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginPOJO> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Maaf terjadi gangguan pada koneksi ke server.", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }
}
