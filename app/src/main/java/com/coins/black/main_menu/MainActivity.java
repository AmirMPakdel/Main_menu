package com.coins.black.main_menu;

import android.content.SharedPreferences;
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
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import Fragments.Start;

public class MainActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    public static MediaPlayer mediaPlayer;

    public static final String PREFS_NAME = "MAFIA_DATA";

    public SharedPreferences save;

    public static Socket socket;

    public static String user_id;

    public static String username;

    public static boolean signedin = false;


    public static void log(String info){Log.i("AAA Team :",info);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Making Activity Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        log("full screened");

        // try to connect to server by making a socket
        log("try to setup socket");
        // setup the socket
        try {
            socket = IO.socket("http://192.168.43.237:3000");

        } catch (URISyntaxException e) {

            log("couldn't connect to the server!");
        }

        socket.connect();

        save = getSharedPreferences(PREFS_NAME, 0);

        // check if user has signed up
        if(save.getString("username","").length() != 0 && save.getString("user_id","").length() !=0){

            signedin = true;

            username = save.getString("username","");
            user_id = save.getString("user_id","");

            // initialize the user
            JSONObject data = null;
            try {

                data = new JSONObject("{'username': "+username+", 'user_id':"+user_id+"}");

                socket.emit("connected", data);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        //play the music
        mediaPlayer = MediaPlayer.create(this, R.raw.mafia_intro);
        mediaPlayer.start();

        // showing the start fragment
        Start fragment_start = new Start();
        log("fragment start obj");

        getSupportFragmentManager().beginTransaction().add(R.id.startFrame, fragment_start).commit();
        log("transaction committed");
    }


    // when a button clicked this will make it feels like in 3D
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

        // double click for exiting the app
        if (this.doubleBackToExitPressedOnce) {
            socket.disconnect();
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
