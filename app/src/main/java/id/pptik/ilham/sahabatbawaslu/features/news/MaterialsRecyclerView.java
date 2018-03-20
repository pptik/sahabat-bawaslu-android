package id.pptik.ilham.sahabatbawaslu.features.news;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.pptik.ilham.sahabatbawaslu.R;

import java.util.List;

import static android.R.id.list;
import static android.R.id.redo;


public class MaterialsRecyclerView extends RecyclerView.Adapter<MaterialsRecyclerView.ViewHolder> {
    private List<Integer> materialTypeList;
    private List<String> datePostList;
    private List<String> titlePostList;
    private List<String> contentPostList;
    private List<String> userPictureProfileList;
    private List<Integer> textNumberFavoriteList;
    private List<Integer> textNumberDownvoteList;
    private List<Integer> textNumberUpvoteList;
    private List<Integer> textNumberCommentList;
    //Buat menentukan warna marker
    private List<String> contentTypeList;

    //Buar menentukan label marker card
    private List<String> contentTextList;
    private List<String> activityTextList;

    private String[] datePost, titlePost,
            contentPost, userPictureProfile, contentType,
            contentText, activityText;
    private Integer[] textNumberFavorite,
            textNumberDownvote, textNumberUpvote,
            textNumberComment, materialType;



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
        public ImageView ivUserpicture;
        public View viewLabelColorNews,viewLabelColorMaterial;
        public ImageView infoButton;
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
            /*infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Infonya", Toast.LENGTH_SHORT).show();
                }
            });*/

        }
    }

    public MaterialsRecyclerView(List<Integer> materialTypeListParam, List<String> datePostListParam,
                                 List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                 List<String> contentTypeListParam,List<String> titlePostListParam,
                                 List<String> contentLabelListParam,List<String> activityLabelListParam,
                                 List<Integer> textNumberFavoriteListParam,List<Integer> textNumberUpvoteListParam,
                                 List<Integer> textNumberDownvoteListParam,List<Integer> textNumberCommentListParam) {

        this.materialTypeList = materialTypeListParam;
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

        materialType = new Integer[materialTypeList.size()];
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

        materialType = materialTypeList.toArray(materialType);
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
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.tvUsername.setText(materialType[position]);
        holder.tvContentPost.setText(contentPost[position]);
        holder.tvTitlePost.setText(titlePost[position]);
        holder.tvDatePost.setText(datePost[position]);
        holder.tvContentCode.setText(contentType[position]);
        holder.tvContentLabel.setText(contentText[position]);
        holder.tvActivityLabel.setText(activityText[position]);
        holder.tvActivityUsername.setText(activityText[position].toLowerCase()+" "+contentText[position].toLowerCase());
        holder.tvNumberFavorite.setText(textNumberFavorite[position].toString());
        holder.tvNumberUpvote.setText(textNumberUpvote[position].toString());
        holder.tvNumberDownvote.setText(textNumberDownvote[position].toString());
        holder.tvNumberComment.setText(textNumberComment[position].toString() +" Komentar");

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

    }


    @Override
    public int getItemCount() {
        return materialType.length;
    }
}
