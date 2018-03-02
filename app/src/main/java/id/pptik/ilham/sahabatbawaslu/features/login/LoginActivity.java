package id.pptik.ilham.sahabatbawaslu.features.login;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;


import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.UserInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
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
    RestServiceInterface restServiceInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        //setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        final UserViewModel userViewModel = new UserViewModel(new UserModel());
        //Binding View Model
        activityLoginBinding.setUser(userViewModel);
        //Binding Interface
        activityLoginBinding.setLoginevent(new UserInterface() {
            @Override
            public void onClickLogin() {
                login(activityLoginBinding.getUser().getEmail(),activityLoginBinding.getUser().getPassword());
            }
        });
    }


    private void login(final String field, final String password){
        //final LoginPOJO login = new LoginPOJO(field, password, true, 4501);
        Call<LoginPOJO> call1 = restServiceInterface.userLogin(field,true,password,4501);
        call1.enqueue(new Callback<LoginPOJO>() {
            @Override
            public void onResponse(Call<LoginPOJO> call, Response<LoginPOJO> response) {
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
