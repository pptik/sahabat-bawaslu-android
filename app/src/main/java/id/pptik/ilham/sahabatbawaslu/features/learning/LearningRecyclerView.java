package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;

/**
 * Created by Ilham on 15/03/18.
 */

public class LearningRecyclerView extends RecyclerView.Adapter<LearningRecyclerView.ViewHolder> {

    private List<Integer> materialTypeList;
    private List<String> datePostList;
    private List<String> descList;
    private List<String> titleList;
    private List<Integer> upVoteNumbersList;
    private List<Integer> downVoteNumbersList;
    private List<Integer> commentNumbersList;
    private List<Integer> favoriteNumbersList;

    private String[]  datePost, desc, title;
    private Integer[] favoriteNumbers, upVoteNumbers, downVoteNumbers,
                     commentNumbers,materialType;

   private  Activity activity;

    /*private String[] author = {"Tono","Budi","Jaka"};
    private String[] datePost = {"11 Maret 2018","12 Maret 2018","12 Maret 2018"};
    private String[] desc = {"Materi ini A","Materi ini B","Materi ini C"};
    private String[] title = {"Materi 1","Materi 2","Materi 3"};*/


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvMaterialType, tvDatePost, tvTitlePost, tvContentPost,
                tvUpVoteNumbers, tvDownVoteNumbers, tvCommentNumbers,
                tvFavoriteNumbers;
        public View view,separator;
        public ImageView buttonComment;
        public LinearLayout secondaryContent,secondaryContentSuplemen;


        public ViewHolder(View itemView) {
            super(itemView);
            tvMaterialType = (TextView)itemView.findViewById(R.id.material_type);
            tvDatePost = (TextView)itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView)itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView)itemView.findViewById(R.id.content_post);
            tvUpVoteNumbers = (TextView)itemView.findViewById(R.id.text_numbers_upvote);
            tvDownVoteNumbers = (TextView)itemView.findViewById(R.id.text_numbers_downvote);
            tvCommentNumbers = (TextView)itemView.findViewById(R.id.text_comments);
            tvFavoriteNumbers = (TextView)itemView.findViewById(R.id.text_number_favorite);
            separator = (View)itemView.findViewById(R.id.separator);
            secondaryContent = (LinearLayout)itemView.findViewById(R.id.secondary_content);
            secondaryContentSuplemen = (LinearLayout)itemView.findViewById(R.id.secondary_content_suplemen);
            //buttonComment = (ImageView) itemView.findViewById(R.id.button_comment);
        }
    }

    public LearningRecyclerView(List<Integer> materialTypeListParam, List<String> datePostListParam,
                                List<String> descriptionListParam, List<String> titleListParam,
                                List<Integer> favoriteNumberListParam, List<Integer> upVoteNumberListParam,
                                List<Integer> downVoteNumberListParam, List<Integer> commentNumberListParam,
                                Activity activity) {
        this.activity = activity;
        this.materialTypeList = materialTypeListParam;
        this.datePostList = datePostListParam;
        this.descList = descriptionListParam;
        this.titleList = titleListParam;
        this.favoriteNumbersList = favoriteNumberListParam;
        this.upVoteNumbersList = upVoteNumberListParam;
        this.downVoteNumbersList = downVoteNumberListParam;
        this.commentNumbersList= commentNumberListParam;

        materialType = new Integer[materialTypeList.size()];
        datePost = new String[datePostList.size()];
        desc = new String[descList.size()];
        title = new String[titleList.size()];
        favoriteNumbers = new Integer[favoriteNumbersList.size()];
        upVoteNumbers = new Integer[upVoteNumbersList.size()];
        downVoteNumbers = new Integer[downVoteNumbersList.size()];
        commentNumbers = new Integer[commentNumbersList.size()];

        materialType = materialTypeList.toArray(materialType);
        datePost = datePostList.toArray(datePost);
        desc = descList.toArray(desc);
        title = titleList.toArray(title);
        favoriteNumbers = favoriteNumbersList.toArray(favoriteNumbers);
        upVoteNumbers= upVoteNumbersList.toArray(upVoteNumbers);
        downVoteNumbers= downVoteNumbersList.toArray(downVoteNumbers);
        commentNumbers= commentNumbersList.toArray(commentNumbers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_learning_content, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (materialType[position]){
            case 0:
                holder.tvMaterialType.setText("suplemen");
                holder.tvCommentNumbers.setVisibility(View.INVISIBLE);
                holder.secondaryContent.setVisibility(View.INVISIBLE);
                holder.secondaryContentSuplemen.setVisibility(View.VISIBLE);
                //holder.buttonComment.setVisibility(View.INVISIBLE);
                ;break;
            case 1:
                holder.tvMaterialType.setText("kasus");
                ;break;
            case 2:
                holder.tvMaterialType.setText("video");
                ;break;
        }

        holder.tvContentPost.setText(desc[position]);
        holder.tvTitlePost.setText(title[position]);
        holder.tvDatePost.setText("dibuat "+datePost[position]);

        holder.tvFavoriteNumbers.setText(Integer.toString(favoriteNumbers[position]));
        holder.tvUpVoteNumbers.setText(Integer.toString(upVoteNumbers[position]));
        holder.tvDownVoteNumbers.setText(Integer.toString(downVoteNumbers[position]));
        holder.tvCommentNumbers.setText(Integer.toString(commentNumbers[position])+" Komentar");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SuplemenMaterialDetailActivity.class);
                v.getContext().startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


    }


    @Override
    public int getItemCount() {
        return title.length;
    }
}

