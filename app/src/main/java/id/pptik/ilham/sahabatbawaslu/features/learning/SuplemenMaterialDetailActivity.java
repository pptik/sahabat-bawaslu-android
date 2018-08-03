package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialDetailPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity.greaterThanEqualLollipop;

public class SuplemenMaterialDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.title_post)TextView titlePost;
    @BindView(R.id.content_post)TextView contentPost;
    @BindView(R.id.text_number_favorite)TextView textNumbersFavorite;
    @BindView(R.id.text_numbers_upvote)TextView textNumbersUpvote;
    @BindView(R.id.text_numbers_downvote)TextView textNumbersDownvote;
    @BindView(R.id.button_unduh_materi)Button buttonUnduhMateri;
    @BindView(R.id.button_favorite_suplemen)ImageView imageButtonFavorite;
    @BindView(R.id.button_upvote_suplemen)ImageView imageButtonUpvote;
    @BindView(R.id.button_downvote_suplemen)ImageView imageButtonDownvote;
    Bundle bundle;
    String materialId, accessToken, title;
    RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;
    Intent intent;
    Request request;
    Task task;
    private SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suplemen_material_detail);

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

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        materialId = bundle.getString(MaterialsRecyclerView.MATERIAL_ID);
        title = bundle.getString(MaterialsRecyclerView.TITLE);
        //Log.d("DEBUG SUPLEMEN",title);
        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", "abcde");

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final retrofit2.Call<MaterialDetailPOJO> materialDetail = restServiceInterface.materialDetail(materialId,accessToken);

        materialDetail.enqueue(new Callback<MaterialDetailPOJO>() {
            @Override
            public void onResponse(Call<MaterialDetailPOJO> call, Response<MaterialDetailPOJO> response) {
                final MaterialDetailPOJO materialDetailPOJO = response.body();
                titlePost.setText(materialDetailPOJO.getResults().getTitle());
                contentPost.setText(materialDetailPOJO.getResults().getDesc());
                textNumbersUpvote.setText(Integer.toString(materialDetailPOJO.getResults().getUpvote()));
                textNumbersDownvote.setText(Integer.toString(materialDetailPOJO.getResults().getDownvote()));
                textNumbersFavorite.setText(Integer.toString(materialDetailPOJO.getResults().getFavorite()));

                //Inisial Gamikasi
                Log.d("Fav status: ",Boolean.toString(materialDetailPOJO.getResults().getFavorited()));
                if(materialDetailPOJO.getResults().getFavorited()){
                    imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                }else{
                    imageButtonFavorite.setImageResource(R.drawable.ic_favorite_border_black_18dp);
                }

                //buttonUpvote.setVisibility(View.GONE);
                if(materialDetailPOJO.getResults().getUpvoted()){
                    imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                }else{
                    imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }

                //buttonDownvote.setVisibility(View.GONE);
                if(materialDetailPOJO.getResults().getDownvoted()){
                    imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                }else{
                    imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }

                //Gamifikasi event handler
                imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(SuplemenMaterialDetailActivity.this, "ABCDE", Toast.LENGTH_SHORT).show();
                        gamifikasiAksiRespon(materialId,4,1,title,accessToken);
                    }
                });

                imageButtonUpvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gamifikasiAksiRespon(materialId,2,1,title,accessToken);
                    }
                });

                imageButtonDownvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gamifikasiAksiRespon(materialId,3,1,title,accessToken);
                    }
                });

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
                                    if(finalItem == 0){
                                        showNotification(getResources().getString(R.string.app_name),getResources().getString(R.string.download_sedang_diunduh), false);
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
                                public void onProgress(Request request, long curBytes, long totalBytes) {

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
                                        showNotification(getResources().getString(R.string.app_name),getResources().getString(R.string.download_selesai_diunduh), true);
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

            @Override
            public void onFailure(Call<MaterialDetailPOJO> call, Throwable t) {
                Log.e("DM",t.getLocalizedMessage());
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                final retrofit2.Call<MaterialDetailPOJO> materialDetail = restServiceInterface.materialDetail(materialId,accessToken);
                materialDetail.enqueue(new Callback<MaterialDetailPOJO>() {
                    @Override
                    public void onResponse(Call<MaterialDetailPOJO> call, Response<MaterialDetailPOJO> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        final MaterialDetailPOJO materialDetailPOJO = response.body();
                        titlePost.setText(materialDetailPOJO.getResults().getTitle());
                        contentPost.setText(materialDetailPOJO.getResults().getDesc());
                        textNumbersUpvote.setText(Integer.toString(materialDetailPOJO.getResults().getUpvote()));
                        textNumbersDownvote.setText(Integer.toString(materialDetailPOJO.getResults().getDownvote()));
                        textNumbersFavorite.setText(Integer.toString(materialDetailPOJO.getResults().getFavorite()));

                        //Inisial Gamikasi
                        Log.d("Fav status: ",Boolean.toString(materialDetailPOJO.getResults().getFavorited()));
                        if(materialDetailPOJO.getResults().getFavorited()){
                            imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                        }else{
                            imageButtonFavorite.setImageResource(R.drawable.ic_favorite_border_black_18dp);
                        }

                        //buttonUpvote.setVisibility(View.GONE);
                        if(materialDetailPOJO.getResults().getUpvoted()){
                            imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                        }else{
                            imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                        }

                        //buttonDownvote.setVisibility(View.GONE);
                        if(materialDetailPOJO.getResults().getDownvoted()){
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                        }else{
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                        }

                        //Gamifikasi event handler
                        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(SuplemenMaterialDetailActivity.this, "ABCDE", Toast.LENGTH_SHORT).show();
                                gamifikasiAksiRespon(materialId,4,1,title,accessToken);
                            }
                        });

                        imageButtonUpvote.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gamifikasiAksiRespon(materialId,2,1,title,accessToken);
                            }
                        });

                        imageButtonDownvote.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gamifikasiAksiRespon(materialId,3,1,title,accessToken);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<MaterialDetailPOJO> call, Throwable t) {
                        Log.e("DM",t.getLocalizedMessage());
                    }
                });

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
                Toast.makeText(SuplemenMaterialDetailActivity.this, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
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
                            imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                            /*imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);*/
                            ;
                            break;
                        case 3:
                            textNumbersUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            textNumbersDownvote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                            imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                            /*imageButtonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                            imageButtonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);*/
                            ;
                            break;
                        case 4:
                            //imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                            //imageButtonFavorite.setClickable(false);
                            imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                            textNumbersFavorite.setText(Integer.toString(votePOJO.getResults().getFavorite()));
                            ;
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<VotePOJO> call, Throwable t) {
                Toast.makeText(SuplemenMaterialDetailActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotification(String title, String content, boolean isDone) {
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

        if(!isDone) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle(title) // title for notification
                    .setContentText(content)// message for notification
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    //.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    //.setContentIntent(pi)
                    .setAutoCancel(true); // clear notification after click

            mNotificationManager.notify(0, mBuilder.build());
        }else{
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle(title) // title for notification
                    .setContentText(content)// message for notification
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    //.setContentIntent(pi)
                    .setAutoCancel(true); // clear notification after

            mNotificationManager.notify(0, mBuilder.build());
        }



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
