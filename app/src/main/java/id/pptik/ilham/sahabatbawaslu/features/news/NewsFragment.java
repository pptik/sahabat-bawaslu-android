package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import id.pptik.ilham.sahabatbawaslu.features.gamification.LeaderboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.notification.NotificationActivity;
import id.pptik.ilham.sahabatbawaslu.features.signup.SignUpActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SEARCH_SERVICE;

/**
 * Created by Ilham on 12/03/18.
 */

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatingActionButton;
    /*List<String> dataSetJudulMateri = new ArrayList<>();
    List<String> dataSetCoverMateri = new ArrayList<>();
    List<Integer> dataSetSubJudulMateri = new ArrayList<>();*/
    public RestServiceInterface restServiceInterface;
    private List<String> username = new ArrayList<String>();
    private List<String> datePost = new ArrayList<String>();
    private List<String> titlePost = new ArrayList<String>();
    private List<String> contentPost = new ArrayList<String>();
    private List<String> userPicturePost = new ArrayList<String>();
    private List<String> contentId = new ArrayList<String>();
    private List<String> contentType = new ArrayList<String>();
    private List<Integer> activityType = new ArrayList<Integer>();
    private List<String> contentLabel = new ArrayList<String>();
    private List<String> activityLabel = new ArrayList<String>();
    private List<String> newsMedia = new ArrayList<String>();
    private List<Integer> numberFavorite = new ArrayList<Integer>();
    private List<Integer> numberUpvote = new ArrayList<Integer>();
    private List<Integer> numberDownvote = new ArrayList<Integer>();
    private List<Integer> numberComments = new ArrayList<Integer>();
    private List<Integer> newsType = new ArrayList<Integer>();

    private List<Boolean> upvoteStatus = new ArrayList<Boolean>();
    private List<Boolean> downvoteStatus = new ArrayList<Boolean>();
    private List<Boolean> favoriteStatus = new ArrayList<Boolean>();

    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;

    public NewsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab_tambah_berita);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Tambah berita!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), AddNewsActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        Call<DashboardPOJO> dashboardPOJOCall = restServiceInterface.dashboard(0,access_token);
        dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
            @Override
            public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                username.clear();
                datePost.clear();
                contentPost.clear();
                userPicturePost.clear();
                contentType.clear();
                titlePost.clear();
                contentLabel.clear();
                activityLabel.clear();
                numberFavorite.clear();
                numberUpvote.clear();
                numberDownvote.clear();
                numberComments.clear();
                upvoteStatus.clear();
                downvoteStatus.clear();
                favoriteStatus.clear();
                upvoteStatus.clear();
                downvoteStatus.clear();
                favoriteStatus.clear();
                newsType.clear();
                newsMedia.clear();
                contentId.clear();
                activityType.clear();

                DashboardPOJO dashboardPOJO = response.body();
                for (int item = 0 ; item < dashboardPOJO.getResults().size(); item++){
                    username.add(dashboardPOJO.getResults().get(item).getDashboard().getPostBy().getUsername());
                    datePost.add(dashboardPOJO.getResults().get(item).getDashboard().getCreatedAt());
                    titlePost.add(dashboardPOJO.getResults().get(item).getDashboard().getTitle());
                    contentPost.add(dashboardPOJO.getResults().get(item).getDashboard().getSynopsis());
                    userPicturePost.add(dashboardPOJO.getResults().get(item).getDashboard().getUserDetail().getDisplayPicture());
                    contentLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getContentText());
                    activityLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityText());
                    contentType.add(dashboardPOJO.getResults().get(item).getDashboard().getContent_code().toString());
                    activityType.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityCode());
                    numberFavorite.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorite());
                    numberUpvote.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvote());
                    numberDownvote.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvote());
                    numberComments.add(dashboardPOJO.getResults().get(item).getDashboard().getComment());
                    upvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvoted());
                    downvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvoted());
                    favoriteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorited());
                    newsType.add(dashboardPOJO.getResults().get(item).getDashboard().getNewsType());
                    //newsMedia.add(dashboardPOJO.getResults().get(item).getDashboard().getFiles().get(0).getHttpPath());
                    contentId.add(dashboardPOJO.getResults().get(item).getDashboard().getId());
                    newsMedia.add("http://filehosting.pptik.id/ioaa/defaultphoto.png");
                }
                mAdapter = new MaterialsRecyclerView(username,datePost,contentPost,
                        userPicturePost,contentType,titlePost,contentLabel,activityLabel,numberFavorite,
                        numberUpvote,numberDownvote,numberComments,upvoteStatus,downvoteStatus,favoriteStatus,getActivity(),
                        newsType,newsMedia,contentId,activityType
                );
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<DashboardPOJO> call, Throwable t) {

            }
        });

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.newssearchmenu,menu);

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

                Call<DashboardPOJO> dashboardSearchTitlePOJOCall = restServiceInterface.dashboardSearchTitle(0,query,access_token);
                dashboardSearchTitlePOJOCall.enqueue(new Callback<DashboardPOJO>() {
                    @Override
                    public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                        //Mengosongkan recycle material yang sudah diisi
                        username.clear();
                        datePost.clear();
                        contentPost.clear();
                        userPicturePost.clear();
                        contentType.clear();
                        titlePost.clear();
                        contentLabel.clear();
                        activityLabel.clear();
                        numberFavorite.clear();
                        numberUpvote.clear();
                        numberDownvote.clear();
                        numberComments.clear();
                        upvoteStatus.clear();
                        downvoteStatus.clear();
                        favoriteStatus.clear();
                        upvoteStatus.clear();
                        downvoteStatus.clear();
                        favoriteStatus.clear();
                        newsType.clear();
                        newsMedia.clear();
                        contentId.clear();
                        activityType.clear();

                        DashboardPOJO dashboardPOJO = response.body();

                        for (int item = 0 ; item < dashboardPOJO.getResults().size(); item++){
                            username.add(dashboardPOJO.getResults().get(item).getDashboard().getPostBy().getUsername());
                            datePost.add(dashboardPOJO.getResults().get(item).getDashboard().getCreatedAt());
                            titlePost.add(dashboardPOJO.getResults().get(item).getDashboard().getTitle());
                            contentPost.add(dashboardPOJO.getResults().get(item).getDashboard().getSynopsis());
                            userPicturePost.add(dashboardPOJO.getResults().get(item).getDashboard().getUserDetail().getDisplayPicture());
                            contentLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getContentText());
                            activityLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityText());
                            contentType.add(dashboardPOJO.getResults().get(item).getDashboard().getContent_code().toString());
                            activityType.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityCode());
                            numberFavorite.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorite());
                            numberUpvote.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvote());
                            numberDownvote.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvote());
                            numberComments.add(dashboardPOJO.getResults().get(item).getDashboard().getComment());
                            upvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvoted());
                            downvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvoted());
                            favoriteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorited());
                            newsType.add(dashboardPOJO.getResults().get(item).getDashboard().getNewsType());
                            //newsMedia.add(dashboardPOJO.getResults().get(item).getDashboard().getFiles().get(0).getHttpPath());
                            newsMedia.add("http://filehosting.pptik.id/ioaa/defaultphoto.png");
                            contentId.add(dashboardPOJO.getResults().get(item).getDashboard().getId());
                        }
                        mAdapter = new MaterialsRecyclerView(username,datePost,contentPost,
                                userPicturePost,contentType,titlePost,contentLabel,activityLabel,numberFavorite,
                                numberUpvote,numberDownvote,numberComments,upvoteStatus,downvoteStatus,favoriteStatus,
                                getActivity(),newsType,newsMedia,contentId,activityType
                        );
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<DashboardPOJO> call, Throwable t) {

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
            case R.id.sort:
                popUpSortMenu(view);
                return true;
            case R.id.action_more:
                popUpMoreMenu(view);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popUpSortMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popupsortcontent,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pop_up_sort_date:
                        sortByCategoryREST(0);
                        return true;
                    case R.id.pop_up_sort_upvote:
                        sortByCategoryREST(1);
                        return true;
                    case R.id.pop_up_sort_downvote:
                        sortByCategoryREST(2);
                        return true;
                    case R.id.pop_up_sort_favorite:
                        sortByCategoryREST(3);
                        return true;
                    case R.id.pop_up_sort_comment:
                        sortByCategoryREST(4);
                        return true;
                    default:return false;
                }
            }
        });
        popupMenu.show();
    }

    public void popUpMoreMenu(View view){
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
                    case R.id.pop_up_leaderboard:
                        Intent intentLeaderboard = new Intent(getContext(), LeaderboardActivity.class);
                        startActivity(intentLeaderboard);
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

    private void sortByCategoryREST(int code){
        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        Call<DashboardPOJO> dashboardPOJOCall = restServiceInterface.dashboardSortBy(0,code,access_token);
        dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
            @Override
            public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                //Mengosongkan recycle material yang sudah diisi
                username.clear();
                datePost.clear();
                contentPost.clear();
                userPicturePost.clear();
                contentType.clear();
                titlePost.clear();
                contentLabel.clear();
                activityLabel.clear();
                numberFavorite.clear();
                numberUpvote.clear();
                numberDownvote.clear();
                numberComments.clear();
                upvoteStatus.clear();
                downvoteStatus.clear();
                favoriteStatus.clear();
                newsType.clear();
                newsMedia.clear();
                contentId.clear();
                activityType.clear();

                DashboardPOJO dashboardPOJO = response.body();
                for (int item = 0 ; item < dashboardPOJO.getResults().size(); item++){
                    username.add(dashboardPOJO.getResults().get(item).getDashboard().getPostBy().getUsername());
                    datePost.add(dashboardPOJO.getResults().get(item).getDashboard().getCreatedAt());
                    titlePost.add(dashboardPOJO.getResults().get(item).getDashboard().getTitle());
                    contentPost.add(dashboardPOJO.getResults().get(item).getDashboard().getSynopsis());
                    userPicturePost.add(dashboardPOJO.getResults().get(item).getDashboard().getUserDetail().getDisplayPicture());
                    contentLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getContentText());
                    activityLabel.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityText());
                    contentType.add(dashboardPOJO.getResults().get(item).getDashboard().getContent_code().toString());
                    activityType.add(dashboardPOJO.getResults().get(item).getDashboard().getActivityCode());
                    numberFavorite.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorite());
                    numberUpvote.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvote());
                    numberDownvote.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvote());
                    numberComments.add(dashboardPOJO.getResults().get(item).getDashboard().getComment());
                    upvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getUpvoted());
                    downvoteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getDownvoted());
                    favoriteStatus.add(dashboardPOJO.getResults().get(item).getDashboard().getFavorited());
                    newsType.add(dashboardPOJO.getResults().get(item).getDashboard().getNewsType());
                    //newsMedia.add(dashboardPOJO.getResults().get(item).getDashboard().getFiles().get(item).getHttpPath());
                    contentId.add(dashboardPOJO.getResults().get(item).getDashboard().getId());
                    newsMedia.add("http://filehosting.pptik.id/ioaa/defaultphoto.png");
                }
                mAdapter = new MaterialsRecyclerView(username,datePost,contentPost,
                        userPicturePost,contentType,titlePost,contentLabel,activityLabel,numberFavorite,
                        numberUpvote,numberDownvote,numberComments,upvoteStatus,downvoteStatus,favoriteStatus,getActivity(),
                        newsType,newsMedia,contentId,activityType
                );
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<DashboardPOJO> call, Throwable t) {

            }
        });
    }
}
