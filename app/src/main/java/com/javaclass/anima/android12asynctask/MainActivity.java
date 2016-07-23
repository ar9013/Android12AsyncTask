package com.javaclass.anima.android12asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    View start,stop;
    TextView tvShow;
    private MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.btnstart);
        stop = findViewById(R.id.btnstop);
        tvShow = (TextView) findViewById(R.id.tvShow);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myTask = new MyTask();
                //傳遞多個參數給AsyncTask運作處理
                myTask.execute("Brad", "Vivian", "Daniel");

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 提早結束AsyncTask
                if (myTask != null && !myTask.isCancelled()) {
                    myTask.cancel(true);
                }
            }
        });
    }

    private class MyTask extends AsyncTask<String,Integer,String>{

        private String[] name;
        private boolean isOver;

        @Override
        protected String doInBackground(String... names) {
            name = names;

            for(int i = 0 ; i < 20 ;i++){
                try{

                    publishProgress(i,i+100,i+200);
                    Thread.sleep(5000); //  暫停休眠

                    if(isCancelled()){
                        isOver = true;
                        break;
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            // 提早結束傳會null; 否則正常下傳回結果參數
            return isOver?null:"終於執行結束";
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            tvShow.setText("提早結束");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // 將接收執行結束厚的結果參數顯示在使用者介面
            tvShow.setText("Result:" + result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int i = values[0];
            // 將執行過程的傳遞參數顯示在使用者介面
            tvShow.setText(name[i%3] + " = " + values[i%3]);
        }
    }




}
