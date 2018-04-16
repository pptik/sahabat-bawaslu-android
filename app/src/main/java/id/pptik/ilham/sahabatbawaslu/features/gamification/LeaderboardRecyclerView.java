package id.pptik.ilham.sahabatbawaslu.features.gamification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;


public class LeaderboardRecyclerView extends RecyclerView.Adapter<LeaderboardRecyclerView.ViewHolder> {
    private List<String> usernameList;
    private List<String> thumbnailList;
    private List<Integer> poinList;

    private String[] username, thumbnail;

    private Integer[] poin;

    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvPoin;

        public ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.text_view_username);
            tvPoin = (TextView) itemView.findViewById(R.id.text_view_poin);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.image_view_thubmnail);
        }
    }

    public LeaderboardRecyclerView(List<String> usernameListParam, List<String> thumbnailListParam,
                                   List<Integer> poinListParam) {

        this.usernameList = usernameListParam;
        this.thumbnailList = thumbnailListParam;
        this.poinList = poinListParam;

        username = new String[usernameList.size()];
        thumbnail = new String[thumbnailList.size()];
        poin = new Integer[poinList.size()];

        username = usernameList.toArray(username);
        thumbnail = thumbnailList.toArray(thumbnail);
        poin = poinList.toArray(poin);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_leaderboard, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        //Identitas pengguna dan resume kegiatan
        holder.tvUsername.setText(username[position]);
        holder.tvPoin.setText(Integer.toString(poin[position]));
        holder.ivThumbnail.setImageDrawable(null);
        Glide.with(holder.ivThumbnail.getContext()).load(thumbnail[position]).into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return username.length;
    }
}
