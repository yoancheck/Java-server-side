package com.example.project_java_natan;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class GameActivity extends AppCompatActivity {

    client client;

    TextView question;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    String answerFromUser;
    int click=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        question = findViewById(R.id.question);
        btn1 = findViewById(R.id.op1);
        btn2 = findViewById(R.id.op2);
        btn3 = findViewById(R.id.op3);
        btn4 = findViewById(R.id.op4);

        client = new client();
        client.execute();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setQuestion();
    }


    public void setQuestion(){
        String[] answer = client.getData();
        question.setText(answer[0]);
        btn1.setText(answer[1]);
        btn2.setText(answer[2]);
        btn3.setText(answer[3]);
        btn4.setText(answer[4]);



    }

    public void sendAsr(View view) throws InterruptedException {
        Button btn = (Button)view;
        answerFromUser=btn.getText().toString();
        client.saveAnswer(answerFromUser);
        Thread.sleep(1000);
        setQuestion();
        if(click<1)
            gameOver();
        click--;
    }


    private void gameOver(){
        int score = Integer.parseInt(client.getScore());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
        if (score>4){
            alertDialogBuilder.
                    setMessage("כל הכבוד! התוצאה היא: " + score );
        }else {
            alertDialogBuilder.
                    setMessage("הפעם לא הצלחת, יאללה תנסה שוב \n תוצאה: "  + score);
        }
        alertDialogBuilder.setPositiveButton("שחק שוב", (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(),GameActivity.class)))
                .setNegativeButton("יציאה",
                        (dialogInterface, i) -> finish());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

