package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import id.pptik.ilham.sahabatbawaslu.features.gamification.LeaderboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.notification.NotificationActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
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
    private SwipeRefreshLayout swipeRefreshRecycler;
    public RestServiceInterface restServiceInterface;
    private List<Integer> authors = new ArrayList<Integer>();
    private List<String> datePosts = new ArrayList<String>();
    private List<String> materialIds = new ArrayList<String>();
    private List<String> descs = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    //private List<String> contentId = new ArrayList<String>();
    private List<Integer> favorites = new ArrayList<Integer>();
    private List<Integer> upVotes = new ArrayList<Integer>();
    private List<Integer> downVotes = new ArrayList<Integer>();
    private List<Integer> comments = new ArrayList<Integer>();
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    public LearningFragment() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learning, container, false);
        progressDialog = new ProgressDialog(view.getContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        getMaterialsList(access_token);

        swipeRefreshRecycler = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMaterialsList(access_token);
                swipeRefreshRecycler.setRefreshing(false);
            }
        });
        return view;
    }

    private void getMaterialsList(String accessToken){
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(getContext())){
            Call<MaterialsListPOJO> callMaterialsList = restServiceInterface.materialsList(0,accessToken);
            callMaterialsList.enqueue(new Callback<MaterialsListPOJO>() {
                @Override
                public void onResponse(Call<MaterialsListPOJO> call, Response<MaterialsListPOJO> response) {
                    //Adapter
                    LearningRecyclerView.materialTypeList.clear();
                    LearningRecyclerView.datePostList.clear();
                    LearningRecyclerView.materialIdList.clear();
                    LearningRecyclerView.descList.clear();
                    LearningRecyclerView.titleList.clear();
                    LearningRecyclerView.upVoteNumbersList.clear();
                    LearningRecyclerView.downVoteNumbersList.clear();
                    LearningRecyclerView.commentNumbersList.clear();
                    LearningRecyclerView.favoriteNumbersList.clear();

                    //Fragment
                    authors.clear();
                    datePosts.clear();
                    descs.clear();
                    titles.clear();
                    upVotes.clear();
                    downVotes.clear();
                    comments.clear();
                    favorites.clear();
                    materialIds.clear();

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
                        materialIds.add(materialsListPOJO.getResults().get(materi).getId());
                        //contentId.add(materialsListPOJO.getResults().get(materi).getId());
                    }
                    mAdapter = new LearningRecyclerView(authors,datePosts,descs,titles,
                            favorites,upVotes,downVotes,comments,materialIds,getActivity());
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MaterialsListPOJO> call, Throwable t) {
                    Log.d("RETROFIT ERROR","ERROR: "+t.toString());
                }
            });
        }else{
            progressDialog.setProgress(100);
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
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

    private void searchMaterialsList(String query, String accessToken){
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(getContext())){
            Call<MaterialsListPOJO> callMaterialsSearchTitle = restServiceInterface.materialsSearchTitle(0,query,accessToken);
            callMaterialsSearchTitle.enqueue(new Callback<MaterialsListPOJO>() {
                @Override
                public void onResponse(Call<MaterialsListPOJO> call, Response<MaterialsListPOJO> response) {

                    //Adapter
                    LearningRecyclerView.materialTypeList.clear();
                    LearningRecyclerView.datePostList.clear();
                    LearningRecyclerView.materialIdList.clear();
                    LearningRecyclerView.descList.clear();
                    LearningRecyclerView.titleList.clear();
                    LearningRecyclerView.upVoteNumbersList.clear();
                    LearningRecyclerView.downVoteNumbersList.clear();
                    LearningRecyclerView.commentNumbersList.clear();
                    LearningRecyclerView.favoriteNumbersList.clear();

                    //Fragment
                    authors.clear();
                    datePosts.clear();
                    descs.clear();
                    titles.clear();
                    upVotes.clear();
                    downVotes.clear();
                    comments.clear();
                    favorites.clear();
                    materialIds.clear();

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
                        materialIds.add(materialsListPOJO.getResults().get(materi).getId());
                    }
                    mAdapter = new LearningRecyclerView(authors,datePosts,descs,titles,
                            favorites,upVotes,downVotes,comments,materialIds,getActivity());
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MaterialsListPOJO> call, Throwable t) {

                }
            });
        }else{
            progressDialog.setProgress(100);
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
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

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                final String access_token = sharedPreferences.getString("accessToken","abcde");

                searchMaterialsList(query,access_token);
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
                popUpMoreMenu(view);
                return true;
            case R.id.sort:
                popUpSortMenu(view);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popUpMoreMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popupslidinglearning,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    /*case R.id.pop_up_notifikasi:
                        Intent intent = new Intent(getContext(), NotificationActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;*/
                    case R.id.pop_up_leaderboard:
                        Intent intentLeaderboard = new Intent(getContext(), LeaderboardActivity.class);
                        startActivity(intentLeaderboard);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                    /*case R.id.pop_up_quiz:
                        Intent intentQuiz = new Intent(getContext(), QuizDetailActivity.class);
                        startActivity(intentQuiz);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                    case R.id.pop_up_edit_profile_slidingtab:
                        Toast.makeText(getContext(), "Edit Profile menu clicked", Toast.LENGTH_SHORT).show();
                        return true;*/
                    case R.id.pop_up_log_out_slidingtab:
                        sharedPreferences = getContext().getSharedPreferences(LoginActivity.SessionPengguna, Context.MODE_PRIVATE);
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

    public void popUpSortMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popupsortmaterialcontent,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pop_up_sort_kasus:
                        sortByCategoryREST(2);
                        return true;
                    case R.id.pop_up_sort_suplemen:
                        sortByCategoryREST(1);
                        return true;
                    case R.id.pop_up_sort_video:
                        sortByCategoryREST(0);
                        return true;
                    default:return false;
                }
            }
        });
        popupMenu.show();
    }

    private void sortByCategoryREST(int code) {
        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(getContext())){
            Call<MaterialsListPOJO> materialsListPOJOCall = restServiceInterface.materialsSortBy(0,code,access_token);
            materialsListPOJOCall.enqueue(new Callback<MaterialsListPOJO>() {
                @Override
                public void onResponse(Call<MaterialsListPOJO> call, Response<MaterialsListPOJO> response) {
                    //Adapter
                    LearningRecyclerView.materialTypeList.clear();
                    LearningRecyclerView.datePostList.clear();
                    LearningRecyclerView.materialIdList.clear();
                    LearningRecyclerView.descList.clear();
                    LearningRecyclerView.titleList.clear();
                    LearningRecyclerView.upVoteNumbersList.clear();
                    LearningRecyclerView.downVoteNumbersList.clear();
                    LearningRecyclerView.commentNumbersList.clear();
                    LearningRecyclerView.favoriteNumbersList.clear();

                    //Fragment
                    authors.clear();
                    datePosts.clear();
                    descs.clear();
                    titles.clear();
                    upVotes.clear();
                    downVotes.clear();
                    comments.clear();
                    favorites.clear();
                    materialIds.clear();

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
                        materialIds.add(materialsListPOJO.getResults().get(materi).getId());
                    }
                    mAdapter = new LearningRecyclerView(authors,datePosts,descs,titles,
                            favorites,upVotes,downVotes,comments,materialIds,getActivity());
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MaterialsListPOJO> call, Throwable t) {

                }
            });
        }else{
            progressDialog.setProgress(100);
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
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
