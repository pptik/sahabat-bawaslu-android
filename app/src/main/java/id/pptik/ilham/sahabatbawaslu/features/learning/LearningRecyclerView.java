package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import id.pptik.ilham.sahabatbawaslu.R;

/**
 * Created by Ilham on 15/03/18.
 */

public class LearningRecyclerView extends RecyclerView.Adapter<LearningRecyclerView.ViewHolder> {
    /*private List<String> dataJudulMateri;
    private List<Integer> dataSubMateri;
    private List<String> dataCoverMateri;

    public String[] username, datePost, titlePost, contentPost;
    public Integer[] userPicture, ivThumbnail;
    */

    private String[] username = {"Tono","Budi"};
    private String[] datePost = {"11 Maret 2018","12 Maret 2018"};
    private String[] titlePost = {"Berita Terbaru Peraturan","Berita Terbaru Peraturan"};
    private String[] contentPost = {"Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. ",
            "Lorem Ipsum dolor sit amet, consetrur adispicing elit, sed do eiusmod tempor incididunt. is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. "};

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

    //public MaterialsRecyclerView(List<String> dataBerupaJudul, List<String> dataBerupaCover, List<Integer> dataBerupaSubMateri) {
    public LearningRecyclerView() {
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

