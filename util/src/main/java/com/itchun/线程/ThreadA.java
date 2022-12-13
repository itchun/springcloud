package com.itchun.线程;

public class ThreadA {

    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        b.start();
        synchronized (b) {
            try {
                Thread.sleep(5000);
                System.out.println("Waiting for b to complete ...");
                b.wait();
            } catch (Exception e) {
                System.out.println("Total is:" + b.total);
            }
        }
        System.out.println("Total is:" + b.total);
    }
}

