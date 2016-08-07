package com.kimifdw.multithread.service

import java.util.concurrent.*

/**
 * Created by kimiyu on 16/8/6.
 */
class FutureService {
    public static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            String tid = String.valueOf(Thread.currentThread().getId());
            System.out.printf("Thread#%s : in call\n", tid);
            return tid;
        }
    }

    public List<Future<String>> getFutureList() {
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++)
            results.add(es.submit(new Task()));
        return results;
    }

    public List<Future<String>> getFutureListPool(int count) {
        List<Future<String>> poolResults = new ArrayList<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(count));
        for (int i = 0; i < count; i++) {
            poolResults.add(threadPoolExecutor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String tid = String.valueOf(Thread.currentThread().getId());
                    System.out.printf("Thread#%s : in call\n", tid);
                    return tid;
                }
            }));
        }
        threadPoolExecutor.shutdown();
        return poolResults;
    }
}
