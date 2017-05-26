package com.example.user.proj13;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    ImageButton imgbtn;
    int time = 0;
    int imgno = 0;
    int isStart = 0;
    int interval = 0;
    int s_time = 0;
    Integer[] IMAGE_MENU = {R.drawable.bird, R.drawable.cat, R.drawable.dog, R.drawable.panda,
            R.drawable.penguin, R.drawable.tiger};
    mTask task;
    public void OnButton(View v) {
        if (v.getId() == R.id.button) {
            //처음으로
            if(task.isCancelled()){
                time = 0;
                imgbtn.setImageResource(android.R.drawable.ic_media_play);
                tv.setText("시작부터 0초");
            }
            else Toast.makeText(getApplicationContext(),"작동을 멈춘 뒤 초기화하세요",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void setImage(){
        switch (imgno % 6){
            case 0:
                imgbtn.setImageResource(IMAGE_MENU[0]);
                break;
            case 1:
                imgbtn.setImageResource(IMAGE_MENU[1]);
                break;
            case 2:
                imgbtn.setImageResource(IMAGE_MENU[2]);
                break;
            case 3:
                imgbtn.setImageResource(IMAGE_MENU[3]);
                break;
            case 4:
                imgbtn.setImageResource(IMAGE_MENU[4]);
                break;
            case 5:
                imgbtn.setImageResource(IMAGE_MENU[5]);
                break;
        }
        imgno++;
    }
    class mTask extends AsyncTask<Integer,Integer,Void>{

        @Override
        protected void onCancelled(Void aVoid) {
            String sel = "";
            if(imgno % 6 == 1) sel = "새 선택";
            else if(imgno % 6 ==2) sel = "고양이 선택";
            else if(imgno % 6 == 3) sel = "개 선택";
            else if(imgno % 6 == 4) sel = "판다 선택";
            else if(imgno % 6 == 5) sel = "펭귄 선택";
            else if(imgno % 6 == 0) sel = "호랑이 선택";

            tv.setText(sel + " (" + time + "초)");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tv.setText("시작부터 " + values[0] + "초");
            s_time++;
            if(s_time == interval) {setImage();s_time = 0;}
        }

        @Override
        protected Void doInBackground(Integer... params) {
            for(int i = params[0]; i<1000;i++){
                if(isCancelled() == true) return null;
                try {
                    time = i;
                    publishProgress(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView2);
        imgbtn = (ImageButton) findViewById(R.id.imageButton);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp = et.getText().toString();
                if(temp.length() ==0)
                    Toast.makeText(getApplicationContext(), "간격을 입력해주세요",
                            Toast.LENGTH_SHORT).show();
                else {
                    interval = Integer.parseInt(temp);
                    isStart ++;

                    if(isStart % 2 ==  1) {
                        task = new mTask();
                        task.execute(time);
                    }
                    else task.cancel(true);
                }

            }
        });
    }
}


