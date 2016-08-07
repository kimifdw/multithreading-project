package com.kimifdw.multithread.thread

import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by kimiyu on 16/8/6.
 */
class ThreadPoolService {
    public List<Integer> getList(int count) {
        final List<Integer> list = new ArrayList<>();
        // 创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(count));
        final Random random = new Random();
        for (int i = 0; i < count; i++)
            threadPoolExecutor.execute(
                    {
                        list.add(random.nextInt())
                    }
            );
        // 关闭线程池
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
