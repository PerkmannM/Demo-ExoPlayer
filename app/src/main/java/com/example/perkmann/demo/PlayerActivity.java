package com.example.perkmann.demo;

/**
 * Created by Perkmann on 06.10.16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class PlayerActivity extends Activity implements ExoPlayer.EventListener, SimpleExoPlayer.VideoListener, SurfaceHolder.Callback {

    public static String ARG_URL = "VideoUrl";

    private SurfaceView mSurfaceView;
    private SimpleExoPlayer mPlayer;
    private AspectRatioFrameLayout mAspectRatioLayout;

    private PlaybackControlView mPlaybackControlView;

    public static Intent build(Context context, String videoUrl) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(PlayerActivity.ARG_URL, videoUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        // get data
        String videoUrl = getIntent().getStringExtra(ARG_URL);

        // initialize view
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceView.getHolder().addCallback(this);
        mAspectRatioLayout = (AspectRatioFrameLayout) findViewById(R.id.aspect_ratio_layout);

        // initialize player
        Handler handler = new Handler();
        ExtractorsFactory extractor = new DefaultExtractorsFactory();
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ExoPlayer Demo");

        mPlayer = ExoPlayerFactory.newSimpleInstance(
                this,
                new DefaultTrackSelector(handler),
                new DefaultLoadControl()
        );
        mPlaybackControlView = (PlaybackControlView) findViewById(R.id.player_view);
        mPlaybackControlView.requestFocus();
        mPlaybackControlView.setPlayer(mPlayer);

        // initialize source
        MediaSource videoSource = new ExtractorMediaSource(
                Uri.parse(videoUrl),
                dataSourceFactory,
                extractor,
                null,
                null
        );
        mPlayer.prepare(videoSource);
        mPlayer.setPlayWhenReady(true);
        mPlayer.addListener(this);
        mPlayer.setVideoListener(this);
    }

    // SimpleExoPlayer.VideoListener

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        mAspectRatioLayout.setAspectRatio(pixelWidthHeightRatio);
    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }

    // ExoPlayer.EventListener
    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
    // SurfaceHolder.Callback

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (mPlayer != null) {
            mPlayer.setVideoSurfaceHolder(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mPlayer != null) {
            mPlayer.setVideoSurfaceHolder(null);
        }
    }

    // Activity Lifecycle

    @Override
    protected void onPause() {
        super.onPause();

        mPlayer.setPlayWhenReady(false);
    }

    protected void onStop(){
        super.onStop();

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}

