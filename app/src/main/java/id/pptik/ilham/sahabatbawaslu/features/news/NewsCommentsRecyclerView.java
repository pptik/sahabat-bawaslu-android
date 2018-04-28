package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsCommentsRecyclerView extends RecyclerView.Adapter<NewsCommentsRecyclerView.ViewHolder> {
    private List<String> usernameList;
    private List<String> datePostList;
    private List<String> contentPostList;
    private List<String> userPictureProfileList;
    private List<Integer> commentNumbersList;
    private List<String> commentIdList;

    private String[] username, datePost,
            contentPost, userPictureProfile,
            textNumberCommentId;

    private Integer[] textNumberCommentNumbers;

    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;

    public static final String CONTENT_ID = "";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvUsername2, tvCommentContent, tvCommentContent2,
                tvDatePost, tvDatePost2, tvJumlahKomentar;

        public ImageView ivUserpicture,ivUserpicture2;
        public CardView cardViewCommentLevel0,cardViewCommentLevel1;
        public static Snackbar snackbar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.username);
            tvUsername2 = (TextView) itemView.findViewById(R.id.username_2);
            tvDatePost = (TextView) itemView.findViewById(R.id.date_post);
            tvDatePost2 = (TextView) itemView.findViewById(R.id.date_post_2);
            tvCommentContent = (TextView) itemView.findViewById(R.id.comment_content);
            tvCommentContent2 = (TextView) itemView.findViewById(R.id.comment_content_2);
            cardViewCommentLevel0 = (CardView) itemView.findViewById(R.id.card_view_comment_level_0);
            cardViewCommentLevel1 = (CardView) itemView.findViewById(R.id.card_view_comment_level_1);
            ivUserpicture = (ImageView) itemView.findViewById(R.id.user_picture);
            ivUserpicture2 = (ImageView) itemView.findViewById(R.id.user_picture_2);
            tvJumlahKomentar = (TextView) itemView.findViewById(R.id.text_view_jumlah_komentar);
        }
    }

    public NewsCommentsRecyclerView(List<String> usernameListParam, List<String> datePostListParam,
                                    List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                    List<String> commentIdListParam, List<Integer> commentNumbersPostListParam) {

        this.usernameList = usernameListParam;
        this.datePostList = datePostListParam;
        this.contentPostList = contentPostListParam;
        this.userPictureProfileList = userPictureProfileListParam;
        this.commentNumbersList = commentNumbersPostListParam;
        this.commentIdList = commentIdListParam;

        username = new String[usernameList.size()];
        datePost = new String[datePostList.size()];
        contentPost = new String[contentPostList.size()];
        userPictureProfile = new String[userPictureProfileList.size()];
        textNumberCommentId = new String[commentIdList.size()];
        textNumberCommentNumbers = new Integer[commentNumbersList.size()];


        username = usernameList.toArray(username);
        datePost = datePostList.toArray(datePost);
        contentPost = contentPostList.toArray(contentPost);
        userPictureProfile = userPictureProfileList.toArray(userPictureProfile);
        textNumberCommentId = commentIdList.toArray(textNumberCommentId);
        textNumberCommentNumbers= commentNumbersList.toArray(textNumberCommentNumbers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_comments_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        //Identitas pengguna dan resume kegiatan
        holder.tvUsername.setText(username[position]);
        holder.tvDatePost.setText(datePost[position]);
        holder.tvCommentContent.setText(contentPost[position]);
        holder.tvDatePost.setText(datePost[position]);

        holder.ivUserpicture.setImageDrawable(null);
        holder.tvJumlahKomentar.setText("\u2022 "+textNumberCommentNumbers[position]+" Komentar");
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
