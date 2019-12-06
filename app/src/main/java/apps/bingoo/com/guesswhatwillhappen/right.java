package apps.bingoo.com.guesswhatwillhappen;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class right extends AppCompatActivity {

    public ProgressBar mProgressBar;
    private int mProgressbarStatus = 0;
    Handler mHandler = new Handler();
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
    ArrayList<Integer> mSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        score = getIntent().getExtras().getInt("score");
        score += 1;
        mText = (TextView) findViewById(R.id.textView4);
        mText.setText("Your score: " + Integer.toString(score));
        mSeen = getIntent().getExtras().getIntegerArrayList("seen");
        mData = new ArrayList<String>();
        mData = getIntent().getExtras().getStringArrayList("database");
        b = getIntent().getExtras().getInt("c",66);
        final int c = GetDataBase.rv(mData.size()-1, mSeen);
        if (c == -1){
            Intent intent = new Intent(this, allseen.class);
            intent.putExtra("score", score);
            intent.putExtra("database", mData);
            startActivity(intent);
            finish();
        }
        else{
            b = c;
            mSeen.add(c);
            vid_start = mData.get(c);
            vid_bg = mData.get(c+1);
            option_1 = mData.get(c+2);
            option_2 = mData.get(c+3);
            option_3 = mData.get(c+4);
            option_4 = mData.get(c+5);
            vid_end = mData.get(c+6);
            right_answer = mData.get(c+7);
        }

        mProgressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        if (c != -1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mProgressbarStatus < 100){
                        mProgressbarStatus++;
                        android.os.SystemClock.sleep(50);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mProgressbarStatus);
                            }
                        });
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (c != -1){
                                Intent intent = new Intent(right.this, Main.class);
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
                                intent.putExtra("score", score);
                                intent.putExtra("c", b);
                                intent.putExtra("seen", mSeen);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }
            }).start();
        }

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
                        Intent intent = new Intent(right.this, Menu.class);
                        intent.putExtra("database", mData);
                        startActivity(intent);
                        finish();

                    }

                }).create().show();

    }

}
