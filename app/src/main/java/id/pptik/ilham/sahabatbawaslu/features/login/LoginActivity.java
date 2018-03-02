package id.pptik.ilham.sahabatbawaslu.features.login;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.UserInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.features.ui.CircleView;
import id.pptik.ilham.sahabatbawaslu.models.UserModel;
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


    private void login(String email, String password){
        final LoginPOJO login = new LoginPOJO(email,password,true,4501);
        Call<LoginPOJO> call1 = restServiceInterface.createUser(login);
        call1.enqueue(new Callback<LoginPOJO>() {
            @Override
            public void onResponse(Call<LoginPOJO> call, Response<LoginPOJO> response) {
                LoginPOJO loginPOJO = response.body();
                Log.e("RESPONSE","RESPONSE: "+ loginPOJO);
            }

            @Override
            public void onFailure(Call<LoginPOJO> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "There is something wrong", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}
