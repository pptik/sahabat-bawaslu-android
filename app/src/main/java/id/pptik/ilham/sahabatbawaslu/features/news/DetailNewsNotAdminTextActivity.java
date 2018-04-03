package id.pptik.ilham.sahabatbawaslu.features.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNewsNotAdminTextActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.content_post_nonadmin_text)TextView textViewContentPostNonAdminText;
    @BindView(R.id.username)TextView textViewUsername;
    @BindView(R.id.activity_username)TextView textViewUsernameActivity;
    @BindView(R.id.date_post)TextView textViewDatePost;
    @BindView(R.id.icon_numbers_favorite)TextView textViewNumberFavorite;
    @BindView(R.id.icon_numbers_upvote)TextView textViewNumberUpvote;
    @BindView(R.id.icon_numbers_downvote)TextView textViewNumberDownVote;
    @BindView(R.id.text_comments)TextView textViewNumberKomentar;
    String contentId, contentPost, username, activityUsername,
    datePost, numberFavorite, numberUpVote, numberDownVote, numberComment;
    Intent intent;
    RestServiceInterface restServiceInterface;

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
    }

    private void contentRequest(String contentId){
        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        Call<DashboardPOJO> newsDetailCall = restServiceInterface.newsDetail(contentId,access_token);
        newsDetailCall.enqueue(new Callback<DashboardPOJO>() {
            @Override
            public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                DashboardPOJO newsDetailPOJO = response.body();

                textViewUsername.setText(newsDetailPOJO.getResults().get(0).getDashboard());
                textViewUsernameActivity.setText("membuat");
                textViewContentPostNonAdminText.setText("KONTEN nnya");
                textViewDatePost.setText("1 hari yll");
            }

            @Override
            public void onFailure(Call<DashboardPOJO> call, Throwable t) {

            }
        });
        /*dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
            @Override
            public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                //Mengosongkan recycle material yang sudah diisi
                username.clear();
                datePost.clear();
                contentPost.clear();
                userPicturePost.clear();
                contentType.clear();
                titlePost.clear();
                contentLabel.clear();
                activityLabel.clear();
                numberFavorite.clear();
                numberUpvote.clear();
                numberDownvote.clear();
                numberComments.clear();
                upvoteStatus.clear();
                downvoteStatus.clear();
                favoriteStatus.clear();
                newsType.clear();
                newsMedia.clear();
                contentId.clear();
                activityType.clear();

                DashboardPOJO dashboardPOJO = response.body();
                for (int item = 0 ; item < dashboardPOJO.getResults().size(); item++){
                    username.add(dashboardPOJO.getResults().get(item).getDashboard().getPostBy().getUsername());
                    datePost.add(dashboardPOJO.getResults().get(item).getDashboard().getCreatedAt());
                    titlePost.add(dashboardPOJO.getResults().get(item).getDashboard().getTitle());
                    contentPost.add(dashboardPOJO.getResults().get(item).getDashboard().getSynopsis());
                    userPicturePost.add(dashboardPOJO.getResults().get(item).getDashboard().getUserDetail().getDisplayPicture());
                    contentLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getContentText());
                    activityLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityText());
                    contentType.add(dashboardPOJO.getResults().get(item).getDashboard().getContent_code().toString());
                    activityType.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityCode());
                    numberFavorite.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorite());
                    numberUpvote.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvote());
                    numberDownvote.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvote());
                    numberComments.add(dashboardPOJO.getResults().get(item).getDashboard().getComment());
                    upvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvoted());
                    downvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvoted());
                    favoriteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorited());
                    newsType.add(dashboardPOJO.getResults().get(item).getDashboard().getNewsType());
                    //newsMedia.add(dashboardPOJO.getResults().get(item).getDashboard().getFiles().get(item).getHttpPath());
                    contentId.add(dashboardPOJO.getResults().get(item).getDashboard().getId());
                    newsMedia.add("http://filehosting.pptik.id/ioaa/defaultphoto.png");
                }
                mAdapter = new MaterialsRecyclerView(username,datePost,contentPost,
                        userPicturePost,contentType,titlePost,contentLabel,activityLabel,numberFavorite,
                        numberUpvote,numberDownvote,numberComments,upvoteStatus,downvoteStatus,favoriteStatus,getActivity(),
                        newsType,newsMedia,contentId,activityType
                );
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<DashboardPOJO> call, Throwable t) {

            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }
}
