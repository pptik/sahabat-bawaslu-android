package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.ArrayRes;
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
    //@BindView(R.id.button_comment)ImageView imageButtonComment;
    @BindView(R.id.recycler_view_komentar)RecyclerView recyclerViewComments;
    @BindView(R.id.fab_tambah_komentar)FloatingActionButton floatingActionButtonTambahKomentar;
    @BindView(R.id.swipeRefreshRecycler)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.icon_numbers_upvote)ImageView iconNumbersUpvote;
    @BindView(R.id.icon_numbers_downvote)ImageView iconNumbersDownvote;
    String contentId, title;
    Intent intent;
    RestServiceInterface restServiceInterface;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    private List<String> datePost = new ArrayList<String>();
    private List<String> username = new ArrayList<String>();
    private List<String> contentPost = new ArrayList<String>();
    private List<String> userProfilePicture = new ArrayList<String>();

    private ArrayList<List<String>> datePostSubKomentar = new ArrayList<List<String>>();
    private ArrayList<List<String>> usernameSubKomentar = new ArrayList<List<String>>();
    private ArrayList<List<String>> contentPostSubKomentar = new ArrayList<List<String>>();
    private ArrayList<List<String>> userProfilePictureSubKomentar = new ArrayList<List<String>>();


    private List<String> commentId = new ArrayList<String>();
    private List<Integer> commentNumber = new ArrayList<Integer>();
    public static final String CONTENT_ID = "";
    //public static final String TITLE = "";
    private int counterRefreshDataOnResume = 0;
    //private String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news_not_admin_text);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        contentId = bundle.getString(MaterialsRecyclerView.CONTENT_ID);
        title = bundle.getString(MaterialsRecyclerView.TITLE);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.detail_berita_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d("PRE CONTENT ID",contentId);
        contentRequest(contentId);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentRequest(contentId);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerViewComments.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewComments.setLayoutManager(mLayoutManager);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        //Tambah Komentar
        floatingActionButtonTambahKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewsCommentActivity.class);
                intent.putExtra(CONTENT_ID,contentId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //Menampilkan daftar komentar
        //commentList(contentId,access_token);

    }

    private void contentRequest(final String contentId){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");
        Log.d("POST CONTENT ID",contentId);
        Call<NewsPOJO> newsDetailCall = restServiceInterface.newsDetail(contentId,access_token);
        newsDetailCall.enqueue(new Callback<NewsPOJO>() {
            @Override
            public void onResponse(Call<NewsPOJO> call, Response<NewsPOJO> response) {
                NewsPOJO newsPOJO = response.body();
                if (newsPOJO.getSuccess() != true){
                    Toast.makeText(DetailNewsNotAdminTextActivity.this, newsPOJO.getRm(), Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(DetailNewsNotAdminTextActivity.this, newsPOJO.getRm(), Toast.LENGTH_SHORT).show();
                iconNumbersUpvote.setVisibility(View.GONE);
                iconNumbersDownvote.setVisibility(View.GONE);
                if(newsPOJO.getResults().getPostBy()==null){
                    textViewUsername.setText("Pengguna");
                }else{
                    textViewUsername.setText(newsPOJO.getResults().getPostBy().getUsername());
                }

                textViewContentPostNonAdminText.setText(newsPOJO.getResults().getContent());
                textViewNumberFavorite.setText(Integer.toString(newsPOJO.getResults().getFavorite()));
                textViewNumberUpvote.setText(Integer.toString(newsPOJO.getResults().getUpvote()));
                textViewNumberDownVote.setText(Integer.toString(newsPOJO.getResults().getDownvote()));
                textViewNumberUpvote.setVisibility(View.GONE);
                textViewNumberDownVote.setVisibility(View.GONE);
                textViewNumberKomentar.setText(Integer.toString(newsPOJO.getResults().getComment())+" Komentar");
                textViewDatePost.setText(newsPOJO.getResults().getCreatedAtFromNow());
                imageViewUserPicture.setImageDrawable(null);
                Glide.with(imageViewUserPicture.getContext()).load(newsPOJO.getResults().getUserDetail().getDisplayPicture()).into(imageViewUserPicture);

                //Gamifikasi status awal
                if(newsPOJO.getResults().getFavorited()){
                    imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                }else{
                    imageButtonFavorite.setImageResource(R.drawable.ic_favorite_border_black_18dp);
                }

                imageButtonUpvote.setVisibility(View.GONE);
                if(newsPOJO.getResults().getUpvoted()){
                    imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                }else{
                    imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }

                imageButtonDownvote.setVisibility(View.GONE);
                if(newsPOJO.getResults().getDownvoted()){
                    imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                }else{
                    imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }

                //Gamifikasi event handler
                imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*String contentID,
                        final int activityCode, int contentCode,
                        String title, String accessToken,
                        int textNumberFavoriteParam,
                        int textNumberUpvoteParam, int textNumberDownvoteParam,
                        final int position*/

                        gamifikasiAksiRespon(contentId,4,2,title,access_token);
                    }
                });

                imageButtonUpvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(DetailNewsNotAdminTextActivity.this, "UP", Toast.LENGTH_SHORT).show();
                        gamifikasiAksiRespon(contentId,2,2,title,access_token);
                    }
                });

                imageButtonDownvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(DetailNewsNotAdminTextActivity.this, "DOWN", Toast.LENGTH_SHORT).show();
                        gamifikasiAksiRespon(contentId,3,2,title,access_token);
                    }
                });

                commentList(contentId, access_token);
                progressDialog.setProgress(100);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<NewsPOJO> call, Throwable t) {
                progressDialog.setProgress(100);
                progressDialog.dismiss();
            }
        });


    }

    private void commentList(final String contentId, String accessToken){
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<CommentsPOJO> commentsList = restServiceInterface.commentsList(contentId,accessToken);
        commentsList.enqueue(new Callback<CommentsPOJO>() {
            @Override
            public void onResponse(Call<CommentsPOJO> call, Response<CommentsPOJO> response) {
                //Adapter
                NewsCommentsRecyclerView.usernameList.clear();
                NewsCommentsRecyclerView.usernameSubKomentarList.clear();
                NewsCommentsRecyclerView.datePostList.clear();
                NewsCommentsRecyclerView.datePostSubKomentarList.clear();
                NewsCommentsRecyclerView.contentPostList.clear();
                NewsCommentsRecyclerView.contentPostSubKomentarList.clear();
                NewsCommentsRecyclerView.userPictureProfileList.clear();
                NewsCommentsRecyclerView.userPictureProfileSubKomentarList.clear();
                NewsCommentsRecyclerView.commentNumbersList.clear();
                NewsCommentsRecyclerView.commentIdList.clear();
                NewsCommentsRecyclerView.statusOpenSubComment.clear();
                NewsCommentsRecyclerView.statusOpenTextViewSubComment.clear();

                //Fragment
                datePost.clear();
                datePostSubKomentar.clear();
                username.clear();
                usernameSubKomentar.clear();
                contentPost.clear();
                contentPostSubKomentar.clear();
                userProfilePicture.clear();
                userProfilePictureSubKomentar.clear();
                commentId.clear();
                commentNumber.clear();

                CommentsPOJO commentsPOJO = response.body();
                for (int item = 0 ; item < commentsPOJO.getResults().size(); item++){
                   username.add(commentsPOJO.getResults().get(item).getPostBy().getUsername());
                   datePost.add(commentsPOJO.getResults().get(item).getCreatedAtFromNow());
                   contentPost.add(commentsPOJO.getResults().get(item).getComment());
                   userProfilePicture.add(commentsPOJO.getResults().get(item).getUserDetail().getDisplayPicture());

                    ArrayList<String> usernameSingleList = new ArrayList<String>();
                    ArrayList<String> datePostSingleList = new ArrayList<String>();
                    ArrayList<String> contentPostSingleList = new ArrayList<String>();
                    ArrayList<String> userProfileSingleList = new ArrayList<String>();

                    for (int subItem = 0;subItem<commentsPOJO.getResults().get(item).getReply().size();subItem++){

                        usernameSingleList.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getPostBy().getUsername());
                        datePostSingleList.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getCreatedAtFromNow());
                        contentPostSingleList.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getComment());
                        userProfileSingleList.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getUserDetail().getDisplayPicture());


                    }
                    usernameSubKomentar.add(usernameSingleList);
                    datePostSubKomentar.add(datePostSingleList);
                    contentPostSubKomentar.add(contentPostSingleList);
                    userProfilePictureSubKomentar.add(userProfileSingleList);

                   commentId.add(commentsPOJO.getResults().get(item).getId());
                   commentNumber.add(commentsPOJO.getResults().get(item).getReply().size());
                }
                mAdapter = new NewsCommentsRecyclerView(username,datePost,contentPost,
                        userProfilePicture,commentId,commentNumber,usernameSubKomentar,datePostSubKomentar,
                        userProfilePictureSubKomentar,contentPostSubKomentar,contentId);

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
                                     String title, String accessToken) {

        //Ganti status Front End response
        /*switch (activityCode) {
            case 2://upvote
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                viewHolder.tvNumberUpvote.setText(Integer.toString(textNumberUpvoteParam));
                break;
            case 3://downvote
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                viewHolder.tvNumberDownvote.setText(Integer.toString(textNumberDownvoteParam));
                break;
            case 4://favorite
                imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                //tvNumberFavorite.setText(Integer.toString(textNumberFavoriteParam));
                break;
        }*/

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentID, activityCode,
                contentCode, title, accessToken);
        voteAction.enqueue(new Callback<VotePOJO>() {
            @Override
            public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                VotePOJO votePOJO = response.body();
                Toast.makeText(DetailNewsNotAdminTextActivity.this, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                if (votePOJO.getSuccess()){
                    switch(activityCode){
                        case 2:
                            textViewNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            textViewNumberDownVote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                            ;break;
                        case 3:
                            textViewNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            textViewNumberDownVote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                            ;break;
                        case 4:
                            imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                            imageButtonFavorite.setClickable(false);
                            textViewNumberFavorite.setText(Integer.toString(votePOJO.getResults().getFavorite()));
                            ;break;
                    }
                }else{
                    Toast.makeText(DetailNewsNotAdminTextActivity.this, votePOJO.getRm().toString(), Toast.LENGTH_SHORT).show();
                }
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

    @Override
    protected void onResume() {
        super.onResume();

        if(counterRefreshDataOnResume > 0){
            datePost.clear();
            username.clear();
            contentPost.clear();
            userProfilePicture.clear();
            commentId.clear();
            commentNumber.clear();

            intent = getIntent();
            contentId = intent.getStringExtra(MaterialsRecyclerView.CONTENT_ID);

            sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
            final String access_token = sharedPreferences.getString("accessToken","abcde");

            contentRequest(contentId);
        }
        counterRefreshDataOnResume++;
    }
}
