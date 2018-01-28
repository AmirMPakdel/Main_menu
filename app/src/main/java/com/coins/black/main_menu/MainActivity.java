package com.coins.black.main_menu;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import Fragments.Main_menu;
import Fragments.Start;

public class MainActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    MediaPlayer mediaPlayer;


    public static void log(String info){Log.i("AAA Team :",info);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Making Activity Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        log("full screened");

        //play the music
        mediaPlayer = MediaPlayer.create(this, R.raw.mafia_intro);
        mediaPlayer.start();

        // showing th start fragment
        Start fragment_start = new Start();
        log("fragment start obj");

        getSupportFragmentManager().beginTransaction().add(R.id.startFrame, fragment_start).commit();
        log("transaction committed");
    }


    // when a button clicked
    public static void ButtonClick(final Button button) {
        button.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                button.setBackgroundResource(R.drawable.button_clicked);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        button.setBackgroundResource(R.drawable.button_gradient);
                    }
                }, 100);
                return false;
            }
        });
    }


    public void ChangeFragment(Fragment fragment) {
        log("next button clicked!");

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

        transaction.replace(R.id.startFrame, fragment ).commit();
    }


    @Override
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            finish();
            mediaPlayer.stop();
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast toast = Toast.makeText(getApplicationContext(), R.string.exit_hint, Toast.LENGTH_SHORT);
        toast.setGravity(17, 0, 0);
        toast.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 4000);
    }
}
