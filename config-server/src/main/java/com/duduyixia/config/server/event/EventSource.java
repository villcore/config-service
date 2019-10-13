package com.duduyixia.config.server.event;

import io.reactivex.Observable;
import org.reactivestreams.Subscriber;

public interface EventSource<T> {

    public Observable<T> getObservable();

    public void publish(T event);

    public void subscribe(Subscriber<T> subscriber);
}
