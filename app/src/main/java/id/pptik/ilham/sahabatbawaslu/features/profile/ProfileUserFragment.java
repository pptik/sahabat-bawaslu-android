package id.pptik.ilham.sahabatbawaslu.features.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.gamification.LeaderboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.notification.NotificationActivity;
import id.pptik.ilham.sahabatbawaslu.features.profile.ProfileUserActivity;
import id.pptik.ilham.sahabatbawaslu.features.signup.SignUpActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProfilePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SEARCH_SERVICE;
public class ProfileUserFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.user_profile_email)TextView textViewEmail;
    @BindView(R.id.user_profile_name)TextView textViewUsername;
    @BindView(R.id.user_profile_phone)TextView textViewPhone;
    @BindView(R.id.profile_poin)TextView textViewPoin;
    @BindView(R.id.user_profile_photo)ImageButton IButtonPhoto;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    public RestServiceInterface restServiceInterface;
    public ProfileUserFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);

        progressDialog = new ProgressDialog(view.getContext());
        ButterKnife.bind(this,view);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");
        final String role=sharedPreferences.getString("role","2");
        getProfileDetail(access_token,view.getContext(),role);

        return view;
    }

    private void getProfileDetail(String accessToken, final Context context, final String role) {
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        if (RestServiceClass.isNetworkAvailable(context)) {
            Call<ProfilePOJO> profilePOJOCall = restServiceInterface.profile(accessToken,"bebasla ya");
            profilePOJOCall.enqueue(new Callback<ProfilePOJO>() {
                @Override
                public void onResponse(Call<ProfilePOJO> call, Response<ProfilePOJO> response) {
                    ProfilePOJO profilePOJO = response.body();
                    if (Integer.parseInt(role)==2){
                        textViewPoin.setText(profilePOJO.getResults().getPoin().toString());
                    }else {
                        textViewPoin.setText(profilePOJO.getResults().getLeaderPoin().toString());
                    }
                    textViewEmail.setText(profilePOJO.getResults().getEmail());
                    textViewUsername.setText(profilePOJO.getResults().getUsername());
                    textViewPhone.setText(profilePOJO.getResults().getPhoneNumber());

                    IButtonPhoto.setImageDrawable(null);
                    Glide.with(IButtonPhoto.getContext()).load(profilePOJO.getResults().getDisplayPicture()).into(IButtonPhoto);
                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ProfilePOJO> call, Throwable t) {
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
