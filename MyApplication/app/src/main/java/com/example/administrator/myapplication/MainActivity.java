package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("helloNDK");
    }

    public native String getStringFromNative();



    private ProgressDialog pd;
    private TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initView();
    }

    private void initView() {
        hello = (TextView) findViewById(R.id.hello);
        new Asy().execute();

    }

    private class Asy extends AsyncTask<Integer, Integer, String>{
        /**
         * 这里的Integer参数对应AsyncTask中的第一个参数
         * 这里的String返回值对应AsyncTask的第三个参数
         * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
         * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
         */
        @Override
        protected String doInBackground(Integer... params) {
            return getStringFromNative();
        }

        @Override
        protected void onPostExecute(String s) {
            hello.setText(s);
        }
    }

    /**
     * 保证代码一定在主线程运行。
     *
     * @param msg
     *            对话框的消息
     */
    public void showDialog(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            initDialog(msg);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initDialog(msg);
                }
            });
        }
    }
    /**
     * 关闭对话框的操作
     */
    public void dismissDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    private void initDialog(final String msg) {
        pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("提醒");
        pd.setMessage(msg);
        pd.show();
    }
}
