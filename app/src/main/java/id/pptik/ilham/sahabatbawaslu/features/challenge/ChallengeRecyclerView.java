package id.pptik.ilham.sahabatbawaslu.features.challenge;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;

/**
 * Created by github/fiyyanputra on 8/13/2018.
 */

public class ChallengeRecyclerView extends RecyclerView.Adapter<ChallengeRecyclerView.ViewHolder>{

    private final List<String> contentsList;
    private final List<Integer> poinsList;
    private final List<String> expireList;
    private final List<String> idList;
    private final Activity activity;

    String[] contentChallenge, expireChallenge, idsChallenge;
    Integer[] poinChallenge;

    public ChallengeRecyclerView(List<String> idsParam,
                                 List<String> contentsParam, List<Integer> poinsParam,
                                 List<String> expiresParam, Activity activityParam){

        this.activity = activityParam;
        this.idList = idsParam;
        this.contentsList = contentsParam;
        this.poinsList = poinsParam;
        this.expireList = expiresParam;

        contentChallenge = new String[contentsList.size()];
        expireChallenge = new String[expiresParam.size()];
        poinChallenge = new Integer[poinsList.size()];
        idsChallenge = new String[idList.size()];

        contentChallenge = contentsList.toArray(contentChallenge);
        expireChallenge = expireList.toArray(expireChallenge);
        poinChallenge = poinsList.toArray(poinChallenge);
        idsChallenge = idList.toArray(idsChallenge);
    }

    @Override
    public int getItemCount() {
        return contentsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent, tvPoin, tvExpire;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.challenge_content);
            tvPoin = (TextView) itemView.findViewById(R.id.poin);
            tvExpire = (TextView) itemView.findViewById(R.id.expire);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_challenge_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvContent.setText(contentChallenge[position]);
        holder.tvPoin.setText(poinChallenge[position]+" poin");
        holder.tvExpire.setText(expireChallenge[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnswerChallengeActivity.class);
                intent.putExtra("CHALLENGE_ID",idsChallenge[position]);
                v.getContext().startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}


