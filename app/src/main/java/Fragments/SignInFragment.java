package Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coins.black.main_menu.MainActivity;
import com.coins.black.main_menu.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInFragment extends Fragment {

    public com.github.nkzawa.socketio.client.Socket socket;

    public SharedPreferences save;

    public static void log(String info){
        Log.i("AAA Team :",info);}

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // geting the socket from main activity
        socket = MainActivity.socket;

        // get those shits in layout...
        final EditText email_txt = (EditText) view.findViewById(R.id.email_txt);
        final EditText password_txt = (EditText) view.findViewById(R.id.password_txt);
        Button signin_btn = (Button) view.findViewById(R.id.signin_btn);
        final TextView results = (TextView) view.findViewById(R.id.results2_lbl);
        TextView forgot_link = (TextView) view.findViewById(R.id.forgot_link);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                log("clicked!");

                results.setText("Clicke!");

                if(email_txt.getText().length() != 0 && password_txt.getText().length() != 0){

                    results.setText("Wait!");

                    JSONObject data = null;
                    try {
                        data = new JSONObject("{'email': "+email_txt.getText()+", 'password':"+password_txt.getText()+"}");

                        socket.emit("signin_s", data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    results.setText("Please fill the empty fields!");
                }
            }
        });

        // get user_id and username form server
        socket.on("signin_r", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject json = null;
                try {

                    json = new JSONObject(args[0].toString());

                    if(json.getString("user_id").length() != 0 && json.getString("username").length() != 0){

                        // saving the user_id, username , email
                        log("try to save the id");

                        save = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);

                        SharedPreferences.Editor saveEditor = save.edit();

                        saveEditor.putString("user_id", json.getString("user_id")).apply();
                        saveEditor.putString("username", json.getString("username")).apply();
                        saveEditor.putString("email", email_txt.getText().toString());

                        log(save.getString("user_id", "Id didn't saved!"));
                        log(save.getString("username", "Username didn't saved!"));

                        // successfully signed in now go to main menu
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                results.setText("You signed in successfully");

                                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                // stop the goddamn music
                                MainActivity.mediaPlayer.stop();
                                // Tell user that successfully signed in
                                Toast.makeText(getActivity(), "You signed in successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        });

                    }else{

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                results.setText("Wrong password!");
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        // send his password by touching forgot_link
        forgot_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://mafiaaa.ir";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return view;

    }
}
