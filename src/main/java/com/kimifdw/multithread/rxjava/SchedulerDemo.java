package com.kimifdw.multithread.rxjava;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.*;

/**
 * Created by kimiyu on 16/8/9.
 */
public class SchedulerDemo {

    private void schedulerFromDemo() {
        Executor executor = new ThreadPoolExecutor(
                10,
                10,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000),
                Executors.defaultThreadFactory()
        );

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Scheduler scheduler = Schedulers.from(executor);
    }

    private Observable<String> simple() {
        return Observable.create(
                (Observable.OnSubscribe<String>) subscriber -> {
                    System.out.println("Subservibed");
                    subscriber.onNext("A");
                    subscriber.onNext("B");
                    subscriber.onCompleted();
                }
        ).subscribeOn(Schedulers.computation());
    }


    ExecutorService poolA = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
    Scheduler schedulerA = Schedulers.from(poolA);
    ExecutorService poolB = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
    Scheduler schedulerB = Schedulers.from(poolB);
    ExecutorService poolC = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
    Scheduler schedulerC = Schedulers.from(poolC);

    public void obServeOnDemo() {
        System.out.println("Starting");
        final Observable<String> observable = simple();
        System.out.println("Created");
        observable.doOnNext(s -> System.out.println("Found 1:" + s))
                .observeOn(schedulerB)
                .doOnNext(x -> System.out.println("Found 2:" + x))
                .observeOn(schedulerC)
                .doOnNext(x -> System.out.println("Found 3:" + x))
                .subscribeOn(schedulerA)
                .subscribe(x -> System.out.println("Got 1:" + x), Throwable::printStackTrace, () -> System.out.println("Completed"));
        System.out.println("Exiting");
    }

    public void subscribeOnDemo() {
        System.out.println("Starting");
        final Observable<String> observable = simple();
        System.out.println("Created");
        Thread mainThread = Thread.currentThread();
        System.out.println("线程名称:" + mainThread.getName());
        observable
                .subscribe(x -> {
                            Thread thread = Thread.currentThread();
                            System.out.println("Got " + x + ";Thread Name:" + thread.getName());
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("Completed")
                );
    }
}
