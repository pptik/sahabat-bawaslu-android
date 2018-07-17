package id.pptik.ilham.sahabatbawaslu.features.profile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;

public class TeamListRecycleView extends RecyclerView.Adapter<TeamListRecycleView.ViewHolder> {
    public static List<String> usernameList = new ArrayList<String>();
    public static List<String> emailList = new ArrayList<String>();
    public static List<String> displayPictureList = new ArrayList<String>();
    private String[] username, email,displaypicture;
    private Activity activity;
    private RestServiceInterface restServiceInterface;
    private SharedPreferences sharedPreferences;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewUsername,textViewEmail;
        public ImageView IVImageIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewUsername=itemView.findViewById(R.id.tv_username);
            textViewEmail=itemView.findViewById(R.id.tv_email);
            IVImageIcon=itemView.findViewById(R.id.imageIcon);

        }
    }

    public TeamListRecycleView(List<String> usernameParam, List<String> emailParam,List<String> displayPictureParam,Activity activity) {
        this.activity = activity;
        usernameList = usernameParam;
        emailList = emailParam;
        displayPictureList=displayPictureParam;


        username = new String[usernameList.size()];
        email = new String[emailList.size()];
        displaypicture=new String[displayPictureList.size()];


        username = usernameList.toArray(username);
        email = emailList.toArray(email);
        displaypicture=displayPictureList.toArray(displaypicture);
        //notifyDataSetChanged();

    }
    @Override
    public TeamListRecycleView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_reference_content, parent, false);

        sharedPreferences = parent.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);


        return new TeamListRecycleView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamListRecycleView.ViewHolder holder, int position) {
      holder.textViewEmail.setText(email[position]);
      holder.textViewUsername.setText(username[position]);
        holder.IVImageIcon.setImageDrawable(null);
        Glide.with(holder.IVImageIcon.getContext()).load(displaypicture[position]).into(holder.IVImageIcon);

    }

    @Override
    public int getItemCount() {
        return usernameList.size();
    }


}
