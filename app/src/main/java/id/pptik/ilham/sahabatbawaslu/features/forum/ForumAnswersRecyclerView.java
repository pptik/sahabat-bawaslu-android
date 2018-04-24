package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;


public class ForumAnswersRecyclerView extends RecyclerView.Adapter<ForumAnswersRecyclerView.ViewHolder> {
    private List<String> usernameList;
    private List<String> datePostList;
    private List<String> contentPostList;
    private List<String> userPictureProfileList;
    private List<Integer> answerLevelList;
    private List<Integer> answerNumbersList;
    private List<String> answerIdList;

    private String[] username, datePost,
            contentPost, userPictureProfile,
            textNumberAnswerId;

    private Integer[] textNumberAnswerNumbers, answerLevelListArray;

    private SharedPreferences sharedPreferences;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvUsername2, tvAnswerContent, tvAnswerContent2,
                tvDatePost, tvDatePost2;

        public ImageView ivUserpicture,ivUserpicture2;
        public CardView cardViewAnswerLevel0,cardViewAnswerLevel1;
        public static Snackbar snackbar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.username);
            tvUsername2 = (TextView) itemView.findViewById(R.id.username_2);
            tvDatePost = (TextView) itemView.findViewById(R.id.date_post);
            tvDatePost2 = (TextView) itemView.findViewById(R.id.date_post_2);
            tvAnswerContent = (TextView) itemView.findViewById(R.id.answer_content);
            tvAnswerContent2 = (TextView) itemView.findViewById(R.id.answer_content_2);
            cardViewAnswerLevel0 = (CardView) itemView.findViewById(R.id.card_view_answer_level_0);
            cardViewAnswerLevel1 = (CardView) itemView.findViewById(R.id.card_view_answer_level_1);
            ivUserpicture = (ImageView) itemView.findViewById(R.id.user_picture);
            ivUserpicture2 = (ImageView) itemView.findViewById(R.id.user_picture_2);
        }
    }

    public ForumAnswersRecyclerView(List<String> usernameListParam, List<String> datePostListParam,
                                    List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                    List<String> answerIdListParam, List<Integer> answerNumbersPostListParam,
                                    List<Integer> answerLevelListParam) {

        this.usernameList = usernameListParam;
        this.datePostList = datePostListParam;
        this.contentPostList = contentPostListParam;
        this.userPictureProfileList = userPictureProfileListParam;
        this.answerIdList = answerIdListParam;
        this.answerLevelList = answerLevelListParam;
        this.answerNumbersList = answerNumbersPostListParam;

        username = new String[usernameList.size()];
        datePost = new String[datePostList.size()];
        contentPost = new String[contentPostList.size()];
        userPictureProfile = new String[userPictureProfileList.size()];
        textNumberAnswerId = new String[answerIdList.size()];
        textNumberAnswerNumbers = new Integer[answerNumbersList.size()];
        answerLevelListArray = new Integer[answerLevelList.size()];

        username = usernameList.toArray(username);
        datePost = datePostList.toArray(datePost);
        contentPost = contentPostList.toArray(contentPost);
        userPictureProfile = userPictureProfileList.toArray(userPictureProfile);
        textNumberAnswerId = answerIdList.toArray(textNumberAnswerId);
        textNumberAnswerNumbers= answerNumbersList.toArray(textNumberAnswerNumbers);
        answerLevelListArray = answerLevelList.toArray(answerLevelListArray);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forum_answers_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        //Identitas pengguna dan resume kegiatan
        holder.tvUsername.setText(username[position]);
        holder.tvDatePost.setText(datePost[position]);
        holder.tvAnswerContent.setText(contentPost[position]);
        holder.tvDatePost.setText(datePost[position]);

        holder.ivUserpicture.setImageDrawable(null);
        Glide.with(holder.ivUserpicture.getContext()).load(userPictureProfile[position]).into(holder.ivUserpicture);

    }



    /*public void gamifikasiAksiRespon(String contentID,
                                     final int activityCode, int contentCode,
                                     String title, String accessToken,
                                     final ViewHolder viewHolder, int textNumberFavoriteParam,
                                     int textNumberUpvoteParam, int textNumberDownvoteParam,
                                     final int position) {

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentID, activityCode,
                contentCode, title, accessToken);
        voteAction.enqueue(new Callback<VotePOJO>() {
            @Override
            public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                VotePOJO votePOJO = response.body();
                Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<VotePOJO> call, Throwable t) {
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return username.length;
    }
}
