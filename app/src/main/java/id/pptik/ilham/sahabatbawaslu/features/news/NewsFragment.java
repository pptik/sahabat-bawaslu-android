package id.pptik.ilham.sahabatbawaslu.features.news;

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
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.gamification.LeaderboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.notification.NotificationActivity;
import id.pptik.ilham.sahabatbawaslu.features.profile.ProfileUserActivity;
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
    private SwipeRefreshLayout swipeRefreshRecycler;
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

    private int skip = 5;
    private boolean loadMore = true;
    SharedPreferences sharedPreferences;
    MaterialsRecyclerView mRV;

    ProgressDialog progressDialog;

    public NewsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_news, container, false);

        progressDialog = new ProgressDialog(view.getContext());


        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_tambah_berita);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Tambah berita!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), AddNewsActivity.class);
                startActivity(intent);
                //startActivityForResult(intent,);
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
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        getNewsList(access_token, view.getContext(), 0);

        swipeRefreshRecycler = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewsList(access_token, view.getContext(), 0);
                swipeRefreshRecycler.setRefreshing(false);
            }
        });

        //Content when scrolling
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());


                int itemCount = mLayoutManager.getItemCount();
                int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                if (itemCount - pos == 1) {
                    getNewsListScrolled(access_token, view.getContext(), skip, itemCount);

                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        });


        return view;

    }

    private void getNewsList(String accessToken, final Context context, int skipParam) {
        skip = 5;
        loadMore = true;
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        if (RestServiceClass.isNetworkAvailable(context)) {
            Call<DashboardPOJO> dashboardPOJOCall = restServiceInterface.dashboard(skipParam, accessToken);
            dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
                @Override
                public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                    //Adapter
                    MaterialsRecyclerView.usernameList.clear();
                    MaterialsRecyclerView.datePostList.clear();
                    MaterialsRecyclerView.titlePostList.clear();
                    MaterialsRecyclerView.contentPostList.clear();
                    MaterialsRecyclerView.userPictureProfileList.clear();
                    MaterialsRecyclerView.newsMediaList.clear();
                    MaterialsRecyclerView.textNumberFavoriteList.clear();
                    MaterialsRecyclerView.textNumberDownvoteList.clear();
                    MaterialsRecyclerView.textNumberUpvoteList.clear();
                    MaterialsRecyclerView.textNumberCommentList.clear();
                    MaterialsRecyclerView.newsTypeList.clear();
                    MaterialsRecyclerView.activityCodeList.clear();
                    MaterialsRecyclerView.contentIdList.clear();
                    MaterialsRecyclerView.contentTypeList.clear();
                    MaterialsRecyclerView.contentTextList.clear();
                    MaterialsRecyclerView.activityTextList.clear();
                    MaterialsRecyclerView.statusUpvoteList.clear();
                    MaterialsRecyclerView.statusDownvoteList.clear();
                    MaterialsRecyclerView.statusFavoriteList.clear();

                    //Fragment
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
                    for (int item = 0; item < dashboardPOJO.getResults().size(); item++) {
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
                    mAdapter = new MaterialsRecyclerView(username, datePost, contentPost,
                            userPicturePost, contentType, titlePost, contentLabel, activityLabel, numberFavorite,
                            numberUpvote, numberDownvote, numberComments, upvoteStatus, downvoteStatus, favoriteStatus, getActivity(),
                            newsType, newsMedia, contentId, activityType
                    );
                    //mAdapter.notifyDataSetChanged();

                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<DashboardPOJO> call, Throwable t) {
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

    private void getNewsListScrolled(final String accessToken, final Context context, final int skipParam, final int itemCount) {

        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        if (RestServiceClass.isNetworkAvailable(context)) {
            if (loadMore) {
                Call<DashboardPOJO> dashboardPOJOCall = restServiceInterface.dashboard(skipParam, accessToken);
                dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
                    @Override
                    public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {

                        DashboardPOJO dashboardPOJO = response.body();

                        if (dashboardPOJO.getResults().size() == 0) {
                            loadMore = false;
                        } else {
                            for (int item = 0; item < dashboardPOJO.getResults().size(); item++) {

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



                            mAdapter = new MaterialsRecyclerView(username, datePost, contentPost,
                                    userPicturePost, contentType, titlePost, contentLabel, activityLabel, numberFavorite,
                                    numberUpvote, numberDownvote, numberComments, upvoteStatus, downvoteStatus, favoriteStatus, getActivity(),
                                    newsType, newsMedia, contentId, activityType
                            );

                            mRecyclerView.setAdapter(mAdapter);


                            mRecyclerView.scrollToPosition(itemCount - 1);

                            skip = skip + 5;
                        }

                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<DashboardPOJO> call, Throwable t) {

                    }
                });
            } else {
                progressDialog.setProgress(100);
                progressDialog.dismiss();
            }
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

    private void addItem(){

    }

    private void searchNewsList(String query, String accessToken, Context context) {
        skip = 5;
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if (RestServiceClass.isNetworkAvailable(context)) {
            restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
            Call<DashboardPOJO> dashboardSearchTitlePOJOCall = restServiceInterface.dashboardSearchTitle(0, query, accessToken);
            dashboardSearchTitlePOJOCall.enqueue(new Callback<DashboardPOJO>() {
                @Override
                public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                    //Adapter
                    MaterialsRecyclerView.usernameList.clear();
                    MaterialsRecyclerView.datePostList.clear();
                    MaterialsRecyclerView.titlePostList.clear();
                    MaterialsRecyclerView.contentPostList.clear();
                    MaterialsRecyclerView.userPictureProfileList.clear();
                    MaterialsRecyclerView.newsMediaList.clear();
                    MaterialsRecyclerView.textNumberFavoriteList.clear();
                    MaterialsRecyclerView.textNumberDownvoteList.clear();
                    MaterialsRecyclerView.textNumberUpvoteList.clear();
                    MaterialsRecyclerView.textNumberCommentList.clear();
                    MaterialsRecyclerView.newsTypeList.clear();
                    MaterialsRecyclerView.activityCodeList.clear();
                    MaterialsRecyclerView.contentIdList.clear();
                    MaterialsRecyclerView.contentTypeList.clear();
                    MaterialsRecyclerView.contentTextList.clear();
                    MaterialsRecyclerView.activityTextList.clear();
                    MaterialsRecyclerView.statusUpvoteList.clear();
                    MaterialsRecyclerView.statusDownvoteList.clear();
                    MaterialsRecyclerView.statusFavoriteList.clear();

                    //Fragment
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

                    for (int item = 0; item < dashboardPOJO.getResults().size(); item++) {
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
                    mAdapter = new MaterialsRecyclerView(username, datePost, contentPost,
                            userPicturePost, contentType, titlePost, contentLabel, activityLabel, numberFavorite,
                            numberUpvote, numberDownvote, numberComments, upvoteStatus, downvoteStatus, favoriteStatus,
                            getActivity(), newsType, newsMedia, contentId, activityType
                    );
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<DashboardPOJO> call, Throwable t) {

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

    @Override
    public void onResume() {
        super.onResume();
        /*SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");
        getNewsList(access_token);*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.newssearchmenu, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Query pencarian materi berdasarkan teks
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                final String access_token = sharedPreferences.getString("accessToken", "abcde");
                searchNewsList(query, access_token, getContext());
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
        switch (item.getItemId()) {
            case R.id.sort:
                popUpSortMenu(view);
                return true;
            case R.id.action_more:
                popUpMoreMenu(view);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popUpSortMenu(final View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popupsortcontent, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pop_up_sort_date:
                        sortByCategoryREST(0, view.getContext());
                        return true;
                    case R.id.pop_up_sort_upvote:
                        sortByCategoryREST(1, view.getContext());
                        return true;
                    case R.id.pop_up_sort_downvote:
                        sortByCategoryREST(2, view.getContext());
                        return true;
                    case R.id.pop_up_sort_favorite:
                        sortByCategoryREST(3, view.getContext());
                        return true;
                    case R.id.pop_up_filter_materi:
                        filterByContentREST(1, view.getContext());
                        return true;
                    case R.id.pop_up_filter_berita:
                        filterByContentREST(2, view.getContext());
                        return true;
                    case R.id.pop_up_sort_comment:
                        filterByContentREST(3, view.getContext());
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void popUpMoreMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popupslidingtab, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
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
                    case R.id.pop_up_profile_user:
                        Intent intentProfileUser = new Intent(getContext(), ProfileUserActivity.class);
                        startActivity(intentProfileUser);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
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
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void sortByCategoryREST(int code, Context context) {
        skip = 5;
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        if (RestServiceClass.isNetworkAvailable(context)) {
            Call<DashboardPOJO> dashboardPOJOCall = restServiceInterface.dashboardSortBy(0, code, access_token);
            dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
                @Override
                public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                    //Adapter
                    MaterialsRecyclerView.usernameList.clear();
                    MaterialsRecyclerView.datePostList.clear();
                    MaterialsRecyclerView.titlePostList.clear();
                    MaterialsRecyclerView.contentPostList.clear();
                    MaterialsRecyclerView.userPictureProfileList.clear();
                    MaterialsRecyclerView.newsMediaList.clear();
                    MaterialsRecyclerView.textNumberFavoriteList.clear();
                    MaterialsRecyclerView.textNumberDownvoteList.clear();
                    MaterialsRecyclerView.textNumberUpvoteList.clear();
                    MaterialsRecyclerView.textNumberCommentList.clear();
                    MaterialsRecyclerView.newsTypeList.clear();
                    MaterialsRecyclerView.activityCodeList.clear();
                    MaterialsRecyclerView.contentIdList.clear();
                    MaterialsRecyclerView.contentTypeList.clear();
                    MaterialsRecyclerView.contentTextList.clear();
                    MaterialsRecyclerView.activityTextList.clear();
                    MaterialsRecyclerView.statusUpvoteList.clear();
                    MaterialsRecyclerView.statusDownvoteList.clear();
                    MaterialsRecyclerView.statusFavoriteList.clear();

                    //Fragment
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
                    for (int item = 0; item < dashboardPOJO.getResults().size(); item++) {
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
                    mAdapter = new MaterialsRecyclerView(username, datePost, contentPost,
                            userPicturePost, contentType, titlePost, contentLabel, activityLabel, numberFavorite,
                            numberUpvote, numberDownvote, numberComments, upvoteStatus, downvoteStatus, favoriteStatus, getActivity(),
                            newsType, newsMedia, contentId, activityType
                    );
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<DashboardPOJO> call, Throwable t) {

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

    private void filterByContentREST(int code, Context context) {
        skip = 5;
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        //Ambil Data dari Networking REST
        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken", "abcde");

        if (RestServiceClass.isNetworkAvailable(context)) {
            Call<DashboardPOJO> dashboardPOJOCall = restServiceInterface.dashboardFilterByContent(0, code, access_token);
            dashboardPOJOCall.enqueue(new Callback<DashboardPOJO>() {
                @Override
                public void onResponse(Call<DashboardPOJO> call, Response<DashboardPOJO> response) {
                    //Adapter
                    MaterialsRecyclerView.usernameList.clear();
                    MaterialsRecyclerView.datePostList.clear();
                    MaterialsRecyclerView.titlePostList.clear();
                    MaterialsRecyclerView.contentPostList.clear();
                    MaterialsRecyclerView.userPictureProfileList.clear();
                    MaterialsRecyclerView.newsMediaList.clear();
                    MaterialsRecyclerView.textNumberFavoriteList.clear();
                    MaterialsRecyclerView.textNumberDownvoteList.clear();
                    MaterialsRecyclerView.textNumberUpvoteList.clear();
                    MaterialsRecyclerView.textNumberCommentList.clear();
                    MaterialsRecyclerView.newsTypeList.clear();
                    MaterialsRecyclerView.activityCodeList.clear();
                    MaterialsRecyclerView.contentIdList.clear();
                    MaterialsRecyclerView.contentTypeList.clear();
                    MaterialsRecyclerView.contentTextList.clear();
                    MaterialsRecyclerView.activityTextList.clear();
                    MaterialsRecyclerView.statusUpvoteList.clear();
                    MaterialsRecyclerView.statusDownvoteList.clear();
                    MaterialsRecyclerView.statusFavoriteList.clear();

                    //Fragment
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
                    for (int item = 0; item < dashboardPOJO.getResults().size(); item++) {
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
                    mAdapter = new MaterialsRecyclerView(username, datePost, contentPost,
                            userPicturePost, contentType, titlePost, contentLabel, activityLabel, numberFavorite,
                            numberUpvote, numberDownvote, numberComments, upvoteStatus, downvoteStatus, favoriteStatus, getActivity(),
                            newsType, newsMedia, contentId, activityType
                    );
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<DashboardPOJO> call, Throwable t) {

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
