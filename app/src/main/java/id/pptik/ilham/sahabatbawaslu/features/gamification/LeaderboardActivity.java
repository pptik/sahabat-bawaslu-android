package id.pptik.ilham.sahabatbawaslu.features.gamification;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.NewsCommentsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LeaderboardPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity.greaterThanEqualLollipop;

public class LeaderboardActivity extends AppCompatActivity {
    String access_token;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.recycler_view_leaderboard)RecyclerView recyclerViewLeaderboard;
    RestServiceInterface restServiceInterface;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshRecycler;
    SharedPreferences sharedPreferences;
    private List<String> username = new ArrayList<String>();
    private List<Integer> poin = new ArrayList<Integer>();
    private List<String> thumbnail = new ArrayList<String>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (greaterThanEqualLollipop){
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        }

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.menu_leaderboard));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerViewLeaderboard.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewLeaderboard.setLayoutManager(mLayoutManager);

        sharedPreferences = this.getSharedPreferences("User",Context.MODE_PRIVATE);
        access_token = sharedPreferences.getString("accessToken","abcde");

        contentLoad(access_token);

        swipeRefreshRecycler = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contentLoad(access_token);
                swipeRefreshRecycler.setRefreshing(false);
            }
        });
    }

    private void contentLoad(String access_token){
        progressDialog = new ProgressDialog(LeaderboardActivity.this);
        //progressDialog.setMessage("Mohon tunggu sedang dalam proses");
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(this)){

            restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
            final Call<LeaderboardPOJO> leaderboardPOJOCall = restServiceInterface.leaderboard(0,access_token);
            leaderboardPOJOCall.enqueue(new Callback<LeaderboardPOJO>() {
                @Override
                public void onResponse(Call<LeaderboardPOJO> call, Response<LeaderboardPOJO> response) {
                    username.clear();
                    poin.clear();
                    thumbnail.clear();

                    LeaderboardPOJO leaderboardPOJO = response.body();
                    for (int item = 0 ; item < leaderboardPOJO.getResults().size(); item++){
                        username.add(leaderboardPOJO.getResults().get(item).getUsername());
                        poin.add(leaderboardPOJO.getResults().get(item).getLeader_poin());
                        thumbnail.add(leaderboardPOJO.getResults().get(item).getDisplay_picture());
                    }

                    mAdapter = new LeaderboardRecyclerView(username,thumbnail,poin);
                    mAdapter.notifyDataSetChanged();
                    recyclerViewLeaderboard.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<LeaderboardPOJO> call, Throwable t) {
                    Log.e("ax",t.getLocalizedMessage());
                }
            });
        }else{
            progressDialog.setProgress(100);
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(R.string.pastikan_internet_label)
                    .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }).show();
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
