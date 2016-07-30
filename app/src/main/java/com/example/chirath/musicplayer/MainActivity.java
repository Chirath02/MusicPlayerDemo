package com.example.chirath.musicplayer;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button B;
    private ProgressBar P;
    private MediaPlayer M;
    private Handler H = new Handler();
    private TextView timeView;
    private long startTime;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intiView();
        initListeners();
    }

    private void initListeners() {
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(M.isPlaying()) {
                    M.pause();
                    B.setText("Play");
                    H.removeCallbacks(updateUI);
                }
                else {
                    M.start();
                    B.setText("Pause");
                    initHandler();
                    startTime = System.currentTimeMillis();
                }
            }
        });
    }

    private void intiView() {
        B = (Button) findViewById(R.id.play);
        P = (ProgressBar) findViewById(R.id.progressBar);
        M = MediaPlayer.create(this, R.raw.song1);
        timeView = (TextView) findViewById(R.id.songTime);
        timeView.setText(String.format("%02d:%02d", 0, 0));
        img = (ImageView) findViewById(R.id.imageView);
        img.setImageDrawable(getResources().getDrawable(R.drawable.imgsong));
    }

    private void initHandler() {
        H.postDelayed(updateUI, 1000);
    }

    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            double seekPercent = 100 * M.getCurrentPosition() / M.getDuration();
            P.setProgress((int) seekPercent);
            long seconds = (System.currentTimeMillis() - startTime)/1000;
            timeView.setText(String.format("%02d:%02d", seconds/60, seconds % 60));
            H.postDelayed(this, 1000);
        }
    };
}
