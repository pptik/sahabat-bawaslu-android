package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsNotAdminTextActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ForumDetailPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.NewsPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailForumActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.username)TextView textViewUsername;
    @BindView(R.id.date_post)TextView textViewDatePost;
    @BindView(R.id.content_forum)TextView textViewContentForum;
    @BindView(R.id.text_number_favorite)TextView textViewNumberFavorite;
    @BindView(R.id.text_numbers_upvote)TextView textViewNumberUpvote;
    @BindView(R.id.text_numbers_downvote)TextView textViewNumberDownvote;
    @BindView(R.id.text_comments)TextView textViewNumberComment;
    @BindView(R.id.user_picture)ImageView imageViewUserPicture;

    Intent intent;
    String contentId;
    RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forum);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.detail_forum_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        contentId = bundle.getString(MaterialsRecyclerView.FORUM_ID);

        contentRequest(contentId);
    }

    private void contentRequest(final String contentId){
        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        Call<ForumDetailPOJO> forumDetailPOJOCall = restServiceInterface.detailForum(contentId,access_token);
        forumDetailPOJOCall.enqueue(new Callback<ForumDetailPOJO>() {
            @Override
            public void onResponse(Call<ForumDetailPOJO> call, Response<ForumDetailPOJO> response) {
                ForumDetailPOJO forumDetailPOJO = response.body();
                textViewUsername.setText(forumDetailPOJO.getResults().getPostBy().getUsername());
                textViewContentForum.setText(forumDetailPOJO.getResults().getTitle());
                textViewDatePost.setText(forumDetailPOJO.getResults().getCreatedAtFromNow());
                textViewNumberFavorite.setText(Integer.toString(forumDetailPOJO.getResults().getFavorite()));
                textViewNumberUpvote.setText(Integer.toString(forumDetailPOJO.getResults().getUpvote()));
                textViewNumberDownvote.setText(Integer.toString(forumDetailPOJO.getResults().getDownvote()));
                imageViewUserPicture.setImageDrawable(null);
                Glide.with(imageViewUserPicture.getContext()).load(forumDetailPOJO.getResults().getUserDetail().getDisplayPicture()).into(imageViewUserPicture);
            }

            @Override
            public void onFailure(Call<ForumDetailPOJO> call, Throwable t) {
                Toast.makeText(DetailForumActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
