package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.AddNewsCommentActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsNotAdminTextActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.news.NewsCommentsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AnswersListPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.CommentsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ForumDetailPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.NewsPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.FORUM_ID;
import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.usernameList;

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
    @BindView(R.id.fab_tambah_answer)FloatingActionButton floatingActionButtonTambahAnswer;
    @BindView(R.id.recycler_view_answer)RecyclerView recyclerViewAnswers;

    Intent intent;
    String contentId, accessToken;
    RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> datePost = new ArrayList<String>();
    private List<String> username = new ArrayList<String>();
    private List<String> contentPost = new ArrayList<String>();
    private List<String> userProfilePicture = new ArrayList<String>();
    private List<String> answerId = new ArrayList<String>();
    private List<Integer> answerLevel = new ArrayList<Integer>();
    private List<Integer> answerReplyCounter = new ArrayList<Integer>();

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
        contentId = bundle.getString(FORUM_ID);

        recyclerViewAnswers.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewAnswers.setLayoutManager(mLayoutManager);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken","abcde");

        contentRequest(contentId,accessToken);
        answersList(contentId,accessToken);
        floatingActionButtonTambahAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddAnswerActivity.class);
                intent.putExtra(FORUM_ID,contentId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                answersList(contentId,accessToken);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void contentRequest(final String contentId, String access_token){

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
                textViewNumberComment.setText(Integer.toString(forumDetailPOJO.getResults().getComment())+" Jawaban");
                imageViewUserPicture.setImageDrawable(null);
                Glide.with(imageViewUserPicture.getContext()).load(forumDetailPOJO.getResults().getUserDetail().getDisplayPicture()).into(imageViewUserPicture);
            }

            @Override
            public void onFailure(Call<ForumDetailPOJO> call, Throwable t) {
                Toast.makeText(DetailForumActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void answersList(String contentId, String accessToken){
        //Activity
        username.clear();
        datePost.clear();
        contentPost.clear();
        userProfilePicture.clear();
        answerId.clear();
        answerReplyCounter.clear();
        answerLevel.clear();

        //Adapter
        ForumAnswersRecyclerView.usernameList.clear();
        ForumAnswersRecyclerView.datePostList.clear();
        ForumAnswersRecyclerView.contentPostList.clear();
        ForumAnswersRecyclerView.userPictureProfileList.clear();
        ForumAnswersRecyclerView.answerLevelList.clear();
        ForumAnswersRecyclerView.answerNumbersList.clear();
        ForumAnswersRecyclerView.answerIdList.clear();

        Call<AnswersListPOJO> answersListPOJOCall = restServiceInterface.answersListForum(contentId,0,accessToken);
        answersListPOJOCall.enqueue(new Callback<AnswersListPOJO>() {
            @Override
            public void onResponse(Call<AnswersListPOJO> call, Response<AnswersListPOJO> response) {
                AnswersListPOJO answersListPOJO = response.body();
                for (int item = 0 ; item < answersListPOJO.getResults().size(); item++){
                    username.add(answersListPOJO.getResults().get(item).getPostBy().getUsername());
                    datePost.add(answersListPOJO.getResults().get(item).getCreatedAtFromNow());
                    contentPost.add(answersListPOJO.getResults().get(item).getAnswerContent());
                    userProfilePicture.add(answersListPOJO.getResults().get(item).getUserDetail().getDisplayPicture());
                    answerId.add(answersListPOJO.getResults().get(item).getId());
                    answerReplyCounter.add(answersListPOJO.getResults().get(item).getReply().size());
                    answerLevel.add(answersListPOJO.getResults().get(item).getLevel());
                }
                mAdapter = new ForumAnswersRecyclerView(username,datePost,contentPost,
                        userProfilePicture,answerId,answerReplyCounter,answerLevel);

                mAdapter.notifyDataSetChanged();
                recyclerViewAnswers.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<AnswersListPOJO> call, Throwable t) {

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
