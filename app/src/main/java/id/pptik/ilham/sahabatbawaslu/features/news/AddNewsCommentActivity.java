package id.pptik.ilham.sahabatbawaslu.features.news;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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
import id.pptik.ilham.sahabatbawaslu.view_models.CommentsViewModel;
import id.pptik.ilham.sahabatbawaslu.view_models.NewsViewModel;

public class AddNewsCommentActivity extends AppCompatActivity {
    private ActivityAddNewsCommentBinding activityAddNewsCommentBinding;
    private RestServiceInterface restServiceInterface;
    android.support.v7.widget.Toolbar toolbar;
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

        final CommentsViewModel commentsViewModel = new CommentsViewModel(new CommentsModel());
        activityAddNewsCommentBinding.setComment(commentsViewModel);
        activityAddNewsCommentBinding.setAddcommentevent(new CommentsInterface() {
            @Override
            public void onClickAddComment() {
                Toast.makeText(AddNewsCommentActivity.this, "ABCDEFGHIJ", Toast.LENGTH_SHORT).show();
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
