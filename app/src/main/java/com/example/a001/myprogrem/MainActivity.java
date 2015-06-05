package com.example.a001.myprogrem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    TextView score, quiz;
    ImageView image;
    Button btn_hint, op1, op2, op3, op4, result;
    Button[] btn;
    Spinner menu;

    Bitmap bitmap;
    SoundPool soundPool;
    String[] question;

    int[] quizbank = {R.array.quiz1, R.array.quiz2 ,R.array.quiz3,R.array.quiz4,R.array.quiz5,R.array.quiz6,R.array.quiz7,R.array.quiz8};

    int[] imgBank = {R.drawable.quiz1, R.drawable.quiz2, R.drawable.quiz3,R.drawable.quiz4,R.drawable.quiz5,R.drawable.quiz6,R.drawable.quiz7,R.drawable.quiz8};

    int win, fail, hint;//音效碼

    int quizNumber = 0;//題號
    int point = 0;//分數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setQuiz();

        //選單監聽器
        menu.setOnItemSelectedListener(this);

        //結果監聽器
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setVisibility(View.INVISIBLE);
                if (quizNumber == quizbank.length) {
                    Intent end = new Intent(getApplicationContext(), EndActivity.class);
                    end.putExtra("Score", point);
                    point = 0;
                    quizNumber = 0;
                    startActivity(end);
                    releaseMemory();
                    finish();
                } else {
                    setQuiz();
                }
            }
        });
    }

    //取得元件及音效
    public void findView(){
        score = (TextView) findViewById(R.id.tv_score);
        quiz = (TextView) findViewById(R.id.tv_quiz);

        menu = (Spinner) findViewById(R.id.spn_menu);

        image = (ImageView) findViewById(R.id.img_pic);

        result = (Button) findViewById(R.id.btn_result);
        btn_hint = (Button) findViewById(R.id.btn_hint);
        op1 = (Button) findViewById(R.id.btn_op1);
        op2 = (Button) findViewById(R.id.btn_op2);
        op3 = (Button) findViewById(R.id.btn_op3);
        op4 = (Button) findViewById(R.id.btn_op4);

        //音效
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 5);
        win = soundPool.load(this, R.raw.win_sound, 1);
        fail = soundPool.load(this, R.raw.fail_sound, 1);
        hint = soundPool.load(this, R.raw.hint_sound, 1);
    }

    //釋放記憶體
    public void releaseMemory(){
        soundPool.release();
        bitmap.recycle();
        System.gc();
    }

    //設定題目
    public void setQuiz(){
        score.setText(String.valueOf(point));
        question = getResources().getStringArray(quizbank[quizNumber]);
        quiz.setText(question[0]);

        //讀取圖片
        InputStream img = this.getResources().openRawResource(imgBank[quizNumber]);
        bitmap = BitmapFactory.decodeStream(img);
        image.setImageBitmap(bitmap);

        btn = new Button[]{op1, op2, op3, op4};

        //設定按鈕可按 按鈕文字 按鈕監聽器
        for(int i=0;i<4;i++){
            btn[i].setClickable(true);
            btn[i].setText(question[i+3]);
            btn[i].setOnClickListener(this);
        }
    }

    //答案按鈕
    @Override
    public void onClick(View view) {
        //設定答題後按鈕不可按
        for(int i=0;i<4;i++){
            btn[i].setClickable(false);
        }

        //取得被按按鈕上答案
        String answer = null;
        for(int i=0;i<4;i++) {
            if (btn[i].getId() == view.getId()) {
                answer = btn[i].getText().toString();
                break;
            }
        }

        //檢查答案是否正確
        if(answer.equals(question[2])){
            soundPool.play(win, 1.0F, 1.0F, 0, 0, 1.0F);
            result.setVisibility(View.VISIBLE);
            result.setBackgroundResource(R.drawable.result_right);
            //增加分數及設定分數文字
            point+=10;
            score.setText(String.valueOf(point));
        }else{
            soundPool.play(fail, 1.0F, 1.0F, 0, 0, 1.0F);
            result.setVisibility(View.VISIBLE);
            result.setBackgroundResource(R.drawable.result_wrong);
        }
        quizNumber+=1;
    }

    //提示按鈕
    public void getHint(View v){
        //播放提示音效
        soundPool.play(hint, 1, 1, 0, 0, 1);
        //吐司訊息
        Toast toast_hint = Toast.makeText(v.getContext(),question[1],Toast.LENGTH_LONG);
        toast_hint.setGravity(Gravity.BOTTOM, 0, 300);
        toast_hint.show();
    }

    //選單功能
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 1){//開啟StartActivity
            Intent menu = new Intent(this, StartActivity.class);
            startActivity(menu);
            finish();
        }else if(i == 2){//離開APP
            finish();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
