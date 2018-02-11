package com.coins.black.main_menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import CharectersToolsFragments.adc_tools;
import CharectersToolsFragments.bly_tools;
import CharectersToolsFragments.doc_tools;
import CharectersToolsFragments.dtc_tools;
import CharectersToolsFragments.gdf_tools;
import CharectersToolsFragments.gns_tools;
import CharectersToolsFragments.klr_tools;
import CharectersToolsFragments.mfa_tools;
import CharectersToolsFragments.plc_tools;
import CharectersToolsFragments.pts_tools;
import CharectersToolsFragments.snp_tools;
import CharectersToolsFragments.spy_tools;
import CharectersToolsFragments.tif_tools;
import CharectersToolsFragments.trr_tools;

public class Playground extends AppCompatActivity {

    public Socket socket;

    public String room_id;

    public String role;

    public static void log(String info){
        Log.i("AAA Team :",info);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        // geting the socket from main activity
        socket = MainActivity.socket;

        // set room_id
        room_id = getIntent().getExtras().getString("room_id");
        log("room_id : "+room_id);

        // get shits in layout
        ImageView send_btn = (ImageView) findViewById(R.id.send_btn);
        final EditText input_txt = (EditText) findViewById(R.id.input_txt);
        final TextView output_lbl = (TextView) findViewById(R.id.output_lbl);

        // get gangs array if role is mfa or gdf
        socket.on("gangs", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject data = new JSONObject(args[0].toString());

                    //Array gangs = (Array) data.get("gangs");

                    log("gangs :"+data.get("gangs").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // find out own role
        socket.on("own_info_r", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    log("own info _r args0 -> "+args[0].toString());

                    JSONObject data = new JSONObject(args[0].toString());

                    JSONObject own = new JSONObject(data.getString("own"));

                    role = own.getString("role");

                    log("role -> "+role);

                    switch (role){
                        case "adc":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new adc_tools()).commit();
                            break;
                        case "bly":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new bly_tools()).commit();
                            break;
                        case "doc":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new doc_tools()).commit();
                            break;
                        case "dtc":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new dtc_tools()).commit();
                            break;
                        case "gdf":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new gdf_tools()).commit();
                            break;
                        case "gns":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new gns_tools()).commit();
                            break;
                        case "klr":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new klr_tools()).commit();
                            break;
                        case "mfa":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new mfa_tools()).commit();
                            break;
                        case "plc":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new plc_tools()).commit();
                            break;
                        case "pts":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new pts_tools()).commit();
                            break;
                        case "snp":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new snp_tools()).commit();
                            break;
                        case "spy":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new spy_tools()).commit();
                            break;
                        case "tif":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new tif_tools()).commit();
                            break;
                        case "trr":
                            getSupportFragmentManager().beginTransaction().add(R.id.toolsFrame, new trr_tools()).commit();
                            break;
                        default:
                            log("ROLE WAS NULL!!!");
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        socket.emit("own_info");

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data =input_txt.getText().toString();

                socket.emit("chat_s", data);

                input_txt.setText("");
            }
        });

        // server send's the news
        socket.on("news", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                final String news = args[0].toString();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        output_lbl.append("[News] -> "+news+"\n\n");
                    }
                });
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

                    runOnUiThread(new Runnable() {
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
    }
}
