package com.dayup.base;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTester {
    class ThreadSafeObject {
        private int fieldInt;
        private Object object;
        private ReentrantLock lock;
        public void f() {
            // fieldInt是基本类型，无法使用synchronized加锁
            synchronized (object) {
                // do sth
            }
            synchronized (this) {
                // do sth
            }
            synchronized (ThreadSafeObject.class) {
                // do sth
            }
            lock.lock();
            // do sth
            lock.unlock();

            boolean locked = lock.tryLock();
            if (locked) {
                try {
                    // do sth
                } finally {
                    lock.unlock();
                }
            }
            try {
                lock.lockInterruptibly();
                // do sth
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testCreatThread() throws InterruptedException {
        Thread t1 = new Thread("Thread-t1") {
            @Override
            public void run() {
                System.out.println(CFunction.__INFO__() + "This thread name is " + Thread.currentThread().getName() + ", id:" + Thread.currentThread().getId());
            }
        };
        t1.run();
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(CFunction.__INFO__() + "This thread name is " + Thread.currentThread().getName() + ", id:" + Thread.currentThread().getId());
            }
        });
        // 线程执行结束后，t2设置的runnable会置为null，因此该线程无法再run起来
        // 因为线程结束调用exit的时间不确定，单独执行run函数，此时，还是有可能线程尚未清理，故可以调用到runnable的run函数
        t2.start();
        TimeUnit.MICROSECONDS.sleep(1000); // 会影响thread的id
        Thread.sleep(100); // 不会影响id
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(t1); // 依旧可以这样启动该线程
        //service.execute(t2); // 此时已经不能保证thread.targe是否为null，可能无法执行重写的run方法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(CFunction.__INFO__() + "This thread name is " + Thread.currentThread().getName() + ", id:" + Thread.currentThread().getId());
            }
        };
        service.execute(runnable);
        service.shutdown();
    }

    public static void testUncaughtExceptionHandler() {
        Thread t = new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
                throw new RuntimeException("this-runtime-exception");
            }
        };
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
            }
        });
//        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println(e);
//            }
//        });
        // 一旦设置了异常处理方法，默认处理方法将失效
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("default caught exception");
            }
        });
        System.out.println(Thread.currentThread().getId());
        t.start();
        t2.start();
    }
    public static void test() {
        try {
            testCreatThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
