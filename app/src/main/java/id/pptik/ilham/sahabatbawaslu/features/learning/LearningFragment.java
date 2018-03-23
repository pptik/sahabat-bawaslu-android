package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.dashboard.DashboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.notification.NotificationActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import retrofit2.Call;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SEARCH_SERVICE;

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
    SharedPreferences sharedPreferences;

    public LearningFragment() {
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.learningsearchmenu,menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Query pencarian materi berdasarkan teks

                restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                final String access_token = sharedPreferences.getString("accessToken","abcde");

                Call<MaterialsListPOJO> callMaterialsSearchTitle = restServiceInterface.materialsSearchTitle(0,query,access_token);
                callMaterialsSearchTitle.enqueue(new Callback<MaterialsListPOJO>() {
                    @Override
                    public void onResponse(Call<MaterialsListPOJO> call, Response<MaterialsListPOJO> response) {

                        //Mengosongkan recycle material yang sudah diisi
                        authors.clear();
                        datePosts.clear();
                        descs.clear();
                        titles.clear();
                        favorites.clear();
                        upVotes.clear();
                        downVotes.clear();
                        comments.clear();

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

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View view = getActivity().findViewById(R.id.action_more);
        switch (item.getItemId()){
            case R.id.action_more:
                popUpMenu(view);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popupslidingtab,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pop_up_notifikasi:
                        Intent intent = new Intent(getContext(), NotificationActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                    case R.id.pop_up_edit_profile_slidingtab:
                        Toast.makeText(getContext(), "Edit Profile menu clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.pop_up_log_out_slidingtab:
                        sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent2 = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent2);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                    default:return false;
                }
            }
        });
        popupMenu.show();
    }


}
