package com.kimifdw.multithread;

import com.kimifdw.multithread.rxjava.ObservableDemo;
import com.kimifdw.multithread.rxjava.SchedulerDemo;

/**
 * Created by kimiyu on 16/8/7.
 */
public class MultithreadingApplication {

    public static void main(String[] args){
//        ObservableDemo observableDemo = new ObservableDemo();
//        observableDemo.createDemo();
        SchedulerDemo schedulerDemo = new SchedulerDemo();
        schedulerDemo.subscribeOnDemo();
    }
}
