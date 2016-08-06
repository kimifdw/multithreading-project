package com.kimifdw.multithread

import com.kimifdw.multithread.service.FutureService

import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

/**
 * Created by kimiyu on 16/8/6.
 */
class MutilThreadApplication {
    static void main(String[] args) {
        FutureService futureService = new FutureService();
        //List<Future<String>> futures = futureService.getFutureList();
        List<Future<String>> futures = futureService.getFutureListPool(100);
        try {
            for (Future<String> res : futures)
                System.out.println("future测试:" + res.get());
        } catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
