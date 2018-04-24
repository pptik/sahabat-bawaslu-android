package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddNewsCommentBinding;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsNotAdminTextActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;

public class AddAnswerActivity extends AppCompatActivity {
    private ActivityAddNewsCommentBinding activityAddNewsCommentBinding;
    private RestServiceInterface restServiceInterface;
    private android.support.v7.widget.Toolbar toolbar;
    private Intent intent;
    private String contentId;
    private SharedPreferences sharedPreferences;
    private EditText editTextAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        activityAddNewsCommentBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_news_comment);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.add_comment_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextAnswer = (EditText)findViewById(R.id.edit_text_answer);

        intent = getIntent();
        contentId = intent.getStringExtra(DetailNewsNotAdminTextActivity.CONTENT_ID);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");
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
