package com.dayup.pattern;

interface SingletonInterface {
    default void print() {
        System.out.println(this.getClass().getSimpleName());
    }
}
// 基于类加载器的机制，线程安全，如果实例创建比较耗时，会造成启动较慢
class Singleton1 implements SingletonInterface{
    private static Singleton1 singleton = new Singleton1();
    private Singleton1() {}
    public static Singleton1 getSingleton() {
        return singleton;
    }
}

// 相比1，实现类延迟加载，在调用时才会实例化，但不是线程安全的
class Singleton2 implements SingletonInterface{
    private static Singleton2 singleton;
    private Singleton2() {}
    public static Singleton2 getSingleton() {
        if (singleton == null) {
            singleton = new Singleton2();
        }
        return singleton;
    }
}

// 使用了方法同步块和volatile关键字，线程安全，缺点是锁比较重，每次都需要加锁
class Singleton3 implements SingletonInterface{
    private static volatile Singleton3 singleton;
    private Singleton3() {}
    public static synchronized Singleton3 getSingleton() {
        if (singleton == null) {
            singleton = new Singleton3();
        }
        return singleton;
    }
}

// 基本完美的方案，线程安全，使用双重检查锁的方法，降低了锁带来的影响，结合2、3的优点
// 同时为了在反序列化时，防止利用反射直接调用构造方法来创建实例，因此在构造方法中做了检测处理
class Singleton4 implements SingletonInterface{
    private static volatile Singleton4 singleton;
    private static volatile boolean flag = true;
    private Singleton4() {
        if (flag) {
            flag = false;
        } else {
            throw  new RuntimeException("The instance  already exists");
        }
    }
    public static Singleton4 getSingleton() {
        if (singleton == null) {
            synchronized (Singleton4.class){
                if (singleton == null) {
                    singleton = new Singleton4();
                }
            }
        }
        return singleton;
    }
}

// 这是利用枚举类实现单例，代码书写简单，实际效果等同第一种
enum Singleton5 implements SingletonInterface{
    INSTANCE;
    String strField;
    Singleton5(){}
    public String getStrField() {
        return strField;
    }
    public void setStrField(String strField) {
        this.strField = strField;
    }
}

public class SingletonPattern {
    public static void test() {
        Singleton1.getSingleton().print();
        Singleton2.getSingleton().print();
        Singleton3.getSingleton().print();
        Singleton4.getSingleton().print();
        Singleton5.INSTANCE.print();
    }
}
