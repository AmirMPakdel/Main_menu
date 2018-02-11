package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coins.black.main_menu.JoiningRoom;
import com.coins.black.main_menu.MainActivity;
import com.coins.black.main_menu.Playground;
import com.coins.black.main_menu.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;


public class WaitBeforeStartFragment extends Fragment {


    public Socket socket;

    public String room_id;


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

        // set room_id
        room_id = JoiningRoom.room_id;

        // get shits in layout
        ImageView send_btn = (ImageView) view.findViewById(R.id.send_btn);
        final EditText input_txt = (EditText) view.findViewById(R.id.input_txt);
        final TextView output_lbl = (TextView) view.findViewById(R.id.output_lbl);
        TextView noe_lbl = (TextView) view.findViewById(R.id.noe_lbl);
        final TextView noe = (TextView) view.findViewById(R.id.noe);
        TextView countdown_lbl = (TextView) view.findViewById(R.id.countdown_lbl);
        final TextView countdown = (TextView) view.findViewById(R.id.countdown);

        // tell server it's ok to emit 'joinPlayer_r'
        socket.emit("ready");

        // sever send's the noe
        socket.on("joinPlayer_r", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
                    JSONObject data = new JSONObject(args[0].toString());

                    final String noe_num = data.getString("noe");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            noe.setText(noe_num);
                            log("joinPlayer_r");
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // server send's the news
        socket.on("news", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                final String news = args[0].toString();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        output_lbl.append("[News] -> "+news+"\n\n");
                    }
                });
            }
        });


        // server send's countdown number
        socket.on("roomState_r", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
                    JSONObject data = new JSONObject(args[0].toString());

                    log(args[0].toString());

                    final String time = data.get("time").toString();
                    final String state = data.get("state").toString();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countdown.setText(time);
                        }
                    });

                    // check if state changed to 's'
                    if(state.equals("s")){

                        // go to Playground Activity
                        startActivity(new Intent(getActivity().getApplicationContext(), Playground.class).putExtra("room_id", room_id));

                        // end this activity
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data =input_txt.getText().toString();

                socket.emit("chat_s", data);

                input_txt.setText("");
            }
        });

        // server sends the chat
        socket.on("chat_r", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {
                    JSONObject data = new JSONObject(args[0].toString());

                    final String username = data.getString("username");
                    final String chat = data.getString("chat");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            output_lbl.append("["+username +"] -> "+chat+"\n\n");
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

}
