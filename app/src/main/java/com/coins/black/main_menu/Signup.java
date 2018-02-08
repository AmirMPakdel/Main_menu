package com.coins.black.main_menu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;


import Fragments.SignInFragment;


public class Signup extends AppCompatActivity {

    public Socket socket;

    public static SharedPreferences save;

    public String username;

    public String email;

    public String passowrd;

    public static void log(String info){
        Log.i("AAA Team :",info);}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // geting the socket from main activity
        socket = MainActivity.socket;

        // Making this Activity Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get button that will send username and email to the server
        final Button check_btn = (Button) findViewById(R.id.Check_btn);

        // get button that will end the sign up successfully
        final Button done_btn = (Button) findViewById(R.id.done_btn);

        // get re-send button
        final Button resend = (Button) findViewById(R.id.resend_btn);

        // get username and email and password EditTexts:
        final EditText username_txt = (EditText) findViewById(R.id.username_txt);
        final EditText email_txt = (EditText) findViewById(R.id.email_txt);
        final EditText password_txt = (EditText) findViewById(R.id.password_txt);
        final EditText code_txt = (EditText) findViewById(R.id.code_txt);

        // get the label that show result message and act as a console
        final TextView results = (TextView) findViewById(R.id.results_lbl);


        // get other labels for seting visibility
        final TextView username_lbl = (TextView) findViewById(R.id.username_lbl);
        final TextView email_lbl = (TextView) findViewById(R.id.email_lbl);
        final TextView password_lbl = (TextView) findViewById(R.id.password_lbl);
        final TextView code_lbl = (TextView) findViewById(R.id.code_lbl);

        // get the app's SharedPreferences
        save = getPreferences(0);

        final SharedPreferences.Editor saveEditor = save.edit();


        //
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the user entered anything
                if(email_txt.getText().length() != 0 && username_txt.getText().length() != 0){

                    results.setText("Wait!");

                    // create the json object that has username and email attributes
                    JSONObject data = null;
                    try {
                        data = new JSONObject("{'username' :"+username_txt.getText()+" , 'email' :"+ email_txt.getText()+"}");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // showing the json object in the result text view
                    results.setText(data+"");

                    // emitting signupEmail_s signal to the server
                    socket.emit("signupEmail_s", data);

                    // waite for the server to respond and give us the verify code
                    socket.on("signupCode_r", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {

                            // create the json object first
                            JSONObject object = null;
                            try {
                                object = new JSONObject(args[0].toString());

                                log(object.getString("code"));

                                // then get the code out of it and pu it in SharedPreferences with 'code' attribute
                                saveEditor.putString("code",object.getString("code")).apply();

                                // keep the username and email
                                username = username_txt.getText().toString();
                                email = email_txt.getText().toString();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // we use runOnUiThread function because we can't change ui views in this thread
                            Signup.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // set username and email's labels and editText and check button invisible
                                    username_lbl.setVisibility(View.INVISIBLE);
                                    email_lbl.setVisibility(View.INVISIBLE);
                                    username_txt.setVisibility(View.INVISIBLE);
                                    email_txt.setVisibility(View.INVISIBLE);
                                    check_btn.setVisibility(View.INVISIBLE);

                                    // visible the code entering part
                                    code_lbl.setVisibility(View.VISIBLE);
                                    code_txt.setVisibility(View.VISIBLE);
                                    resend.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });


                    code_txt.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            // check if code is correct
                            if(code_txt.getText().toString().equals(save.getString("code",null))){

                                Signup.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // invisible the code entering part
                                        code_lbl.setVisibility(View.INVISIBLE);
                                        code_txt.setVisibility(View.INVISIBLE);
                                        resend.setVisibility(View.INVISIBLE);

                                        // visible the password entering part
                                        password_lbl.setVisibility(View.VISIBLE);
                                        password_txt.setVisibility(View.VISIBLE);
                                        done_btn.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                            return false;
                        }
                    });

                    // re-send the code
                    resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            JSONObject data = null;
                            try {
                                data = new JSONObject("{'email':"+email_txt.getText()+" , 'code':"+ save.getString("code", null) +"}");

                                socket.emit("reSendEmail_s", data);

                                log("data :"+ data.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    // everything is good and we save username and password in SP
                    done_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            passowrd = password_txt.getText().toString();

                            saveEditor.putString("username",username).apply();
                            saveEditor.putString("email",email).apply();
                            // we don't put password in SharedPreferences because of security!!!

                            // creating the json object to send it to server
                            JSONObject data = null;
                            try {
                                data = new JSONObject("{'username':"+username+", 'email':"+email+", 'password': "+passowrd+"}");

                                socket.emit("signup_s", data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    // after sending the username , email and password to the server and saved it in db
                    socket.on("signup_r", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {

                            // sever give us user_id
                            JSONObject json = null;
                            try {

                                json = new JSONObject(args[0].toString());

                                if(!json.getString("user_id").isEmpty()){

                                    // saving the user_id
                                    saveEditor.putString("user_id", json.getString("user_id"));

                                    log(json.getString("user_id"));

                                    // successfully signed up now go to main menu
                                    finish();
                                }


                                // something is wrong like email exist or username exist or both
                                if(json.getString("state") != null){

                                    final String state = json.getString("state");

                                    Signup.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            results.setText(state);
                                        }
                                    });

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });


                    // if the user have'nt entered username or email
                }else{

                    results.setText("Please fill the empty Fields!");
                }

            }
        });

        // get signin text view
        TextView signin = (TextView) findViewById(R.id.signin_link);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // showing the signin fragment
                SignInFragment fragment = new SignInFragment();

                getSupportFragmentManager().beginTransaction().add(R.id.signin_frame, fragment).commit();
            }
        });
    }
}
