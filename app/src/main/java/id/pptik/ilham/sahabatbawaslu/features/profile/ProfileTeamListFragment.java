package id.pptik.ilham.sahabatbawaslu.features.profile;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProfilePOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.TeamListPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileTeamListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> usernameList = new ArrayList<String>();
    private List<String> emailList = new ArrayList<String>();
    private List<String> displayPictureList = new ArrayList<String>();
    @BindView(R.id.tv_username)TextView textViewUsername;
    @BindView(R.id.tv_email)TextView textViewEmail;
    @BindView(R.id.imageIcon)ImageView imageViewDP;
    private SwipeRefreshLayout swipeRefreshRecycler;

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    public RestServiceInterface restServiceInterface;
    public ProfileTeamListFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_reference_list, container, false);

        progressDialog = new ProgressDialog(view.getContext());
        ButterKnife.bind(this,view);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");
        getTeamList(access_token,view.getContext());

        swipeRefreshRecycler = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTeamList(access_token,view.getContext());
                swipeRefreshRecycler.setRefreshing(false);
            }
        });
        return view;
    }

    private void getTeamList(String accessToken, final Context context) {
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        if (RestServiceClass.isNetworkAvailable(context)) {
            Call<TeamListPOJO> teamListPOJOCall = restServiceInterface.TEAM_LIST_POJO_CALL(accessToken,"bebasla ya");
            teamListPOJOCall.enqueue(new Callback<TeamListPOJO>() {
                @Override
                public void onResponse(Call<TeamListPOJO> call, Response<TeamListPOJO> response) {
                    TeamListPOJO teamListPOJO = response.body();

                    textViewEmail.setText(teamListPOJO.getResults().getEmail());
                    textViewUsername.setText(teamListPOJO.getResults().getUsername());

                    imageViewDP.setImageDrawable(null);
                    Glide.with(imageViewDP.getContext()).load(teamListPOJO.getResults().getDisplayPicture()).into(imageViewDP);

                    TeamListRecycleView.usernameList.clear();
                    TeamListRecycleView.emailList.clear();
                    TeamListRecycleView.displayPictureList.clear();

                    usernameList.clear();
                    emailList.clear();
                    displayPictureList.clear();

                    for (int item = 0; item < teamListPOJO.getResults().getTeammates().size(); item++) {
                        usernameList.add(teamListPOJO.getResults().getTeammates().get(item).getUsername());
                        emailList.add(teamListPOJO.getResults().getTeammates().get(item).getEmail());
                        displayPictureList.add(teamListPOJO.getResults().getTeammates().get(item).getDisplayPicture());
                    }
                    mAdapter = new TeamListRecycleView(usernameList, emailList,displayPictureList,getActivity());
                    //mAdapter.notifyDataSetChanged();

                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<TeamListPOJO> call, Throwable t) {
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
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }).show();
        }

    }
}
