package id.pptik.ilham.sahabatbawaslu.features.login;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.UserInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.features.ui.CircleView;
import id.pptik.ilham.sahabatbawaslu.models.UserModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.view_models.UserViewModel;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;

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
                //Toast.makeText(LoginActivity.this, activityLoginBinding.getUser().getEmail(), Toast.LENGTH_LONG).show();


            }
        });

        CircleView circleView = new CircleView(this);

    }


}
