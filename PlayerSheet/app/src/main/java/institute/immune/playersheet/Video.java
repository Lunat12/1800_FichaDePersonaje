package institute.immune.playersheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class Video extends AppCompatActivity {

    //region GLOBAL VARIABLES
    private SimpleExoPlayer player;

    private Player.EventListener playerStateChangeListener;

    private PlayerView video;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        assignViewElements();
        createListeners();
        loadVideo();
        assignListeners();
    }

    private void loadVideo(){
        MediaItem videoLink = MediaItem.fromUri(Constants.VIDEO);

        player = new SimpleExoPlayer.Builder(getApplicationContext()).build();

        video.setPlayer(player);
        video.setUseController(false);

        player.setMediaItem(videoLink);

        player.prepare();
        player.play();
    }

    private void assignViewElements(){
        video = findViewById(R.id.video);
    }

    private void createListeners(){
        playerStateChangeListener =new Player.EventListener() {

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {
                    case ExoPlayer.STATE_ENDED:
                        finish();
                        startActivity(new Intent(Video.this, MainActivity.class));
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void assignListeners(){
        player.addListener(playerStateChangeListener);
    }

}