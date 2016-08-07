package com.kimifdw.multithread.thread

import com.kimifdw.multithread.thread.inf.Computable

import java.util.concurrent.*

/**
 * Created by kimiyu on 16/8/6.
 */
class Memoizer<A, V> implements Computable<A, V> {

    // 用线程安全类代替unSafe class[不需要在compute中加入synchronized标记
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computable;

    public Memoizer(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> future = cache.get(arg);
            if (future == null) {
                Callable<V> callable = {
                    return computable.compute(arg);
                }
                FutureTask<V> futureTask = new FutureTask<>(callable);
                // 保证加入缓存操作的原子性
                future = cache.putIfAbsent(arg, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                cache.remove(arg, future);
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
    }

    private static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
