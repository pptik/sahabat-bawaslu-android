package id.pptik.ilham.sahabatbawaslu.features.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.NewsInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddNewsBinding;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityLoginBinding;
import id.pptik.ilham.sahabatbawaslu.models.NewsModel;
import id.pptik.ilham.sahabatbawaslu.models.UserModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddNewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.SignUpPOJO;
import id.pptik.ilham.sahabatbawaslu.view_models.NewsViewModel;
import id.pptik.ilham.sahabatbawaslu.view_models.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewsActivity extends AppCompatActivity {
    private ActivityAddNewsBinding activityAddNewsBinding;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    Button buttonSubmit;
    EditText editTextCaption;
    private RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;
    String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        activityAddNewsBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_news);
        final NewsViewModel userViewModel = new NewsViewModel(new NewsModel());
        //Binding View Model
        editTextCaption = (EditText)findViewById(R.id.edit_text_caption);
        buttonSubmit = (Button)findViewById(R.id.button_masuk);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        access_token = sharedPreferences.getString("accessToken","abcde");

        activityAddNewsBinding.setNews(userViewModel);
        activityAddNewsBinding.setAddnewsevent(new NewsInterface() {
            @Override
            public void onClickAddNews() {
                Call<AddNewsPOJO> callAddNews = restServiceInterface.newsCreateText(editTextCaption.getText().toString(),access_token);
                callAddNews.enqueue(new Callback<AddNewsPOJO>() {
                    @Override
                    public void onResponse(Call<AddNewsPOJO> call, Response<AddNewsPOJO> response) {
                        AddNewsPOJO addNewsPOJO = response.body();
                        Toast.makeText(AddNewsActivity.this, addNewsPOJO.getRm().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<AddNewsPOJO> call, Throwable t) {
                        Toast.makeText(AddNewsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.add_news_label));
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
