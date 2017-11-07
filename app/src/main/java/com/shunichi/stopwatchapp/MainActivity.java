package com.shunichi.stopwatchapp;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private long startTime;
    private long elapsedTime = 0l;

    private Handler handler = new Handler();
    private Runnable updateTimer;

    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private TextView timerLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        timerLabel = (TextView) findViewById(R.id.timerLabel);

        setButtonState(true, false, false);
    }

    public void setButtonState(boolean start, boolean stop, boolean reset){
        startButton.setEnabled(start);
        stopButton.setEnabled(stop);
        resetButton.setEnabled(reset);
    }


    public void startTimer(View view){
        startTime = SystemClock.elapsedRealtime();
        updateTimer = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                long t = SystemClock.elapsedRealtime() - startTime + elapsedTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SS", Locale.JAPAN);
                timerLabel.setText(sdf.format(t));
                handler.removeCallbacks(updateTimer);
                handler.postDelayed(updateTimer, 10);
            }
        };

        handler.postDelayed(updateTimer, 10);

        setButtonState(false, true, false);
    }

    public void stopTimer(View view){
        elapsedTime += SystemClock.elapsedRealtime() - startTime;
        handler.removeCallbacks(updateTimer);
        setButtonState(true, false, true);
    }

    public void resetTimer(View view){
        elapsedTime = 0l;
        timerLabel.setText("00:00:00");
        setButtonState(true, false, false);
    }
}

