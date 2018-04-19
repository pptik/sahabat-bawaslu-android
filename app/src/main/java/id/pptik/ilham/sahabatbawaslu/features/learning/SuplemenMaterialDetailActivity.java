package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuplemenMaterialDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.title_post)TextView titlePost;
    @BindView(R.id.content_post)TextView contentPost;
    @BindView(R.id.text_number_favorite)TextView textNumbersFavorite;
    @BindView(R.id.text_numbers_upvote)TextView textNumbersUpvote;
    @BindView(R.id.text_numbers_downvote)TextView textNumbersDownvote;
    @BindView(R.id.button_unduh_materi)Button buttonUnduhMateri;
    Bundle bundle;
    String materialId, accessToken;
    RestServiceInterface restServiceInterface;
    SharedPreferences sharedPreferences;
    Intent intent;
    Request request;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suplemen_material_detail);

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
        materialId = intent.getStringExtra(MaterialsRecyclerView.MATERIAL_ID);

        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", "abcde");

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final retrofit2.Call<MaterialDetailPOJO> materialDetail = restServiceInterface.materialDetail(materialId,accessToken);
        Log.d("DM","ID MATERI: "+materialId+" Akses Token: "+accessToken);
        materialDetail.enqueue(new Callback<MaterialDetailPOJO>() {
            @Override
            public void onResponse(Call<MaterialDetailPOJO> call, Response<MaterialDetailPOJO> response) {
                final MaterialDetailPOJO materialDetailPOJO = response.body();
                titlePost.setText(materialDetailPOJO.getResults().getTitle());
                contentPost.setText(materialDetailPOJO.getResults().getDesc());
                textNumbersUpvote.setText(Integer.toString(materialDetailPOJO.getResults().getUpvote()));
                textNumbersDownvote.setText(Integer.toString(materialDetailPOJO.getResults().getDownvote()));
                textNumbersFavorite.setText(Integer.toString(materialDetailPOJO.getResults().getFavorite()));

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

            @Override
            public void onFailure(Call<MaterialDetailPOJO> call, Throwable t) {
                Log.e("DM",t.getLocalizedMessage());
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
}
