package eu.dsgsystems.partcquestion1.View;

/**
 * Created by Dimitris on 14/3/2018.
 */

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import eu.dsgsystems.partcquestion1.R;

public class URLVideoActivity extends Activity {

    private int position = 0;
    private MediaController mediaController;
    private VideoView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.url_activity_video);
        video = findViewById(R.id.videoView);
        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(URLVideoActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(video);


            // Set MediaController for VideoView
            video.setMediaController(mediaController);
        }

        try {
            // ID of video file.
            String uri = getIntent().getStringExtra("VIDEO_URI");
            video.setVideoPath(uri);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        video.requestFocus();


        // When the video file ready for playback.
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {


                video.seekTo(position);
                if (position == 0) {
                    video.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(video);
                    }
                });
            }
        });
        // video.start();

    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", video.getCurrentPosition());
        video.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        video.seekTo(position);
    }

}

