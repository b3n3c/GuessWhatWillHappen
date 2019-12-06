package apps.bingoo.com.guesswhatwillhappen;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
//public class Menu extends AppCompatActivity implements BillingProcessor.IBillingHandler

public class Menu extends AppCompatActivity{
    public VideoView mVideo;
    ArrayList<String> mData;
    ArrayList<Integer> mSeen;
    public String vid_start;
    public String vid_bg;
    public String option_1;
    public String option_2;
    public String option_3;
    public String option_4;
    public String vid_end;
    public String right_answer;
    public static final String SHARED_PREFS_HIGHSCORE = "sharedPrefsHighScore";
    public static final String SHARED_PREFS_ADS = "sharedPrefsHighScore";
    TextView mText;
    public int c;
    Button mStart, mRemove;
    private InterstitialAd mInterstitialAd;
    //BillingProcessor bp;
    boolean bought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnjRsgA3ORN5yTScA9gWkZf8uJX1qhT1wnUK7o5JsDFGZc/+Ep5c8DgRuAzMQl+Y2O9MQkd7DUsuufAYe0HW+W+TpnNmfVNbqQE6/67zAn6ApXGpRK/FuRTRE/2qtRPBchp61RoKhimrb+Yrxk2YrvPv1v+mvHw9cka1SI2C4c3frvZ4gN1jHBJVnJBgmp8Q80MmqsX3NllnDeT5ne5s5Rkfh+m71rG43bUwHUMfgQwxidAaYXnIJ8229FQq9IiRFZL/LdXkMPzsWN3fii9VS8pBh2jtf6/bQXzlK7PVooM7QuNoSnbKTlRJE3AFs2bNOzUFQ1c5pq+sOcgLG6x+UYwIDAQAB", this);
        //mRemove = (Button) findViewById(R.id.adremover);
        mStart = (Button) findViewById(R.id.start);
        mVideo = (VideoView) findViewById(R.id.menuvideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.menu);
        mVideo.setVideoURI(uri);
        mVideo.start();
        mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setScreenOnWhilePlaying(false);
            }
        });
        /*if (!bp.isPurchased("remove_adds")){
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();
        }*/
        //else{
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_ADS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("bought", true);
            editor.apply();
            //mRemove.setVisibility(View.GONE);
        //}
        SharedPreferences sharedPreferences_1 = getSharedPreferences(SHARED_PREFS_HIGHSCORE, MODE_PRIVATE);
        int highscore = sharedPreferences_1.getInt("score", 0);
        mText = (TextView) findViewById(R.id.textView5);
        mText.setText("High Score: " + Integer.toString(highscore));

        mSeen = new ArrayList<Integer>();
        mData = new ArrayList<String>();
        mData = getIntent().getExtras().getStringArrayList("database");
        c = GetDataBase.rv(mData.size()-1, mSeen);
        if (c == -1){
            Intent intent = new Intent(this, allseen.class);
            startActivity(intent);
            finish();
        }
        else{
            mSeen.add(c);
            vid_start = mData.get(c);
            vid_bg = mData.get(c+1);
            option_1 = mData.get(c+2);
            option_2 = mData.get(c+3);
            option_3 = mData.get(c+4);
            option_4 = mData.get(c+5);
            vid_end = mData.get(c+6);
            right_answer = mData.get(c+7);
            //Toast.makeText(this, Integer.toString(mData.size()), Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.menu);
        mVideo.setVideoURI(uri);
        mVideo.start();
        mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideo.stopPlayback();
    }

    public void start(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_ADS, MODE_PRIVATE);
        bought = sharedPreferences.getBoolean("bought", false);
        //if (!bought) showInterstitial();
        //else
        goToNextLevel();
    }

    public void quit(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public  void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Quit")
                .setMessage("Are you sure you want to quit?")

                .setNegativeButton("No", null)


                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }).create().show();

    }


    public void send_gmail(View view) {
        Log.i("Send email", "");

        String[] TO = {"bingooappsreply@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);


        try {
            startActivity(emailIntent);
            //finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Menu.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoInsta(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/bingoo.apps/"));
        startActivity(browserIntent);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /*   private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mStart.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mStart.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        mStart.setEnabled(false);
        Bundle extras = new Bundle();
        extras.putString("max_ad_content_rating", "G");

        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }
    */
    private void goToNextLevel() {
        Intent intent;
        intent = new Intent(this, Main.class);
        intent.putExtra("vid_start", vid_start);
        intent.putExtra("c", c);
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
    /*
    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_ADS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("bought", true);
        editor.apply();
        Toast.makeText(this, "Ads have been removed.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    public void remove_ads(View view) {
        bp.purchase(Menu.this, "remove_adds");
    }
    */
}
