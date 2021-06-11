package com.zzz2757.remote_computer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ControlActivity extends AppCompatActivity {


    var connectedThread = ((MainActivity)MainActivity.context_main).var;

    Button  btnCommand_Send, btnText_Send;
    TextView input_command, input_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        btnCommand_Send = (Button) findViewById(R.id.btn_command_send);
        btnText_Send = (Button) findViewById(R.id.btn_text_send);
        input_command = (EditText) findViewById(R.id.input_command);
        input_text = (EditText) findViewById(R.id.input_text);
    }

    // Send string
    public void onClickButtonSendCommand(View view){
        if(connectedThread!=null){connectedThread.write(input_command.getText().toString());}
    }
    public void onClickButtonSendText(View view){
        if(connectedThread!=null){connectedThread.write("/txt/"+input_text.getText().toString()+"|");}
    }
}
