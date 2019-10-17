package com.duduyixia.config.server.event;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class EventSourceImpl<T> implements EventSource<T> {

    private String name;
    private ConnectableObservable<T> observable;
    private ObservableEmitter<T> emitter;
    private Disposable disposable;

    public EventSourceImpl(String name) {
        this.name = name;
        observable = Observable.create(
                (ObservableOnSubscribe<T>) emitter -> EventSourceImpl.this.emitter = emitter)
                .publish();
        this.disposable = observable.connect();
    }

    public String name() {
        return name;
    }

    @Override
    public Observable<T> getObservable() {
        return observable;
    }

    @Override
    public void publish(T event) {
        if (disposable.isDisposed()) {
             throw new IllegalStateException("Observable " + name + " is already disposed");
        }
        this.emitter.onNext(event);
    }

    @Override
    public void complete() {
        this.emitter.onComplete();
    }

    @Override
    public void error(Throwable throwable) {
        this.emitter.onError(throwable);
    }

    @Override
    public void subscribe(Consumer<T> consumer) {
        observable.subscribe(consumer::accept);
    }

    @Override
    public void subscribe(Executor executor, Consumer<T> consumer) {
        observable.observeOn(Schedulers.from(executor)).subscribe(consumer::accept);
    }

    @Override
    public void close() {
        complete();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
