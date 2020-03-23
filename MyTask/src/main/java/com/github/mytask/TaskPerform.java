package com.github.mytask;

public abstract class TaskPerform<T> implements BaseTaskPerform<T> {
    @Override
    public void onPrepare() {
    }

    @Override
    public abstract void perform(Emitter<T> emitter);

    @Override
    public abstract void onNext(T next);

    @Override
    public void onComplete(Object object) {
    }

    @Override
    public void onError(Exception e, Object object) {
    }
}
