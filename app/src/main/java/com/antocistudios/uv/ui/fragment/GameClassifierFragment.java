package com.antocistudios.uv.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.antocistudios.business.entity.VideoGame;
import com.antocistudios.moviecatalog.R;
import com.antocistudios.uv.UpcomingVideogames;
import com.antocistudios.uv.presenter.GameClassifierPresenter;
import com.antocistudios.uv.presenter.event.DisplayGameList;
import com.antocistudios.uv.ui.activity.DetailActivity;
import com.antocistudios.uv.ui.base.RecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Adrian Antoci.
 */
public class GameClassifierFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener {

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;

    @Inject
    public GameClassifierPresenter mPresenter;

    private RecyclerViewAdapter mAdapter;
    private int mPlatform = -1;

    public static GameClassifierFragment newInstance(int platform) {
        GameClassifierFragment f = new GameClassifierFragment();
        Bundle args = new Bundle();
        args.putInt("platform", platform);
        f.setArguments(args);
        return f;
    }

    public GameClassifierFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
        ((UpcomingVideogames) getActivity().getApplication()).inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        mPresenter.onFragmentViewDetached();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classifier_layout, container, false);

        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new RecyclerViewAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        Bundle args = getArguments();
        mPlatform = args.getInt("platform", -1);

        mPresenter.onFragmentViewAttached(mPlatform);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onItemClick(View view, VideoGame viewModel) {
        DetailActivity.navigate((AppCompatActivity) getActivity(), view.findViewById(R.id.imageView), viewModel);
    }

    public void onEvent(DisplayGameList event){
        if (mPlatform == event.getPlatform() && mAdapter.getItemCount() == 0){

            mAdapter.add(event.getItemList());
        }
        mProgressBar.setVisibility(View.GONE);
    }
}