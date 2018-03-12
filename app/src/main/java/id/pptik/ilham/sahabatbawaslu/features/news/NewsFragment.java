package id.pptik.ilham.sahabatbawaslu.features.news;

import android.app.ProgressDialog;
import android.content.Context;
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

/**
 * Created by Ilham on 12/03/18.
 */

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<String> dataSetJudulMateri = new ArrayList<>();
    List<String> dataSetCoverMateri = new ArrayList<>();
    List<Integer> dataSetSubJudulMateri = new ArrayList<>();

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

        mAdapter = new MaterialsRecyclerView(dataSetJudulMateri, dataSetCoverMateri,dataSetSubJudulMateri);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }


}
