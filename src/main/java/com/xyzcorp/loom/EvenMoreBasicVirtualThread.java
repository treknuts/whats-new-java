package com.xyzcorp.loom;

/*
 * Project Loom (Virtual Threads) stores threads in the Heap and managed by the jvm
 * Removes scheduling from the OS
 * Benefits:
 * We can choose to block a thread (Thread.sleep, Thread.join, Socket.connect, Socket.read, Socket.write, Object.wait)
 * Lightweight 1kb
 * Block virtual threads is cheap. 23 million virtual threads on 16GB memory
 * Managed by JVM
 * No context switching
*/
public class EvenMoreBasicVirtualThread {
    public static void main(String[] args) throws InterruptedException {
        Thread newThread = Thread.ofVirtual().name("my-virtual-thread").unstarted(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
                System.out.println("Hello from my new thread");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        newThread.start();
        newThread.join();
    }
}
