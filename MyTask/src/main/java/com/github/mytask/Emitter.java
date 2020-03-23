package com.github.mytask;

public interface Emitter<T> {
    void onNext(T obj);

    void onComplete(Object object);

    void onError(Exception e, Object object);
}