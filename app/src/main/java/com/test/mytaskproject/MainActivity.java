package com.test.mytaskproject;

import android.os.Looper;
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
            public void perform(Emitter emitter) {
                /*子线程*/
                emitter.onNext("2123");
                emitter.onNext("2222");
                emitter.onNext("3333");
                emitter.onComplete(1);
            }
            @Override
            public void onNext(String next) {
                /*主线程*/
            }
        });

    }
    private void log(String msg){
    }
}
