package id.pptik.ilham.sahabatbawaslu.features.profile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;


public class UserLogRecyclerView extends RecyclerView.Adapter<UserLogRecyclerView.ViewHolder> {

    public static List<String> userActivitiesList= new ArrayList<String>();
    public static List<String> dateuseractivitiesList= new ArrayList<String>();
    public static List<Integer> contentCodeList= new ArrayList<Integer>();
    public int[] titleIcon = {R.drawable.ic_news,R.drawable.ic_book_open,R.drawable.ic_news,R.drawable.ic_forum_white_24dp,R.drawable.ic_forum_white_24dp,R.drawable.ic_forum_white_24dp};
    private String[] activitiesLog, dateActivitiesLog;
    private Integer[] contentCodeLog;
    private Activity activity;
    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewActivity,textViewActivityTime;
        public ImageView IVImageIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewActivity=itemView.findViewById(R.id.text_view_activity);
            textViewActivityTime=itemView.findViewById(R.id.text_view_activity_time);
            IVImageIcon=itemView.findViewById(R.id.imageIcon);

        }
    }

    public UserLogRecyclerView(List<String> useractivitieslogParam, List<String> dateuseractivitieslogParam,List<Integer> contentcodeparam,Activity activity) {
        this.activity = activity;
        userActivitiesList = useractivitieslogParam;
        dateuseractivitiesList = dateuseractivitieslogParam;
        contentCodeList=contentcodeparam;


        activitiesLog = new String[userActivitiesList.size()];
        dateActivitiesLog = new String[dateuseractivitiesList.size()];
        contentCodeLog=new Integer[contentCodeList.size()];


        activitiesLog = userActivitiesList.toArray(activitiesLog);
        dateActivitiesLog = dateuseractivitiesList.toArray(dateActivitiesLog);
        contentCodeLog=contentCodeList.toArray(contentCodeLog);
        Log.d("disini",activitiesLog.toString());
        //notifyDataSetChanged();

    }

    @Override
    public UserLogRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_activities_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);


        return new UserLogRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("POS", "POSISI: " + position);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");
        holder.textViewActivity.setText(activitiesLog[position]);
        holder.textViewActivityTime.setText(dateActivitiesLog[position]);
        holder.IVImageIcon.setImageResource(titleIcon[contentCodeLog[position]]);
    }

    @Override
    public int getItemCount() {
        return userActivitiesList.size();
    }

}
