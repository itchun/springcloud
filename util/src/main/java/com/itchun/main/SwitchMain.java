package com.itchun.main;

public class SwitchMain {

    public static void main(String[] args) {
        System.out.println(test());
    }

    public static String test() {
        String a = "aa";
        return switch (a) {
            case "aa" -> "test";
            case "bb" -> {
                System.out.println("123123");
                yield "12312333";
            }
            case "cc" -> "testasdfa";
            default -> "";
        };
    }
}
