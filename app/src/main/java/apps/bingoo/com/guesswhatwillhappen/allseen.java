package apps.bingoo.com.guesswhatwillhappen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class allseen extends AppCompatActivity {
    public static final String SHARED_PREFS_HIGHSCORE = "sharedPrefsHighScore";
    public int score;
    ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allseen);
        mData = getIntent().getExtras().getStringArrayList("database");
        score = getIntent().getExtras().getInt("score", 0);
        SharedPreferences sharedPreferences_1 = getSharedPreferences(SHARED_PREFS_HIGHSCORE, MODE_PRIVATE);
        SharedPreferences.Editor editor_1 = sharedPreferences_1.edit();
        int highscore = sharedPreferences_1.getInt("score", 0);
        if (score > highscore){
            editor_1.putInt("score", score);
            editor_1.apply();
        }
    }

    public void menufromallseen(View view) {
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("database", mData);
        startActivity(intent);
        finish();
    }
}
