package com.dayup.base;

import java.util.Date;

interface MyInterface {
    int DEFULT_FIELD = 1;  // 域，默认且必须修饰符 public static final

    // 实现接口的类或者子接口，不继承静态方法，和类继承明显不同
    static void staticFunc() {
        System.out.println(CFunction.__INFO__());
    }

    void func(); // 域和方法，默认且只能是public

    String func2(); // 域和方法，默认且只能是public

    // 接口新特性，加上default修饰符，可以有默认实现
    // 如果实现多个接口时，每个接口都有相同的default方法需要重写该方法，因为不能明确要继承哪个，如下面的MyInstanceSon类示例
    default void defaultFunc() {
        System.out.println(CFunction.__INFO__());
        String str = func2();// 调用func2方法，是具体实现类对象的。破坏了接口的原则，是为了扩展现有类库而支持的特性
        System.out.println(CFunction.__INFO__() + str);
    }

    default void defaultFunc2() {
        // 默认方法，可以像类继承一样被覆盖重写
        System.out.println(CFunction.__INFO__());
    }
}

interface MyInterfaceSon1 extends MyInterface {
    default void sameNameDefaultFunc() {
    }
}

interface MyInterfaceSon2 extends MyInterface {
    default void sameNameDefaultFunc() {
    }
}

class MyInstance extends CFunction implements MyInterface {
    private final String str;

    public MyInstance() {
        str = new Date().toString();
    }

    // @Override 接口静态方法，无覆盖，实现类中作为新方法加入，与接口无关
    static void staticFunc() {
        System.out.println(CFunction.__INFO__());
    }

    @Override
    public void func() {
        staticFunc();
        System.out.println(CFunction.__INFO__());
    }

    @Override
    public String func2() {
        return str;
    }

    @Override
    public void defaultFunc2() {
        System.out.println(CFunction.__INFO__());
    }
}

class MyInstanceSon extends MyInstance implements MyInterfaceSon1, MyInterfaceSon2 {
    @Override
    public void sameNameDefaultFunc() {
        // 这个例子证明，继承多个接口中存在相同的默认方法
        // 如果追踪到默认方法的根源是一个接口，比如defaultFunc和defaultFunc2方法，也就不强制重写该默认方法
        // 否则，就像当前方法的默认方法是在两个不同的接口中定义的，就必须重写
    }
}

public class InterfaceTester {
    public static void test() {
        MyInterface myInstance = new MyInstance();
        myInstance.func();
        myInstance.defaultFunc();
        MyInterface.staticFunc();
        MyInstanceSon myInstanceSon = new MyInstanceSon();
        myInstanceSon.defaultFunc();
    }
}
