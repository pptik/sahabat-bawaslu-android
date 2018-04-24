package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;

import static id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView.FORUM_ID;

/**
 * Created by Ilham on 15/03/18.
 */

public class ForumRecyclerView extends RecyclerView.Adapter<ForumRecyclerView.ViewHolder> {

    private List<Integer> materialTypeList;
    private List<String> datePostList;
    private List<String> hashtagList;
    private List<String> titleList;
    private List<String> forumIdList;
    private List<Integer> upVoteNumbersList;
    private List<Integer> downVoteNumbersList;
    private List<Integer> commentNumbersList;
    private List<Integer> favoriteNumbersList;
    private Activity activity;

    private String[]  datePost, hashtag, title, forumId;
    private Integer[] favoriteNumbers, upVoteNumbers, downVoteNumbers,
                     commentNumbers;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDatePost, tvTitlePost, tvHashtag,
                tvUpVoteNumbers, tvDownVoteNumbers, tvCommentNumbers,
                tvFavoriteNumbers;
        public View view;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDatePost = (TextView)itemView.findViewById(R.id.date_post);
            tvTitlePost = (TextView)itemView.findViewById(R.id.title_post);
            tvHashtag = (TextView)itemView.findViewById(R.id.hashtag);
            tvUpVoteNumbers = (TextView)itemView.findViewById(R.id.text_numbers_upvote);
            tvDownVoteNumbers = (TextView)itemView.findViewById(R.id.text_numbers_downvote);
            tvCommentNumbers = (TextView)itemView.findViewById(R.id.text_comments);
            tvFavoriteNumbers = (TextView)itemView.findViewById(R.id.text_number_favorite);
        }
    }

    public ForumRecyclerView(List<String> datePostListParam, List<String> forumIdParam,
                             List<String> hashtagListParam, List<String> titleListParam,
                             List<Integer> favoriteNumberListParam, List<Integer> upVoteNumberListParam,
                             List<Integer> downVoteNumberListParam, List<Integer> commentNumberListParam,
                             Activity activity) {
        this.activity = activity;
        this.datePostList = datePostListParam;
        this.hashtagList = hashtagListParam;
        this.titleList = titleListParam;
        this.favoriteNumbersList = favoriteNumberListParam;
        this.upVoteNumbersList = upVoteNumberListParam;
        this.downVoteNumbersList = downVoteNumberListParam;
        this.commentNumbersList= commentNumberListParam;
        this.forumIdList= forumIdParam;

        datePost = new String[datePostList.size()];
        hashtag = new String[hashtagList.size()];
        title = new String[titleList.size()];
        favoriteNumbers = new Integer[favoriteNumbersList.size()];
        upVoteNumbers = new Integer[upVoteNumbersList.size()];
        downVoteNumbers = new Integer[downVoteNumbersList.size()];
        commentNumbers = new Integer[commentNumbersList.size()];
        forumId = new String[forumIdList.size()];

        datePost = datePostList.toArray(datePost);
        hashtag = hashtagList.toArray(hashtag);
        title = titleList.toArray(title);
        favoriteNumbers = favoriteNumbersList.toArray(favoriteNumbers);
        upVoteNumbers= upVoteNumbersList.toArray(upVoteNumbers);
        downVoteNumbers= downVoteNumbersList.toArray(downVoteNumbers);
        commentNumbers= commentNumbersList.toArray(commentNumbers);
        forumId= forumIdList.toArray(forumId);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forum_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvHashtag.setText(hashtag[position]);
        holder.tvTitlePost.setText(title[position]);
        holder.tvDatePost.setText("dibuat "+datePost[position]);

        holder.tvFavoriteNumbers.setText(Integer.toString(favoriteNumbers[position]));
        holder.tvUpVoteNumbers.setText(Integer.toString(upVoteNumbers[position]));
        holder.tvDownVoteNumbers.setText(Integer.toString(downVoteNumbers[position]));
        holder.tvCommentNumbers.setText(Integer.toString(commentNumbers[position])+" Jawaban");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForum = new Intent(v.getContext(), DetailForumActivity.class);
                intentForum.putExtra(FORUM_ID,forumId[position]);
                v.getContext().startActivity(intentForum);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    @Override
    public int getItemCount() {
        return title.length;
    }
}

