package com.github.mytask;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TaskHelper {
    private static TaskHelper singleObj;
    private Handler handler;
    private ExecutorService executorService;

    private TaskHelper() {
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    public static TaskHelper get() {
        if (singleObj == null) {
            synchronized (TaskHelper.class) {
                if (singleObj == null) {
                    singleObj = new TaskHelper();
                }
            }
        }
        return singleObj;
    }

    /**********************************************************/

    public boolean isOnUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public void post(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        getHandler().post(runnable);
    }

    private Handler getHandler() {
        if(handler==null){
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }

    public ExecutorService getExecutorService() {
        if(executorService==null){
            executorService = Executors.newCachedThreadPool();
        }
        return executorService;
    }
}
