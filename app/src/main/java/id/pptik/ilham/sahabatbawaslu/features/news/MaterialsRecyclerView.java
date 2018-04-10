package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.media.Image;
import android.net.Uri;
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
import id.pptik.ilham.sahabatbawaslu.features.learning.SuplemenMaterialDetailActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.SignUpPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.VotePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static android.R.id.content;
import static android.R.id.list;
import static android.R.id.redo;


public class MaterialsRecyclerView extends RecyclerView.Adapter<MaterialsRecyclerView.ViewHolder> {
    private List<String> usernameList;
    private List<String> datePostList;
    private List<String> titlePostList;
    private List<String> contentPostList;
    private List<String> userPictureProfileList;
    private List<String> newsMediaList;
    private List<Integer> textNumberFavoriteList;
    private List<Integer> textNumberDownvoteList;
    private List<Integer> textNumberUpvoteList;
    private List<Integer> textNumberCommentList;
    private List<Integer> newsTypeList;
    private List<Integer> activityCodeList;
    //Buat menentukan warna marker
    private List<String> contentIdList;
    private List<String> contentTypeList;

    //Buar menentukan label marker card
    private List<String> contentTextList;
    private List<String> activityTextList;


    private List<Boolean> statusUpvoteList;
    private List<Boolean> statusDownvoteList;
    private List<Boolean> statusFavoriteList;

    private String[] username, datePost, titlePost,
            contentPost, userPictureProfile, contentType,
            contentText, activityText, newsMedia,
            contentId;

    private Integer[] textNumberFavorite,
            textNumberDownvote, textNumberUpvote,
            textNumberComment, newsType, activityCode;
    private Boolean[] statusUpvote, statusDownvote,
            statusFavorite;



    private Activity activity;
    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;

    public static final String CONTENT_ID = "";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvDatePost, tvTitlePost,
                tvContentPost, tvContentCode, tvContentLabel, tvActivityLabel,
                tvNumberFavorite, tvNumberDownvote, tvNumberUpvote,
                tvNumberComment, tvActivityUsername, tvTitlePostNewsAdmin,
                tvContentPostNewsAdmin, tvTitlePostNewsNonAdminText, tvContentPostNewsNonAdminText,
                tvContentPostNewsNonAdminMedia;

        public ImageView ivUserpicture, buttonUpvote,
                buttonDownvote, buttonFavorite, buttonComment,
                infoButton, newsMedia;
        public RelativeLayout relativeLayoutNotNewsContent, relativeLayoutNewsContentNotAdminMedia,
                relativeLayoutNewsContentNotAdminText, relativeLayoutNewsAdmin;
        public View viewLabelColorNews, viewLabelColorMaterial;
        public static Snackbar snackbar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.username);
            tvDatePost = (TextView) itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView) itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView) itemView.findViewById(R.id.content_post);
            tvContentCode = (TextView) itemView.findViewById(R.id.post_label);
            tvContentLabel = (TextView) itemView.findViewById(R.id.post_content_desc);
            tvActivityLabel = (TextView) itemView.findViewById(R.id.post_activity_desc);
            tvNumberFavorite = (TextView) itemView.findViewById(R.id.text_number_favorite);
            tvNumberDownvote = (TextView) itemView.findViewById(R.id.text_numbers_downvote);
            tvNumberUpvote = (TextView) itemView.findViewById(R.id.text_numbers_upvote);
            tvNumberComment = (TextView) itemView.findViewById(R.id.text_comments);
            tvActivityUsername = (TextView) itemView.findViewById(R.id.activity_username);
            tvTitlePostNewsAdmin = (TextView) itemView.findViewById(R.id.title_post_news_admin);
            tvContentPostNewsAdmin = (TextView) itemView.findViewById(R.id.content_post_news_admin);
            tvTitlePostNewsNonAdminText = (TextView) itemView.findViewById(R.id.title_post_news_nonadmin_text);
            tvContentPostNewsNonAdminText = (TextView) itemView.findViewById(R.id.content_post_nonadmin_text);
            newsMedia = (ImageView) itemView.findViewById(R.id.media_post_news_nonadmin_media);
            tvContentPostNewsNonAdminMedia = (TextView) itemView.findViewById(R.id.caption_post_news_nonadmin_media);

            viewLabelColorNews = (View) itemView.findViewById(R.id.label_color_news);
            viewLabelColorMaterial = (View) itemView.findViewById(R.id.label_color_material);

            ivUserpicture = (ImageView) itemView.findViewById(R.id.user_picture);
            infoButton = (ImageView) itemView.findViewById(R.id.info_button_content_activity);

            buttonUpvote = (ImageView) itemView.findViewById(R.id.button_upvote);
            buttonDownvote = (ImageView) itemView.findViewById(R.id.button_downvote);
            buttonFavorite = (ImageView) itemView.findViewById(R.id.button_favorite);
            buttonComment = (ImageView) itemView.findViewById(R.id.button_comment);

            relativeLayoutNotNewsContent = (RelativeLayout) itemView.findViewById(R.id.relativelayout_not_news_content);
            relativeLayoutNewsAdmin = (RelativeLayout) itemView.findViewById(R.id.relativelayout_news_admin);
            relativeLayoutNewsContentNotAdminText = (RelativeLayout) itemView.findViewById(R.id.relativelayout_news_nonadmin_text);
            relativeLayoutNewsContentNotAdminMedia = (RelativeLayout) itemView.findViewById(R.id.relativelayout_news_nonadmin_media);

        }
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
        this.usernameList = usernameListParam;
        this.datePostList = datePostListParam;
        this.contentPostList = contentPostListParam;
        this.userPictureProfileList = userPictureProfileListParam;
        this.contentTypeList = contentTypeListParam;
        this.titlePostList = titlePostListParam;
        this.contentTextList = contentLabelListParam;
        this.activityTextList = activityLabelListParam;
        this.textNumberDownvoteList = textNumberDownvoteListParam;
        this.textNumberUpvoteList = textNumberUpvoteListParam;
        this.textNumberFavoriteList = textNumberFavoriteListParam;
        this.textNumberCommentList = textNumberCommentListParam;
        this.statusUpvoteList = statusUpvotesParam;
        this.statusDownvoteList = statusDownvotesParam;
        this.statusFavoriteList = statusFavoritesParam;
        this.newsTypeList = newsTypeListParam;
        this.newsMediaList = newsMediaListParam;
        this.newsMediaList = newsMediaListParam;
        this.contentIdList = contentIdListParam;
        this.activityCodeList = activityCodeListParam;

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
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        switch (newsType[position]) {
            case 0://berita dari admin atau bukan berita
                if (contentType[position] == "1") {//Materi
                    holder.relativeLayoutNotNewsContent.setVisibility(View.VISIBLE);
                    holder.tvContentPost.setText(contentPost[position]);
                    holder.tvTitlePost.setText(titlePost[position]);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(activity, "bukan 0,1,2", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (contentType[position] == "2") {//Berita
                    holder.relativeLayoutNewsAdmin.setVisibility(View.VISIBLE);
                    holder.tvTitlePostNewsAdmin.setText(titlePost[position]);
                    holder.tvContentPostNewsAdmin.setText(contentPost[position]);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Intent intent = new Intent(v.getContext(), DetailNewsNotAdminTextActivity.class);
                            v.getContext().startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                            //Toast.makeText(activity, "0", Toast.LENGTH_SHORT).show();
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
                break;
            case 2://berita dari relawan text
                holder.relativeLayoutNewsContentNotAdminText.setVisibility(View.VISIBLE);
                holder.tvTitlePostNewsNonAdminText.setText(titlePost[position]);
                holder.tvContentPostNewsNonAdminText.setText("\"" + contentPost[position] + "\"");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DetailNewsNotAdminTextActivity.class);
                        intent.putExtra(CONTENT_ID,contentId[position]);
                        v.getContext().startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default://bukan berita
                holder.relativeLayoutNotNewsContent.setVisibility(View.VISIBLE);
                holder.tvContentPost.setText(contentPost[position]);
                holder.tvTitlePost.setText(titlePost[position]);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(activity, "bukan 0,1,2", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        //Identitas pengguna dan resume kegiatan
        holder.tvUsername.setText(username[position]);
        holder.tvDatePost.setText(datePost[position]);
        holder.tvContentCode.setText(contentType[position]);
        holder.tvContentLabel.setText(contentText[position]);
        holder.tvActivityLabel.setText(activityText[position]);
        holder.tvActivityUsername.setText(activityText[position].toString().toLowerCase() + " " + contentText[position].toString().toLowerCase());
        holder.ivUserpicture.setImageDrawable(null);
        Glide.with(holder.ivUserpicture.getContext()).load(userPictureProfile[position]).into(holder.ivUserpicture);

        //Gamifikasi hasil respon
        holder.tvNumberFavorite.setText(textNumberFavorite[position].toString());
        holder.tvNumberUpvote.setText(textNumberUpvote[position].toString());
        holder.tvNumberDownvote.setText(textNumberDownvote[position].toString());
        holder.tvNumberComment.setText(textNumberComment[position].toString() + " Komentar");


        //Gamifikasi respon
        if (statusUpvote[position]) {
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);

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
                }
            });
        }

        if (statusDownvote[position]) {
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);

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
                }
            });
        }

        if (statusFavorite[position]) {
            holder.buttonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
            holder.buttonUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gamifikasiAksiRespon(contentId[position], 4,
                            Integer.parseInt(contentType[position]), titlePost[position],
                            access_token, holder, textNumberFavorite[position],
                            textNumberUpvote[position], textNumberDownvote[position],
                            position);
                }
            });
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
    }


    @Override
    public int getItemCount() {
        return username.length;
    }
}
