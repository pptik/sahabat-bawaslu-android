package id.pptik.ilham.sahabatbawaslu.features.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProfilePOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.UserLogsPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivitiesLogFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> activitiesLog = new ArrayList<String>();
    private List<String> activitiesLogDate = new ArrayList<String>();
    private List<Integer> contentCode = new ArrayList<Integer>();
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    public RestServiceInterface restServiceInterface;
    public ProfileActivitiesLogFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_activities_list, container, false);

        progressDialog = new ProgressDialog(view.getContext());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");
        getListActivitiesLog(access_token,view.getContext());
        return view;
    }

    private void getListActivitiesLog(String accessToken, final Context context) {

        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();
        if (RestServiceClass.isNetworkAvailable(context)) {
            Call<UserLogsPOJO> UserLogsPOJO = restServiceInterface.userlogs(accessToken,"exampleReq");
            UserLogsPOJO.enqueue(new Callback<UserLogsPOJO>() {
                @Override
                public void onResponse(Call<UserLogsPOJO> call, Response<UserLogsPOJO> response) {
                    //Adapter
                    UserLogRecyclerView.userActivitiesList.clear();
                    UserLogRecyclerView.dateuseractivitiesList.clear();
                    UserLogRecyclerView.contentCodeList.clear();

                    //Fragment
                    activitiesLog.clear();
                    activitiesLogDate.clear();
                    contentCode.clear();


                    UserLogsPOJO UserLogsPOJO = response.body();
                    for (int item = 0; item < UserLogsPOJO.getResults().size(); item++) {
                       activitiesLog.add(UserLogsPOJO.getResults().get(item).getActivityText()+" "+UserLogsPOJO.getResults().get(item).getTitle());
                        activitiesLogDate.add(UserLogsPOJO.getResults().get(item).getCreatedAtFromNow());
                        contentCode.add(UserLogsPOJO.getResults().get(item).getContentCode());
                    }
                    mAdapter = new UserLogRecyclerView(activitiesLog, activitiesLogDate,contentCode,getActivity());
                    //mAdapter.notifyDataSetChanged();

                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UserLogsPOJO> call, Throwable t) {
                    progressDialog.setProgress(100);
                    progressDialog.dismiss();

                    Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            progressDialog.setProgress(100);
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage(R.string.pastikan_internet_label)
                    .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }).show();
        }
    }
}
