package com.zzz2757.remote_computer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    TextView textStatus, input_command;
    Button btnParied, btnSearch, btnCommand_Send, btnText_Send,
            btnKey_q, btnKey_w, btnKey_e, btnKey_r, btnKey_t, btnKey_y, btnKey_u, btnKey_i, btnKey_o, btnKey_p, btnKey_a, btnKey_s, btnKey_d, btnKey_f, btnKey_g, btnKey_h, btnKey_j, btnKey_k, btnKey_l, btnKey_z, btnKey_x, btnKey_c, btnKey_v, btnKey_b, btnKey_n, btnKey_m, btnKey_space, btnKey_ba, btnKey_1, btnKey_2, btnKey_3, btnKey_4, btnKey_5, btnKey_6, btnKey_7, btnKey_8, btnKey_9, btnKey_0, btnKey_minus, btnKey_equal, btnKey_backspace, btnKey_tab, btnKey_rightbracket, btnKey_leftbracket, btnKey_backslash, btnKey_cap, btnKey_semicolon, btnKey_apostrophe, btnKey_enter, btnKey_shift, btnKey_comma, btnKey_dot, btnKey_slash, btnKey_ctrl, btnKey_gui, btnKey_leftalt, btnKey_rightalt, btnKey_up, btnKey_down, btnKey_left, btnKey_right, btnMouse_leftup, btnMouse_up, btnMouse_rightup, btnMouse_left, btnMouse_right, btnMouse_leftdown, btnMouse_down, btnMouse_rightdown, btnMouse_leftclick, btnMouse_rightclick, btnMouse_wheelup, btnMouse_wheeldown;
    ListView listView;
    ConstraintLayout connect, control, keyboard, mouse;

    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> btArrayAdapter;
    ArrayList<String> deviceAddressArray;

    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothSocket btSocket = null;
    ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get permission
        String[] permission_list = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(MainActivity.this, permission_list, 1);

        // Enable bluetooth
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // variables
        textStatus = (TextView) findViewById(R.id.text_status);
        btnParied = (Button) findViewById(R.id.btn_paired);
        btnSearch = (Button) findViewById(R.id.btn_search);
        listView = (ListView) findViewById(R.id.listview);
        btnCommand_Send = (Button) findViewById(R.id.btn_command_send);
        btnText_Send = (Button) findViewById(R.id.btn_text_send);
        input_command = (EditText) findViewById(R.id.input_command);
        connect = (ConstraintLayout) findViewById(R.id.connect);
        connect.setVisibility(View.VISIBLE);
        control = (ConstraintLayout) findViewById(R.id.control);
        control.setVisibility(View.INVISIBLE);
        keyboard = (ConstraintLayout) findViewById(R.id.keyboard);
        keyboard.setVisibility(View.INVISIBLE);
        mouse = (ConstraintLayout) findViewById(R.id.mouse);
        mouse.setVisibility(View.INVISIBLE);
        btnKey_shift = (Button) findViewById(R.id.btn_key_shift);
        btnKey_ctrl = (Button) findViewById(R.id.btn_key_ctrl);
        btnKey_leftalt = (Button) findViewById(R.id.btn_key_leftalt);
        btnKey_gui = (Button) findViewById(R.id.btn_key_gui);
        btnKey_q = (Button) findViewById(R.id.btn_key_q);
        btnKey_w = (Button) findViewById(R.id.btn_key_w);
        btnKey_e = (Button) findViewById(R.id.btn_key_e);
        btnKey_r = (Button) findViewById(R.id.btn_key_r);
        btnKey_t = (Button) findViewById(R.id.btn_key_t);
        btnKey_y = (Button) findViewById(R.id.btn_key_y);
        btnKey_u = (Button) findViewById(R.id.btn_key_u);
        btnKey_i = (Button) findViewById(R.id.btn_key_i);
        btnKey_o = (Button) findViewById(R.id.btn_key_o);
        btnKey_p = (Button) findViewById(R.id.btn_key_p);
        btnKey_a = (Button) findViewById(R.id.btn_key_a);
        btnKey_s = (Button) findViewById(R.id.btn_key_s);
        btnKey_d = (Button) findViewById(R.id.btn_key_d);
        btnKey_f = (Button) findViewById(R.id.btn_key_f);
        btnKey_g = (Button) findViewById(R.id.btn_key_g);
        btnKey_h = (Button) findViewById(R.id.btn_key_h);
        btnKey_j = (Button) findViewById(R.id.btn_key_j);
        btnKey_k = (Button) findViewById(R.id.btn_key_k);
        btnKey_l = (Button) findViewById(R.id.btn_key_l);
        btnKey_z = (Button) findViewById(R.id.btn_key_z);
        btnKey_x = (Button) findViewById(R.id.btn_key_x);
        btnKey_c = (Button) findViewById(R.id.btn_key_c);
        btnKey_v = (Button) findViewById(R.id.btn_key_v);
        btnKey_b = (Button) findViewById(R.id.btn_key_b);
        btnKey_n = (Button) findViewById(R.id.btn_key_n);
        btnKey_m = (Button) findViewById(R.id.btn_key_m);
        btnKey_space = (Button) findViewById(R.id.btn_key_space);
        btnKey_ba = (Button) findViewById(R.id.btn_key_ba);
        btnKey_1 = (Button) findViewById(R.id.btn_key_1);
        btnKey_2 = (Button) findViewById(R.id.btn_key_2);
        btnKey_3 = (Button) findViewById(R.id.btn_key_3);
        btnKey_4 = (Button) findViewById(R.id.btn_key_4);
        btnKey_5 = (Button) findViewById(R.id.btn_key_5);
        btnKey_6 = (Button) findViewById(R.id.btn_key_6);
        btnKey_7 = (Button) findViewById(R.id.btn_key_7);
        btnKey_8 = (Button) findViewById(R.id.btn_key_8);
        btnKey_9 = (Button) findViewById(R.id.btn_key_9);
        btnKey_0 = (Button) findViewById(R.id.btn_key_0);
        btnKey_minus = (Button) findViewById(R.id.btn_key_minus);
        btnKey_equal = (Button) findViewById(R.id.btn_key_equal);
        btnKey_backspace = (Button) findViewById(R.id.btn_key_backspace);
        btnKey_tab = (Button) findViewById(R.id.btn_key_tab);
        btnKey_rightbracket = (Button) findViewById(R.id.btn_key_rightbracket);
        btnKey_leftbracket = (Button) findViewById(R.id.btn_key_leftbracket);
        btnKey_backslash = (Button) findViewById(R.id.btn_key_backslash);
        btnKey_cap = (Button) findViewById(R.id.btn_key_cap);
        btnKey_semicolon = (Button) findViewById(R.id.btn_key_semicolon);
        btnKey_apostrophe = (Button) findViewById(R.id.btn_key_apostrophe);
        btnKey_enter = (Button) findViewById(R.id.btn_key_enter);
        btnKey_shift = (Button) findViewById(R.id.btn_key_shift);
        btnKey_comma = (Button) findViewById(R.id.btn_key_comma);
        btnKey_dot = (Button) findViewById(R.id.btn_key_dot);
        btnKey_slash = (Button) findViewById(R.id.btn_key_slash);
        btnKey_ctrl = (Button) findViewById(R.id.btn_key_ctrl);
        btnKey_gui = (Button) findViewById(R.id.btn_key_gui);
        btnKey_leftalt = (Button) findViewById(R.id.btn_key_leftalt);
        btnKey_rightalt = (Button) findViewById(R.id.btn_key_rightalt);
        btnKey_up = (Button) findViewById(R.id.btn_key_up);
        btnKey_down = (Button) findViewById(R.id.btn_key_down);
        btnKey_left = (Button) findViewById(R.id.btn_key_left);
        btnKey_right = (Button) findViewById(R.id.btn_key_right);
        btnMouse_leftup = (Button) findViewById(R.id.btn_mouse_leftup);
        btnMouse_up = (Button) findViewById(R.id.btn_mouse_up);
        btnMouse_rightup = (Button) findViewById(R.id.btn_mouse_rightup);
        btnMouse_left = (Button) findViewById(R.id.btn_mouse_left);
        btnMouse_right = (Button) findViewById(R.id.btn_mouse_right);
        btnMouse_leftdown = (Button) findViewById(R.id.btn_mouse_leftdown);
        btnMouse_down = (Button) findViewById(R.id.btn_mouse_down);
        btnMouse_rightdown = (Button) findViewById(R.id.btn_mouse_rightdown);
        btnMouse_rightclick = (Button) findViewById(R.id.btn_mouse_rightclick);
        btnMouse_leftclick = (Button) findViewById(R.id.btn_mouse_leftclick);
        btnMouse_wheelup = (Button) findViewById(R.id.btn_mouse_wheelup);
        btnMouse_wheeldown = (Button) findViewById(R.id.btn_mouse_wheeldown);

        // Show paired devices
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        listView.setAdapter(btArrayAdapter);

        listView.setOnItemClickListener(new myOnItemClickListener());

        btnKey_q.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,113|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,113|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,119|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,119|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_e.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,101|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,101|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,114|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,114|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,116|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,116|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_y.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,121|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,121|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_u.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,117|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,117|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_i.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,105|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,105|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_o.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,111|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,111|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_p.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,112|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,112|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_a.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,97|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,97|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,115|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,115|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_d.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,100|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,100|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,102|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,102|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_g.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,103|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,103|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_h.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,104|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,104|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_j.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,106|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,106|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_k.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,107|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,107|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,108|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,108|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_z.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,122|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,122|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_x.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,120|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,120|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_c.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,99|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,99|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,118|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,118|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,98|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,98|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_n.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,110|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,110|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_m.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,109|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,109|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_space.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,32|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,32|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_ba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,96|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,96|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,49|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,49|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,50|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,50|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,51|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,51|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,52|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,52|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,53|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,53|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,54|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,54|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,55|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,55|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,56|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,56|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,57|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,57|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,48|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,48|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_minus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,45|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,45|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_equal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,61|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,61|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_backspace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,8|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,8|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_tab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,9|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,9|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_rightbracket.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,93|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,93|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_leftbracket.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,91|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,91|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_backslash.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,92|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,92|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_cap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,193|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,193|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_semicolon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,59|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,59|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_apostrophe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,39|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,39|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_enter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,10|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_shift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,129|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,129|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_comma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,44|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,44|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_dot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,46|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,46|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_slash.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,47|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,47|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_ctrl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,128|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,128|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_gui.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,131|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,131|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_leftalt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,130|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,130|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_rightalt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,134|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,134|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,218|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,218|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,217|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,217|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,216|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,216|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnKey_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/key/1,215|");
                        break;
                    case MotionEvent.ACTION_UP:
                        connectedThread.write("/key/0,215|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
        btnMouse_leftup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/-10,-10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/-5,-5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/0,-10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/0,-5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_rightup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/10,-10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/5,-5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/-10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/-5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_leftdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/-10,10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/-5,5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/0,10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/0,5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_rightdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mm/10,10|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mm/5,5|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_leftclick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/ml/1|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/ml/0|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_rightclick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mr/1|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mr/0|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_wheelup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mw/1|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mw/2|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
        btnMouse_wheeldown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        connectedThread.write("/mw/-1|");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run(){
                                connectedThread.write("/mw/-2|");
                            }
                        },100);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "세로모드", Toast.LENGTH_SHORT).show();
        }
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "가로모드", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickButtonPaired(View view){
        btArrayAdapter.clear();
        if(deviceAddressArray!=null && !deviceAddressArray.isEmpty()){ deviceAddressArray.clear(); }
        pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
            }
        }
    }

    public void onClickButtonSearch(View view){
        // Check if the device is already discovering
        if(btAdapter.isDiscovering()){
            btAdapter.cancelDiscovery();
        } else {
            if (btAdapter.isEnabled()) {
                btAdapter.startDiscovery();
                btArrayAdapter.clear();
                if (deviceAddressArray != null && !deviceAddressArray.isEmpty()) {
                    deviceAddressArray.clear();
                }
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(receiver, filter);
            } else {
                Toast.makeText(getApplicationContext(), "bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
                btArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }

    // Send string
    public void onClickButtonSendCommand(View view){
        if(connectedThread!=null){connectedThread.write(input_command.getText().toString());}
    }
    public void onClickButtonSendText(View view){
        if(connectedThread!=null){connectedThread.write("/txt/"+input_command.getText().toString()+"|");}
    }

    public class myOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), btArrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();

            textStatus.setText("try...");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 세로회전
            connect.setVisibility(View.VISIBLE);
            control.setVisibility(View.INVISIBLE);
            keyboard.setVisibility(View.INVISIBLE);
            mouse.setVisibility(View.INVISIBLE);

            final String name = btArrayAdapter.getItem(position); // get name
            final String address = deviceAddressArray.get(position); // get address
            boolean flag = true;

            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            // create & connect socket
            try {
                btSocket = createBluetoothSocket(device);
                btSocket.connect();
            } catch (IOException e) {
                flag = false;
                textStatus.setText("connection failed!");
                e.printStackTrace();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 세로회전
                connect.setVisibility(View.VISIBLE);
                control.setVisibility(View.INVISIBLE);
                keyboard.setVisibility(View.INVISIBLE);
                mouse.setVisibility(View.INVISIBLE);
            }
            // start bluetooth communication
            if(flag){
                textStatus.setText("connected to "+name);
                connectedThread = new ConnectedThread(btSocket);
                connectedThread.start();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 가로회전
                connect.setVisibility(View.INVISIBLE);
                control.setVisibility(View.VISIBLE);
                keyboard.setVisibility(View.VISIBLE);
                mouse.setVisibility(View.VISIBLE);
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }
}