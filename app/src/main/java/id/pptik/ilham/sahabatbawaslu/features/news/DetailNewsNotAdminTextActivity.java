package id.pptik.ilham.sahabatbawaslu.features.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.CommentsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.NewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNewsNotAdminTextActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.content_post_nonadmin_text)TextView textViewContentPostNonAdminText;
    @BindView(R.id.username)TextView textViewUsername;
    @BindView(R.id.activity_username)TextView textViewUsernameActivity;
    @BindView(R.id.date_post)TextView textViewDatePost;
    @BindView(R.id.text_number_favorite)TextView textViewNumberFavorite;
    @BindView(R.id.text_numbers_upvote)TextView textViewNumberUpvote;
    @BindView(R.id.text_numbers_downvote)TextView textViewNumberDownVote;
    @BindView(R.id.text_comments)TextView textViewNumberKomentar;
    @BindView(R.id.user_picture)ImageView imageViewUserPicture;
    @BindView(R.id.button_favorite)ImageView imageButtonFavorite;
    @BindView(R.id.button_upvote)ImageView imageButtonUpvote;
    @BindView(R.id.button_downvote)ImageView imageButtonDownvote;
    @BindView(R.id.button_comment)ImageView imageButtonComment;
    @BindView(R.id.recycler_view_komentar)RecyclerView recyclerViewComments;
    @BindView(R.id.fab_tambah_komentar)FloatingActionButton floatingActionButtonTambahKomentar;
    String contentId;
    Intent intent;
    RestServiceInterface restServiceInterface;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    SharedPreferences sharedPreferences;

    private List<String> datePost = new ArrayList<String>();
    private List<String> username = new ArrayList<String>();
    private List<String> contentPost = new ArrayList<String>();
    private List<String> userProfilePicture = new ArrayList<String>();
    private List<String> commentId = new ArrayList<String>();
    private List<Integer> commentNumber = new ArrayList<Integer>();
    public static final String CONTENT_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news_not_admin_text);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);

        intent = getIntent();
        contentId = intent.getStringExtra(MaterialsRecyclerView.CONTENT_ID);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.detail_berita_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        contentRequest(contentId);

        recyclerViewComments.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewComments.setLayoutManager(mLayoutManager);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        floatingActionButtonTambahKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddNewsCommentActivity.class);
                intent.putExtra(CONTENT_ID,contentId);
                startActivity(intent);
                //startActivityForResult(intent,);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        commentList(contentId,access_token);

    }

    private void contentRequest(String contentId){
        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        Call<NewsPOJO> newsDetailCall = restServiceInterface.newsDetail(contentId,access_token);
        newsDetailCall.enqueue(new Callback<NewsPOJO>() {
            @Override
            public void onResponse(Call<NewsPOJO> call, Response<NewsPOJO> response) {
                NewsPOJO newsPOJO = response.body();
                if (newsPOJO.getSuccess() != true){
                    Toast.makeText(DetailNewsNotAdminTextActivity.this, newsPOJO.getRm(), Toast.LENGTH_SHORT).show();
                }

                textViewUsername.setText(newsPOJO.getResults().getPostBy().getUsername());
                textViewContentPostNonAdminText.setText(newsPOJO.getResults().getContent());
                textViewNumberFavorite.setText(Integer.toString(newsPOJO.getResults().getFavorite()));
                textViewNumberUpvote.setText(Integer.toString(newsPOJO.getResults().getUpvote()));
                textViewNumberDownVote.setText(Integer.toString(newsPOJO.getResults().getDownvote()));
                textViewNumberFavorite.setText(Integer.toString(newsPOJO.getResults().getFavorite()));
                textViewNumberKomentar.setText(Integer.toString(newsPOJO.getResults().getComment()));
                textViewNumberKomentar.setText(Integer.toString(newsPOJO.getResults().getComment())+" Komentar");
                textViewDatePost.setText(newsPOJO.getResults().getCreatedAtFromNow());
                //imageViewUserPicture.
                imageViewUserPicture.setImageDrawable(null);
                Glide.with(imageViewUserPicture.getContext()).load(newsPOJO.getResults().getUserDetail().getDisplayPicture()).into(imageViewUserPicture);
                imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //gamifikasiAksiRespon();
                        Toast.makeText(DetailNewsNotAdminTextActivity.this, "tesss", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<NewsPOJO> call, Throwable t) {

            }
        });
    }

    private void commentList(final String contentId, String accessToken){
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<CommentsPOJO> commentsList = restServiceInterface.commentsList(contentId,accessToken);
        commentsList.enqueue(new Callback<CommentsPOJO>() {
            @Override
            public void onResponse(Call<CommentsPOJO> call, Response<CommentsPOJO> response) {
                CommentsPOJO commentsPOJO = response.body();
                for (int item = 0 ; item < commentsPOJO.getResults().size(); item++){
                   username.add(commentsPOJO.getResults().get(item).getPostBy().getUsername());
                   datePost.add(commentsPOJO.getResults().get(item).getCreatedAtFromNow());
                   contentPost.add(commentsPOJO.getResults().get(item).getComment());
                   userProfilePicture.add(commentsPOJO.getResults().get(item).getUserDetail().getDisplayPicture());
                   commentId.add(commentsPOJO.getResults().get(item).getId());
                   commentNumber.add(commentsPOJO.getResults().get(item).getLevel());
                }
                mAdapter = new NewsCommentsRecyclerView(username,datePost,contentPost,
                        userProfilePicture,commentId,commentNumber);

                mAdapter.notifyDataSetChanged();
                recyclerViewComments.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<CommentsPOJO> call, Throwable t) {

            }
        });
    }

    public void gamifikasiAksiRespon(String contentID,
                                     final int activityCode, int contentCode,
                                     String title, String accessToken,
                                     int textNumberFavoriteParam,
                                     int textNumberUpvoteParam, int textNumberDownvoteParam,
                                     final int position) {

        //Ganti status Front End response
        switch (activityCode) {
            /*case 2://upvote
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                viewHolder.tvNumberUpvote.setText(Integer.toString(textNumberUpvoteParam));
                break;
            case 3://downvote
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                viewHolder.tvNumberDownvote.setText(Integer.toString(textNumberDownvoteParam));
                break;*/
            case 4://favorite
                imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                //tvNumberFavorite.setText(Integer.toString(textNumberFavoriteParam));
                break;
        }

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentID, activityCode,
                contentCode, title, accessToken);
        voteAction.enqueue(new Callback<VotePOJO>() {
            @Override
            public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                VotePOJO votePOJO = response.body();
                Toast.makeText(DetailNewsNotAdminTextActivity.this, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<VotePOJO> call, Throwable t) {
                Toast.makeText(DetailNewsNotAdminTextActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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
