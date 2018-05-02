package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.forum.DetailForumActivity;
import id.pptik.ilham.sahabatbawaslu.features.learning.MaterialAdapter;
import id.pptik.ilham.sahabatbawaslu.features.learning.SuplemenMaterialDetailActivity;
import id.pptik.ilham.sahabatbawaslu.features.learning.VideoMaterialDetailActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialDetailPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.SignUpPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.content;
import static android.R.id.list;
import static android.R.id.redo;


public class MaterialsRecyclerView extends RecyclerView.Adapter<MaterialsRecyclerView.ViewHolder> {
    public static List<String> usernameList= new ArrayList<String>();
    public static List<String> datePostList= new ArrayList<String>();
    public static List<String> titlePostList= new ArrayList<String>();
    public static List<String> contentPostList= new ArrayList<String>();
    public static List<String> userPictureProfileList= new ArrayList<String>();
    public static List<String> newsMediaList= new ArrayList<String>();
    public static List<Integer> textNumberFavoriteList= new ArrayList<Integer>();
    public static List<Integer> textNumberDownvoteList= new ArrayList<Integer>();
    public static List<Integer> textNumberUpvoteList= new ArrayList<Integer>();
    public static List<Integer> textNumberCommentList= new ArrayList<Integer>();
    public static List<Integer> newsTypeList= new ArrayList<Integer>();
    public static List<Integer> activityCodeList= new ArrayList<Integer>();
    //Buat menentukan warna marker
    public static List<String> contentIdList= new ArrayList<String>();
    public static List<String> contentTypeList= new ArrayList<String>();

    //Buar menentukan label marker card
    public static List<String> contentTextList= new ArrayList<String>();
    public static List<String> activityTextList= new ArrayList<String>();


    public static List<Boolean> statusUpvoteList= new ArrayList<Boolean>();
    public static List<Boolean> statusDownvoteList= new ArrayList<Boolean>();
    public static List<Boolean> statusFavoriteList= new ArrayList<Boolean>();

    private String[] username, datePost, titlePost,
            contentPost, userPictureProfile, contentType,
            contentText, activityText, newsMedia,
            contentId;

    private Integer[] textNumberFavorite,
            textNumberDownvote, textNumberUpvote,
            textNumberComment, newsType, activityCode;
    private Boolean[] statusUpvote, statusDownvote,
            statusFavorite;

    //private String favoriteNumberAfterRequest, upvoteNumberAfterRequest, downvoteNumberAfterRequest;

    private Activity activity;
    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;

    public static final String CONTENT_ID = "CONTENT_ID";
    public static final String TITLE = "TITLE";
    public static final String MATERIAL_ID = "MATERIAL_ID";
    public static final String FORUM_ID = "FORUM_ID";


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvDatePost, tvTitlePost,
                tvContentPost, tvContentCode, tvContentLabel, tvActivityLabel,
                tvNumberFavorite, tvNumberDownvote, tvNumberUpvote,
                tvNumberComment, tvActivityUsername, tvTitlePostNewsAdmin,
                tvContentPostNewsAdmin, tvTitlePostNewsNonAdminText, tvContentPostNewsNonAdminText,
                tvContentPostNewsNonAdminMedia;

        public ImageView ivUserpicture, buttonUpvote,
                buttonDownvote, buttonFavorite, buttonComment,
                infoButton, newsMedia, ivIconNumbersUpvote, ivIconNumbersDownvote;
        public RelativeLayout relativeLayoutNotNewsContent, relativeLayoutNewsContentNotAdminMedia,
                relativeLayoutNewsContentNotAdminText, relativeLayoutNewsAdmin;
        public View viewLabelColorNews, viewLabelColorMaterial;
        public static Snackbar snackbar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.username);
            tvDatePost = itemView.findViewById(R.id.date_post);
            tvTitlePost = itemView.findViewById(R.id.title_post);
            tvContentPost = itemView.findViewById(R.id.content_post);
            tvContentCode = itemView.findViewById(R.id.post_label);
            tvContentLabel = itemView.findViewById(R.id.post_content_desc);
            tvActivityLabel = itemView.findViewById(R.id.post_activity_desc);
            tvNumberFavorite = itemView.findViewById(R.id.text_number_favorite);
            tvNumberDownvote = itemView.findViewById(R.id.text_numbers_downvote);
            tvNumberUpvote = itemView.findViewById(R.id.text_numbers_upvote);
            tvNumberComment = itemView.findViewById(R.id.text_comments);
            tvActivityUsername = itemView.findViewById(R.id.activity_username);
            tvTitlePostNewsAdmin = itemView.findViewById(R.id.title_post_news_admin);
            tvContentPostNewsAdmin = itemView.findViewById(R.id.content_post_news_admin);
            tvTitlePostNewsNonAdminText = itemView.findViewById(R.id.title_post_news_nonadmin_text);
            tvContentPostNewsNonAdminText = itemView.findViewById(R.id.content_post_nonadmin_text);
            newsMedia = itemView.findViewById(R.id.media_post_news_nonadmin_media);
            tvContentPostNewsNonAdminMedia = itemView.findViewById(R.id.caption_post_news_nonadmin_media);

            viewLabelColorNews = itemView.findViewById(R.id.label_color_news);
            viewLabelColorMaterial = itemView.findViewById(R.id.label_color_material);

            ivUserpicture = itemView.findViewById(R.id.user_picture);
            infoButton = itemView.findViewById(R.id.info_button_content_activity);
            ivIconNumbersUpvote = itemView.findViewById(R.id.icon_numbers_upvote);
            ivIconNumbersDownvote = itemView.findViewById(R.id.icon_numbers_downvote);

            buttonUpvote = itemView.findViewById(R.id.button_upvote);
            buttonDownvote = itemView.findViewById(R.id.button_downvote);
            buttonFavorite = itemView.findViewById(R.id.button_favorite);
            buttonComment = itemView.findViewById(R.id.button_comment);

            relativeLayoutNotNewsContent = itemView.findViewById(R.id.relativelayout_not_news_content);
            relativeLayoutNewsAdmin = itemView.findViewById(R.id.relativelayout_news_admin);
            relativeLayoutNewsContentNotAdminText = itemView.findViewById(R.id.relativelayout_news_nonadmin_text);
            relativeLayoutNewsContentNotAdminMedia = itemView.findViewById(R.id.relativelayout_news_nonadmin_media);

        }
    }

    public  void updateData(List<String> usernameListParam, List<String> datePostListParam,
                           List<String> contentPostListParam, List<String> userPictureProfileListParam,
                           List<String> contentTypeListParam, List<String> titlePostListParam,
                           List<String> contentLabelListParam, List<String> activityLabelListParam,
                           List<Integer> textNumberFavoriteListParam, List<Integer> textNumberUpvoteListParam,
                           List<Integer> textNumberDownvoteListParam, List<Integer> textNumberCommentListParam,
                           List<Boolean> statusUpvotesParam, List<Boolean> statusDownvotesParam,
                           List<Boolean> statusFavoritesParam, Activity activity, List<Integer> newsTypeListParam,
                           List<String> newsMediaListParam, List<String> contentIdListParam,
                           List<Integer> activityCodeListParam){
        usernameList = usernameListParam;
        datePostList = datePostListParam;
        contentPostList = contentPostListParam;
        userPictureProfileList = userPictureProfileListParam;
        contentTypeList = contentTypeListParam;
        titlePostList = titlePostListParam;
        contentTextList = contentLabelListParam;
        activityTextList = activityLabelListParam;
        textNumberDownvoteList = textNumberDownvoteListParam;
        textNumberUpvoteList = textNumberUpvoteListParam;
        textNumberFavoriteList = textNumberFavoriteListParam;
        textNumberCommentList = textNumberCommentListParam;
        statusUpvoteList = statusUpvotesParam;
        statusDownvoteList = statusDownvotesParam;
        statusFavoriteList = statusFavoritesParam;
        newsTypeList = newsTypeListParam;
        newsMediaList = newsMediaListParam;
        contentIdList = contentIdListParam;
        activityCodeList = activityCodeListParam;
        notifyDataSetChanged();

    }

    public MaterialsRecyclerView(List<String> usernameListParam, List<String> datePostListParam,
                                 List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                 List<String> contentTypeListParam, List<String> titlePostListParam,
                                 List<String> contentLabelListParam, List<String> activityLabelListParam,
                                 List<Integer> textNumberFavoriteListParam, List<Integer> textNumberUpvoteListParam,
                                 List<Integer> textNumberDownvoteListParam, List<Integer> textNumberCommentListParam,
                                 List<Boolean> statusUpvotesParam, List<Boolean> statusDownvotesParam,
                                 List<Boolean> statusFavoritesParam, Activity activity, List<Integer> newsTypeListParam,
                                 List<String> newsMediaListParam, List<String> contentIdListParam,
                                 List<Integer> activityCodeListParam) {

        this.activity = activity;
        usernameList = usernameListParam;
        datePostList = datePostListParam;
        contentPostList = contentPostListParam;
        userPictureProfileList = userPictureProfileListParam;
        contentTypeList = contentTypeListParam;
        titlePostList = titlePostListParam;
        contentTextList = contentLabelListParam;
        activityTextList = activityLabelListParam;
        textNumberDownvoteList = textNumberDownvoteListParam;
        textNumberUpvoteList = textNumberUpvoteListParam;
        textNumberFavoriteList = textNumberFavoriteListParam;
        textNumberCommentList = textNumberCommentListParam;
        statusUpvoteList = statusUpvotesParam;
        statusDownvoteList = statusDownvotesParam;
        statusFavoriteList = statusFavoritesParam;
        newsTypeList = newsTypeListParam;
        newsMediaList = newsMediaListParam;
        contentIdList = contentIdListParam;
        activityCodeList = activityCodeListParam;

        username = new String[usernameList.size()];
        datePost = new String[datePostList.size()];
        titlePost = new String[titlePostList.size()];
        contentPost = new String[contentPostList.size()];
        userPictureProfile = new String[userPictureProfileList.size()];
        contentType = new String[contentTypeList.size()];
        contentText = new String[contentTextList.size()];
        activityText = new String[activityTextList.size()];
        textNumberFavorite = new Integer[textNumberFavoriteList.size()];
        textNumberUpvote = new Integer[textNumberUpvoteList.size()];
        textNumberDownvote = new Integer[textNumberDownvoteList.size()];
        textNumberComment = new Integer[textNumberCommentList.size()];
        statusUpvote = new Boolean[statusUpvoteList.size()];
        statusDownvote = new Boolean[statusDownvoteList.size()];
        statusFavorite = new Boolean[statusFavoriteList.size()];
        newsType = new Integer[newsTypeList.size()];
        newsMedia = new String[newsMediaList.size()];
        contentId = new String[contentIdList.size()];
        activityCode = new Integer[activityCodeList.size()];

        username = usernameList.toArray(username);
        datePost = datePostList.toArray(datePost);
        titlePost = titlePostList.toArray(titlePost);
        contentPost = contentPostList.toArray(contentPost);
        userPictureProfile = userPictureProfileList.toArray(userPictureProfile);
        contentType = contentTypeList.toArray(contentType);
        contentText = contentTextList.toArray(contentText);
        activityText = activityTextList.toArray(activityText);
        textNumberFavorite = textNumberFavoriteList.toArray(textNumberFavorite);
        textNumberUpvote = textNumberUpvoteList.toArray(textNumberUpvote);
        textNumberDownvote = textNumberDownvoteList.toArray(textNumberDownvote);
        textNumberComment = textNumberCommentList.toArray(textNumberComment);
        statusUpvote = statusUpvoteList.toArray(statusUpvote);
        statusDownvote = statusDownvoteList.toArray(statusDownvote);
        statusFavorite = statusFavoriteList.toArray(statusFavorite);
        newsType = newsTypeList.toArray(newsType);
        newsMedia = newsMediaList.toArray(newsMedia);
        contentId = contentIdList.toArray(contentId);
        activityCode = activityCodeList.toArray(activityCode);

        //notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("POS","POSISI: "+position);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        int test = newsType[position];

        //Khusus berita
        switch (test) {
            case 0://konten admin
                if (contentType[position].equals("1")) {//Materi
                    holder.relativeLayoutNotNewsContent.setVisibility(View.VISIBLE);
                    holder.tvContentPost.setText(contentPost[position]);
                    holder.tvTitlePost.setText(titlePost[position]);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materialAdapter(contentId[position], access_token, activity);
                        }
                    });
                    holder.tvNumberComment.setVisibility(View.GONE);
                } else if (contentType[position].equals("2")) {//Berita
                    holder.relativeLayoutNewsAdmin.setVisibility(View.VISIBLE);
                    holder.tvTitlePostNewsAdmin.setText(titlePost[position]);
                    holder.tvContentPostNewsAdmin.setText(contentPost[position]);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(), DetailNewsAdminActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(CONTENT_ID,contentId[position]);
                            bundle.putString(TITLE,titlePost[position]);
                            intent.putExtras(bundle);
                            v.getContext().startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    });
                    holder.tvNumberUpvote.setVisibility(View.GONE);
                    holder.tvNumberDownvote.setVisibility(View.GONE);
                    holder.buttonUpvote.setVisibility(View.GONE);
                    holder.buttonDownvote.setVisibility(View.GONE);
                    holder.ivIconNumbersUpvote.setVisibility(View.GONE);
                    holder.ivIconNumbersDownvote.setVisibility(View.GONE);
                }else if (contentType[position].equals("3")) {//Forum
                    holder.relativeLayoutNewsAdmin.setVisibility(View.VISIBLE);
                    holder.tvTitlePostNewsAdmin.setText(titlePost[position]);
                    holder.tvContentPostNewsAdmin.setText(contentPost[position]);
                    holder.tvContentPostNewsAdmin.setVisibility(View.GONE);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentForum = new Intent(v.getContext(), DetailForumActivity.class);
                            intentForum.putExtra(FORUM_ID,contentId[position]);
                            v.getContext().startActivity(intentForum);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    });
                }
                break;
            case 1://berita dari relawan media
                holder.relativeLayoutNewsContentNotAdminMedia.setVisibility(View.VISIBLE);
                //holder.newsMedia.setText(titlePost[position]);
                Glide.with(holder.newsMedia.getContext()).load(newsMedia[position]).into(holder.newsMedia);
                holder.tvContentPostNewsNonAdminMedia.setText(contentPost[position]);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Intent intent = new Intent(v.getContext(), SuplemenMaterialDetailActivity.class);
                        v.getContext().startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                        //Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.tvNumberUpvote.setVisibility(View.GONE);
                holder.tvNumberDownvote.setVisibility(View.GONE);
                holder.buttonUpvote.setVisibility(View.GONE);
                holder.buttonDownvote.setVisibility(View.GONE);
                holder.ivIconNumbersUpvote.setVisibility(View.GONE);
                holder.ivIconNumbersDownvote.setVisibility(View.GONE);
                break;
            case 2://berita dari relawan text
                holder.relativeLayoutNewsContentNotAdminText.setVisibility(View.VISIBLE);
                holder.tvTitlePostNewsNonAdminText.setText(titlePost[position]);
                holder.tvContentPostNewsNonAdminText.setText("\"" + contentPost[position] + "\"");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DetailNewsNotAdminTextActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CONTENT_ID,contentId[position]);
                        bundle.putString(TITLE,titlePost[position]);
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
                holder.tvNumberUpvote.setVisibility(View.GONE);
                holder.tvNumberDownvote.setVisibility(View.GONE);
                holder.buttonUpvote.setVisibility(View.GONE);
                holder.buttonDownvote.setVisibility(View.GONE);
                holder.ivIconNumbersUpvote.setVisibility(View.GONE);
                holder.ivIconNumbersDownvote.setVisibility(View.GONE);
                break;
        }

        //Identitas pengguna dan resume kegiatan
        holder.tvUsername.setText(username[position]);
        holder.tvDatePost.setText(datePost[position]);
        holder.tvContentCode.setText(contentType[position]);
        holder.tvContentLabel.setText(contentText[position]);
        holder.tvActivityLabel.setText(activityText[position]);
        //holder.tvActivityUsername.setText("Dummy");
        holder.tvActivityUsername.setText(activityText[position].toString().toLowerCase() + " " + contentText[position].toString().toLowerCase());
        holder.ivUserpicture.setImageDrawable(null);
        Glide.with(holder.ivUserpicture.getContext()).load(userPictureProfile[position]).into(holder.ivUserpicture);

        //Gamifikasi hasil respon

        holder.tvNumberFavorite.setText(textNumberFavorite[position].toString());
        holder.tvNumberUpvote.setText(textNumberUpvote[position].toString());
        holder.tvNumberDownvote.setText(textNumberDownvote[position].toString());

        if(newsType[position] == 0 && contentType[position] == "3"){//Forum
            holder.tvNumberComment.setText(textNumberComment[position].toString() + " Jawaban");
        }else {
            holder.tvNumberComment.setText(textNumberComment[position].toString() + " Komentar");
        }

        //Gamifikasi respon
        if (statusUpvote[position]) {
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
            holder.buttonUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(contentId[position], 2,
                            Integer.parseInt(contentType[position]), titlePost[position],
                            access_token, holder, textNumberFavorite[position],
                            textNumberUpvote[position], textNumberDownvote[position],
                            position);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 2,
                            Integer.parseInt(contentType[position]), titlePost[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            Log.d("UP",Integer.toString(votePOJO.getResults().getFavorite()));
                            holder.tvNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
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
        } else {

            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            holder.buttonUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(contentId[position], 2,
                            Integer.parseInt(contentType[position]), titlePost[position],
                            access_token, holder, textNumberFavorite[position],
                            textNumberUpvote[position], textNumberDownvote[position],
                            position);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 2,
                            Integer.parseInt(contentType[position]), titlePost[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            Log.d("UP",Integer.toString(votePOJO.getResults().getUpvote()));
                            holder.tvNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            holder.tvNumberDownvote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
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
        }

        if (statusDownvote[position]) {
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
            holder.buttonDownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(contentId[position], 3,
                            Integer.parseInt(contentType[position]), titlePost[position],
                            access_token, holder, textNumberFavorite[position],
                            textNumberUpvote[position], textNumberDownvote[position],
                            position);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 3,
                            Integer.parseInt(contentType[position]), titlePost[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            Log.d("DOWN",Integer.toString(votePOJO.getResults().getDownvote()));
                            holder.tvNumberUpvote.setText(Integer.toString(votePOJO.getResults().getUpvote()));
                            holder.tvNumberDownvote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
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
        } else {
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            holder.buttonDownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(contentId[position], 3,
                            Integer.parseInt(contentType[position]), titlePost[position],
                            access_token, holder, textNumberFavorite[position],
                            textNumberUpvote[position], textNumberDownvote[position],
                            position);

                    restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                    final Call<VotePOJO> voteAction = restServiceInterface.voteAction(contentId[position], 3,
                            Integer.parseInt(contentType[position]), titlePost[position], access_token);
                    voteAction.enqueue(new Callback<VotePOJO>() {
                        @Override
                        public void onResponse(Call<VotePOJO> call, Response<VotePOJO> response) {
                            VotePOJO votePOJO = response.body();
                            Toast.makeText(activity, votePOJO.getRm(), Toast.LENGTH_SHORT).show();
                            Log.d("DOWN",Integer.toString(votePOJO.getResults().getFavorite()));
                            holder.tvNumberDownvote.setText(Integer.toString(votePOJO.getResults().getDownvote()));
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

    public void gamifikasiAksiRespon(String contentID,
                                     final int activityCode, int contentCode,
                                     String title, String accessToken,
                                     final ViewHolder viewHolder, int textNumberFavoriteParam,
                                     int textNumberUpvoteParam, int textNumberDownvoteParam,
                                     final int position) {

        //Ganti status Front End response
        switch (activityCode) {
            case 2://upvote
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                viewHolder.tvNumberUpvote.setText(Integer.toString(textNumberUpvoteParam));

                break;
            case 3://downvote
                viewHolder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                viewHolder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                viewHolder.tvNumberDownvote.setText(Integer.toString(textNumberDownvoteParam));

                break;
            case 4://favorite
                viewHolder.buttonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
                viewHolder.tvNumberFavorite.setText(Integer.toString(textNumberFavoriteParam));
                break;
        }

    }

    private void materialAdapter(final String content_id, final String access_token, final Activity activity){
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        retrofit2.Call<MaterialDetailPOJO> materialDetail = restServiceInterface.materialDetail(content_id,access_token);
        materialDetail.enqueue(new Callback<MaterialDetailPOJO>() {
            @Override
            public void onResponse(retrofit2.Call<MaterialDetailPOJO> call, Response<MaterialDetailPOJO> response) {
                MaterialDetailPOJO materialDetailPOJO = response.body();
                Log.d("XYZ","tipe materi: "+materialDetailPOJO.getResults().getType());
                switch (materialDetailPOJO.getResults().getType()){
                    case 0://video
                        Intent intentVideo = new Intent(activity , VideoMaterialDetailActivity.class);
                        intentVideo.putExtra(MATERIAL_ID,content_id);
                        activity.startActivity(intentVideo);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 1://suplemen
                        Intent intentSuplemen = new Intent(activity , SuplemenMaterialDetailActivity.class);
                        intentSuplemen.putExtra(MATERIAL_ID,content_id);
                        activity.startActivity(intentSuplemen);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 2://kasus
                        break;
                }
            }

            @Override
            public void onFailure(retrofit2.Call<MaterialDetailPOJO> call, Throwable t) {
                Log.e("ERROR",t.getLocalizedMessage());
            }
        });
    }


    @Override
    public int getItemCount() {
        return usernameList.size();
    }


}
