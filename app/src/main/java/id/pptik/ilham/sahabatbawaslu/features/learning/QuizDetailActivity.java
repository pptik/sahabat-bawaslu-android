package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
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
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.news.NewsCommentsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.NewsPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.QuizzDetailPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.recycler_view)RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private String quizTitle, quizId;
    private RestServiceInterface restServiceInterface;
    private ProgressDialog progressDialog;

    private List<String> quizQuestionsArrayList = new ArrayList<String>();
    private List<Integer> quizQuestionsPointArrayList = new ArrayList<Integer>();
    private ArrayList<List<String>> quizTextQuestionMultipleChoice = new ArrayList<List<String>>();
    private ArrayList<List<Boolean>> quizCorrectQuestionMultipleChoice = new ArrayList<List<Boolean>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));

        ButterKnife.bind(this);

        intent = getIntent();
        Bundle bundle = intent.getExtras();

        quizId = bundle.getString(QuizListActivity.QUIZ_ID);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(QuizDetailActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        contentRequest(quizId);
    }

    private void contentRequest(final String quizId) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        Call<QuizzDetailPOJO> quizzDetailPOJOCall = restServiceInterface.quizDetail(quizId,access_token);
        quizzDetailPOJOCall.enqueue(new Callback<QuizzDetailPOJO>() {
            @Override
            public void onResponse(Call<QuizzDetailPOJO> call, Response<QuizzDetailPOJO> response) {
                QuizzDetailPOJO quizzDetailPOJO = response.body();
                for (int item = 0;item<quizzDetailPOJO.getResults().getQuestionList().size();item++){
                    quizQuestionsArrayList.add(quizzDetailPOJO.getResults().getQuestionList().get(item).getQuestion());
                    quizQuestionsPointArrayList.add(quizzDetailPOJO.getResults().getQuestionList().get(item).getPoin());

                    ArrayList<String> quizQuestionMultipleChoiceTextSingleList = new ArrayList<String>();
                    ArrayList<Boolean> quizQuestionMultipleChoiceCorrectSingleList = new ArrayList<Boolean>();

                    for (int multipleChoice = 0;
                         multipleChoice<quizzDetailPOJO.getResults().getQuestionList().get(item).getMultipleChoice().size();
                         multipleChoice++){

                        quizQuestionMultipleChoiceTextSingleList
                                .add(quizzDetailPOJO.getResults().getQuestionList()
                                .get(item).getMultipleChoice().get(multipleChoice)
                                .getText());
                        quizQuestionMultipleChoiceCorrectSingleList
                                .add(quizzDetailPOJO.getResults().getQuestionList()
                                .get(item).getMultipleChoice().get(multipleChoice)
                                .getCorrect());
                    }

                    quizTextQuestionMultipleChoice.add(quizQuestionMultipleChoiceTextSingleList);
                    quizCorrectQuestionMultipleChoice.add(quizQuestionMultipleChoiceCorrectSingleList);
                    quizTitle = quizzDetailPOJO.getResults().getTitle();
                    toolbar.setTitle("Kuis "+quizTitle);

                }

                mAdapter = new QuizDetailRecyclerView(quizQuestionsArrayList,quizQuestionsPointArrayList,
                        quizTextQuestionMultipleChoice,quizCorrectQuestionMultipleChoice);

                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);

                progressDialog.setProgress(100);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<QuizzDetailPOJO> call, Throwable t) {
                Log.e("DETAIL ACTIVITY",t.getLocalizedMessage());
                progressDialog.setProgress(100);
                progressDialog.dismiss();
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
