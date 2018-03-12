package id.pptik.ilham.sahabatbawaslu.features.news;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import id.pptik.ilham.sahabatbawaslu.R;

import java.util.List;

import static android.R.id.list;


public class MaterialsRecyclerView extends RecyclerView.Adapter<MaterialsRecyclerView.ViewHolder> {
    /*private List<String> dataJudulMateri;
    private List<Integer> dataSubMateri;
    private List<String> dataCoverMateri;

    public String[] username, datePost, titlePost, contentPost;
    public Integer[] userPicture, ivThumbnail;
    */

    private String[] username = {"Asep","Jajang"};
    private String[] datePost = {"11 Maret 2018","12 Maret 2018"};
    private String[] titlePost = {"Intro","Prelimi"};
    private String[] contentPost = {"Lorem Ipsum","Lorem Ipsum"};

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

            view = (View) itemView.findViewById(R.id.separatorLine);
        }
    }

    //public MaterialsRecyclerView(List<String> dataBerupaJudul, List<String> dataBerupaCover, List<Integer> dataBerupaSubMateri) {
    public MaterialsRecyclerView() {
        /*this.dataJudulMateri = dataBerupaJudul;
        this.dataCoverMateri = dataBerupaCover;
        this.dataSubMateri = dataBerupaSubMateri;

        judul =  new String[dataJudulMateri.size()];
        cover =  new String[dataCoverMateri.size()];
        subMateri =  new Integer[dataSubMateri.size()];

        judul = dataBerupaJudul.toArray(judul);
        cover = dataBerupaCover.toArray(cover);
        subMateri = dataBerupaSubMateri.toArray(subMateri);*/
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

        //holder.imageView.setImageDrawable(null);

        /*Glide
                .with(holder.imageView.getContext())
                .load(cover[position])
                .crossFade()
                .placeholder(R.drawable.loading)
                .into(holder.imageView);

        if(judul.length == (position+1)){
            holder.view.setVisibility(View.GONE);
        }*/

    }


    @Override
    public int getItemCount() {
        return username.length;
    }
}
