package com.zzz2757.remote_computer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ControlActivity extends AppCompatActivity {

    var connectedThread = ((MainActivity)MainActivity.context_main).var;

    Button  btnCommand_Send, btnText_Send;

    btnCommand_Send = (Button) findViewById(R.id.btn_command_send);
    btnText_Send = (Button) findViewById(R.id.btn_text_send);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
    }

    // Send string
    public void onClickButtonSendCommand(View view){
        if(connectedThread!=null){connectedThread.write(input_command.getText().toString());}
    }
    public void onClickButtonSendText(View view){
        if(connectedThread!=null){connectedThread.write("/txt/"+input_text.getText().toString()+"|");}
    }
}
