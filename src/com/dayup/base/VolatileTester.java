package com.dayup.base;

public class VolatileTester {
    private volatile static boolean stop = false;

    public static void test() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 如果stop变量不是volatile易变变量，死循环将不会退出
                while (!stop) {}
                System.out.println("end");
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stop = true;
        System.out.println(CFunction.__INFO__());
    }
}