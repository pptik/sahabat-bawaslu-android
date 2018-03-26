package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import static android.R.id.list;
import static android.R.id.redo;


public class MaterialsRecyclerView extends RecyclerView.Adapter<MaterialsRecyclerView.ViewHolder> {
    private List<String> usernameList;
    private List<String> datePostList;
    private List<String> titlePostList;
    private List<String> contentPostList;
    private List<String> userPictureProfileList;
    private List<Integer> textNumberFavoriteList;
    private List<Integer> textNumberDownvoteList;
    private List<Integer> textNumberUpvoteList;
    private List<Integer> textNumberCommentList;
    private List<Integer> newsTypeList;
    //Buat menentukan warna marker
    private List<String> contentTypeList;

    //Buar menentukan label marker card
    private List<String> contentTextList;
    private List<String> activityTextList;

    private List<Boolean> statusUpvoteList;
    private List<Boolean> statusDownvoteList;
    private List<Boolean> statusFavoriteList;

    private String[] username, datePost, titlePost,
            contentPost, userPictureProfile, contentType,
            contentText, activityText;
    private Integer[] textNumberFavorite,
            textNumberDownvote, textNumberUpvote,
            textNumberComment, newsType;
    private Boolean[] statusUpvote, statusDownvote,
            statusFavorite;

    private  Activity activity;
    /*private String[] username = {"Asep","Jajang"};
    private String[] datePost = {"11 Maret 2018","12 Maret 2018"};
    private String[] titlePost = {"Berita Terbaru Peraturan","Berita Terbaru Peraturan"};
    private String[] contentPost = {"Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. ",
            "Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt. is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. "};*/

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvUsername, tvDatePost, tvTitlePost,
                tvContentPost, tvContentCode, tvContentLabel, tvActivityLabel,
                tvNumberFavorite, tvNumberDownvote, tvNumberUpvote,
                tvNumberComment, tvActivityUsername;
        public ImageView ivUserpicture,buttonUpvote,
                buttonDownvote, buttonFavorite, buttonComment,
                infoButton;
        public RelativeLayout relativeLayoutNotNewsContent,relativeLayoutNewsContentNotAdminMedia,
                relativeLayoutNewsContentNotAdminText,relativeLayoutNewsAdmin;
        public View viewLabelColorNews,viewLabelColorMaterial;
        public static Snackbar snackbar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView)itemView.findViewById(R.id.username);
            tvDatePost = (TextView)itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView)itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView)itemView.findViewById(R.id.content_post);
            tvContentCode = (TextView)itemView.findViewById(R.id.post_label);
            tvContentLabel = (TextView)itemView.findViewById(R.id.post_content_desc);
            tvActivityLabel = (TextView)itemView.findViewById(R.id.post_activity_desc);
            tvNumberFavorite = (TextView)itemView.findViewById(R.id.text_number_favorite);
            tvNumberDownvote = (TextView)itemView.findViewById(R.id.text_numbers_downvote);
            tvNumberUpvote = (TextView)itemView.findViewById(R.id.text_numbers_upvote);
            tvNumberComment = (TextView)itemView.findViewById(R.id.text_comments);
            tvActivityUsername = (TextView)itemView.findViewById(R.id.activity_username);

            viewLabelColorNews = (View) itemView.findViewById(R.id.label_color_news);
            viewLabelColorMaterial = (View) itemView.findViewById(R.id.label_color_material);

            ivUserpicture = (ImageView) itemView.findViewById(R.id.user_picture);
            infoButton = (ImageView) itemView.findViewById(R.id.info_button_content_activity);

            buttonUpvote = (ImageView) itemView.findViewById(R.id.button_upvote);
            buttonDownvote = (ImageView) itemView.findViewById(R.id.button_downvote);
            buttonFavorite = (ImageView) itemView.findViewById(R.id.button_favorite);
            buttonComment = (ImageView) itemView.findViewById(R.id.button_comment);

            relativeLayoutNotNewsContent = (RelativeLayout) itemView.findViewById(R.id.relativelayout_not_news_content);
            relativeLayoutNewsAdmin= (RelativeLayout) itemView.findViewById(R.id.relativelayout_news_admin);
            relativeLayoutNewsContentNotAdminText= (RelativeLayout) itemView.findViewById(R.id.relativelayout_news_nonadmin_text);
            relativeLayoutNewsContentNotAdminMedia= (RelativeLayout) itemView.findViewById(R.id.relativelayout_news_nonadmin_media);

        }
    }

    public MaterialsRecyclerView(List<String> usernameListParam, List<String> datePostListParam,
                                 List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                 List<String> contentTypeListParam, List<String> titlePostListParam,
                                 List<String> contentLabelListParam, List<String> activityLabelListParam,
                                 List<Integer> textNumberFavoriteListParam, List<Integer> textNumberUpvoteListParam,
                                 List<Integer> textNumberDownvoteListParam, List<Integer> textNumberCommentListParam,
                                 List<Boolean> statusUpvotesParam, List<Boolean> statusDownvotesParam,
                                 List<Boolean> statusFavoritesParam, Activity activity, List<Integer> newsTypeListParam) {
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
        this.statusUpvoteList =  statusUpvotesParam;
        this.statusDownvoteList =  statusDownvotesParam;
        this.statusFavoriteList=  statusFavoritesParam;
        this.newsTypeList=  newsTypeListParam;

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
        textNumberComment = textNumberDownvoteList.toArray(textNumberComment);
        statusUpvote = statusUpvoteList.toArray(statusUpvote);
        statusDownvote = statusDownvoteList.toArray(statusDownvote);
        statusFavorite = statusFavoriteList.toArray(statusFavorite);
        newsType = newsTypeList.toArray(newsType);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        switch (newsType[position]){
            case 0://berita dari admin
                holder.relativeLayoutNewsAdmin.setVisibility(View.VISIBLE);break;
            case 1://berita dari relawan media
                holder.relativeLayoutNewsContentNotAdminMedia.setVisibility(View.VISIBLE);break;
            case 2://berita dari relawan text
                holder.relativeLayoutNewsContentNotAdminText.setVisibility(View.VISIBLE);break;
            default://bukan berita
                holder.relativeLayoutNotNewsContent.setVisibility(View.VISIBLE);break;
        }
        holder.tvUsername.setText(username[position]);
        holder.tvContentPost.setText(contentPost[position]);
        holder.tvTitlePost.setText(titlePost[position]);
        holder.tvDatePost.setText(datePost[position]);
        holder.tvContentCode.setText(contentType[position]);
        holder.tvContentLabel.setText(contentText[position]);
        holder.tvActivityLabel.setText(activityText[position]);
        holder.tvNumberFavorite.setText(textNumberFavorite[position].toString());
        holder.tvNumberUpvote.setText(textNumberUpvote[position].toString());
        holder.tvNumberDownvote.setText(textNumberDownvote[position].toString());
        holder.tvNumberComment.setText(textNumberComment[position].toString() +" Komentar");
        holder.tvActivityUsername.setText(activityText[position].toString().toLowerCase() +" "+contentText[position].toString().toLowerCase());

        //Menggunakan library Glide untuk menampilkan foto pengguna
        holder.ivUserpicture.setImageDrawable(null);
        Glide.with(holder.ivUserpicture.getContext()).load(userPictureProfile[position]).into(holder.ivUserpicture);

        //Menentukan tanda pada card Dashboard -> 1 Materi 2 Berita
        //holder.viewLabelColor.setBackgroundColor(R.drawable.gradient_color);
        switch (contentType[position]){
            case "1":
                //holder.viewLabelColorMaterial.setVisibility(View.VISIBLE);
                ;break;
            case "2":
                //holder.viewLabelColorNews.setVisibility(View.VISIBLE);
                ;break;
        }

        holder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), activityText[position]+" "+contentText[position], Toast.LENGTH_SHORT).show();
                ViewHolder.snackbar = Snackbar.make(v.getRootView(),activityText[position]+" "+contentText[position],Snackbar.LENGTH_SHORT);
                ViewHolder.snackbar.show();
                }
        });

        if (statusUpvote[position]){
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
        }else{
            holder.buttonUpvote.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }

        if (statusDownvote[position]){
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
        }else{
            holder.buttonDownvote.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }

        if (statusFavorite[position]){
            holder.buttonFavorite.setImageResource(R.drawable.ic_favorite_black_18dp);
        }else{
            holder.buttonFavorite.setImageResource(R.drawable.ic_favorite_border_black_18dp);
        }
        /*switch (statusUpvote[position]){
            case true:
                //holder.viewLabelColorMaterial.setVisibility(View.VISIBLE);
                ;break;
            case "2":
                //holder.viewLabelColorNews.setVisibility(View.VISIBLE);
                ;break;
        }*/
        if(contentType[position]=="2"){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SuplemenMaterialDetailActivity.class);
                    v.getContext().startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return username.length;
    }
}
