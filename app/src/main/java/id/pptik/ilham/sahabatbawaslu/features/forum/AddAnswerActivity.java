package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.commands.CommentsInterface;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddAnswerBinding;
import id.pptik.ilham.sahabatbawaslu.databinding.ActivityAddNewsCommentBinding;
import id.pptik.ilham.sahabatbawaslu.features.news.AddNewsCommentActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsNotAdminTextActivity;
import id.pptik.ilham.sahabatbawaslu.models.CommentsModel;
import id.pptik.ilham.sahabatbawaslu.networks.HTMLParser;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddAnswerPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddCommentPOJO;
import id.pptik.ilham.sahabatbawaslu.view_models.CommentsViewModel;
import jp.wasabeef.richeditor.RichEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity.greaterThanEqualLollipop;
import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.FORUM_ID;

public class AddAnswerActivity extends AppCompatActivity {
    private ActivityAddAnswerBinding activityAddAnswerBinding;
    private RestServiceInterface restServiceInterface;
    private android.support.v7.widget.Toolbar toolbar;
    private Intent intent;
    private String contentId;
    private SharedPreferences sharedPreferences;
    private EditText editTextAnswer;
    private RichEditor richEditor;
    private Button buttonAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        activityAddAnswerBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_answer);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (greaterThanEqualLollipop){
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));
        }

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.add_answer_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        richEditor = (RichEditor)findViewById(R.id.rich_editor);
        richEditor.setEditorHeight(300);
        richEditor.setEditorFontSize(15);
        richEditor.setPlaceholder("Tuliskan Jawaban disini");
        richEditor.setPadding(10,10,10,10);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        contentId = bundle.getString(FORUM_ID);
        //contentId = intent.getStringExtra(DetailNewsNotAdminTextActivity.CONTENT_ID);
        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        final CommentsViewModel commentsViewModel = new CommentsViewModel(new CommentsModel());
        activityAddAnswerBinding.setComment(commentsViewModel);
        activityAddAnswerBinding.setAddcommentevent(new CommentsInterface() {
            @Override
            public void onClickAddComment() {
            submitAnswer(contentId,access_token,HTMLParser.html2text(richEditor.getHtml()));
            }
        });
    }

    private void submitAnswer(String contentId, String accessToken, String answerContent){

        Call<AddAnswerPOJO> addAnswerPOJOCall = restServiceInterface.
                addAnswerForum(contentId,answerContent,accessToken);
        addAnswerPOJOCall.enqueue(new Callback<AddAnswerPOJO>() {
            @Override
            public void onResponse(Call<AddAnswerPOJO> call, Response<AddAnswerPOJO> response) {
                AddAnswerPOJO addAnswerPOJO = response.body();
                Toast.makeText(AddAnswerActivity.this, addAnswerPOJO.getRm(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<AddAnswerPOJO> call, Throwable t) {
                Toast.makeText(AddAnswerActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
