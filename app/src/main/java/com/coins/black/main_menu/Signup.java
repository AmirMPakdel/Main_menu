package com.coins.black.main_menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Signup extends AppCompatActivity {

    private Emitter.Listener onNewMessage;

    public Socket socket;

    public String username;

    public String email;

    public static void log(String info){
        Log.i("AAA Team :",info);}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        log("try to setup socket");
        // setup the socket
        try {
            socket = IO.socket("http://192.168.1.33:3000");

        } catch (URISyntaxException e) {

            log("couldn't connect to the server!");
        }

        onNewMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                Signup.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];

                        String username;
                        String message;

                        try {
                            username = data.getString("username");
                            message = data.getString("message");
                        } catch (JSONException e) {

                            return;
                        }
                    }
                });
            }
        };

        socket.on("connect", onNewMessage);

        socket.connect();


        // Making this Activity Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get this button taht will send username and email to the server
        Button check_btn = (Button) findViewById(R.id.Check_btn);

        // get username and email EditTexts:
        final EditText username_txt = (EditText) findViewById(R.id.username_txt);
        final EditText email_txt = (EditText) findViewById(R.id.email_txt);

        // get the lable that show result message and act as a console
        final TextView results = (TextView) findViewById(R.id.results_lbl);

        //
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the user entered anything
                if(email_txt.getText().length() != 0 && username_txt.getText().length() != 0){

                    results.setText("Wait!");



                }else{

                    results.setText("Please fill the empty Fields!");
                }
            }
        });

    }
}
