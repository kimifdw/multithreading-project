package com.kimifdw.multithread.thread

import java.util.concurrent.Semaphore

/**
 * Created by kimiyu on 16/8/6.
 */
class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    /**
     * 初始化集合及信号量
     *
     * @param bound
     */
    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.semaphore = new Semaphore(bound);
    }

    /**
     * 向容器中添加元素
     *
     * @param o
     * @return
     * @throws InterruptedException
     */
    def add(T o) throws InterruptedException {
        // 一直阻塞直到获取许可
        semaphore.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            // 对象未成功加入到集合中,那么信号量则释放许可
            if (!wasAdded) {
                semaphore.release();
            }
        }
    }

    /**
     * 向容器中移除元素
     *
     * @param o
     * @return
     */
    def remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            semaphore.release();
        }
        return wasRemoved;
    }
}
