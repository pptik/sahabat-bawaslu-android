package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nicky.litefiledownloader.DownloadListener;
import com.nicky.litefiledownloader.FileDownloader;
import com.nicky.litefiledownloader.Request;
import com.nicky.litefiledownloader.Task;

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
import id.pptik.ilham.sahabatbawaslu.networks.pojos.CommentsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialDetailPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaseMaterialDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.title_post)TextView titlePost;
    @BindView(R.id.content_post)TextView contentPost;
    @BindView(R.id.text_number_favorite)TextView textNumbersFavorite;
    @BindView(R.id.text_numbers_upvote)TextView textNumbersUpvote;
    @BindView(R.id.text_numbers_downvote)TextView textNumbersDownvote;
    @BindView(R.id.button_unduh_materi)Button buttonUnduhMateri;
    @BindView(R.id.button_favorite_suplemen)
    ImageView imageButtonFavorite;
    @BindView(R.id.button_upvote_suplemen)
    ImageView imageButtonUpvote;
    @BindView(R.id.button_downvote_suplemen)
    ImageView imageButtonDownvote;
    @BindView(R.id.fab_tambah_komentar)
    FloatingActionButton floatingActionButtonTambahKomentar;
    @BindView(R.id.recycler_view_komentar)
    RecyclerView recyclerViewComments;
    @BindView(R.id.swipeRefreshRecycler)
    SwipeRefreshLayout swipeRefreshLayout;


    private List<String> datePost = new ArrayList<String>();
    private ArrayList<List<String>> datePostSubKomentar = new ArrayList<List<String>>();
    private List<String> username = new ArrayList<String>();
    private ArrayList<List<String>> usernameSubKomentar= new ArrayList<List<String>>();
    private List<String> contentPostList = new ArrayList<String>();
    private ArrayList<List<String>> contentPostSubKomentar= new ArrayList<List<String>>();
    private List<String> userProfilePicture = new ArrayList<String>();
    private ArrayList<List<String>> userProfilePictureSubKomentar= new ArrayList<List<String>>();

    private List<String> commentId = new ArrayList<String>();
    private List<Integer> commentNumber = new ArrayList<Integer>();
    Bundle bundle;
    String materialId, accessToken, title;
    RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    Intent intent;
    Request request;
    Task task;
    ProgressDialog progressDialog;


    public static final String CONTENT_ID = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_material_detail);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.oranyeGelap));

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.materi_label));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        materialId = bundle.getString(MaterialsRecyclerView.MATERIAL_ID);
        title = bundle.getString(MaterialsRecyclerView.TITLE);
        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", "abcde");

        requestContent(materialId,accessToken);

        recyclerViewComments.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewComments.setLayoutManager(mLayoutManager);

        floatingActionButtonTambahKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewsCommentActivity.class);
                intent.putExtra(CONTENT_ID, materialId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestContent(materialId,accessToken);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void requestContent(final String materialId, final String accessToken){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<MaterialDetailPOJO> materialDetail = restServiceInterface.materialDetail(materialId,accessToken);
        Log.d("DM","ID KASUS MATERI: "+materialId+" Akses Token: "+accessToken);
        materialDetail.enqueue(new Callback<MaterialDetailPOJO>() {
            @Override
            public void onResponse(Call<MaterialDetailPOJO> call, Response<MaterialDetailPOJO> response) {
                final MaterialDetailPOJO materialDetailPOJO = response.body();
                titlePost.setText(materialDetailPOJO.getResults().getTitle());
                contentPost.setText(materialDetailPOJO.getResults().getDesc());
                textNumbersUpvote.setText(Integer.toString(materialDetailPOJO.getResults().getUpvote()));
                textNumbersDownvote.setText(Integer.toString(materialDetailPOJO.getResults().getDownvote()));
                textNumbersFavorite.setText(Integer.toString(materialDetailPOJO.getResults().getFavorite()));

                commentList(materialId,accessToken);
                //Gamifikasi event handler
                imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(CaseMaterialDetailActivity.this, "ABCDE", Toast.LENGTH_SHORT).show();
                        gamifikasiAksiRespon(materialId,4,1,materialDetailPOJO.getResults().getTitle(),accessToken);
                    }
                });

                imageButtonUpvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gamifikasiAksiRespon(materialId,2,1,materialDetailPOJO.getResults().getTitle(),accessToken);
                    }
                });

                imageButtonDownvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gamifikasiAksiRespon(materialId,3,1,materialDetailPOJO.getResults().getTitle(),accessToken);
                    }
                });

                if(materialDetailPOJO.getResults().getFiles().size() == 0){
                    buttonUnduhMateri.setVisibility(View.GONE);
                }else if(materialDetailPOJO.getResults().getFiles().size() > 0){
                    buttonUnduhMateri.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FileDownloader downloader = FileDownloader
                                    .createBuilder()
                                    .build();

                            for (int item = 0; item <materialDetailPOJO.getResults().getFiles().size();item++){
                                request = Request
                                        .createBuilder()
                                        .url(materialDetailPOJO.getResults().getFiles().get(item).getHttp_path())
                                        .build();
                                task = downloader.newTask(request);
                                final int finalItem = item;
                                task.enqueue(new DownloadListener() {
                                    @Override
                                    public void onStart(Request request) {

                                    }

                                    @Override
                                    public void onProgress(Request request, long curBytes, long totalBytes) {
                                        if(finalItem == 0){
                                            showNotification(getResources().getString(R.string.app_name),getResources().getString(R.string.download_sedang_diunduh));
                                            TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.download_sedang_diunduh), TSnackbar.LENGTH_LONG);
                                            snackbar.setActionTextColor(Color.WHITE);
                                            View snackbarView = snackbar.getView();
                                            snackbarView.setBackgroundColor(Color.parseColor("#E37114"));
                                            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                                            textView.setTextColor(Color.WHITE);
                                            snackbar.show();
                                        }
                                    }

                                    @Override
                                    public void onPause(Request request) {

                                    }

                                    @Override
                                    public void onRestart(Request request) {

                                    }

                                    @Override
                                    public void onFinished(Request request) {
                                        if(finalItem - materialDetailPOJO.getResults().getFiles().size() == -1){
                                            showNotification(getResources().getString(R.string.app_name),getResources().getString(R.string.download_selesai_diunduh));
                                            TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.download_selesai_diunduh), TSnackbar.LENGTH_LONG);
                                            snackbar.setActionTextColor(Color.WHITE);
                                            View snackbarView = snackbar.getView();
                                            snackbarView.setBackgroundColor(Color.parseColor("#E37114"));
                                            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                                            textView.setTextColor(Color.WHITE);
                                            snackbar.show();
                                        }
                                    }

                                    @Override
                                    public void onCancel(Request request) {

                                    }

                                    @Override
                                    public void onFailed(Request request, Exception e) {

                                    }
                                });
                            }
                        }
                    });
                }
                progressDialog.setProgress(100);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MaterialDetailPOJO> call, Throwable t) {
                Log.e("DM",t.getLocalizedMessage());
                progressDialog.setProgress(100);
                progressDialog.dismiss();
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
                contentPostList.clear();
                contentPostSubKomentar.clear();
                userProfilePicture.clear();
                userProfilePictureSubKomentar.clear();
                commentId.clear();
                commentNumber.clear();


                CommentsPOJO commentsPOJO = response.body();
                for (int item = 0; item < commentsPOJO.getResults().size(); item++) {
                    username.add(commentsPOJO.getResults().get(item).getPostBy().getUsername());
                    datePost.add(commentsPOJO.getResults().get(item).getCreatedAtFromNow());
                    contentPostList.add(commentsPOJO.getResults().get(item).getComment());
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
                mAdapter = new NewsCommentsRecyclerView(username, datePost, contentPostList,
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
    //

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
                Toast.makeText(CaseMaterialDetailActivity.this, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                if (!votePOJO.getRc().equals("0050")) {
                    Log.d("RP ContentID",contentID);
                    Log.d("RP ActCode",Integer.toString(activityCode));
                    Log.d("RP ContCode",Integer.toString(contentCode));
                    Log.d("RP Title",title);
                    Log.d("RP AccToken",accessToken);
                    Log.d("RP",votePOJO.getRm());
                    switch (activityCode) {
                        case 2:
                            textNumbersUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            textNumbersDownvote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            /*imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);*/
                            ;
                            break;
                        case 3:
                            textNumbersUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            textNumbersDownvote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            /*imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);*/
                            ;
                            break;
                        case 4:
                            //imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                            //imageButtonFavorite.setClickable(false);
                            textNumbersFavorite.setText(Integer.toString(votePOJO.getResults().getFavorite()));
                            ;
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<VotePOJO> call, Throwable t) {
                Toast.makeText(CaseMaterialDetailActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotification(String title, String content) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getApplicationContext(), FileManagerDownloadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //mBuilder.setContentIntent(pi);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                //.setContentIntent(pi)
                .setAutoCancel(true); // clear notification after click

        mNotificationManager.notify(0, mBuilder.build());

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
        //requestContent(materialId,accessToken);
    }
}
