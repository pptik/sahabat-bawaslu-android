package id.pptik.ilham.sahabatbawaslu.features.news;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.pptik.ilham.sahabatbawaslu.R;

import java.util.List;

import static android.R.id.list;


public class MaterialsRecyclerView extends RecyclerView.Adapter<MaterialsRecyclerView.ViewHolder> {
    private List<String> usernameList;
    private List<String> datePostList;
    private List<String> titlePostList;
    private List<String> contentPostList;
    private List<String> userPictureProfileList;
    private List<Integer> contentTypeList;
    private String[] username, datePost, titlePost, contentPost, userPictureProfile, contentType;

    /*private String[] username = {"Asep","Jajang"};
    private String[] datePost = {"11 Maret 2018","12 Maret 2018"};
    private String[] titlePost = {"Berita Terbaru Peraturan","Berita Terbaru Peraturan"};
    private String[] contentPost = {"Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. ",
            "Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt. is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. "};*/

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvUsername, tvDatePost, tvTitlePost, tvContentPost;
        public ImageView ivUserpicture, ivThumbnail;
        public View view;


        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView)itemView.findViewById(R.id.username);
            tvDatePost = (TextView)itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView)itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView)itemView.findViewById(R.id.content_post);

            ivUserpicture = (ImageView) itemView.findViewById(R.id.user_picture);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);


        }
    }

    public MaterialsRecyclerView(List<String> usernameListParam, List<String> datePostListParam,
                                 List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                 List<Integer> contentTypeListParam,List<String> titlePostListParam) {

        this.usernameList = usernameListParam;
        this.datePostList = datePostListParam;
        this.contentPostList = contentPostListParam;
        this.userPictureProfileList = userPictureProfileListParam;
        this.contentTypeList = contentTypeListParam;
        this.titlePostList = titlePostListParam;

        username = new String[usernameList.size()];
        datePost = new String[datePostList.size()];
        titlePost = new String[titlePostList.size()];
        contentPost = new String[contentPostList.size()];
        userPictureProfile = new String[userPictureProfileList.size()];
        contentType = new String[contentTypeList.size()];


        username = usernameList.toArray(username);
        datePost = datePostList.toArray(datePost);
        titlePost = titlePostList.toArray(titlePost);
        contentPost = contentPostList.toArray(contentPost);
        userPictureProfile = contentPostList.toArray(userPictureProfile);
        contentType = contentPostList.toArray(contentType);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tvUsername.setText(username[position]);
        holder.tvContentPost.setText(contentPost[position]);
        holder.tvTitlePost.setText(titlePost[position]);
        holder.tvDatePost.setText(datePost[position]);

        //holder.ivUserpicture.setImageDrawable(null);
        //holder.ivThumbnail.setImageDrawable(null);

        /*Glide
                .with(holder.ivUserpicture.getContext())
                .load(userPictureProfile[position])
                .into(holder.ivUserpicture);*/



    }


    @Override
    public int getItemCount() {
        return username.length;
    }
}
