package com.roxy.second;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calculagraph extends AppCompatActivity implements View.OnClickListener {


    public final String TAG="Calculagraph";
    TextView score;
    TextView score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculagraph);

        score = (TextView)findViewById(R.id.show_score1);
        score2 = (TextView) findViewById(R.id.show_score2);

    }
    
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String scorea=((TextView)findViewById(R.id.show_score1)).getText().toString();
        String scoreb=((TextView)findViewById(R.id.show_score2)).getText().toString();
        Log.i(TAG, "onSaveInstanceState: ");

        outState.putString("show_score1",scorea);
        outState.putString("show_score2",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea=savedInstanceState.getString("show_score1");
        String scoreb=savedInstanceState.getString("show_score2");

        Log.i(TAG, "onRestoreInstanceState:");

        ((TextView)findViewById(R.id.show_score1)).setText(scorea);
        ((TextView)findViewById(R.id.show_score2)).setText(scoreb);

    }

    public void btnadd1(View btn) {
        if (btn.getId() == R.id.score3) {
            showScore(1);
        } else {
            showScore2(1);
        }
    }

    public void btnadd2(View btn) {
        if (btn.getId() == R.id.score2) {
            showScore(2);
        } else {
            showScore2(2);
        }
    }


    public void btnadd3(View btn) {
        if (btn.getId() == R.id.score1) {
            showScore(3);
        } else {
            showScore2(3);
        }
    }


    private void showScore(int inc) {
        Log.i("show", "inc=" + inc);
        String oldScore = (String) score.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore) + inc);
        score.setText("" + newScore);

    }

    private void showScore2(int inc) {
        Log.i("show", "inc=" + inc);
        String oldScore = (String) score2.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore) + inc);
        score2.setText("" + newScore);
    }


    public void button_reset(View btn) {
        score.setText("0");
        score2.setText("0");
    }


    @Override
    public void onClick(View view) {

    }

}

