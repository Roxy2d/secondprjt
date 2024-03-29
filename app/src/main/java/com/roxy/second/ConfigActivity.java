package com.roxy.second;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {
    public final String TAG="ConfigAcivity";

    EditText dollarText;
    EditText euroText;
    EditText wonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent=getIntent();


        float dollar2=intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2=intent.getFloatExtra("euro_rate_key",0.0f);
        float won2=intent.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG,"onCreate:dollar2="+dollar2);
        Log.i(TAG,"onCreate:euro2="+euro2);
        Log.i(TAG,"onCreate:won2="+won2);

        dollarText=(EditText)findViewById(R.id.DollarRate);
        euroText=(EditText)findViewById(R.id.EuroRate);
        wonText=(EditText)findViewById(R.id.WonRate);

        //显示数据到控件

        dollarText.setText(String.valueOf(dollar2));
        euroText.setText((String.valueOf(euro2)));
        wonText.setText((String.valueOf(won2)));
    }

    public void save(View btn){
        Log.i(TAG,"save:");

        //获取新的值
        float newDollar =Float.parseFloat(dollarText.getText().toString());
        float newEuro =Float.parseFloat(euroText.getText().toString());
        float newWon =Float.parseFloat(wonText.getText().toString());

        Log.i(TAG,"save:Get new value");
        Log.i(TAG,"save:newDollar="+newDollar);
        Log.i(TAG,"save:newEuro="+newEuro);
        Log.i(TAG,"save:newWon="+newWon);

        //保存到Bundle或者放入Extra
        Intent intent=getIntent();
        Bundle bdl =new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);

        intent.putExtras(bdl);
        setResult(2,intent);

        //返回到调用页面
        finish();

    }
}
