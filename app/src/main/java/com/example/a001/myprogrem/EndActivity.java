package com.example.a001.myprogrem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class EndActivity extends ActionBarActivity {
    TextView score;

    int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        findView();

        Intent it = getIntent();
        point = it.getIntExtra("Score", 0);
        score.setText(String.valueOf(point));

        saveScore(point);
    }

    public void findView(){
        score = (TextView) findViewById(R.id.tv_endscore);
    }

    //開啟MainActivity
    public void playAgain(View v){
        Intent back = new Intent(this, StartActivity.class);
        startActivity(back);
        finish();
    }

    public void goExit(View v){
        finish();
    }

    //存分數
    public void saveScore(int data){
        SharedPreferences preferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        int pref_score = preferences.getInt("pref_score", 0);

        if (data>pref_score){
            preferences.edit().putInt("pref_score", data).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
