package id.pptik.ilham.sahabatbawaslu.features.login;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.features.ui.CircleView;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);


        CircleView circleView = new CircleView(this);

    }


}
