package com.test.mytaskproject;

import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mytask.BaseTaskPerform;
import com.github.mytask.Emitter;
import com.github.mytask.Task;
import com.github.mytask.TaskPerform;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bt=findViewById(R.id.bt);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt:
                test();
            break;
        }
    }

    private void test() {
        Task.start(new TaskPerform<String>() {
            @Override
            public void onPrepare() {
                log((Looper.myLooper()==Looper.getMainLooper())+"======onPrepare22");
            }
            @Override
            public void perform(Emitter emitter) {
                log((Looper.myLooper()==Looper.getMainLooper())+"======perform");
                emitter.onNext("y123");
                emitter.onNext("2222");
                emitter.onNext("3333");
                emitter.onComplete(1);
                emitter.onError(null,55);
                emitter.onComplete(2);
                emitter.onError(null,23);
                emitter.onError(null,55);
                emitter.onNext("55");
            }
            @Override
            public void onNext(String next) {
                log((Looper.myLooper()==Looper.getMainLooper())+"======onNext"+next);
            }
            @Override
            public void onComplete(Object object) {
                log((Looper.myLooper()==Looper.getMainLooper())+"======onComplete"+object);
            }
            @Override
            public void onError(Exception e, Object object) {
                log((Looper.myLooper()==Looper.getMainLooper())+"======onError"+object);
            }
        });
    }
    private void log(String msg){
        Log.i("=====","====="+msg);
    }
}
