package com.dayup.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.*;

public class ConcurrentTester {
    class Child extends Thread{
        protected String name;
        protected CyclicBarrier cyclicBarrier;
        public Child(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " 正在洗手...");
                TimeUnit.SECONDS.sleep(1);
                cyclicBarrier.await();
                System.out.println(name + " 正在吃饭...");
                TimeUnit.SECONDS.sleep(2);
                cyclicBarrier.await();
                System.out.println(name + " 开始玩耍...");
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(CFunction.__INFO__() + name + e.toString());
            }
        }
    }

    class Child2 extends Child {
        public Child2(String name, CyclicBarrier cyclicBarrier) {
            super(name, cyclicBarrier);
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " 正在洗手...");
                TimeUnit.SECONDS.sleep(1);
                cyclicBarrier.await();
                System.out.println(name + " 正在吃饭...");
                cyclicBarrier.await(1, TimeUnit.SECONDS);
                System.out.println(name + " 开始玩耍...");
            } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
                System.out.println(CFunction.__INFO__() + name + e.toString());
            }
        }
    }

    private CyclicBarrier createCyclicBarrier(int parties) {
        return new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("可以做下一件事了");
            }
        });
    }

    private Thread createChild(Class<? extends Thread> cls, CyclicBarrier cyclicBarrie) {
        try {
            // 这里使用反射，构造函数，因为是内部类，第一个参数默认会传入外部类的实例this。
            String name = "child-" + new Random().nextInt(100);
            return cls.getDeclaredConstructor(this.getClass(), String.class, CyclicBarrier.class).newInstance(this, name, cyclicBarrie);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cyclicBarrierCase(int i) {
        CyclicBarrier cyclicBarrier;
        switch (i) {
            case 1:
                // 第一种场景，正常协作
                cyclicBarrier = createCyclicBarrier(3);
                createChild(Child.class, cyclicBarrier).start();
                createChild(Child.class, cyclicBarrier).start();
                createChild(Child.class, cyclicBarrier).start();
                break;
            case 2:
                // 第二种场景，parties和协作对象数不一致，将会发生混乱，不可预期
                cyclicBarrier = createCyclicBarrier(3);
                createChild(Child.class, cyclicBarrier).start();
                createChild(Child.class, cyclicBarrier).start();
                createChild(Child.class, cyclicBarrier).start();
                createChild(Child.class, cyclicBarrier).start();
                break;
            case 3:
                // 还可以使用有限等待方法，超时未到齐，会抛出超时异常，后续到齐后会抛出broken异常
                cyclicBarrier = createCyclicBarrier(3);
                createChild(Child2.class, cyclicBarrier).start();
                createChild(Child2.class, cyclicBarrier).start();
                createChild(Child2.class, cyclicBarrier).start();
                break;
        }
    }

    class Child3 extends Thread {
        private int anInt = new Random().nextInt(5);
        private String name;
        private CountDownLatch countDownLatch;
        public Child3(String name, CountDownLatch countDownLatch) {
            this.name = name;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(anInt);
                countDownLatch.countDown();
                System.out.println(name + " 到了，等待其他小伙伴一起吃饭...");
                countDownLatch.await();
                System.out.println(name + " 一起吃饭...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void countDownLatchCase() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Child3("child-1", countDownLatch).start();
        new Child3("child-2", countDownLatch).start();
        new Child3("child-3", countDownLatch).start();
    }

    class Child4 extends Thread {
        private int anInt = new Random().nextInt(10);
        private String name;
        private Semaphore semaphore;
        public Child4(String name ,Semaphore semaphore) {
            this.name = name;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " 等待洗手...");
                semaphore.acquire();
                System.out.println(name + " 正在洗手...");
                TimeUnit.SECONDS.sleep(anInt);
                System.out.println(name + " 洗手完毕...");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void semaphoreCase() {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 1; i < 5; i++) {
            new Child4("child-" + i, semaphore).start();
        }
    }

    public static void test() {
        //new ConcurrentTester().cyclicBarrierCase(1);
        //new ConcurrentTester().countDownLatchCase();
        new ConcurrentTester().semaphoreCase();
    }
}
