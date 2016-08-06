package com.kimifdw.multithread.thread.inf

/**
 * Created by kimiyu on 16/8/6.
 */
interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
