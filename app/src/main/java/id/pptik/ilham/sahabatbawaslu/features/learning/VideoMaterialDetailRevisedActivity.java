package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.AddNewsCommentActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.news.NewsCommentsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.YoutubeConfig;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.CommentsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialDetailPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialDetailVideoPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.NewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity.greaterThanEqualLollipop;

public class VideoMaterialDetailRevisedActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.username)
    TextView textViewUsername;
    @BindView(R.id.date_post)
    TextView textViewDatePost;
    @BindView(R.id.text_number_favorite)
    TextView textViewNumberFavorite;
    @BindView(R.id.text_numbers_upvote)
    TextView textViewNumberUpvote;
    @BindView(R.id.text_numbers_downvote)
    TextView textViewNumberDownVote;
    @BindView(R.id.text_comments)
    TextView textViewNumberKomentar;
    @BindView(R.id.title_post)
    TextView textViewTitlePost;
    @BindView(R.id.content_post)
    TextView textViewContentPost;
    @BindView(R.id.user_picture)
    ImageView imageViewUserPicture;
    @BindView(R.id.button_favorite)
    ImageView imageButtonFavorite;
    @BindView(R.id.button_upvote)
    ImageView imageButtonUpvote;
    @BindView(R.id.button_downvote)
    ImageView imageButtonDownvote;
    //@BindView(R.id.button_comment)ImageView imageButtonComment;
    @BindView(R.id.recycler_view_komentar)
    RecyclerView recyclerViewComments;
    @BindView(R.id.fab_tambah_komentar)
    FloatingActionButton floatingActionButtonTambahKomentar;


    String contentId, title, materialId;
    Intent intent;
    RestServiceInterface restServiceInterface;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    @BindView(R.id.swipeRefreshRecycler)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.icon_numbers_upvote)ImageView iconNumbersUpvote;
    @BindView(R.id.icon_numbers_downvote)ImageView iconNumbersDownvote;

    private List<String> datePost = new ArrayList<String>();
    private ArrayList<List<String>> datePostSubKomentar = new ArrayList<List<String>>();
    private List<String> username = new ArrayList<String>();
    private ArrayList<List<String>> usernameSubKomentar= new ArrayList<List<String>>();
    private List<String> contentPost = new ArrayList<String>();
    private ArrayList<List<String>> contentPostSubKomentar= new ArrayList<List<String>>();
    private List<String> userProfilePicture = new ArrayList<String>();
    private ArrayList<List<String>> userProfilePictureSubKomentar= new ArrayList<List<String>>();

    private List<String> commentId = new ArrayList<String>();
    private List<Integer> commentNumber = new ArrayList<Integer>();

    private YouTubePlayer youTubePlayerGeneral;
    private YouTubePlayerFragment youTubePlayerFragment;

    /*usernameSubKomentar,
    datePostSubKomentar, userProfilePictureSubKomentar,
    contentPostSubKomentar);*/

    public static final String CONTENT_ID = "";
    private String videoCode;
    //public static final String TITLE = "";
    private int counterRefreshDataOnResume = 0;
    private static final int RQS_ErrorDialog = 1;
    //private String access_token;

    YouTubePlayerFragment myYouTubePlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_material_detail_revised);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);

        /*intent = getIntent();
        materialId = intent.getStringExtra(MaterialsRecyclerView.MATERIAL_ID);*/

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        materialId = bundle.getString(MaterialsRecyclerView.MATERIAL_ID);
        title = bundle.getString(MaterialsRecyclerView.TITLE);
        //Log.d("DEBUG VIDEO",title);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (greaterThanEqualLollipop){
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));
        }

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.materi_label));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentRequest(materialId);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerViewComments.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewComments.setLayoutManager(mLayoutManager);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);

        //Tambah Komentar
        floatingActionButtonTambahKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMaterialCommentActivity.class);
                intent.putExtra(CONTENT_ID,materialId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        contentRequest(materialId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == YoutubeConfig.RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action

        }
    }

    private void contentRequest(final String materialId) {
        //Ambil Data dari Networking REST
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");
        Log.d("POST CONTENT ID", materialId);
        Call<MaterialDetailVideoPOJO> materialDetailVideoPOJOCall = restServiceInterface.materialDetailVideo(materialId, access_token);
        materialDetailVideoPOJOCall.enqueue(new Callback<MaterialDetailVideoPOJO>() {
            @Override
            public void onResponse(Call<MaterialDetailVideoPOJO> call, Response<MaterialDetailVideoPOJO> response) {
                MaterialDetailVideoPOJO materialDetailVideoPOJO = response.body();
                progressDialog.setProgress(100);
                progressDialog.dismiss();
                if (!materialDetailVideoPOJO.getSuccess()){
                    Toast.makeText(VideoMaterialDetailRevisedActivity.this, materialDetailVideoPOJO.getRm(), Toast.LENGTH_SHORT).show();
                }else{
                    String videoCodeRaw = materialDetailVideoPOJO.getResults().getFiles().get(0);
                    String[] videoCodeSplitter = videoCodeRaw.split("v=");

                    videoCode = videoCodeSplitter[1];

                    youTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
                            .findFragmentById(R.id.youtubeplayerfragment);
                    youTubePlayerFragment.initialize(YoutubeConfig.YOUTUBE_API_KEY, VideoMaterialDetailRevisedActivity.this);

                    textViewTitlePost.setText(materialDetailVideoPOJO.getResults().getTitle());
                    textViewContentPost.setText(materialDetailVideoPOJO.getResults().getDesc());
                    textViewUsername.setText(materialDetailVideoPOJO.getResults().getPostBy().getUsername());
                    textViewDatePost.setText(materialDetailVideoPOJO.getResults().getCreatedAtFromNow());
                    textViewNumberUpvote.setText(Integer.toString(materialDetailVideoPOJO.getResults().getUpvote()));
                    textViewNumberDownVote.setText(Integer.toString(materialDetailVideoPOJO.getResults().getDownvote()));
                    textViewNumberFavorite.setText(Integer.toString(materialDetailVideoPOJO.getResults().getFavorite()));

                    //imageViewUserPicture
                    /*imageViewUserPicture.setImageDrawable(null);
                    if(dashboardPOJO.getResults().get(item).getDashboard().getFiles() == null){
                        newsMedia.add("http://filehosting.pptik.id/ioaa/defaultphoto.png");
                    }else{
                        newsMedia.add(dashboardPOJO.getResults().get(item).getDashboard().getFiles().get(0).getHttpPath());
                    }*/
                    //Log.d("FOTO DETAIL VIDEO",materialDetailVideoPOJO.getResults().getUserDetail().getDisplayPicture().toString());
                    Glide.with(imageViewUserPicture.getRootView().getContext()).load( "http://filehosting.pptik.id/ioaa/defaultphoto.png").into(imageViewUserPicture);
                    //Gamifikasi status awal
                    /*if(materialDetailVideoPOJO.getResults().getFavorited()){
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
                    }*/

                    //Gamifikasi event handler
                    imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gamifikasiAksiRespon(materialId,4,1,title,access_token);
                        }
                    });

                    imageButtonUpvote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gamifikasiAksiRespon(materialId,2,1,title,access_token);
                        }
                    });

                    imageButtonDownvote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gamifikasiAksiRespon(materialId,3,1,title,access_token);
                        }
                    });

                    commentList(materialId, access_token);
                }
            }

            @Override
            public void onFailure(Call<MaterialDetailVideoPOJO> call, Throwable t) {

            }
        });
    }

    private void commentList(final String contentId, String accessToken) {
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<CommentsPOJO> commentsList = restServiceInterface.commentsList(contentId, accessToken);
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
                for (int item = 0; item < commentsPOJO.getResults().size(); item++) {
                    username.add(commentsPOJO.getResults().get(item).getPostBy().getUsername());
                    datePost.add(commentsPOJO.getResults().get(item).getCreatedAtFromNow());
                    contentPost.add(commentsPOJO.getResults().get(item).getComment());
                    userProfilePicture.add(commentsPOJO.getResults().get(item).getUserDetail().getDisplayPicture());

                    ArrayList<String> usernameSingleList = new ArrayList<String>();
                    ArrayList<String> datePostSingleList = new ArrayList<String>();
                    ArrayList<String> contentPostSingleList = new ArrayList<String>();
                    ArrayList<String> userProfileSingleList = new ArrayList<String>();

                    for (int subItem = 0;subItem<commentsPOJO.getResults().get(item).getReply().size();subItem++){
                        /*usernameSubKomentar.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getPostBy().getUsername());
                        datePostSubKomentar.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getCreatedAtFromNow());
                        contentPostSubKomentar.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getComment());
                        userProfilePictureSubKomentar.add(commentsPOJO.getResults().get(item).getReply().get(subItem).getUserDetail().getDisplayPicture());*/

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
                mAdapter = new NewsCommentsRecyclerView(username, datePost, contentPost,
                        userProfilePicture, commentId, commentNumber, usernameSubKomentar,
                        datePostSubKomentar, userProfilePictureSubKomentar,
                        contentPostSubKomentar,contentId);

                mAdapter.notifyDataSetChanged();
                recyclerViewComments.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<CommentsPOJO> call, Throwable t) {

            }
        });
    }

    public void gamifikasiAksiRespon(final String contentID,
                                     final int activityCode, final int contentCode,
                                     final String title, final String accessToken) {

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentID, activityCode,
                contentCode, title, accessToken);
        voteAction.enqueue(new Callback<VotePOJO>() {

            @Override
            public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                VotePOJO votePOJO = response.body();
                /*
                Log.d("RP ContentID",contentID);
                Log.d("RP ActCode",Integer.toString(activityCode));
                Log.d("RP ContCode",Integer.toString(contentCode));
                Log.d("RP Title",title);
                Log.d("RP AccToken",accessToken);*/

                if (votePOJO.getSuccess()) {
                    /*Log.d("RP ContentID",contentID);
                    Log.d("RP ActCode",Integer.toString(activityCode));
                    Log.d("RP ContCode",Integer.toString(contentCode));
                    Log.d("RP Title",title);
                    Log.d("RP AccToken",accessToken);
                    Log.d("RP",votePOJO.getRm());*/
                    switch (activityCode) {
                        case 2:
                            textViewNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            textViewNumberDownVote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            /*imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);*/
                            ;
                            break;
                        case 3:
                            textViewNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            textViewNumberDownVote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            /*imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);*/
                            ;
                            break;
                        case 4:
                            //imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                            imageButtonFavorite.setClickable(false);
                            textViewNumberFavorite.setText(Integer.toString(votePOJO.getResults().getFavorite()));
                            ;
                            break;
                    }
                }else{
                    Toast.makeText(VideoMaterialDetailRevisedActivity.this, votePOJO.getRm().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VotePOJO> call, Throwable t) {
                Toast.makeText(VideoMaterialDetailRevisedActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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

        if (counterRefreshDataOnResume > 0) {
            datePost.clear();
            username.clear();
            contentPost.clear();
            userProfilePicture.clear();
            commentId.clear();
            commentNumber.clear();

            //intent = getIntent();
            //contentId = intent.getStringExtra(MaterialsRecyclerView.CONTENT_ID);

            /*sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
            final String access_token = sharedPreferences.getString("accessToken", "abcde");*/

            contentRequest(materialId);
        }
        counterRefreshDataOnResume++;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayerGeneral = youTubePlayer;

        /*Toast.makeText(getApplicationContext(),
                "YouTubePlayer.onInitializationSuccess()",
                Toast.LENGTH_LONG).show();*/

        if (!b) {
            youTubePlayer.cueVideo(videoCode);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RQS_ErrorDialog).show();
        } else {
            Toast.makeText(this,
                    "YouTubePlayer.onInitializationFailure(): " + youTubeInitializationResult.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
