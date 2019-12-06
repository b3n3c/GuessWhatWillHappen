package apps.bingoo.com.guesswhatwillhappen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Splash extends AppCompatActivity {
    ArrayList<String> mData;
    ImageView mLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLogo = (ImageView) findViewById(R.id.logo);
        Animation anima = AnimationUtils.loadAnimation(this, R.anim.splash_transition);
        mLogo.startAnimation(anima);

        if(!isConnected(Splash.this)){
            Intent intent;
            intent = new Intent(Splash.this, nointernet.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        mData = new ArrayList<>();
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(5000);
                    GetDataBase foo = new GetDataBase();
                    new Thread(foo).start();
                    mData = foo.downloadData();
                    findowloand();
                    if (mData.size() != Integer.parseInt(mData.get(0))) sleep(2000);
                } catch (Exception e) {

                } finally {
                    Intent intent;
                    intent = new Intent(Splash.this, Menu.class);
                    intent.putExtra("database", mData);
                    startActivity(intent);
                    finish();
                }
            }
        };
        welcomeThread.start();

    }

    private void findowloand() {
        int c = 0;
        while (mData.isEmpty()) c += 1;
        c = 0;
        while (c == 0){
            try {
                mData.get(0);
                c = 1;
            }
            catch(Exception e) {
                Log.e("", "ROME parse error: " + e.toString());
                c = 0;
            }
            catch(Error e2) {
                Log.e("", "ROME parse error2: " + e2.toString());
                c = 0;
            }
        }
        while(mData.size() != Integer.parseInt(mData.get(0))) c += 1;


    }


    public boolean isConnected(Context context) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }





}
