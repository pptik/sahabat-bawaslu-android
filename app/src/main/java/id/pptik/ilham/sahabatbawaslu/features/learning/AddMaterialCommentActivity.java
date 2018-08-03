package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.CommentsInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddMaterialCommentBinding;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddNewsCommentBinding;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsNotAdminTextActivity;
import id.pptik.ilham.sahabatbawaslu.models.CommentsModel;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddCommentPOJO;
import id.pptik.ilham.sahabatbawaslu.view_models.CommentsViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity.greaterThanEqualLollipop;

public class AddMaterialCommentActivity extends AppCompatActivity {
    private ActivityAddMaterialCommentBinding activityAddMaterialCommentBinding;
    private RestServiceInterface restServiceInterface;
    android.support.v7.widget.Toolbar toolbar;
    EditText editTextComment;
    Intent intent;
    String contentId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_comment);
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        activityAddMaterialCommentBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_material_comment);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (greaterThanEqualLollipop){
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));
        }

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.add_comment_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextComment = (EditText)findViewById(R.id.edit_text_comment);
        Log.e("XXXContentGE","sxs");
        intent = getIntent();
        contentId = intent.getStringExtra(VideoMaterialDetailRevisedActivity.CONTENT_ID);
        Log.e("XXXContentId",contentId);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        final CommentsViewModel commentsViewModel = new CommentsViewModel(new CommentsModel());
        activityAddMaterialCommentBinding.setComment(commentsViewModel);
        activityAddMaterialCommentBinding.setAddcommentevent(new CommentsInterface() {
            @Override
            public void onClickAddComment() {
                Call<AddCommentPOJO> addCommentPOJOCall = restServiceInterface.
                        commentCreate(editTextComment.getText().toString(),contentId,2,access_token);
                addCommentPOJOCall.enqueue(new Callback<AddCommentPOJO>() {
                    @Override
                    public void onResponse(Call<AddCommentPOJO> call, Response<AddCommentPOJO> response) {
                        Log.e("XXXComment",editTextComment.getText().toString());
                        Log.e("XXXContentId",contentId);
                        Log.e("XXXAksesToken",access_token);
                        Toast.makeText(AddMaterialCommentActivity.this, response.body().getRm(), Toast.LENGTH_SHORT).show();

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
