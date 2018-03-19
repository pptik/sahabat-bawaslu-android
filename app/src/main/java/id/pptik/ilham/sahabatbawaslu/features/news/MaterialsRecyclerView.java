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
import static android.R.id.redo;


public class MaterialsRecyclerView extends RecyclerView.Adapter<MaterialsRecyclerView.ViewHolder> {
    private List<String> usernameList;
    private List<String> datePostList;
    private List<String> titlePostList;
    private List<String> contentPostList;
    private List<String> userPictureProfileList;
    //Buat menentukan warna marker
    private List<String> contentTypeList;

    //Buar menentukan label marker card
    private List<String> contentTextList;
    private List<String> activityTextList;

    private String[] username, datePost, titlePost,
            contentPost, userPictureProfile, contentType,
            contentText, activityText;


    /*private String[] username = {"Asep","Jajang"};
    private String[] datePost = {"11 Maret 2018","12 Maret 2018"};
    private String[] titlePost = {"Berita Terbaru Peraturan","Berita Terbaru Peraturan"};
    private String[] contentPost = {"Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. ",
            "Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt. is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. "};*/

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvUsername, tvDatePost, tvTitlePost,
                tvContentPost, tvContentCode, tvContentLabel, tvActivityLabel;
        public ImageView ivUserpicture;
        public View viewLabelColorNews,viewLabelColorMaterial;


        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView)itemView.findViewById(R.id.username);
            tvDatePost = (TextView)itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView)itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView)itemView.findViewById(R.id.content_post);
            tvContentCode = (TextView)itemView.findViewById(R.id.post_label);
            tvContentLabel = (TextView)itemView.findViewById(R.id.post_content_desc);
            tvActivityLabel = (TextView)itemView.findViewById(R.id.post_activity_desc);

            viewLabelColorNews = (View) itemView.findViewById(R.id.label_color_news);
            viewLabelColorMaterial = (View) itemView.findViewById(R.id.label_color_material);

            ivUserpicture = (ImageView) itemView.findViewById(R.id.user_picture);
            //ivThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);


        }
    }

    public MaterialsRecyclerView(List<String> usernameListParam, List<String> datePostListParam,
                                 List<String> contentPostListParam, List<String> userPictureProfileListParam,
                                 List<String> contentTypeListParam,List<String> titlePostListParam,
                                 List<String> contentLabelListParam,List<String> activityLabelListParam) {

        this.usernameList = usernameListParam;
        this.datePostList = datePostListParam;
        this.contentPostList = contentPostListParam;
        this.userPictureProfileList = userPictureProfileListParam;
        this.contentTypeList = contentTypeListParam;
        this.titlePostList = titlePostListParam;
        this.contentTextList = contentLabelListParam;
        this.activityTextList = activityLabelListParam;

        username = new String[usernameList.size()];
        datePost = new String[datePostList.size()];
        titlePost = new String[titlePostList.size()];
        contentPost = new String[contentPostList.size()];
        userPictureProfile = new String[userPictureProfileList.size()];
        contentType = new String[contentTypeList.size()];
        contentText = new String[contentTextList.size()];
        activityText = new String[activityTextList.size()];


        username = usernameList.toArray(username);
        datePost = datePostList.toArray(datePost);
        titlePost = titlePostList.toArray(titlePost);
        contentPost = contentPostList.toArray(contentPost);
        userPictureProfile = userPictureProfileList.toArray(userPictureProfile);
        contentType = contentTypeList.toArray(contentType);
        contentText = contentTextList.toArray(contentText);
        activityText = activityTextList.toArray(activityText);
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
        holder.tvContentCode.setText(contentType[position]);
        holder.tvContentLabel.setText(contentText[position]);
        holder.tvActivityLabel.setText(activityText[position]);

        //Menggunakan library Glide untuk menampilkan foto pengguna
        holder.ivUserpicture.setImageDrawable(null);
        Glide.with(holder.ivUserpicture.getContext()).load(userPictureProfile[position]).into(holder.ivUserpicture);

        //Menentukan tanda pada card Dashboard -> 1 Materi 2 Berita
        //holder.viewLabelColor.setBackgroundColor(R.drawable.gradient_color);
        switch (contentType[position]){
            case "1":
                holder.viewLabelColorMaterial.setVisibility(View.VISIBLE);
                ;break;
            case "2":
                holder.viewLabelColorNews.setVisibility(View.VISIBLE);
                ;break;
        }



    }


    @Override
    public int getItemCount() {
        return username.length;
    }
}
