package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import id.pptik.ilham.sahabatbawaslu.features.gamification.LeaderboardActivity;
import id.pptik.ilham.sahabatbawaslu.features.learning.LearningRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.AddNewsActivity;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.notification.NotificationActivity;
import id.pptik.ilham.sahabatbawaslu.features.profile.ProfileUserActivity;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.DashboardPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ForumsListPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.SEARCH_SERVICE;

/**
 * Created by Ilham on 15/03/18.
 */

public class ForumFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestServiceInterface restServiceInterface;
    private List<String> forumId = new ArrayList<String>();
    private List<String> hashtag = new ArrayList<String>();
    private List<String> datePosts = new ArrayList<String>();
    private List<String> descs = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    private List<Integer> favorites = new ArrayList<Integer>();
    private List<Integer> upVotes = new ArrayList<Integer>();
    private List<Integer> downVotes = new ArrayList<Integer>();
    private List<Integer> comments = new ArrayList<Integer>();

    private List<Boolean> upvoteStatus = new ArrayList<Boolean>();
    private List<Boolean> downvoteStatus = new ArrayList<Boolean>();
    private List<Boolean> favoriteStatus = new ArrayList<Boolean>();

    private StringBuilder hashtagStringBuilder = new StringBuilder();
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeRefreshRecycler;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private int skip = 5;
    private boolean loadMore = true;

    public ForumFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_forum, container, false);
        progressDialog = new ProgressDialog(view.getContext());
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab_tambah_forum);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Tambah berita!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), AddForumActivity.class);
                startActivity(intent);
                //startActivityForResult(intent,);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        getForumsList(access_token);

        swipeRefreshRecycler = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshRecycler);
        swipeRefreshRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getForumsList(access_token);
                swipeRefreshRecycler.setRefreshing(false);
            }
        });

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
                    if(itemCount >= 5) {
                        getForumsListScrolled(access_token, skip, itemCount);
                    }
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

    private void getForumsList(String accessToken){
        skip = 5;
        loadMore = true;
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(getContext())){
            Call<ForumsListPOJO> callForumsList = restServiceInterface.forumsList(0,accessToken);
            callForumsList.enqueue(new Callback<ForumsListPOJO>() {
                @Override
                public void onResponse(Call<ForumsListPOJO> call, Response<ForumsListPOJO> response) {

                    ForumsListPOJO forumsListPOJO = response.body();
                    if(forumsListPOJO.getSuccess()){
                    Log.d("FORUMM",forumsListPOJO.getRm());
                    //Fragment
                    datePosts.clear();
                    hashtag.clear();
                    titles.clear();
                    upVotes.clear();
                    downVotes.clear();
                    favorites.clear();
                    forumId.clear();
                    upvoteStatus.clear();
                    downvoteStatus.clear();
                    favoriteStatus.clear();

                    //Adapter
                    ForumRecyclerView.materialTypeList.clear();
                    ForumRecyclerView.datePostList.clear();
                    ForumRecyclerView.hashtagList.clear();
                    ForumRecyclerView.titleList.clear();
                    ForumRecyclerView.forumIdList.clear();
                    ForumRecyclerView.upVoteNumbersList.clear();
                    ForumRecyclerView.downVoteNumbersList.clear();
                    ForumRecyclerView.commentNumbersList.clear();
                    ForumRecyclerView.favoriteNumbersList.clear();
                    ForumRecyclerView.contentIdList.clear();
                    ForumRecyclerView.statusUpvoteList.clear();
                    ForumRecyclerView.statusDownvoteList.clear();
                    ForumRecyclerView.statusFavoriteList.clear();


                    for (int forum = 0;forum<forumsListPOJO.getResults().size();forum++){
                        //datePosts.add(forumsListPOJO.getResults().get(forum).getForum().getCreatedAtFromNow());
                        datePosts.add(forumsListPOJO.getResults().get(forum).getForum().getCreatedAtFromNow());

                        for (int hashtag = 0; hashtag<forumsListPOJO.getResults().get(forum).getForum().getTags().size();hashtag++){
                            String hashTag = forumsListPOJO.getResults().get(forum).getForum().getTags().get(hashtag);
                            if(!hashTag.equals("")){
                                hashtagStringBuilder.append("#" + hashTag + " ");
                            }
                        }

                        hashtag.add(hashtagStringBuilder.toString());
                        titles.add(forumsListPOJO.getResults().get(forum).getForum().getTitle());
                        upVotes.add(forumsListPOJO.getResults().get(forum).getForum().getUpvote());
                        downVotes.add(forumsListPOJO.getResults().get(forum).getForum().getDownvote());
                        comments.add(forumsListPOJO.getResults().get(forum).getForum().getComment());
                        favorites.add(forumsListPOJO.getResults().get(forum).getForum().getFavorite());
                        forumId.add(forumsListPOJO.getResults().get(forum).getForum().getId());


                        upvoteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getUpvoted());
                        downvoteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getDownvoted());
                        favoriteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getFavorited());
                        //Clear String hashtag Builder
                        hashtagStringBuilder = new StringBuilder();
                    }

                    mAdapter = new ForumRecyclerView(datePosts,forumId,hashtag,titles,
                            favorites,upVotes,downVotes,comments
                            ,forumId,upvoteStatus,downvoteStatus,favoriteStatus,
                            getActivity());
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                    }else{
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();

                        Toast.makeText(getActivity().getApplicationContext(), forumsListPOJO.getRm(), Toast.LENGTH_SHORT).show();

                        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(LoginActivity.SessionPengguna, getActivity().getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }

                }

                @Override
                public void onFailure(Call<ForumsListPOJO> call, Throwable t) {

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

    private void getForumsListScrolled(String accessToken,final int skipParam, final int itemCount){
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(getContext())){
            if (loadMore) {

            Call<ForumsListPOJO> callForumsList = restServiceInterface.forumsList(skipParam,accessToken);
            callForumsList.enqueue(new Callback<ForumsListPOJO>() {
                @Override
                public void onResponse(Call<ForumsListPOJO> call, Response<ForumsListPOJO> response) {
                    //Fragment
                    /*datePosts.clear();
                    hashtag.clear();
                    titles.clear();
                    upVotes.clear();
                    downVotes.clear();
                    favorites.clear();
                    forumId.clear();*/

                    //Adapter
                    /*ForumRecyclerView.materialTypeList.clear();
                    ForumRecyclerView.datePostList.clear();
                    ForumRecyclerView.hashtagList.clear();
                    ForumRecyclerView.titleList.clear();
                    ForumRecyclerView.forumIdList.clear();
                    ForumRecyclerView.upVoteNumbersList.clear();
                    ForumRecyclerView.downVoteNumbersList.clear();
                    ForumRecyclerView.commentNumbersList.clear();
                    ForumRecyclerView.favoriteNumbersList.clear();*/

                    ForumsListPOJO forumsListPOJO = response.body();

                    if(forumsListPOJO.getResults().size() == 0){
                        loadMore = false;
                    }else {

                        if (forumsListPOJO.getSuccess()) {
                            for (int forum = 0; forum < forumsListPOJO.getResults().size(); forum++) {
                                datePosts.add(forumsListPOJO.getResults().get(forum).getForum().getCreatedAtFromNow());

                                for (int hashtag = 0; hashtag < forumsListPOJO.getResults().get(forum).getForum().getTags().size(); hashtag++) {
                                    hashtagStringBuilder.append("#" + forumsListPOJO.getResults().get(forum).getForum().getTags().get(hashtag) + " ");
                                }

                                hashtag.add(hashtagStringBuilder.toString());
                                titles.add(forumsListPOJO.getResults().get(forum).getForum().getTitle());
                                upVotes.add(forumsListPOJO.getResults().get(forum).getForum().getUpvote());
                                downVotes.add(forumsListPOJO.getResults().get(forum).getForum().getDownvote());
                                comments.add(forumsListPOJO.getResults().get(forum).getForum().getComment());
                                favorites.add(forumsListPOJO.getResults().get(forum).getForum().getFavorite());
                                forumId.add(forumsListPOJO.getResults().get(forum).getForum().getId());


                                upvoteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getUpvoted());
                                downvoteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getDownvoted());
                                favoriteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getFavorited());
                                //Clear String hashtag Builder
                                hashtagStringBuilder = new StringBuilder();
                            }

                            mAdapter = new ForumRecyclerView(datePosts, forumId, hashtag, titles, favorites,
                                    upVotes, downVotes, comments, forumId, upvoteStatus, downvoteStatus,
                                    favoriteStatus, getActivity());
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(mAdapter);

                            mRecyclerView.scrollToPosition(itemCount - 1);

                            skip = skip + 5;
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), forumsListPOJO.getRm(), Toast.LENGTH_SHORT).show();

                            sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(LoginActivity.SessionPengguna, getActivity().getApplicationContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.commit();

                            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }


                    }

                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ForumsListPOJO> call, Throwable t) {

                }
            });
            }else {
                progressDialog.setProgress(100);
                progressDialog.dismiss();
            }
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

    private void searchForumsList(String query,String accessToken, Context context){
        skip = 5;
        progressDialog.setMessage(getResources().getString(R.string.mohon_tunggu_label));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        if(RestServiceClass.isNetworkAvailable(context)){
            restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
            Call<ForumsListPOJO> callForumSearch = restServiceInterface.searchForum(query,accessToken);
            callForumSearch.enqueue(new Callback<ForumsListPOJO>() {
                @Override
                public void onResponse(Call<ForumsListPOJO> call, Response<ForumsListPOJO> response) {

                    ForumsListPOJO forumsListPOJO = response.body();

                    if (forumsListPOJO.getSuccess()){

                        //Fragment
                        datePosts.clear();
                        hashtag.clear();
                        titles.clear();
                        upVotes.clear();
                        downVotes.clear();
                        favorites.clear();
                        forumId.clear();
                        upvoteStatus.clear();
                        downvoteStatus.clear();
                        favoriteStatus.clear();

                        //Adapter
                        ForumRecyclerView.materialTypeList.clear();
                        ForumRecyclerView.datePostList.clear();
                        ForumRecyclerView.hashtagList.clear();
                        ForumRecyclerView.titleList.clear();
                        ForumRecyclerView.forumIdList.clear();
                        ForumRecyclerView.upVoteNumbersList.clear();
                        ForumRecyclerView.downVoteNumbersList.clear();
                        ForumRecyclerView.commentNumbersList.clear();
                        ForumRecyclerView.favoriteNumbersList.clear();
                        ForumRecyclerView.contentIdList.clear();
                        ForumRecyclerView.statusUpvoteList.clear();
                        ForumRecyclerView.statusDownvoteList.clear();
                        ForumRecyclerView.statusFavoriteList.clear();

                        Log.d("PJNG KEMBALIAN",Integer.toString(forumsListPOJO.getResults().size()));
                        for (int forum = 0;forum<forumsListPOJO.getResults().size();forum++){
                            //datePosts.add(forumsListPOJO.getResults().get(forum).getForum().getCreatedAtFromNow());

                            for (int hashtag = 0; hashtag<forumsListPOJO.getResults().get(forum).getForum().getTags().size();hashtag++){
                                hashtagStringBuilder.append("#"+forumsListPOJO.getResults().get(forum).getForum().getTags().get(hashtag)+" ");
                            }

                            hashtag.add(hashtagStringBuilder.toString());
                            titles.add(forumsListPOJO.getResults().get(forum).getForum().getTitle());
                            upVotes.add(forumsListPOJO.getResults().get(forum).getForum().getUpvote());
                            downVotes.add(forumsListPOJO.getResults().get(forum).getForum().getDownvote());
                            comments.add(forumsListPOJO.getResults().get(forum).getForum().getComment());
                            favorites.add(forumsListPOJO.getResults().get(forum).getForum().getFavorite());
                            forumId.add(forumsListPOJO.getResults().get(forum).getForum().getId());


                            upvoteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getUpvoted());
                            downvoteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getDownvoted());
                            favoriteStatus.add(forumsListPOJO.getResults().get(forum).getForum().getFavorited());
                            //Clear String hashtag Builder
                            hashtagStringBuilder = new StringBuilder();
                        }

                        mAdapter = new ForumRecyclerView(datePosts,forumId,hashtag,titles,
                                favorites,upVotes,downVotes,comments,forumId,upvoteStatus,
                                downvoteStatus,favoriteStatus,getActivity());
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mAdapter);

                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), forumsListPOJO.getRm(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ForumsListPOJO> call, Throwable t) {

                }
            });
        }else{
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forumssearchmenu,menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Query pencarian materi berdasarkan teks
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(LoginActivity.SessionPengguna, Context.MODE_PRIVATE);
                final String access_token = sharedPreferences.getString("accessToken","abcde");
                searchForumsList(query,access_token,getContext());
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void popUpMoreMenu(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popupslidingtab,popupMenu.getMenu());
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
                    case R.id.pop_up_profile_user:
                        Intent intentProfileUser = new Intent(getContext(), ProfileUserActivity.class);
                        startActivity(intentProfileUser);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
