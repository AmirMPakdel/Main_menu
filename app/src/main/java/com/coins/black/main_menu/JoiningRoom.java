package com.coins.black.main_menu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import com.github.nkzawa.socketio.client.Socket;


public class JoiningRoom extends AppCompatActivity {

    public Socket socket;

    public static SharedPreferences save;

    public static void log(String info){
        Log.i("AAA Team :",info);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining_room);

        // geting the socket from main activity
        socket = MainActivity.socket;

        // get the app's SharedPreferences
        save = getPreferences(0);
    }
}
