package com.villcore.config.server.event;

import io.reactivex.Observable;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public interface EventSource<T> {

    public String name();

    public Observable<T> getObservable();

    public void publish(T event);

    public void subscribe(Consumer<T> consumer);

    public void subscribe(Executor executor, Consumer<T> consumer);

    public void complete();

    public void error(Throwable throwable);

    public void close();
}
