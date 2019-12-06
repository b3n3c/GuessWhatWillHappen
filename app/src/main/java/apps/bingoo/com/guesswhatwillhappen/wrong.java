package apps.bingoo.com.guesswhatwillhappen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class wrong extends AppCompatActivity {
    public Button mRetry, mMenu;
    public String vid_start;
    public String vid_bg;
    public String option_1;
    public String option_2;
    public String option_3;
    public String option_4;
    public String vid_end;
    public String right_answer;
    ArrayList<String> mData;
    public int score, b;
    TextView mText;
    public static final String SHARED_PREFS_HIGHSCORE = "sharedPrefsHighScore";
    private InterstitialAd mInterstitialAd;
    public static final String SHARED_PREFS_ADS = "sharedPrefsHighScore";
    boolean bought;
    ArrayList<Integer> mSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);
        mText = (TextView) findViewById(R.id.textView3);
        mRetry = (Button) findViewById(R.id.retry);
        mMenu = (Button) findViewById(R.id.tomenu);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_ADS, MODE_PRIVATE);
        bought = sharedPreferences.getBoolean("bought", false);
        mSeen = new ArrayList<Integer>();
        if (!bought){
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();
        }
        mData = new ArrayList<String>();
        mData = getIntent().getExtras().getStringArrayList("database");
        b = getIntent().getExtras().getInt("c",1);
        score = getIntent().getExtras().getInt("score", 0);
        mText.setText("Your score: " + Integer.toString(score));
        int c = GetDataBase.rv(mData.size()-1, mSeen);
        mSeen.add(c);
        vid_start = mData.get(c);
        vid_bg = mData.get(c+1);
        option_1 = mData.get(c+2);
        option_2 = mData.get(c+3);
        option_3 = mData.get(c+4);
        option_4 = mData.get(c+5);
        vid_end = mData.get(c+6);
        right_answer = mData.get(c+7);



        SharedPreferences sharedPreferences_1 = getSharedPreferences(SHARED_PREFS_HIGHSCORE, MODE_PRIVATE);
        SharedPreferences.Editor editor_1 = sharedPreferences_1.edit();
        int highscore = sharedPreferences_1.getInt("score", 0);
        if (score > highscore){
            editor_1.putInt("score", score);
            editor_1.apply();
        }

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(wrong.this, Main.class);
                intent.putExtra("vid_start", vid_start);
                intent.putExtra("vid_bg",vid_bg);
                intent.putExtra("option_1",option_1);
                intent.putExtra("option_2",option_2);
                intent.putExtra("option_3",option_3);
                intent.putExtra("option_4",option_4);
                intent.putExtra("vid_end",vid_end);
                intent.putExtra("vid_toPlay",1);
                intent.putExtra("right_answer",right_answer);
                intent.putExtra("database", mData);
                intent.putExtra("seen", mSeen);
                intent.putExtra("score", 0);
                startActivity(intent);
                finish();
            }
        });

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wrong.this, Menu.class);
                intent.putExtra("database", mData);
                startActivity(intent);
                finish();
            }
        });
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
                        Intent intent = new Intent(wrong.this, Menu.class);
                        intent.putExtra("database", mData);
                        startActivity(intent);
                        finish();
                    }

                }).create().show();

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mRetry.setEnabled(true);
                mMenu.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                mRetry.setEnabled(true);
                mMenu.setEnabled(true);
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();

        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        mRetry.setEnabled(false);
        mMenu.setEnabled(false);
        Bundle extras = new Bundle();
        extras.putString("max_ad_content_rating", "G");
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }
}
