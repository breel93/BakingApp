package com.example.breezil.bakingapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.model.Step;
import com.example.breezil.bakingapp.view_model.DetailViewModel;
import com.example.breezil.bakingapp.databinding.FragmentStepDetailBinding;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment  {

    private static final String STEP_LIST = "list";



    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SimpleExoPlayer simpleExoPlayer;
    private DetailViewModel viewModel;

    boolean isTablet;


    private FragmentStepDetailBinding binding;
    int position;

    int totalSteps;

    private boolean AutoPlay = false;

    Step step;
    private static final String STEP = "step";



    public StepDetailFragment() {
        // Required empty public constructor
    }


    public static StepDetailFragment theStep(ArrayList<Step> stepList){
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEP_LIST,stepList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_step_detail, container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getContext().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE
                && getContext().getResources().getConfiguration().smallestScreenWidthDp < 600) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(DetailViewModel.class);
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        if(isTablet){

            viewModel.setStep(getStep());
        }

        position = viewModel.getStep().getValue().getId();

        viewModel.setStepsValue(getStepList());

        totalSteps = viewModel.getSteps().getValue().size();

        startPlaying(position);


        AutoPlay = true;


        binding.nextFab.setOnClickListener(v -> {

            position = position + 1;
            startPlaying(position);


        });

        binding.previousFab.setOnClickListener(v -> {

            position = position - 1;
            startPlaying(position);

        });
        binding.doneFab.setOnClickListener(v -> {
            getActivity().finish();
        });



    }

    private void startPlaying(int position) {


        if(position + 1 == totalSteps){
            binding.nextFab.setVisibility(View.GONE);
        }else{
            binding.nextFab.setVisibility(View.VISIBLE);
        }

        if(position == 0){
            binding.previousFab.setVisibility(View.GONE);
        }else {
            binding.previousFab.setVisibility(View.VISIBLE);
        }

        if(position + 1 == totalSteps){
            binding.doneFab.setVisibility(View.VISIBLE);
        }else{
            binding.doneFab.setVisibility(View.GONE);
        }



        step = viewModel.getSteps().getValue().get(position);

        binding.shortDescription.setText(step.getShortDescription());
        binding.longDescription.setText(step.getDescription());
        String videoUrl = step.getVideoURL();
        if (videoUrl.isEmpty()) {
            videoUrl = step.getThumbnailURL();
        }

        if (!videoUrl.isEmpty()) {
            initializePlayer(Uri.parse(videoUrl));
        }

    }


    private void initializePlayer(Uri uri) {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector selector = new DefaultTrackSelector(factory);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),selector);

        binding.exoPlayer.setPlayer(simpleExoPlayer);

        DataSource.Factory sourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getString(R.string.app_name)));

        MediaSource audio = new ExtractorMediaSource.Factory(sourceFactory)
                .createMediaSource(uri);

        simpleExoPlayer.prepare(audio);
        simpleExoPlayer.setPlayWhenReady(AutoPlay);

    }
    private void releasePlayer(){
        if(simpleExoPlayer != null){
            AutoPlay = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Nullable
    private Step getStep() {

        assert getArguments() != null;
        return getArguments().getParcelable(STEP);
    }


    @Nullable
    private List<Step> getStepList(){
        assert getArguments() != null;
        return getArguments().getParcelableArrayList(STEP_LIST);
    }

}
