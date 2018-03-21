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

import java.util.ArrayList;
import java.util.Arrays;
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
    private List<Integer> authors = new ArrayList<Integer>();
    private List<String> datePosts = new ArrayList<String>();
    private List<String> descs = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    private List<Integer> favorites = new ArrayList<Integer>();
    private List<Integer> upVotes = new ArrayList<Integer>();
    private List<Integer> downVotes = new ArrayList<Integer>();
    private List<Integer> comments = new ArrayList<Integer>();

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

        /*mAdapter = new ForumRecyclerView(authors,datePosts,descs,titles);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);*/

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");
        //Log.d("AKSES TOKEN:","AKSES TOKEN: "+access_token);


        Call<MaterialsListPOJO> callMaterialsList = restServiceInterface.materialsList(0,access_token);

        callMaterialsList.enqueue(new Callback<MaterialsListPOJO>() {
            @Override
            public void onResponse(Call<MaterialsListPOJO> call, Response<MaterialsListPOJO> response) {
                MaterialsListPOJO materialsListPOJO = response.body();
                for (int materi = 0;materi<materialsListPOJO.getResults().size();materi++){
                  authors.add(materialsListPOJO.getResults().get(materi).getType());
                  datePosts.add(materialsListPOJO.getResults().get(materi).getCreatedAtFromNow());
                  descs.add(materialsListPOJO.getResults().get(materi).getDesc());
                  titles.add(materialsListPOJO.getResults().get(materi).getTitle());
                  upVotes.add(materialsListPOJO.getResults().get(materi).getUpvote());
                  downVotes.add(materialsListPOJO.getResults().get(materi).getDownvote());
                  comments.add(materialsListPOJO.getResults().get(materi).getComment());
                  favorites.add(materialsListPOJO.getResults().get(materi).getFavorite());
                }
                mAdapter = new LearningRecyclerView(authors,datePosts,descs,titles,
                        favorites,upVotes,downVotes,comments,getActivity());
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MaterialsListPOJO> call, Throwable t) {
                Log.d("RETROFIT ERROR","ERROR: "+t.toString());
            }
        });


        return view;

    }
}
