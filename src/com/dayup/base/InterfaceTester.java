package com.dayup.base;

interface MyInterface {
    int DEFULT_FIELD = 1;  // 域，默认且必须修饰符 public static final
    void func(); // 域和方法，默认且只能是public
    // 实现接口的类或者子接口，不继承静态方法，和类继承明显不同
    static void staticFunc() {
        System.out.println(CFunction.__INFO__());
    }
    // 接口新特性，加上default修饰符，可以有默认实现
    // 如果实现多个接口时，每个接口都有相同的default方法需要重写该方法，因为不能明确要继承哪个
    default void defaultFunc() {
        System.out.println(CFunction.__INFO__());
    }
}

class MyInstance extends CFunction implements MyInterface {
    @Override
    public void func() {
        staticFunc();
        System.out.println(CFunction.__INFO__());
    }
    @Override
    public void defaultFunc() {
        System.out.println(CFunction.__INFO__());
    }
    // @Override 接口静态方法，无覆盖，实现类中作为新方法加入，与接口无关
    static void staticFunc() {
        System.out.println(CFunction.__INFO__());
    }
}

interface MyInterfaceSon1 extends MyInterface {}
interface MyInterfaceSon2 extends MyInterface {}

class MyInstanceSon extends MyInstance implements MyInterfaceSon1,MyInterfaceSon2 {
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
