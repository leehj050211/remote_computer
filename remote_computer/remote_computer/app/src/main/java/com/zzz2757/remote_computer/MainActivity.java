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
    Button btnParied, btnSearch, btnCommand_Send, btnText_Send, btnKey_shift, btnKey_ctrl, btnKey_leftalt, btnKey_gui;
    ListView listView;
    ConstraintLayout connect, control, keyboard, mouse;

    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> btArrayAdapter;
    ArrayList<String> deviceAddressArray;

    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothSocket btSocket = null;
    ConnectedThread connectedThread;

    boolean key_shift, key_ctrl, key_leftalt, key_gui = false;

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

        // Show paired devices
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        listView.setAdapter(btArrayAdapter);

        listView.setOnItemClickListener(new myOnItemClickListener());

        Button btnKey_q = (Button)findViewById(R.id.btn_key_q);
        Button btnKey_w = (Button)findViewById(R.id.btn_key_w);
        Button btnKey_e = (Button)findViewById(R.id.btn_key_e);
        Button btnKey_r = (Button)findViewById(R.id.btn_key_r);
        Button btnKey_t = (Button)findViewById(R.id.btn_key_t);
        Button btnKey_y = (Button)findViewById(R.id.btn_key_y);
        Button btnKey_u = (Button)findViewById(R.id.btn_key_u);
        Button btnKey_i = (Button)findViewById(R.id.btn_key_i);
        Button btnKey_o = (Button)findViewById(R.id.btn_key_o);
        Button btnKey_p = (Button)findViewById(R.id.btn_key_p);
        Button btnKey_a = (Button)findViewById(R.id.btn_key_a);
        Button btnKey_s = (Button)findViewById(R.id.btn_key_s);
        Button btnKey_d = (Button)findViewById(R.id.btn_key_d);
        Button btnKey_f = (Button)findViewById(R.id.btn_key_f);
        Button btnKey_g = (Button)findViewById(R.id.btn_key_g);
        Button btnKey_h = (Button)findViewById(R.id.btn_key_h);
        Button btnKey_j = (Button)findViewById(R.id.btn_key_j);
        Button btnKey_k = (Button)findViewById(R.id.btn_key_k);
        Button btnKey_l = (Button)findViewById(R.id.btn_key_l);
        Button btnKey_z = (Button)findViewById(R.id.btn_key_z);
        Button btnKey_x = (Button)findViewById(R.id.btn_key_x);
        Button btnKey_c = (Button)findViewById(R.id.btn_key_c);
        Button btnKey_v = (Button)findViewById(R.id.btn_key_v);
        Button btnKey_b = (Button)findViewById(R.id.btn_key_b);
        Button btnKey_n = (Button)findViewById(R.id.btn_key_n);
        Button btnKey_m = (Button)findViewById(R.id.btn_key_m);
        Button btnKey_space = (Button)findViewById(R.id.btn_key_space);
        Button btnKey_ba = (Button)findViewById(R.id.btn_key_ba);
        Button btnKey_1 = (Button)findViewById(R.id.btn_key_1);
        Button btnKey_2 = (Button)findViewById(R.id.btn_key_2);
        Button btnKey_3 = (Button)findViewById(R.id.btn_key_3);
        Button btnKey_4 = (Button)findViewById(R.id.btn_key_4);
        Button btnKey_5 = (Button)findViewById(R.id.btn_key_5);
        Button btnKey_6 = (Button)findViewById(R.id.btn_key_6);
        Button btnKey_7 = (Button)findViewById(R.id.btn_key_7);
        Button btnKey_8 = (Button)findViewById(R.id.btn_key_8);
        Button btnKey_9 = (Button)findViewById(R.id.btn_key_9);
        Button btnKey_0 = (Button)findViewById(R.id.btn_key_0);
        Button btnKey_minus = (Button)findViewById(R.id.btn_key_minus);
        Button btnKey_equal = (Button)findViewById(R.id.btn_key_equal);
        Button btnKey_backspace = (Button)findViewById(R.id.btn_key_backspace);
        Button btnKey_tab = (Button)findViewById(R.id.btn_key_tab);
        Button btnKey_rightbracket = (Button)findViewById(R.id.btn_key_rightbracket);
        Button btnKey_leftbracket = (Button)findViewById(R.id.btn_key_leftbracket);
        Button btnKey_backslash = (Button)findViewById(R.id.btn_key_backslash);
        Button btnKey_cap = (Button)findViewById(R.id.btn_key_cap);
        Button btnKey_semicolon = (Button)findViewById(R.id.btn_key_semicolon);
        Button btnKey_apostrophe = (Button)findViewById(R.id.btn_key_apostrophe);
        Button btnKey_enter = (Button)findViewById(R.id.btn_key_enter);
        Button btnKey_shift = (Button)findViewById(R.id.btn_key_shift);
        Button btnKey_comma = (Button)findViewById(R.id.btn_key_comma);
        Button btnKey_dot = (Button)findViewById(R.id.btn_key_dot);
        Button btnKey_slash = (Button)findViewById(R.id.btn_key_slash);
        Button btnKey_ctrl = (Button)findViewById(R.id.btn_key_ctrl);
        Button btnKey_gui = (Button)findViewById(R.id.btn_key_gui);
        Button btnKey_leftalt = (Button)findViewById(R.id.btn_key_leftalt);
        Button btnKey_rightalt = (Button)findViewById(R.id.btn_key_rightalt);
        Button btnKey_up = (Button)findViewById(R.id.btn_key_up);
        Button btnKey_down = (Button)findViewById(R.id.btn_key_down);
        Button btnKey_left = (Button)findViewById(R.id.btn_key_left);
        Button btnKey_right = (Button)findViewById(R.id.btn_key_right);
        Button btnMouse_leftup = (Button)findViewById(R.id.btn_mouse_leftup);
        Button btnMouse_up = (Button)findViewById(R.id.btn_mouse_up);
        Button btnMouse_rightup = (Button)findViewById(R.id.btn_mouse_rightup);
        Button btnMouse_left = (Button)findViewById(R.id.btn_mouse_left);
        Button btnMouse_right = (Button)findViewById(R.id.btn_mouse_right);
        Button btnMouse_leftdown = (Button)findViewById(R.id.btn_mouse_leftdown);
        Button btnMouse_down = (Button)findViewById(R.id.btn_mouse_down);
        Button btnMouse_rightdown = (Button)findViewById(R.id.btn_mouse_rightdown);
        Button btnMouse_rightclick = (Button)findViewById(R.id.btn_mouse_rightclick);
        Button btnMouse_leftclick = (Button)findViewById(R.id.btn_mouse_leftclick);
        Button btnMouse_wheelup = (Button)findViewById(R.id.btn_mouse_wheelup);
        Button btnMouse_wheeldown = (Button)findViewById(R.id.btn_mouse_wheeldown);

        btnKey_q.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,113|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,113|");
                }
                return true;
            }
        });
        btnKey_w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,119|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,119|");
                }
                return true;
            }
        });
        btnKey_e.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,101|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,101|");
                }
                return true;
            }
        });
        btnKey_r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,114|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,114|");
                }
                return true;
            }
        });
        btnKey_t.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,116|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,116|");
                }
                return true;
            }
        });
        btnKey_y.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,121|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,121|");
                }
                return true;
            }
        });
        btnKey_u.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,117|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,117|");
                }
                return true;
            }
        });
        btnKey_i.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,105|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,105|");
                }
                return true;
            }
        });
        btnKey_o.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,111|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,111|");
                }
                return true;
            }
        });
        btnKey_p.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,112|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,112|");
                }
                return true;
            }
        });
        btnKey_a.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,97|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,97|");
                }
                return true;
            }
        });
        btnKey_s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,115|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,115|");
                }
                return true;
            }
        });
        btnKey_d.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,100|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,100|");
                }
                return true;
            }
        });
        btnKey_f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,102|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,102|");
                }
                return true;
            }
        });
        btnKey_g.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,103|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,103|");
                }
                return true;
            }
        });
        btnKey_h.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,104|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,104|");
                }
                return true;
            }
        });
        btnKey_j.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,106|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,106|");
                }
                return true;
            }
        });
        btnKey_k.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,107|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,107|");
                }
                return true;
            }
        });
        btnKey_l.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,108|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,108|");
                }
                return true;
            }
        });
        btnKey_z.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,122|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,122|");
                }
                return true;
            }
        });
        btnKey_x.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,120|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,120|");
                }
                return true;
            }
        });
        btnKey_c.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,99|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,99|");
                }
                return true;
            }
        });
        btnKey_v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,118|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,118|");
                }
                return true;
            }
        });
        btnKey_b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,98|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,98|");
                }
                return true;
            }
        });
        btnKey_n.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,110|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,110|");
                }
                return true;
            }
        });
        btnKey_m.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,109|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,109|");
                }
                return true;
            }
        });
        btnKey_space.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,32|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,32|");
                }
                return true;
            }
        });
        btnKey_ba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,96|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,96|");
                }
                return true;
            }
        });
        btnKey_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,49|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,49|");
                }
                return true;
            }
        });
        btnKey_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,50|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,50|");
                }
                return true;
            }
        });
        btnKey_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,51|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,51|");
                }
                return true;
            }
        });
        btnKey_4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,52|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,52|");
                }
                return true;
            }
        });
        btnKey_5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,53|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,53|");
                }
                return true;
            }
        });
        btnKey_6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,54|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,54|");
                }
                return true;
            }
        });
        btnKey_7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,55|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,55|");
                }
                return true;
            }
        });
        btnKey_8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,56|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,56|");
                }
                return true;
            }
        });
        btnKey_9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,57|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,57|");
                }
                return true;
            }
        });
        btnKey_0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,48|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,48|");
                }
                return true;
            }
        });
        btnKey_minus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,45|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,45|");
                }
                return true;
            }
        });
        btnKey_equal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,61|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,61|");
                }
                return true;
            }
        });
        btnKey_backspace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,8|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,8|");
                }
                return true;
            }
        });
        btnKey_tab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,9|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼었을 때
                    connectedThread.write("/key/0,9|");
                }
                return true;
            }
        });
        btnKey_rightbracket.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,93|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,93|");
                }
                return true;
            }
        });
        btnKey_leftbracket.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,91|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,91|");
                }
                return true;
            }
        });
        btnKey_backslash.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,92|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,92|");
                }
                return true;
            }
        });
        btnKey_cap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,193|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,193|");
                }
                return true;
            }
        });
        btnKey_semicolon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,59|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,59|");
                }
                return true;
            }
        });
        btnKey_apostrophe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,39|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,39|");
                }
                return true;
            }
        });
        btnKey_enter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,10|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,10|");
                }
                return true;
            }
        });
        btnKey_shift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,129|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,129|");
                }
                return true;
            }
        });
        btnKey_comma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,44|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,44|");
                }
                return true;
            }
        });
        btnKey_dot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,46|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,46|");
                }
                return true;
            }
        });
        btnKey_slash.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,47|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,47|");
                }
                return true;
            }
        });
        btnKey_ctrl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,128|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,128|");
                }
                return true;
            }
        });
        btnKey_gui.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,131|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,131|");
                }
                return true;
            }
        });
        btnKey_leftalt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,130|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,130|");
                }
                return true;
            }
        });
        btnKey_rightalt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,134|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,134|");
                }
                return true;
            }
        });
        btnKey_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,218|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,218|");
                }
                return true;
            }
        });
        btnKey_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,217|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,217|");
                }
                return true;
            }
        });
        btnKey_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,216|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,216|");
                }
                return true;
            }
        });
        btnKey_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/key/1,215|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/key/0,215|");
                }
                return true;
            }
        });
        btnMouse_leftup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/-10,-10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/-5,-5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/0,-10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/0,-5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_rightup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/10,-10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/5,-5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/-10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/-5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_leftdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/-10,10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/-5,5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/0,10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/0,5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_rightdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/10,10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/5,5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_rightdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mm/10,10|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mm/5,5|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_leftclick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/ml/1|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/ml/0|");
                }
                return true;
            }
        });
        btnMouse_rightclick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mr/1|");
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    connectedThread.write("/mr/0|");
                }
                return true;
            }
        });
        btnMouse_wheelup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mw/1|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mw/2|");
                        }
                    },100);
                }
                return true;
            }
        });
        btnMouse_wheeldown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    connectedThread.write("/mw/-1|");
                }else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            connectedThread.write("/mw/-2|");
                        }
                    },100);
                }
                return true;
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