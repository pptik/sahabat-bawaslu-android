package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.FORUM_ID;

/**
 * Created by Ilham on 15/03/18.
 */

public class ForumRecyclerView extends RecyclerView.Adapter<ForumRecyclerView.ViewHolder> {

    public static List<Integer> materialTypeList = new ArrayList<Integer>();
    public static List<String> datePostList = new ArrayList<String>();
    public static List<String> hashtagList = new ArrayList<String>();
    public static List<String> titleList = new ArrayList<String>();
    public static List<String> forumIdList = new ArrayList<String>();
    public static List<Integer> upVoteNumbersList = new ArrayList<Integer>();
    public static List<Integer> downVoteNumbersList = new ArrayList<Integer>();
    public static List<Integer> commentNumbersList = new ArrayList<Integer>();
    public static List<Integer> favoriteNumbersList = new ArrayList<Integer>();

    public static List<String> contentIdList= new ArrayList<String>();
    public static List<String> contentTypeList= new ArrayList<String>();


    private Activity activity;
    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;

    public static List<Boolean> statusUpvoteList= new ArrayList<Boolean>();
    public static List<Boolean> statusDownvoteList= new ArrayList<Boolean>();
    public static List<Boolean> statusFavoriteList= new ArrayList<Boolean>();

    private String[]  datePost, hashtag, title, forumId, contentId, contentType;
    private Integer[] favoriteNumbers, upVoteNumbers, downVoteNumbers,
                     commentNumbers;
    private Boolean[] statusUpvote, statusDownvote,
            statusFavorite;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDatePost, tvTitlePost, tvHashtag,
                tvUpVoteNumbers, tvDownVoteNumbers, tvCommentNumbers,
                tvFavoriteNumbers;
        public ImageView buttonUpvote,
                buttonDownvote, buttonFavorite,
                buttonComment, ivIconNumbersUpvote,
                ivIconNumbersDownvote;
        public View view;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDatePost = (TextView)itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView)itemView.findViewById(R.id.title_post);
            tvHashtag = (TextView)itemView.findViewById(R.id.hashtag);
            tvUpVoteNumbers = (TextView)itemView.findViewById(R.id.text_numbers_upvote);
            tvDownVoteNumbers = (TextView)itemView.findViewById(R.id.text_numbers_downvote);
            tvCommentNumbers = (TextView)itemView.findViewById(R.id.text_comments);
            tvFavoriteNumbers = (TextView)itemView.findViewById(R.id.text_number_favorite);

            buttonUpvote = (ImageView)itemView.findViewById(R.id.button_upvote);
            buttonDownvote = (ImageView)itemView.findViewById(R.id.button_downvote);
            buttonFavorite = (ImageView)itemView.findViewById(R.id.button_favorite);
            buttonComment = (ImageView)itemView.findViewById(R.id.button_comment);

            ivIconNumbersUpvote = (ImageView)itemView.findViewById(R.id.icon_numbers_upvote);
            ivIconNumbersDownvote = (ImageView)itemView.findViewById(R.id.icon_numbers_downvote);
        }
    }

    public ForumRecyclerView(List<String> datePostListParam, List<String> forumIdParam,
                             List<String> hashtagListParam, List<String> titleListParam,
                             List<Integer> favoriteNumberListParam, List<Integer> upVoteNumberListParam,
                             List<Integer> downVoteNumberListParam, List<Integer> commentNumberListParam,
                             List<String> contentIdListParam,List<Boolean> statusUpvoteListsParam,
                             List<Boolean> statusDownvoteListsParam, List<Boolean> statusFavoriteListsParam,
                             Activity activity) {
        this.activity = activity;
        this.datePostList = datePostListParam;
        this.hashtagList = hashtagListParam;
        this.titleList = titleListParam;
        this.favoriteNumbersList = favoriteNumberListParam;
        this.upVoteNumbersList = upVoteNumberListParam;
        this.downVoteNumbersList = downVoteNumberListParam;
        this.commentNumbersList= commentNumberListParam;
        this.forumIdList= forumIdParam;
        this.contentIdList = contentIdListParam;
        this.statusUpvoteList = statusUpvoteListsParam;
        this.statusDownvoteList = statusDownvoteListsParam;
        this.statusFavoriteList = statusFavoriteListsParam;


        datePost = new String[datePostList.size()];
        hashtag = new String[hashtagList.size()];
        title = new String[titleList.size()];
        favoriteNumbers = new Integer[favoriteNumbersList.size()];
        upVoteNumbers = new Integer[upVoteNumbersList.size()];
        downVoteNumbers = new Integer[downVoteNumbersList.size()];
        commentNumbers = new Integer[commentNumbersList.size()];
        forumId = new String[forumIdList.size()];
        contentId = new String[contentIdList.size()];
        statusUpvote = new Boolean[statusUpvoteList.size()];
        statusDownvote = new Boolean[statusDownvoteList.size()];
        statusFavorite = new Boolean[statusFavoriteList.size()];


        datePost = datePostList.toArray(datePost);
        hashtag = hashtagList.toArray(hashtag);
        title = titleList.toArray(title);
        favoriteNumbers = favoriteNumbersList.toArray(favoriteNumbers);
        upVoteNumbers= upVoteNumbersList.toArray(upVoteNumbers);
        downVoteNumbers= downVoteNumbersList.toArray(downVoteNumbers);
        commentNumbers= commentNumbersList.toArray(commentNumbers);
        forumId= forumIdList.toArray(forumId);
        contentId = contentIdList.toArray(contentId);
        statusUpvote = statusUpvoteList.toArray(statusUpvote);
        statusDownvote = statusDownvoteList.toArray(statusDownvote);
        statusFavorite = statusFavoriteList.toArray(statusFavorite);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forum_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        holder.tvHashtag.setText(hashtag[position]);
        holder.tvTitlePost.setText(title[position]);
        holder.tvDatePost.setText("dibuat "+datePost[position]);

        holder.tvCommentNumbers.setText(Integer.toString(commentNumbers[position])+" Jawaban");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForum = new Intent(v.getContext(), DetailForumActivity.class);
                intentForum.putExtra(FORUM_ID,forumId[position]);
                v.getContext().startActivity(intentForum);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //Gamifikasi
        holder.tvFavoriteNumbers.setText(Integer.toString(favoriteNumbers[position]));
        holder.tvUpVoteNumbers.setText(Integer.toString(upVoteNumbers[position]));
        holder.tvDownVoteNumbers.setText(Integer.toString(downVoteNumbers[position]));

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);

        if (statusUpvote[position]){
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
            holder.buttonUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(2, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);
                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 2,
                            3, title[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            Log.d("UP",Integer.toString(votePOJO.getResults().getUpvote()));
                            holder.tvUpVoteNumbers.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            holder.buttonDownvote.setClickable(true);
                            holder.buttonUpvote.setClickable(false);
                        }

                        @Override
                        public void onFailure(Call<VotePOJO> call, Throwable t) {
                            Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            holder.buttonUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(2, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);
                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 2,
                            3, title[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            //Log.d("UPVOTE getRC",votePOJO.getRc());
                            if(votePOJO.getRc().equals("0000")){
                                holder.tvUpVoteNumbers.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                                holder.buttonDownvote.setClickable(true);
                                holder.buttonUpvote.setClickable(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<VotePOJO> call, Throwable t) {
                            Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        if (statusDownvote[position]){
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
            holder.buttonDownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(3, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 3,
                            3, title[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            holder.tvUpVoteNumbers.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            holder.tvDownVoteNumbers.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                            holder.buttonDownvote.setClickable(false);
                            holder.buttonUpvote.setClickable(true);
                        }

                        @Override
                        public void onFailure(Call<VotePOJO> call, Throwable t) {
                            Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else{
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            holder.buttonDownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(3, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 3,
                            3, title[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            if(votePOJO.getRc().equals("0000")){
                                holder.tvUpVoteNumbers.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                                holder.tvDownVoteNumbers.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                                holder.buttonDownvote.setClickable(false);
                                holder.buttonUpvote.setClickable(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<VotePOJO> call, Throwable t) {
                            Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        //
        if (statusFavorite[position]) {
            holder.buttonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
            holder.buttonFavorite.setClickable(false);
            /*holder.buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(contentId[position], 4,
                            Integer.parseInt(contentType[position]), titlePost[position],
                            access_token, holder, textNumberFavorite[position],
                            textNumberUpvote[position], textNumberDownvote[position],
                            position);

                            restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                            final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 4,
                                    Integer.parseInt(contentType[position]), titlePost[position], access_token);
                            voteAction.enqueue(new Callback<VotePOJO>() {
                                @Override
                                public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                                    VotePOJO votePOJO = response.body();
                                    Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();

                                    Log.d("FAV",Integer.toString(votePOJO.getResults().getFavorite()));
                                    holder.tvNumberFavorite.setText(Integer.toString(votePOJO.getResults().getFavorite()));
                                    holder.tvNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                                    holder.tvNumberDownvote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
                                    holder.tvNumberComment.setText(Integer.toString(votePOJO.getResults().getComment())+" Komentar");

                                }

                                @Override
                                public void onFailure(Call<VotePOJO> call, Throwable t) {
                                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });*/
        } else {
            holder.buttonFavorite.setImageResource(R.drawable.ic_favorite_border_black_18dp);
            holder.buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(4, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    Log.d("LOVE ContentId",contentId[position]);
                    Log.d("LOVE Title",title[position]);
                    /*final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 3,
                            3, title[position], access_token);*/
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 4,
                            3, title[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            //Log.d("FAV",Integer.toString(votePOJO.getResults().getFavorite()));
                            if(votePOJO.getRc().equals("0000")){
                                holder.tvFavoriteNumbers.setText(Integer.toString(votePOJO.getResults().getFavorite()));
                            }

                        }

                        @Override
                        public void onFailure(Call<VotePOJO> call, Throwable t) {
                            Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }


    private void gamifikasiAksiRespon(
                                     final int activityCode,
                                     final ForumRecyclerView.ViewHolder viewHolder, int textNumberFavoriteParam,
                                     int textNumberUpvoteParam, int textNumberDownvoteParam
                                     ) {

        //Ganti status Front End response
        switch (activityCode) {
            case 2://upvote
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                viewHolder.tvUpVoteNumbers.setText(Integer.toString(textNumberUpvoteParam));

                break;
            case 3://downvote
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                viewHolder.tvDownVoteNumbers.setText(Integer.toString(textNumberDownvoteParam));

                break;
            case 4://favorite
                viewHolder.buttonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                viewHolder.tvFavoriteNumbers.setText(Integer.toString(textNumberFavoriteParam));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}

