package com.parse.ummalibu.objects;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by rjaylward on 10/15/15
 */
public class AudioPlayer {

    private boolean isPlaying;
    private MediaPlayer mMediaPlayer;
    private boolean initialStage = true;
    private SeekBar mSeekBar;
    private Button mPlayPauseButton;
    private String mSourceLink;
    private TextView mTitle;
    private String mTrackTitle;
    private ProgressBar mProgressSpinner;
    private TextView mCountDown;
    private TextView mCountUp;

    private int mPlayButtonResourceId;
    private int mPauseButtonResourceId;

    private Handler mSeekHandler;

    public void setUpPlayer(Button playPauseButton, SeekBar seekBar, TextView textView, String trackTitle,
                            ProgressBar progressBar, TextView countDown, TextView countUp) {

        mPlayPauseButton = playPauseButton;
        mSeekBar = seekBar;
        mTitle = textView;
        mTrackTitle = trackTitle;
        mProgressSpinner = progressBar;
        mCountDown = countDown;
        mCountUp = countUp;

        mSeekHandler = new Handler();

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(progress);
                }
            }
        });

        mPlayPauseButton.setOnClickListener(mPlayButtonOnClickListener);
    }

    public void setSourceLink(String sourceLink) {
        mSourceLink = sourceLink;
    }

    public void changeSourceLink(String sourceLink) {
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            resetPlayer();
        }

        mSourceLink = sourceLink;
    }

    public void setPlayPauseImage(int playImageId, int pauseImageId) {
        mPauseButtonResourceId = pauseImageId;
        mPlayButtonResourceId = playImageId;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    Runnable run = new Runnable() { @Override public void run() { seekUpdation(); } };

    public void seekUpdation() {
        mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
        double currentTimeMin = Math.floor(mMediaPlayer.getCurrentPosition()/60);
        int totalSecs = Math.round(mMediaPlayer.getCurrentPosition() / 1000) ;

        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        int negSecs = Math.round(mMediaPlayer.getDuration()/1000) - totalSecs;

        int negMinutes = (negSecs % 3600) / 60;
        int negSeconds = negSecs % 60;

        mCountUp.setText(String.format("%d:%02d", minutes, seconds));
        mCountDown.setText(String.format("-%d:%02d", negMinutes, negSeconds));
        mSeekHandler.postDelayed(run, 1000);
    }

    private View.OnClickListener mPlayButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isPlaying)
                play();
            else
                pause();
        }
    };

    public void play() {
        //mPlayPauseButton.setBackgroundResource(mPauseButtonResourceId);
        mPlayPauseButton.setText("II");
        if (initialStage) {
            new Player()
                    .execute(mSourceLink);

        }
        else {
            if (!mMediaPlayer.isPlaying())
                mMediaPlayer.start();
            seekUpdation();
        }
        isPlaying = true;
    }

    public void pause() {
        mPlayPauseButton.setText("\u25B6");
        //mPlayPauseButton.setBackgroundResource(mPlayButtonResourceId);
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
        mSeekHandler.removeCallbacks(run);
        isPlaying = false;
    }

    public void resetPlayer() {
        if (mMediaPlayer != null) {
            mSeekHandler.removeCallbacks(run);
            mMediaPlayer.reset();
            mSeekBar.setProgress(0);
            mPlayPauseButton.setText("\u25B6");
            isPlaying = false;
            initialStage = true;
        }
    }

    public void releasePlayer() {
        if (mMediaPlayer != null) {
            resetPlayer();
            mMediaPlayer.release();
            initialStage = true;
        }
    }

// * preparing mediaplayer will take sometime to buffer the content so prepare it inside the background thread and starting it on UI thread.
// * @author piyush
// *

    class Player extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean prepared;
            try {
                mMediaPlayer.setDataSource(params[0]);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //initialStage = false;
                        isPlaying = false;
                        mPlayPauseButton.setText("\u25B6");
                        //mPlayPauseButton.setBackgroundResource(mPlayButtonResourceId);
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                });
                mMediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                Log.d("IllegalArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mProgressSpinner.setVisibility(View.GONE);
            mTitle.setText(mTrackTitle);
            Log.d("Prepared", "//" + result);
            seekUpdation();
            mMediaPlayer.start();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            initialStage = false;
        }

        public Player() {
            mProgressSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTitle.setText("Buffering...");
        }
    }

}
