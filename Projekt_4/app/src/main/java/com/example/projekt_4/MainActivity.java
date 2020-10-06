package com.example.projekt_4;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTytul;
    private TextView tvZmiana;
    private ProgressBar progress;
    private Button bZmianaTv;
    private Button bAsyncTask;
    private static Handler handler;
    int i=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTytul = (TextView) findViewById(R.id.tvTytul);
        tvZmiana = (TextView) findViewById(R.id.tvZmiana);
        progress = (ProgressBar) findViewById(R.id.progress);
        bAsyncTask = (Button) findViewById(R.id.bAsyncTask);
        bZmianaTv = (Button) findViewById(R.id.bZmianaTv);


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tvZmiana.setText(String.valueOf(msg.arg1));
            }
        };

        bZmianaTv.setOnClickListener(this);
        bAsyncTask.setOnClickListener(this);


    }

    public void onStartThreadClick(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=10; i<=10; i--){
                    if(i>=0){
                    Message message = new Message();
                    message.arg1=i;
                    handler.sendMessage(message);
                    SystemClock.sleep(200);}
                }
            }
        }).start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bZmianaTv:
            onStartThreadClick(view);
                break;
            case R.id.bAsyncTask:
                onStartAsyncTaskClick(view);
                break;

        }
    }

    public void onStartAsyncTaskClick(View view) {
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute("10");
    }

    private class MyAsyncTask extends AsyncTask<String, String, String>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setProgress(0);
    }

    @Override
    protected String doInBackground(String... params) {
        int a = Integer.parseInt(params[0]);
        for (int i=0; i<100; i++) {
            a++;
            if (a < 100) publishProgress(String.valueOf(a));
            else publishProgress(String.valueOf(i));
            SystemClock.sleep(200);
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        progress.setProgress(Integer.parseInt(values[0]));
    }

    @Override
    protected void onPostExecute(String tekst) {
        super.onPostExecute(tekst);
        tekst= "SUKCES!";
        tvZmiana.setText(tekst);
    }
}


}
