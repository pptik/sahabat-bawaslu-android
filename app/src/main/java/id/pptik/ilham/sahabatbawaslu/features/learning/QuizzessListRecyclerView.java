package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.forum.DetailForumActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.AddNewsActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsAdminActivity;

import static id.pptik.ilham.sahabatbawaslu.features.learning.QuizListActivity.QUIZ_ID;
import static id.pptik.ilham.sahabatbawaslu.features.learning.QuizListActivity.QUIZ_TITLE;
import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.FORUM_ID;

/**
 * Created by Ilham on 15/03/18.
 */

public class QuizzessListRecyclerView extends RecyclerView.Adapter<QuizzessListRecyclerView.ViewHolder> {

    public static List<String> quizIdArrayList = new ArrayList<String>();
    public static List<String> quizTitleArrayList = new ArrayList<String>();
    public static List<String> quizDescArrayList = new ArrayList<String>();
    public static List<String> quizLevelArrayList = new ArrayList<String>();
    public static List<String> quizUserScoreArrayList = new ArrayList<String>();
    public static List<String> quizTotalScoreArrayList = new ArrayList<String>();

    private Activity activity;

    private String[]  quizIdArray, quizTitleArray,
            quizDescArray, quizLevelArray, quizUserTotalScoreArray,
            quizTotalScoreArray;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvQuizLevel, tvQuizTitle, tvQuizDesc,
                tvQuizScore, tvQuizTotalScore;
        public Button btMulaiQuiz;
        public View view;


        public ViewHolder(View itemView) {
            super(itemView);

            tvQuizTitle = (TextView)itemView.findViewById(R.id.text_view_quiz_title);
            tvQuizDesc = (TextView)itemView.findViewById(R.id.text_view_quiz_desc);
            tvQuizLevel = (TextView)itemView.findViewById(R.id.text_view_quiz_level);
            tvQuizScore = (TextView)itemView.findViewById(R.id.text_view_quiz_score);
            tvQuizTotalScore = (TextView)itemView.findViewById(R.id.text_view_quiz_total_score);
            btMulaiQuiz = (Button) itemView.findViewById(R.id.button_take_quiz);

        }
    }

    public QuizzessListRecyclerView(List<String> quizIdArrayListParam, List<String> quizTitleArrayListParam,
                                    List<String> quizDescArrayListParam, List<String> quizLevelArrayListParam,
                                    List<String> quizUserScoreArrayListParam, List<String> quizScoreTotalArrayListParam,
                                    Activity activity) {
        this.activity = activity;
        this.quizIdArrayList = quizIdArrayListParam;
        this.quizTitleArrayList = quizTitleArrayListParam;
        this.quizDescArrayList = quizDescArrayListParam;
        this.quizLevelArrayList = quizLevelArrayListParam;
        this.quizUserScoreArrayList = quizUserScoreArrayListParam;
        this.quizTotalScoreArrayList = quizScoreTotalArrayListParam;

        quizIdArray = new String[quizIdArrayList.size()];
        quizTitleArray = new String[quizTitleArrayList.size()];
        quizDescArray = new String[quizDescArrayList.size()];
        quizLevelArray = new String[quizLevelArrayList.size()];
        quizUserTotalScoreArray = new String[quizUserScoreArrayList.size()];
        quizTotalScoreArray = new String[quizTotalScoreArrayList.size()];

        quizIdArray = quizIdArrayList.toArray(quizIdArray);
        quizTitleArray = quizTitleArrayList.toArray(quizTitleArray);
        quizDescArray = quizDescArrayList.toArray(quizDescArray);
        quizLevelArray = quizLevelArrayList.toArray(quizLevelArray);
        quizUserTotalScoreArray = quizUserScoreArrayList.toArray(quizUserTotalScoreArray);
        quizTotalScoreArray = quizTotalScoreArrayList.toArray(quizTotalScoreArray);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_quizzess_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvQuizTitle.setText(quizTitleArray[position]);
        holder.tvQuizDesc.setText(quizDescArray[position]);
        holder.tvQuizLevel.setText(quizLevelArray[position]);

        holder.tvQuizScore.setText(holder.itemView.getResources().getString(R.string.quiz_score_label)+" "+quizUserTotalScoreArray[position]);
        holder.tvQuizTotalScore.setText(holder.itemView.getResources().getString(R.string.quiz_total_score_label)+" "+quizTotalScoreArray[position]);

        holder.btMulaiQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext());
                alertDialog.setMessage(R.string.ensure_quiz_label)
                        .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /*Intent intent = new Intent(v.getContext(), DetailNewsAdminActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(CONTENT_ID,contentId[position]);
                                bundle.putString(TITLE,titlePost[position]);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/

                                Intent intent = new Intent(holder.itemView.getContext(), QuizDetailActivity.class);
                                Bundle bundle = new Bundle();
                                Log.d("QUIZ ID","QUIZ ID: "+quizIdArray[position]);
                                bundle.putString(QUIZ_ID,quizIdArray[position]);
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        })
                        .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return quizIdArray.length;
    }
}

