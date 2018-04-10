package id.pptik.ilham.sahabatbawaslu.features.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import butterknife.BindView;
import id.pptik.ilham.sahabatbawaslu.R;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.commands.CommentsInterface;
import id.pptik.ilham.sahabatbawaslu.commands.NewsInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddNewsBinding;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddNewsCommentBinding;
import id.pptik.ilham.sahabatbawaslu.models.CommentsModel;
import id.pptik.ilham.sahabatbawaslu.models.NewsModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddCommentPOJO;
import id.pptik.ilham.sahabatbawaslu.view_models.CommentsViewModel;
import id.pptik.ilham.sahabatbawaslu.view_models.NewsViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewsCommentActivity extends AppCompatActivity {
    private ActivityAddNewsCommentBinding activityAddNewsCommentBinding;
    private RestServiceInterface restServiceInterface;
    android.support.v7.widget.Toolbar toolbar;
    EditText editTextComment;
    Intent intent;
    String contentId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_comment);
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

        editTextComment = (EditText)findViewById(R.id.edit_text_comment);

        intent = getIntent();
        contentId = intent.getStringExtra(DetailNewsNotAdminTextActivity.CONTENT_ID);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        final CommentsViewModel commentsViewModel = new CommentsViewModel(new CommentsModel());
        activityAddNewsCommentBinding.setComment(commentsViewModel);
        activityAddNewsCommentBinding.setAddcommentevent(new CommentsInterface() {
            @Override
            public void onClickAddComment() {
                Call<AddCommentPOJO> addCommentPOJOCall = restServiceInterface.
                        commentCreate(editTextComment.getText().toString(),contentId,2,access_token);
                addCommentPOJOCall.enqueue(new Callback<AddCommentPOJO>() {
                    @Override
                    public void onResponse(Call<AddCommentPOJO> call, Response<AddCommentPOJO> response) {
                        Toast.makeText(AddNewsCommentActivity.this, response.body().getRm(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<AddCommentPOJO> call, Throwable t) {

                    }
                });
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
