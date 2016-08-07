package com.kimifdw.multithread;

import com.kimifdw.multithread.rxjava.ObservableDemo;

/**
 * Created by kimiyu on 16/8/7.
 */
public class MultithreadingApplication {

    public static void main(String[] args){
        ObservableDemo observableDemo = new ObservableDemo();
        observableDemo.createDemo();
    }
}
