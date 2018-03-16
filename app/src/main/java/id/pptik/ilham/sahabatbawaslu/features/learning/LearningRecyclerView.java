package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;

/**
 * Created by Ilham on 15/03/18.
 */

public class LearningRecyclerView extends RecyclerView.Adapter<LearningRecyclerView.ViewHolder> {

    private List<String> authorList;
    private List<String> datePostList;
    private List<String> descList;
    private List<String> titleList;
    private String[] author, datePost, desc, title;

    /*private String[] author = {"Tono","Budi","Jaka"};
    private String[] datePost = {"11 Maret 2018","12 Maret 2018","12 Maret 2018"};
    private String[] desc = {"Materi ini A","Materi ini B","Materi ini C"};
    private String[] title = {"Materi 1","Materi 2","Materi 3"};*/


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvAuthor, tvDatePost, tvTitlePost, tvContentPost;
        public View view;


        public ViewHolder(View itemView) {
            super(itemView);
            tvAuthor = (TextView)itemView.findViewById(R.id.material_author);
            tvDatePost = (TextView)itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView)itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView)itemView.findViewById(R.id.content_post);


        }
    }

    public LearningRecyclerView(List<String> authorListParam, List<String> datePostListParam, List<String> descriptionListParam, List<String> titleListParam) {

        this.authorList = authorListParam;
        this.datePostList = datePostListParam;
        this.descList = descriptionListParam;
        this.titleList = titleListParam;

        author = new String[authorList.size()];
        datePost = new String[datePostList.size()];
        desc = new String[descList.size()];
        title = new String[titleList.size()];

        author = authorList.toArray(author);
        datePost = datePostList.toArray(datePost);
        desc = descList.toArray(desc);
        title = titleList.toArray(desc);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_learning_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tvAuthor.setText(author[position]);
        holder.tvContentPost.setText(desc[position]);
        holder.tvTitlePost.setText(title[position]);
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
        return title.length;
    }
}

