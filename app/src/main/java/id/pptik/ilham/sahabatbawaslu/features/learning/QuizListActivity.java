package id.pptik.ilham.sahabatbawaslu.features.learning;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.QuizzessListPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.swipeRefreshRecycler)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;
    private String accessToken;
    private ProgressDialog progressDialog;
    private List<String> quizTitleArrayList = new ArrayList<String>();
    private List<String> quizDescArrayList = new ArrayList<String>();
    private List<String> quizIdArrayList = new ArrayList<String>();
    private List<String> quizLevelArrayList = new ArrayList<String>();
    private List<String> quizTotalScoreArrayList = new ArrayList<String>();
    private List<String> quizUserScoreArrayList = new ArrayList<String>();
    public static final String QUIZ_ID = "";
    public static final String QUIZ_TITLE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));

        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.menu_list_quiz));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(QuizListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);

        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", "abcde");
        progressDialog = new ProgressDialog(this);

        getQuizzessList(accessToken);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQuizzessList(accessToken);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getQuizzessList(String accessToken){
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        if (RestServiceClass.isNetworkAvailable(this)) {
            Call<QuizzessListPOJO> quizzessListPOJOCall = restServiceInterface.quizzessList("Dummy", accessToken);
            quizzessListPOJOCall.enqueue(new Callback<QuizzessListPOJO>() {
                @Override
                public void onResponse(Call<QuizzessListPOJO> call, Response<QuizzessListPOJO> response) {

                    //Adapter
                    QuizzessListRecyclerView.quizIdArrayList.clear();
                    QuizzessListRecyclerView.quizTitleArrayList.clear();
                    QuizzessListRecyclerView.quizDescArrayList.clear();
                    QuizzessListRecyclerView.quizLevelArrayList.clear();
                    QuizzessListRecyclerView.quizTotalScoreArrayList.clear();
                    QuizzessListRecyclerView.quizUserScoreArrayList.clear();

                    //Fragment
                    quizIdArrayList.clear();
                    quizTitleArrayList.clear();
                    quizDescArrayList.clear();
                    quizLevelArrayList.clear();
                    quizTotalScoreArrayList.clear();
                    quizUserScoreArrayList.clear();

                   QuizzessListPOJO quizzessListPOJO = response.body();
                   for (int item = 0;item < quizzessListPOJO.getResults().size();item++){
                       quizIdArrayList.add(quizzessListPOJO.getResults().get(item).getId());
                       Log.d("QUIZLIST","QUIZ ID: "+quizzessListPOJO.getResults().get(item).getId());
                       quizTitleArrayList.add(quizzessListPOJO.getResults().get(item).getTitle());
                       quizDescArrayList.add(quizzessListPOJO.getResults().get(item).getDesc());
                       quizLevelArrayList.add(Integer.toString(quizzessListPOJO.getResults().get(item).getLevel()));
                       quizUserScoreArrayList.add(Integer.toString(quizzessListPOJO.getResults().get(item).getUserScore()));
                       quizTotalScoreArrayList.add(Integer.toString(quizzessListPOJO.getResults().get(item).getTotalScore()));
                   }

                    mAdapter = new QuizzessListRecyclerView(quizIdArrayList,
                            quizTitleArrayList,quizDescArrayList,quizLevelArrayList,
                            quizUserScoreArrayList, quizTotalScoreArrayList,QuizListActivity.this);

                    recyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<QuizzessListPOJO> call, Throwable t) {
                    Log.e("ERROR",t.getLocalizedMessage());
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
                            Intent intent = new Intent(QuizListActivity.this, LoginActivity.class);
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
