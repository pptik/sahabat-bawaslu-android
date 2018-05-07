package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.NewsSubCommentsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.AddSubCommentPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizDetailRecyclerView extends RecyclerView.Adapter<QuizDetailRecyclerView.ViewHolder> {
    public static List<String> questionsList = new ArrayList<String>();
    public static List<Integer> pointsList = new ArrayList<Integer>();
    public static ArrayList<List<String>> quizTextQuestionMultipleChoiceArrayList= new ArrayList<List<String>>();
    public static ArrayList<List<Boolean>> quizCorrectQuestionMultipleChoiceArrayList= new ArrayList<List<Boolean>>();

    private String contentId;
    private String[] questionsArray;
    private Boolean[] pointsArray;


    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;

    public static final String CONTENT_ID = "";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSoal;
        public RadioGroup radioGroup;

        public Button buttonSubmitAnswers;

        private RestServiceInterface restServiceInterface;

        public static Snackbar snackbar;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewSoal = (TextView) itemView.findViewById(R.id.text_view_soal);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radio_group_question);
        }
    }

    public QuizDetailRecyclerView(List<String> questionsListParam) {
        this.questionsList = questionsListParam;

        /*this.usernameSubKomentarList = usernameListParam2;
        this.datePostSubKomentarList = datePostListParam2;
        this.contentPostSubKomentarList = contentPostListParam2;
        this.userPictureProfileSubKomentarList = userPictureProfileListParam2;*/

        questionsArray = new String[questionsList.size()];

        /*usernameSubKomentar = new String[usernameSubKomentarList.size()];
        datePostSubKomentar = new String[datePostSubKomentarList.size()];
        contentPostSubKomentar = new String[contentPostSubKomentarList.size()];
        userPictureProfileSubKomentar = new String[userPictureProfileSubKomentarList.size()];*/


        questionsArray = questionsList.toArray(questionsArray);

        /*usernameSubKomentar = usernameSubKomentarList.toArray(usernameSubKomentar);
        datePostSubKomentar = datePostSubKomentarList.toArray(datePostSubKomentar);
        contentPostSubKomentar = contentPostSubKomentarList.toArray(contentPostSubKomentar);
        userPictureProfileSubKomentar = userPictureProfileSubKomentarList.toArray(userPictureProfileSubKomentar);*/

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_quiz_detail, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);



        return new ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //final String access_token = sharedPreferences.getString("accessToken", "abcde");
        holder.textViewSoal.setText(questionsArray[position]);


        RadioButton radioButton = new RadioButton(holder.itemView.getContext());
        radioButton.setText("Tess");
        radioButton.setId(3000);//set radiobutton id and store it somewhere
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams
                (RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        holder.radioGroup.addView(radioButton, params);

    }

    @Override
    public int getItemCount() {
        return questionsArray.length;
    }
}
