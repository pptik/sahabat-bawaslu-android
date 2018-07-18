package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.forum.ForumRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsNotAdminTextActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.CONTENT_ID;
import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.MATERIAL_ID;
import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.TITLE;

/**
 * Created by Ilham on 15/03/18.
 */

public class LearningRecyclerView extends RecyclerView.Adapter<LearningRecyclerView.ViewHolder> {

    public static List<Integer> materialTypeList = new ArrayList<Integer>();
    public static List<String> datePostList = new ArrayList<String>();
    public static List<String> materialIdList = new ArrayList<String>();
    public static List<String> descList = new ArrayList<String>();
    public static List<String> titleList = new ArrayList<String>();
    public static List<Integer> upVoteNumbersList = new ArrayList<Integer>();
    public static List<Integer> downVoteNumbersList = new ArrayList<Integer>();
    public static List<Integer> commentNumbersList = new ArrayList<Integer>();
    public static List<Integer> favoriteNumbersList = new ArrayList<Integer>();
    public static List<Boolean> statusUpvoteList= new ArrayList<Boolean>();
    public static List<Boolean> statusDownvoteList= new ArrayList<Boolean>();
    public static List<Boolean> statusFavoriteList= new ArrayList<Boolean>();
    private RestServiceInterface restServiceInterface;
    private String[] datePost, desc, title, materialId;
    private Integer[] favoriteNumbers, upVoteNumbers, downVoteNumbers,
            commentNumbers, materialType;
    private Boolean[] statusUpvote, statusDownvote,
            statusFavorite;

    private Activity activity;
    private SharedPreferences sharedPreferences;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMaterialType, tvDatePost, tvTitlePost, tvContentPost,
                tvUpVoteNumbers, tvDownVoteNumbers, tvCommentNumbers,
                tvFavoriteNumbers;
        public View view, separator;
        public ImageView buttonComment,buttonUpvote,buttonDownvote, buttonFavorite;
        public LinearLayout secondaryContent, secondaryContentSuplemen;


        public ViewHolder(View itemView) {
            super(itemView);
            tvMaterialType = (TextView) itemView.findViewById(R.id.material_type);
            tvDatePost = (TextView) itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView) itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView) itemView.findViewById(R.id.content_post);
            tvUpVoteNumbers = (TextView) itemView.findViewById(R.id.text_numbers_upvote);
            tvDownVoteNumbers = (TextView) itemView.findViewById(R.id.text_numbers_downvote);
            tvCommentNumbers = (TextView) itemView.findViewById(R.id.text_comments);
            tvFavoriteNumbers = (TextView) itemView.findViewById(R.id.text_number_favorite);
            separator = (View) itemView.findViewById(R.id.separator);
            secondaryContent = (LinearLayout) itemView.findViewById(R.id.secondary_content);
            secondaryContentSuplemen = (LinearLayout) itemView.findViewById(R.id.secondary_content_suplemen);
            //buttonComment = (ImageView) itemView.findViewById(R.id.button_comment);
            buttonFavorite = (ImageView) itemView.findViewById(R.id.button_favorite);
            buttonUpvote = (ImageView) itemView.findViewById(R.id.button_upvote);
            buttonDownvote = (ImageView) itemView.findViewById(R.id.button_downvote);
        }
    }

    public LearningRecyclerView(List<Integer> materialTypeListParam, List<String> datePostListParam,
                                List<String> descriptionListParam, List<String> titleListParam,
                                List<Integer> favoriteNumberListParam, List<Integer> upVoteNumberListParam,
                                List<Integer> downVoteNumberListParam, List<Integer> commentNumberListParam,
                                List<String> materialIdListParam, Activity activity, List<Boolean> downVotedListParam,
                                List<Boolean> upVotedListParam,List<Boolean> favoritedListParam) {
        this.activity = activity;
        this.materialTypeList = materialTypeListParam;
        this.datePostList = datePostListParam;
        this.descList = descriptionListParam;
        this.titleList = titleListParam;
        this.favoriteNumbersList = favoriteNumberListParam;
        this.upVoteNumbersList = upVoteNumberListParam;
        this.downVoteNumbersList = downVoteNumberListParam;
        this.commentNumbersList = commentNumberListParam;
        this.materialIdList = materialIdListParam;
        this.statusUpvoteList = upVotedListParam;
        this.statusDownvoteList = downVotedListParam;
        this.statusFavoriteList = favoritedListParam;

        materialType = new Integer[materialTypeList.size()];
        datePost = new String[datePostList.size()];
        desc = new String[descList.size()];
        title = new String[titleList.size()];
        materialId = new String[materialIdList.size()];
        favoriteNumbers = new Integer[favoriteNumbersList.size()];
        upVoteNumbers = new Integer[upVoteNumbersList.size()];
        downVoteNumbers = new Integer[downVoteNumbersList.size()];
        commentNumbers = new Integer[commentNumbersList.size()];
        statusUpvote = new Boolean[statusUpvoteList.size()];
        statusDownvote = new Boolean[statusDownvoteList.size()];
        statusFavorite = new Boolean[statusFavoriteList.size()];

        materialType = materialTypeList.toArray(materialType);
        datePost = datePostList.toArray(datePost);
        desc = descList.toArray(desc);
        title = titleList.toArray(title);
        materialId = materialIdList.toArray(materialId);
        favoriteNumbers = favoriteNumbersList.toArray(favoriteNumbers);
        upVoteNumbers = upVoteNumbersList.toArray(upVoteNumbers);
        downVoteNumbers = downVoteNumbersList.toArray(downVoteNumbers);
        commentNumbers = commentNumbersList.toArray(commentNumbers);
        statusUpvote = statusUpvoteList.toArray(statusUpvote);
        statusDownvote = statusDownvoteList.toArray(statusDownvote);
        statusFavorite = statusFavoriteList.toArray(statusFavorite);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_learning_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String access_token = sharedPreferences.getString("accessToken", "abcde");
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);

        switch (materialType[position]) {
            case 0:
                holder.tvMaterialType.setText("video");

                holder.secondaryContent.setVisibility(View.INVISIBLE);
                holder.secondaryContentSuplemen.setVisibility(View.VISIBLE);
                //holder.buttonComment.setVisibility(View.INVISIBLE);
                ;
                break;
            case 1:
                holder.tvMaterialType.setText("suplemen");
                holder.tvCommentNumbers.setVisibility(View.INVISIBLE);
                ;
                break;
            case 2:
                holder.tvMaterialType.setText("kasus");
                ;
                break;
        }

        holder.tvContentPost.setText(desc[position]);
        holder.tvTitlePost.setText(title[position]);
        holder.tvDatePost.setText("dibuat " + datePost[position]);

        holder.tvFavoriteNumbers.setText(Integer.toString(favoriteNumbers[position]));
        holder.tvUpVoteNumbers.setText(Integer.toString(upVoteNumbers[position]));
        holder.tvDownVoteNumbers.setText(Integer.toString(downVoteNumbers[position]));
        holder.tvCommentNumbers.setText(Integer.toString(commentNumbers[position]) + " Komentar");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(v.getContext(), DetailNewsNotAdminTextActivity.class);
                Bundle bundle = new Bundle();
                //intent.putExtra(CONTENT_ID,contentId[position]);
                bundle.putString(CONTENT_ID,contentId[position]);
                bundle.putString(TITLE,titlePost[position]);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);*/
                switch (materialType[position]) {
                    case 0://video
                        Intent intentVideo = new Intent(activity , VideoMaterialDetailRevisedActivity.class);
                        Bundle bundleVideo = new Bundle();
                        bundleVideo.putString(MATERIAL_ID,materialId[position]);
                        bundleVideo.putString(TITLE,title[position]);
                        intentVideo.putExtras(bundleVideo);

                        activity.startActivity(intentVideo);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 1://suplemen

                        Intent intentSuplemen = new Intent(activity , SuplemenMaterialDetailActivity.class);
                        Bundle bundleSuplemen = new Bundle();
                        bundleSuplemen.putString(MATERIAL_ID,materialId[position]);
                        bundleSuplemen.putString(TITLE,title[position]);
                        intentSuplemen.putExtras(bundleSuplemen);

                        activity.startActivity(intentSuplemen);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 2://kasus
                        Intent intentKasus = new Intent(activity , CaseMaterialDetailActivity.class);
                        Bundle bundleKasus = new Bundle();
                        bundleKasus.putString(MATERIAL_ID,materialId[position]);
                        bundleKasus.putString(TITLE,title[position]);
                        intentKasus.putExtras(bundleKasus);

                        activity.startActivity(intentKasus);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                /*Intent intent = new Intent(v.getContext(), MaterialAdapter.class);
                intent.putExtra("materialId",materialId[position]);
                v.getContext().startActivity(intent);

                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/

            }
        });

        if (statusUpvote[position]){
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
            holder.buttonUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(2, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);
                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(materialId[position], 2,
                            3, title[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            if(votePOJO.getSuccess()) {
                                Log.d("UP", Integer.toString(votePOJO.getResults().getUpvote()));
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
        }else{
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            holder.buttonUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(2, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);
                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(materialId[position], 2,
                            1, title[position], access_token);
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
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(materialId[position], 3,
                            1, title[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            if(votePOJO.getSuccess()) {
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
        }else{
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            holder.buttonDownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(3, holder,favoriteNumbers[position],upVoteNumbers[position],downVoteNumbers[position]);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(materialId[position], 3,
                            1, title[position], access_token);
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
                    Log.d("LOVE ContentId",materialId[position]);
                    Log.d("LOVE Title",title[position]);
                    /*final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 3,
                            3, title[position], access_token);*/
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(materialId[position], 4,
                            1, title[position], access_token);
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
            final LearningRecyclerView.ViewHolder viewHolder, int textNumberFavoriteParam,
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

