package com.parse.ummalibu.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ummalibu.R;
import com.parse.ummalibu.objects.AudioPlayer;
import com.parse.ummalibu.objects.Talk;
import com.parse.ummalibu.objects.TumblrTalk;

/**
 * Created by rjaylward on 10/15/15
 */
public class AudioPlayerView extends RelativeLayout {

    private ImageView mImageView;
    private Button mPlayButton;
    private SeekBar mSeekBar;
    private ProgressBar mLoadingBar;
    private TextView mTrackTitleView;
    private AudioPlayer mAudioPlayer;
    private TextView mCountDown;
    private TextView mCountUp;

    public AudioPlayerView(Context context) {
        super(context);
        inflateLayout();
    }

    public AudioPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public AudioPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.new_audio_player_layout, this, true);
//        LayoutInflater.from(getContext()).inflate(R.layout.audio_player_layout, this, true);
//
//        int height = Math.round(getResources().getDimension(R.dimen.media_player_height));
//        setLayoutParams(new RelativeLayout.LayoutParams(height, height));

        mImageView = (ImageView) findViewById(R.id.media_player_image);
        mPlayButton = (Button) findViewById(R.id.media_player_play_button);
        mSeekBar = (SeekBar) findViewById(R.id.media_player_seek_bar);
        mLoadingBar = (ProgressBar) findViewById(R.id.media_player_progress_bar);
        mTrackTitleView = (TextView) findViewById(R.id.media_player_title_text_view);
        mCountDown = (TextView) findViewById(R.id.media_player_count_down);
        mCountUp = (TextView) findViewById(R.id.media_player_count_up);

        mAudioPlayer = new AudioPlayer();
    }

    public void changeSourceUrl(String url) {
        mAudioPlayer.changeSourceLink(url);
    }

    public void setUpAudioPlayer(String sourceLinkUrl, String trackTile, String imageUrl) {
        if(imageUrl != null && !imageUrl.equals("") && mImageView != null)
            Glide.with(getContext()).load(imageUrl).into(mImageView);
        mAudioPlayer.setUpPlayer(mPlayButton, mSeekBar, mTrackTitleView, trackTile, mLoadingBar,
                mCountDown, mCountUp);
        mAudioPlayer.setSourceLink(sourceLinkUrl);
        mTrackTitleView.setText(trackTile);
    }

    public void setUpAudioPlayer(TumblrTalk talk) {
        if(talk.getImageUrl() != null && !talk.getImageUrl().equals("") && mImageView != null)
            Glide.with(getContext()).load(talk.getImageUrl()).into(mImageView);
        mAudioPlayer.setUpPlayer(mPlayButton, mSeekBar, mTrackTitleView, talk.getTitle(), mLoadingBar,
                mCountDown, mCountUp);
        mAudioPlayer.setSourceLink(talk.getAudioUrl());
        mTrackTitleView.setText(talk.getTitle());
    }

    public void changeTalk(Talk talk) {
        if(talk.getImageUrl() != null && !talk.getImageUrl().equals("") && mImageView != null)
            Glide.with(getContext()).load(talk.getImageUrl()).into(mImageView);
        mAudioPlayer.changeSourceLink(talk.getAudioUrl());
        mTrackTitleView.setText(talk.getTitle());
    }

    public void changeTalk(TumblrTalk talk) {
        if(talk.getImageUrl() != null && !talk.getImageUrl().equals(""))
            Glide.with(getContext()).load(talk.getImageUrl()).into(mImageView);
        mAudioPlayer.changeSourceLink(talk.getAudioUrl());
        mTrackTitleView.setText(talk.getTitle());
    }

    public void play() {
        mAudioPlayer.play();
    }

    public void pause() {
        mAudioPlayer.pause();
    }

    public void release() {
        if(mAudioPlayer != null)
            mAudioPlayer.releasePlayer();
    }
}
