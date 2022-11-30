package com.itchun.main;

public class TryMain {

    public static void main(String[] args) throws Exception {
        try {
            System.out.println("1");
            throw new Exception("xx");
        } catch (Exception e) {
            System.out.println("2");
            throw new Exception("xx");
        } finally {
            System.out.println("3");
        }
    }
}
