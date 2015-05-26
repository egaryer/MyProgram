package com.example.a001.myprogrem;

import android.content.Intent;
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

public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    TextView score, quiz;
    ImageView image;
    Button hint, op1, op2, op3, op4;
    Button[] btn;
    Spinner menu;

    int[] imgBank = {R.drawable.quiz1, R.drawable.quiz2, R.drawable.quiz3,R.drawable.quiz4,R.drawable.quiz5,R.drawable.quiz6,R.drawable.quiz7,R.drawable.quiz8};

    int[] quizbank = {R.array.quiz1, R.array.quiz2 ,R.array.quiz3,R.array.quiz4,R.array.quiz5,R.array.quiz6,R.array.quiz7,R.array.quiz8};
    String[] question;

    int quizNumber = 0;
    int point = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setQuiz();
    }

    public void findView(){
        score = (TextView) findViewById(R.id.tv_score);
        quiz = (TextView) findViewById(R.id.tv_quiz);

        menu = (Spinner) findViewById(R.id.spn_menu);

        image = (ImageView) findViewById(R.id.img_pic);

        hint = (Button) findViewById(R.id.btn_hint);
        op1 = (Button) findViewById(R.id.btn_op1);
        op2 = (Button) findViewById(R.id.btn_op2);
        op3 = (Button) findViewById(R.id.btn_op3);
        op4 = (Button) findViewById(R.id.btn_op4);
    }

    public void setQuiz(){
        score.setText(String.valueOf(point));
        question = getResources().getStringArray(quizbank[quizNumber]);
        quiz.setText(question[0]);
        image.setImageResource(imgBank[quizNumber]);

        menu.setOnItemSelectedListener(this);

        btn = new Button[]{op1, op2, op3, op4};
        for(int i=0;i<4;i++){
            btn[i].setText(question[i+3]);
            btn[i].setOnClickListener(this);
        }
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

    @Override
    public void onClick(View view) {
        Toast result = new Toast(view.getContext());
        result.setGravity(Gravity.BOTTOM, 0, 300);

        String answer = "";
        for(int i=0;i<4;i++) {
            if (btn[i].getId() == view.getId()) {
                answer = btn[i].getText().toString();
            }
        }

        if(answer.equals(question[2])){
            result = Toast.makeText(view.getContext(),"正確",Toast.LENGTH_SHORT);
            result.show();
            point+=10;
            score.setText(String.valueOf(point));
        }else{
            result = Toast.makeText(view.getContext(),"錯誤",Toast.LENGTH_SHORT);
            result.show();
        }
        quizNumber+=1;

        if(quizNumber == quizbank.length){
            Intent end = new Intent(this, EndActivity.class);
            end.putExtra("Score", point);
            point = 0;
            quizNumber = 0;
            startActivity(end);
            finish();
        }else{
        setQuiz();
        }
    }

    public void getHint(View v){
        Toast ttHint = Toast.makeText(v.getContext(),question[1],Toast.LENGTH_LONG);
        ttHint.setGravity(Gravity.BOTTOM, 0, 300);
        ttHint.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 1){
            quizNumber+=1;
            setQuiz();
            menu.setSelection(0);
        }else if(i == 2){
            Intent menu = new Intent(this, StartActivity.class);
            startActivity(menu);
            finish();
        }
        else if(i == 3){
            finish();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
