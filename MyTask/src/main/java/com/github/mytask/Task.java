package com.github.mytask;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class Task {
    public static <T> void start(final TaskPerform<T> taskPerform) {
        if (taskPerform == null) {
            throw new IllegalStateException("start(TaskPerform taskPerform) taskPerform can't be null");
        }
        start((BaseTaskPerform)taskPerform);
    }
    public static <T> void start(final BaseTaskPerform<T> taskPerform) {
        if (taskPerform == null) {
            throw new IllegalStateException("start(BaseTaskPerform taskPerform) taskPerform can't be null");
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        final Emitter emitter = new Emitter<T>() {
            @Override
            public void onNext(final T obj) {
                if (atomicBoolean.get()) {
                    return;
                }
                TaskHelper.get().post(new Runnable() {
                    @Override
                    public void run() {
                        taskPerform.onNext(obj);
                    }
                });
            }

            @Override
            public void onComplete(final Object object) {
                if (atomicBoolean.get()) {
                    return;
                }
                atomicBoolean.set(true);
                TaskHelper.get().post(new Runnable() {
                    @Override
                    public void run() {
                        taskPerform.onComplete(object);
                    }
                });
            }

            @Override
            public void onError(final Exception e, final Object object) {
                if (atomicBoolean.get()) {
                    return;
                }
                atomicBoolean.set(true);
                TaskHelper.get().post(new Runnable() {
                    @Override
                    public void run() {
                        taskPerform.onError(e, object);
                    }
                });
            }
        };
        if (TaskHelper.get().isOnUiThread()) {
            taskPerform.onPrepare();
        } else {
            TaskHelper.get().post(new Runnable() {
                @Override
                public void run() {
                    taskPerform.onPrepare();
                }
            });
        }
        TaskHelper.get().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    taskPerform.perform(emitter);
                } catch (final Exception e) {
                    e.printStackTrace();
                    if (atomicBoolean.get()) {
                        return;
                    }
                    atomicBoolean.set(true);
                    TaskHelper.get().post(new Runnable() {
                        @Override
                        public void run() {
                            taskPerform.onError(e, e);
                        }
                    });
                }
            }
        });
    }
}
