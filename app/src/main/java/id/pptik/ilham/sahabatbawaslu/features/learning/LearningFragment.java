package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import retrofit2.Call;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ilham on 15/03/18.
 */

public class LearningFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public RestServiceInterface restServiceInterface;


    public LearningFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learning, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //mAdapter = new MaterialsRecyclerView(dataSetJudulMateri, dataSetCoverMateri,dataSetSubJudulMateri);
        mAdapter = new LearningRecyclerView();
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");
        //Log.d("AKSES TOKEN:","AKSES TOKEN: "+access_token);


        Call<MaterialsListPOJO> callMaterialsList = restServiceInterface.materialsList(0,access_token);

        callMaterialsList.enqueue(new Callback<MaterialsListPOJO>() {
            @Override
            public void onResponse(Call<MaterialsListPOJO> call, Response<MaterialsListPOJO> response) {
                MaterialsListPOJO materialsListPOJO = response.body();
                Log.d("RETROFIT:","RETROFIT: "+materialsListPOJO.getResults().get(0).getTitle().toString());
            }

            @Override
            public void onFailure(Call<MaterialsListPOJO> call, Throwable t) {
                Log.d("RETROFIT","RETROFIT: "+t.toString());
            }
        });

        /*callMaterialsList.enqueue(new Callback<List<MaterialsListPOJO>>() {
            @Override
            public void onResponse(Call<List<MaterialsListPOJO>> call, Response<List<MaterialsListPOJO>> response) {

            }

            @Override
            public void onFailure(Call<List<MaterialsListPOJO>> call, Throwable t) {

            }


        });*/
        return view;

    }
}
