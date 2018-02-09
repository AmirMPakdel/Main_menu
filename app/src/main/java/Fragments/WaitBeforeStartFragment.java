package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coins.black.main_menu.MainActivity;
import com.coins.black.main_menu.R;
import com.github.nkzawa.socketio.client.Socket;


public class WaitBeforeStartFragment extends Fragment {


    public Socket socket;

    public static SharedPreferences save;


    public static void log(String info){
        Log.i("AAA Team :",info);}


    public WaitBeforeStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wait_before_start, container, false);

        // geting the socket from main activity
        socket = MainActivity.socket;

        // get the app's SharedPreferences
        save = getActivity().getPreferences(0);



        // tell serer this user wants to join a room
        //socket.emit("join_s", )

        return view;
    }

}
