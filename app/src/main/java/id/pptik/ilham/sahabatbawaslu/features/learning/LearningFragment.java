package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.learning.LearningRecyclerView;
import id.pptik.ilham.sahabatbawaslu.features.news.MaterialsRecyclerView;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import retrofit2.Call;
import id.pptik.ilham.sahabatbawaslu.networks.RestServiceInterface;

/**
 * Created by Ilham on 15/03/18.
 */

public class LearningFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RestServiceInterface restServiceInterface;

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

        Call<MaterialsListPOJO> callMaterialsList = restServiceInterface.materialsList(0);

        return view;

    }
}
