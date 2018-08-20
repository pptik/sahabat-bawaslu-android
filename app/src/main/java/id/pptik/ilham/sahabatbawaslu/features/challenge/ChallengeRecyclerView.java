package id.pptik.ilham.sahabatbawaslu.features.challenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;

import id.pptik.ilham.sahabatbawaslu.R;

/**
 * Created by github/fiyyanputra on 8/13/2018.
 */

public class ChallengeRecyclerView extends RecyclerView.Adapter<ChallengeRecyclerView.ViewHolder>{

    private final List<String> contentsList;
    private final List<String> titlesList;
    private final List<Integer> poinsList;
    private final List<String> expireList;
    private final List<String> idList;
    private final Activity activity;

    String[] contentChallenge, expireChallenge, idsChallenge, titleChallenge;
    Integer[] poinChallenge;

    private SimpleDateFormat format =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    public ChallengeRecyclerView(List<String> titlesParam, List<String> idsParam,
                                 List<String> contentsParam, List<Integer> poinsParam,
                                 List<String> expiresParam, Activity activityParam){

        this.activity = activityParam;
        this.idList = idsParam;
        this.titlesList = titlesParam;
        this.contentsList = contentsParam;
        this.poinsList = poinsParam;
        this.expireList = expiresParam;

        contentChallenge = new String[contentsList.size()];
        expireChallenge = new String[expiresParam.size()];
        poinChallenge = new Integer[poinsList.size()];
        idsChallenge = new String[idList.size()];
        titleChallenge = new String[titlesList.size()];

        contentChallenge = contentsList.toArray(contentChallenge);
        expireChallenge = expireList.toArray(expireChallenge);
        poinChallenge = poinsList.toArray(poinChallenge);
        idsChallenge = idList.toArray(idsChallenge);
        titleChallenge = titlesList.toArray(titleChallenge);
    }

    @Override
    public int getItemCount() {
        return contentsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public HtmlTextView tvContent;
        public TextView tvTitle, tvPoin, tvExpire;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.challenge_title);
            tvContent = (HtmlTextView) itemView.findViewById(R.id.challenge_content);
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
        holder.tvTitle.setText(titleChallenge[position]);
        holder.tvContent.setHtml(contentChallenge[position], new HtmlHttpImageGetter(holder.tvContent));
        holder.tvPoin.setText(poinChallenge[position]+" poin");
        try {

            format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
            Date date = format.parse(expireChallenge[position]);
            holder.tvExpire.setText(dateFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage(R.string.ensure_challenge_label)
                        .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, AnswerChallengeActivity.class);
                                intent.putExtra("CHALLENGE_ID",idsChallenge[position]);
                                context.startActivity(intent);
                                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        })
                        .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });
    }
}


