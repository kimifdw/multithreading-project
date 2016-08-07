package com.kimifdw.multithread.rxjava;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by kimiyu on 16/8/7.
 */
public class ObservableDemo {

    /**
     * Observable.create DEMO
     */
    public void createDemo() {
        Observable<Integer> ints = Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("create");
                subscriber.onNext(5);
                subscriber.onNext(6);
                subscriber.onNext(7);
                subscriber.onCompleted();
                System.out.println("completed");
            }
        });
        System.out.println("start");
        ints.subscribe(integer -> System.out.println("element:" + integer));

        System.out.println("end");
    }


}
