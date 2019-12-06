package apps.bingoo.com.guesswhatwillhappen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Guess extends AppCompatActivity {
    public ImageView mBackground;
    public String vid_bg;
    public String option_1;
    public String option_2;
    public String option_3;
    public String option_4;
    public String vid_end;
    public Button mOpt_1;
    public Button mOpt_2;
    public Button mOpt_3;
    public Button mOpt_4;
    public String right_answer;
    public int check, score, c;
    ArrayList<String> mData;
    private InterstitialAd mInterstitialAd;
    public static final String SHARED_PREFS_ADS = "sharedPrefsHighScore";
    boolean bought;
    ArrayList<Integer> mSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        mOpt_1 = (Button) findViewById(R.id.opt_1);
        mOpt_2 = (Button) findViewById(R.id.opt_2);
        mOpt_3 = (Button) findViewById(R.id.opt_3);
        mOpt_4 = (Button) findViewById(R.id.opt_4);
        mSeen = getIntent().getExtras().getIntegerArrayList("seen");
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_ADS, MODE_PRIVATE);
        bought = sharedPreferences.getBoolean("bought", false);
        /*if (!bought){
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();
        }*/
        mBackground = (ImageView) findViewById(R.id.background);
        vid_bg = getIntent().getExtras().getString("vid_bg", "");
        option_1 = getIntent().getExtras().getString("option_1", "");
        option_2 = getIntent().getExtras().getString("option_2", "");
        option_3 = getIntent().getExtras().getString("option_3", "");
        option_4 = getIntent().getExtras().getString("option_4", "");
        vid_end = getIntent().getExtras().getString("vid_end", "");
        right_answer = getIntent().getExtras().getString("right_answer", "");
        mData = getIntent().getExtras().getStringArrayList("database");
        score = getIntent().getExtras().getInt("score", 0);
        c = getIntent().getExtras().getInt("c",69);
        mOpt_1.setText(option_1);
        mOpt_2.setText(option_2);
        mOpt_3.setText(option_3);
        mOpt_4.setText(option_4);
        Picasso.get().load(vid_bg).into(mBackground);
        mBackground.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        mBackground.setScaleType(ImageView.ScaleType.FIT_XY);

    }


    public void optselected(View view) {
        Button mButton = (Button) findViewById(view.getId());
        String mButtonText = String.valueOf(mButton.getText());
        if (mButtonText.equals(right_answer)){
            check = 1;
        }
        else
            check = 2;
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("vid_toPlay",2);
        intent.putExtra("vid_end",vid_end);
        intent.putExtra("check",check);
        intent.putExtra("database", mData);
        intent.putExtra("score", score);
        intent.putExtra("c", c);
        intent.putExtra("seen", mSeen);
        startActivity(intent);
        finish();


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
                        Intent intent = new Intent(Guess.this, Menu.class);
                        intent.putExtra("database", mData);
                        startActivity(intent);
                        finish();
                    }

                }).create().show();

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*  private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mOpt_1.setEnabled(true);
                mOpt_2.setEnabled(true);
                mOpt_3.setEnabled(true);
                mOpt_4.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                mOpt_1.setEnabled(true);
                mOpt_2.setEnabled(true);
                mOpt_3.setEnabled(true);
                mOpt_4.setEnabled(true);
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
        mOpt_1.setEnabled(false);
        mOpt_2.setEnabled(false);
        mOpt_3.setEnabled(false);
        mOpt_4.setEnabled(false);
        Bundle extras = new Bundle();
        extras.putString("max_ad_content_rating", "G");

        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .setRequestAgent("android_studio:ad_template")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
*/
}
