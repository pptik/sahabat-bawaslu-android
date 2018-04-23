package id.pptik.ilham.sahabatbawaslu.features.forum;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceClass;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ForumsListPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ilham on 15/03/18.
 */

public class ForumFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestServiceInterface restServiceInterface;
    private List<String> hashtag = new ArrayList<String>();
    private List<String> datePosts = new ArrayList<String>();
    private List<String> descs = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    private List<Integer> favorites = new ArrayList<Integer>();
    private List<Integer> upVotes = new ArrayList<Integer>();
    private List<Integer> downVotes = new ArrayList<Integer>();
    private List<Integer> comments = new ArrayList<Integer>();
    private StringBuilder hashtagStringBuilder = new StringBuilder();

    public ForumFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        restServiceInterface = RestServiceClass.getClient().create(RestServiceInterface.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        final String access_token = sharedPreferences.getString("accessToken","abcde");

        Call<ForumsListPOJO> callForumsList = restServiceInterface.forumsList(0,access_token);
        callForumsList.enqueue(new Callback<ForumsListPOJO>() {
            @Override
            public void onResponse(Call<ForumsListPOJO> call, Response<ForumsListPOJO> response) {
                ForumsListPOJO forumsListPOJO = response.body();
                for (int forum = 0;forum<forumsListPOJO.getResults().size();forum++){
                    datePosts.add(forumsListPOJO.getResults().get(forum).getCreatedAtFromNow());
                    for (int hashtag = 0; hashtag<forumsListPOJO.getResults().get(forum).getTags().size();hashtag++){
                        hashtagStringBuilder.append("#"+forumsListPOJO.getResults().get(forum).getTags()+" ");
                    }
                    hashtag.add(hashtagStringBuilder.toString());
                    titles.add(forumsListPOJO.getResults().get(forum).getTitle());
                    upVotes.add(forumsListPOJO.getResults().get(forum).getUpvote());
                    downVotes.add(forumsListPOJO.getResults().get(forum).getDownvote());
                    comments.add(forumsListPOJO.getResults().get(forum).getComment());
                    favorites.add(forumsListPOJO.getResults().get(forum).getFavorite());

                    //Clear String hashtag Builder
                    hashtagStringBuilder = new StringBuilder();
                }

                mAdapter = new ForumRecyclerView(datePosts,descs,titles,favorites,upVotes,downVotes,comments);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ForumsListPOJO> call, Throwable t) {

            }
        });

        return view;

    }
}
