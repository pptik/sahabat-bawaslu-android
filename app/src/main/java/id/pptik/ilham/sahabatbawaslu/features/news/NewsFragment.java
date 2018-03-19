package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ilham on 12/03/18.
 */

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    /*List<String> dataSetJudulMateri = new ArrayList<>();
    List<String> dataSetCoverMateri = new ArrayList<>();
    List<Integer> dataSetSubJudulMateri = new ArrayList<>();*/
    public RestServiceInterface restServiceInterface;
    private List<String> username = new ArrayList<String>();
    private List<String> datePost = new ArrayList<String>();
    private List<String> titlePost = new ArrayList<String>();
    private List<String> contentPost = new ArrayList<String>();
    private List<String> userPicturePost = new ArrayList<String>();
    private List<Integer> contentType = new ArrayList<Integer>();

    ProgressDialog progressDialog;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //mAdapter = new MaterialsRecyclerView(dataSetJudulMateri, dataSetCoverMateri,dataSetSubJudulMateri);



        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");
        //Log.d("AKSES TOKEN:","AKSES TOKEN: "+access_token);


        Call<DashboardPOJO> dashboardPOJOCall = restServiceInterface.dashboard(0,access_token);
        dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
            @Override
            public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                DashboardPOJO dashboardPOJO = response.body();
                for (int item = 0 ; item < dashboardPOJO.getResults().size(); item++){
                    username.add(dashboardPOJO.getResults().get(item).getPostBy().getUsername());
                    datePost.add(dashboardPOJO.getResults().get(item).getCreatedAt());
                    titlePost.add(dashboardPOJO.getResults().get(item).getTitle());
                    contentPost.add(dashboardPOJO.getResults().get(item).getDesc());
                    userPicturePost.add(dashboardPOJO.getResults().get(item).getUserDetail().getDisplayPicture());
                    contentType.add(dashboardPOJO.getResults().get(item).getContent_code());


                    /*
                    titlePost
                    contentPost
                    userPicturePost
                    contentType*/
                }
                mAdapter = new MaterialsRecyclerView(username,datePost,contentPost,userPicturePost,contentType,titlePost);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<DashboardPOJO> call, Throwable t) {

            }
        });

        return view;

    }


}
