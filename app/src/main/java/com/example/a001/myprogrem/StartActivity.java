package com.example.a001.myprogrem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StartActivity extends ActionBarActivity {

    Button btn_play,btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findView();
    }
    public void findView(){
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_exit = (Button) findViewById(R.id.btn_exit);

    }

    //開啟MainActivity
    public void btnPlay(View v){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    //開啟RankActivity
    public void btnRank(View v){
        Intent rank = new Intent(this, RankActivity.class);
        startActivity(rank);
    }

    //離開
    public void btnExit(View v){
        finish();
    }
}
