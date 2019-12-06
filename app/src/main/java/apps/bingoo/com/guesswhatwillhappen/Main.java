package apps.bingoo.com.guesswhatwillhappen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class Main extends YouTubeBaseActivity implements YouTubePlayer.PlayerStateChangeListener,  YouTubePlayer.OnInitializedListener{

    YouTubePlayerView mYoutubePlayerView;
    private YouTubePlayer player;
    public String vid_start;
    public String vid_bg;
    public String option_1;
    public String option_2;
    public String option_3;
    public String option_4;
    public String vid_end;
    public int vid_toPlay;
    public String vid;
    public String right_answer;
    public int check = 0, score, c;
    ArrayList<String> mData;
    ArrayList<Integer> mSeen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mYoutubePlayerView = (YouTubePlayerView) findViewById(R.id.view2);
        mYoutubePlayerView.initialize(YouTubeConfig.getApiKey(), this);
        vid_toPlay = getIntent().getExtras().getInt("vid_toPlay", 1);
        c = getIntent().getExtras().getInt("c",0);
        if (vid_toPlay == 1){
            vid_start = getIntent().getExtras().getString("vid_start","");
            vid_bg = getIntent().getExtras().getString("vid_bg", "");
            option_1 = getIntent().getExtras().getString("option_1", "");
            option_2 = getIntent().getExtras().getString("option_2", "");
            option_3 = getIntent().getExtras().getString("option_3", "");
            option_4 = getIntent().getExtras().getString("option_4", "");
            vid_end = getIntent().getExtras().getString("vid_end", "");
            right_answer = getIntent().getExtras().getString("right_answer", "");
            vid = vid_start;
        }
        else{
            right_answer = getIntent().getExtras().getString("right_answer", "");
            vid_end = getIntent().getExtras().getString("vid_end", "");
            vid = vid_end;
            check = getIntent().getExtras().getInt("check", 1);
        }
        mData = getIntent().getExtras().getStringArrayList("database");
        score = getIntent().getExtras().getInt("score", 0);
        mSeen = getIntent().getExtras().getIntegerArrayList("seen");


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        player = youTubePlayer;
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.cueVideo(vid);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public void onLoading() {
        player.setFullscreen(true);
    }

    @Override
    public void onLoaded(String s) {
        player.play();
    }

    @Override
    public void onAdStarted() {
        player.setFullscreen(true);
    }

    @Override
    public void onVideoStarted() {
        player.setFullscreen(true);
    }

    @Override
    public void onVideoEnded() {
        if (check == 0){
            Intent intent;
            intent = new Intent(this, Guess.class);
            intent.putExtra("vid_start", vid_start);
            intent.putExtra("vid_bg",vid_bg);
            intent.putExtra("option_1",option_1);
            intent.putExtra("option_2",option_2);
            intent.putExtra("option_3",option_3);
            intent.putExtra("option_4",option_4);
            intent.putExtra("vid_end",vid_end);
            intent.putExtra("right_answer",right_answer);
            intent.putExtra("database", mData);
            intent.putExtra("seen", mSeen);
            intent.putExtra("score", score);
            intent.putExtra("c", c);
            startActivity(intent);
            finish();
        }
        if (check == 1){
            Intent intent;
            intent = new Intent(this, right.class);
            intent.putExtra("score", score);
            intent.putExtra("database", mData);
            intent.putExtra("seen", mSeen);
            intent.putExtra("c", c);
            startActivity(intent);
            finish();
        }
        if (check == 2){
            Intent intent;
            intent = new Intent(this, wrong.class);
            intent.putExtra("score", score);
            intent.putExtra("database", mData);
            intent.putExtra("c", c);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public  void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Quit")
                .setMessage("Go back to menu?")

                .setNegativeButton("No", null)


                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Main.this, Menu.class);
                        intent.putExtra("database", mData);
                        startActivity(intent);
                        finish();
                    }

                }).create().show();

    }


}
