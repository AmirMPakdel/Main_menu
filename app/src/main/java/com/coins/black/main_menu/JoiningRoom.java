package com.coins.black.main_menu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import Fragments.WaitBeforeStartFragment;


public class JoiningRoom extends AppCompatActivity {

    public Socket socket;

    public static SharedPreferences save;

    public static final String PREFS_NAME = "MAFIA_DATA";

    public static String room_id;

    public static void log(String info){
        Log.i("AAA Team :",info);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining_room);

        // geting the socket from main activity
        socket = MainActivity.socket;

        // get the app's SharedPreferences
        save = getSharedPreferences(PREFS_NAME, 0);


        // check if user has id and signed in
        if(save.getString("user_id", "").length() != 0){

            // loading until server find us a room and give use it's id
            socket.on("join_r", new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                    try {
                        JSONObject data = new JSONObject(args[0].toString());

                        // get the room_id
                        room_id = data.getString("room_id");

                        // go to the next fragment
                        getSupportFragmentManager().beginTransaction().add(R.id.fragmentFrame, new WaitBeforeStartFragment()).commit();


                        // restart the socket
                        /*socket.disconnect();
                        //socket.connect();

                        // initialize the user
                        JSONObject data2 = null;
                        try {

                            data2 = new JSONObject("{'username': "+save.getString("username","")+", 'user_id':"+save.getString("user_id","")+", 'room_id':"+room_id+"}");

                            socket.emit("connected", data2);

                            // now sever knows this socket's room_id

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            // tell server this user wants to join a room
            socket.emit("join_s");

        }else{

            Toast.makeText(getApplicationContext(), "You are not signed in!", Toast.LENGTH_LONG).show();
        }





    }
}
