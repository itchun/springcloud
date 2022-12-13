package com.itchun.线程;

public class ThreadB extends Thread {
    int total;

    public void run() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                total += i;
            }
            System.out.println("total is:" + total);
            notify();
        }
    }
}
