package id.pptik.ilham.sahabatbawaslu.features.challenge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ChallengePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.recycler_view_challenge)RecyclerView recyclerViewChallenge;
    private SwipeRefreshLayout swipeRefreshRecycler;
    private String access_token;
    private ProgressDialog progressDialog;
    private RestServiceInterface restServiceInterface;
    private List<String> idsChallenge = new ArrayList<>();
    private List<String> contents = new ArrayList<>();
    private List<Integer> poins = new ArrayList<>();
    private List<String> expire = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.menu_challenge));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerViewChallenge.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewChallenge.setLayoutManager(mLayoutManager);

        challengeLoad(access_token);
        swipeRefreshRecycler = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                challengeLoad(access_token);
                swipeRefreshRecycler.setRefreshing(false);
            }
        });

    }

    private void challengeLoad(String access_token) {
        progressDialog = new ProgressDialog(ChallengeActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(this)){ //checking if have internet connectivity
            //inisiasi restService
            restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
            final Call<ChallengePOJO> challengesListPOJOCall = restServiceInterface.challengesList(0, access_token);
            challengesListPOJOCall.enqueue(new Callback<ChallengePOJO>() {
                @Override
                public void onResponse(Call<ChallengePOJO> call, Response<ChallengePOJO> response) {
                    contents.clear();
                    poins.clear();
                    expire.clear();
                    idsChallenge.clear();
                    ChallengePOJO challengePOJO = response.body();
                    if(challengePOJO.getSuccess()){
                        for(int i=0; i<challengePOJO.getResults().size(); i++){
                            idsChallenge.add(challengePOJO.getResults().get(i).get_id());
                            contents.add(challengePOJO.getResults().get(i).getContent());
                            poins.add(challengePOJO.getResults().get(i).getPoin());
                            expire.add(challengePOJO.getResults().get(i).getExpire());
                        }

                        mAdapter = new ChallengeRecyclerView(idsChallenge, contents, poins, expire, ChallengeActivity.this);
                        mAdapter.notifyDataSetChanged();
                        recyclerViewChallenge.setAdapter(mAdapter);

                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                    }else{
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), challengePOJO.getRm(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ChallengePOJO> call, Throwable t) {

                }
            });
        }else {
            progressDialog.setProgress(100);
            progressDialog.dismiss();

            //show error message
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
