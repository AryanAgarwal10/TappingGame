package com.example.tappinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView timeLeft;
    TextView score;
    CountDownTimer downTimer;
    long remainTime;
    int value;
    boolean counterBtn = true;
    boolean counterTimer=false;
    boolean canceler=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        timeLeft = findViewById(R.id.timeLeft);
        score = findViewById(R.id.score);
        if(savedInstanceState!=null)
        {
            remainTime= savedInstanceState.getLong("remainTime");
            value=savedInstanceState.getInt("score");

            if(savedInstanceState.getBoolean("counterTimer")) {
                btn.setText("Tap");

            timer();
            }

        }
        else {
            remainTime =60000;
            value = 200;
        }
        timeLeft.setText((int) remainTime / 1000 + "");
        score.setText(value + "");
        
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterBtn) {
                    btn.setText("TAP");
                    timer();
                    counterBtn=false;
                }
                score.setText(--value + "");
                if (value <= 0 && remainTime > 0) {
                    canceler=true;

                    alert(R.string.winTitle);

                }


            }
        });
    }

    private void timer() {
        counterTimer=true;
        canceler=false;
        downTimer = new CountDownTimer(remainTime, 1000) {
            @Override
            public void onTick(long milLeft) {
                remainTime = milLeft;
                timeLeft.setText((int) remainTime / 1000 + "");
                if(canceler)
                    cancel();
            }

            @Override
            public void onFinish() {

                alert(R.string.loseTitle);
                cancel();
            }
        };
        downTimer.start();
    }
    private void reset()
    {
        counterTimer=false;
        downTimer.cancel();
        remainTime = 60000;
        value = 200;
        timeLeft.setText((int) remainTime / 1000 + "");
        score.setText(value + "");
        counterBtn=true;
        btn.setText(R.string.start);
    }
    private void alert(int a){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(a)
                .setMessage(R.string.msg)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    reset();
                    }
                }).setCancelable(false).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score",value);
        outState.putLong("remainTime",remainTime);
        outState.putBoolean("counterTimer",counterTimer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.info)
        {
            Toast.makeText(this, "Version "+BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}