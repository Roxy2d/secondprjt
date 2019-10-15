package com.roxy.second;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText input=findViewById(R.id.input);
        String str=input.getText().toString();

        Button btn1=findViewById(R.id.ok);
        btn1.setOnClickListener(this);
            }

    @Override
    public void onClick(View view) {
        Log.i("main","clicked");
        out=findViewById(R.id.input);
        EditText input=findViewById(R.id.input);
        String str=input.getText().toString();
        out.setText("Hello,"+str);
    }
}
