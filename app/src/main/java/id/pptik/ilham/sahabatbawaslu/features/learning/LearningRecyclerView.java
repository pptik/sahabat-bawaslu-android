package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.DetailNewsNotAdminTextActivity;

import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.CONTENT_ID;
import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.MATERIAL_ID;
import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.TITLE;

/**
 * Created by Ilham on 15/03/18.
 */

public class LearningRecyclerView extends RecyclerView.Adapter<LearningRecyclerView.ViewHolder> {

    public static List<Integer> materialTypeList = new ArrayList<Integer>();
    public static List<String> datePostList = new ArrayList<String>();
    public static List<String> materialIdList = new ArrayList<String>();
    public static List<String> descList = new ArrayList<String>();
    public static List<String> titleList = new ArrayList<String>();
    public static List<Integer> upVoteNumbersList = new ArrayList<Integer>();
    public static List<Integer> downVoteNumbersList = new ArrayList<Integer>();
    public static List<Integer> commentNumbersList = new ArrayList<Integer>();
    public static List<Integer> favoriteNumbersList = new ArrayList<Integer>();

    private String[] datePost, desc, title, materialId;
    private Integer[] favoriteNumbers, upVoteNumbers, downVoteNumbers,
            commentNumbers, materialType;

    private Activity activity;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMaterialType, tvDatePost, tvTitlePost, tvContentPost,
                tvUpVoteNumbers, tvDownVoteNumbers, tvCommentNumbers,
                tvFavoriteNumbers;
        public View view, separator;
        public ImageView buttonComment;
        public LinearLayout secondaryContent, secondaryContentSuplemen;


        public ViewHolder(View itemView) {
            super(itemView);
            tvMaterialType = (TextView) itemView.findViewById(R.id.material_type);
            tvDatePost = (TextView) itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView) itemView.findViewById(R.id.title_post);
            tvContentPost = (TextView) itemView.findViewById(R.id.content_post);
            tvUpVoteNumbers = (TextView) itemView.findViewById(R.id.text_numbers_upvote);
            tvDownVoteNumbers = (TextView) itemView.findViewById(R.id.text_numbers_downvote);
            tvCommentNumbers = (TextView) itemView.findViewById(R.id.text_comments);
            tvFavoriteNumbers = (TextView) itemView.findViewById(R.id.text_number_favorite);
            separator = (View) itemView.findViewById(R.id.separator);
            secondaryContent = (LinearLayout) itemView.findViewById(R.id.secondary_content);
            secondaryContentSuplemen = (LinearLayout) itemView.findViewById(R.id.secondary_content_suplemen);
            //buttonComment = (ImageView) itemView.findViewById(R.id.button_comment);
        }
    }

    public LearningRecyclerView(List<Integer> materialTypeListParam, List<String> datePostListParam,
                                List<String> descriptionListParam, List<String> titleListParam,
                                List<Integer> favoriteNumberListParam, List<Integer> upVoteNumberListParam,
                                List<Integer> downVoteNumberListParam, List<Integer> commentNumberListParam,
                                List<String> materialIdListParam, Activity activity) {
        this.activity = activity;
        this.materialTypeList = materialTypeListParam;
        this.datePostList = datePostListParam;
        this.descList = descriptionListParam;
        this.titleList = titleListParam;
        this.favoriteNumbersList = favoriteNumberListParam;
        this.upVoteNumbersList = upVoteNumberListParam;
        this.downVoteNumbersList = downVoteNumberListParam;
        this.commentNumbersList = commentNumberListParam;
        this.materialIdList = materialIdListParam;

        materialType = new Integer[materialTypeList.size()];
        datePost = new String[datePostList.size()];
        desc = new String[descList.size()];
        title = new String[titleList.size()];
        materialId = new String[materialIdList.size()];
        favoriteNumbers = new Integer[favoriteNumbersList.size()];
        upVoteNumbers = new Integer[upVoteNumbersList.size()];
        downVoteNumbers = new Integer[downVoteNumbersList.size()];
        commentNumbers = new Integer[commentNumbersList.size()];

        materialType = materialTypeList.toArray(materialType);
        datePost = datePostList.toArray(datePost);
        desc = descList.toArray(desc);
        title = titleList.toArray(title);
        materialId = materialIdList.toArray(materialId);
        favoriteNumbers = favoriteNumbersList.toArray(favoriteNumbers);
        upVoteNumbers = upVoteNumbersList.toArray(upVoteNumbers);
        downVoteNumbers = downVoteNumbersList.toArray(downVoteNumbers);
        commentNumbers = commentNumbersList.toArray(commentNumbers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_learning_content, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        switch (materialType[position]) {
            case 0:
                holder.tvMaterialType.setText("video");

                holder.secondaryContent.setVisibility(View.INVISIBLE);
                holder.secondaryContentSuplemen.setVisibility(View.VISIBLE);
                //holder.buttonComment.setVisibility(View.INVISIBLE);
                ;
                break;
            case 1:
                holder.tvMaterialType.setText("suplemen");
                holder.tvCommentNumbers.setVisibility(View.INVISIBLE);
                ;
                break;
            case 2:
                holder.tvMaterialType.setText("kasus");
                ;
                break;
        }

        holder.tvContentPost.setText(desc[position]);
        holder.tvTitlePost.setText(title[position]);
        holder.tvDatePost.setText("dibuat " + datePost[position]);

        holder.tvFavoriteNumbers.setText(Integer.toString(favoriteNumbers[position]));
        holder.tvUpVoteNumbers.setText(Integer.toString(upVoteNumbers[position]));
        holder.tvDownVoteNumbers.setText(Integer.toString(downVoteNumbers[position]));
        holder.tvCommentNumbers.setText(Integer.toString(commentNumbers[position]) + " Komentar");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(v.getContext(), DetailNewsNotAdminTextActivity.class);
                Bundle bundle = new Bundle();
                //intent.putExtra(CONTENT_ID,contentId[position]);
                bundle.putString(CONTENT_ID,contentId[position]);
                bundle.putString(TITLE,titlePost[position]);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);*/
                switch (materialType[position]) {
                    case 0://video
                        /*Intent intent = new Intent(v.getContext(), DetailNewsNotAdminTextActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CONTENT_ID,contentId[position]);
                        bundle.putString(TITLE,titlePost[position]);
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);*/

                        Intent intentVideo = new Intent(activity , VideoMaterialDetailRevisedActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(MATERIAL_ID,materialId[position]);
                        bundle.putString(TITLE,title[position]);
                        intentVideo.putExtras(bundle);

                        activity.startActivity(intentVideo);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 1://suplemen
                        Intent intentSuplemen = new Intent(activity , SuplemenMaterialDetailActivity.class);
                        intentSuplemen.putExtra(MATERIAL_ID,materialId[position]);
                        activity.startActivity(intentSuplemen);
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 2://kasus

                        break;
                }
                /*Intent intent = new Intent(v.getContext(), MaterialAdapter.class);
                intent.putExtra("materialId",materialId[position]);
                v.getContext().startActivity(intent);

                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/

            }
        });


    }


    @Override
    public int getItemCount() {
        return title.length;
    }


}

