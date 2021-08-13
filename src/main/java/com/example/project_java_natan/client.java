package com.example.project_java_natan;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class client extends AsyncTask<Void,Void,Void> {
    private String question;
    private String[] options;
    private String answer="";
    private int fiveQ=0;
    private String score;

    @Override
    protected Void doInBackground(Void... v) {
        try {
            Socket socket = new Socket("192.168.1.119", 8010);

            ObjectOutputStream toServer=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer=new ObjectInputStream(socket.getInputStream());
            while(fiveQ<5){
                toServer.writeObject("getQuestion");
                question=fromServer.readObject().toString();
                options= (String[]) fromServer.readObject();

                while(true){
                    if(!answer.isEmpty()){
                        toServer.writeObject(answer);
                        answer="";
                        break;
                    }
                }
                fiveQ++;
            }

            toServer.writeObject("getScore");

            score=fromServer.readObject().toString();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String[] getData(){
        String[] answer = new String[]{question,options[0],options[1],options[2],options[3]};
        return answer;
    }

    public void saveAnswer(String answer){
        this.answer=answer;
        System.out.println(answer);
    }

    public String getScore() {
        return score;
    }
}
