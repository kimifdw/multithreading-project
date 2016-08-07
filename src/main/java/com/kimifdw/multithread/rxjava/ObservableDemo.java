package com.kimifdw.multithread.rxjava;

import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.TimeUnit;

/**
 * Created by kimiyu on 16/8/7.
 */
public class ObservableDemo {

    /**
     * Observable.create DEMO
     */
    public void createDemo() {
        Observable<Integer> ints = Observable.create((Observable.OnSubscribe<Integer>) subscriber -> {
            System.out.println("create");
            subscriber.onNext(5);
            subscriber.onNext(6);
            subscriber.onNext(7);
            subscriber.onCompleted();
            System.out.println("completed");
        }).cache();
        System.out.println("start");
        // 多次调用
        ints.subscribe(integer -> System.out.println("element1:" + integer));
        ints.subscribe(integer -> System.out.println("element2:" + integer));
        System.out.println("end");
    }


}
