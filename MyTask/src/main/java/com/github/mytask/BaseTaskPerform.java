package com.github.mytask;

public interface BaseTaskPerform<T> {
    void onPrepare();

    void perform(Emitter<T> emitter);

    void onNext(T next);

    void onComplete(Object object);

    void onError(Exception e, Object object);
}
