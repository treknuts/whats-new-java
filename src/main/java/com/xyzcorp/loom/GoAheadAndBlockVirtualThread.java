package com.xyzcorp.loom;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

public class GoAheadAndBlockVirtualThread {
    public static void main(String[] args)
        throws InterruptedException {

        System.out.printf("The Process ID is %d",
            ProcessHandle.current().pid());

        Thread.sleep(20000);

        long startTime = System.currentTimeMillis();
        ThreadFactory tf = Thread
            .ofVirtual()
            .name("thread-go-and-block")
            .factory();
        try (
            ExecutorService executorService = Executors.newThreadPerTaskExecutor(tf)) {
//            ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            Stream<Callable<Integer>> callableStream =
                Stream.iterate(0, integer -> integer + 1).map(i -> () -> {
                    System.out.format("Process(%d) Started: inside of Thread " +
                            "%s\n",
                        i, Thread.currentThread());
                    Thread.sleep(5000); //Block
                    System.out.format("Process(%d) Finished: Inside of Thread" +
                            " %s\n"
                        , i, Thread.currentThread());
                    return 100;
                });

            // Between 80 and 600 threads there was NO CHANGE IN PERFORMANCE. Mind blown....
            List<Callable<Integer>> callables =
                callableStream.limit(80).toList();
            executorService.invokeAll(callables);
            executorService.shutdown();
            System.out.println("Done:" + (System.currentTimeMillis() - startTime));
        }
    }
}
