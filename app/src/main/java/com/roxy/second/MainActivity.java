package com.roxy.second;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText input=findViewById(R.id.editText2);
        String str=input.getText().toString();

        Button btn1=findViewById(R.id.button);
        btn1.setOnClickListener(this);
            }

    @Override
    public void onClick(View view) {
        Log.i("main","clicked");
        out=findViewById(R.id.textView2);
        EditText input=findViewById(R.id.editText2);
        String str=input.getText().toString();
        out.setText("Hello,"+str);
    }
}
