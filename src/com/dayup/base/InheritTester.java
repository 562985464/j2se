package com.dayup.base;

class ParentClass {
    // 静态方法可以被覆盖
    public static void staticFunc() {
        System.out.println(CFunction.__INFO__());
    }
    // final方法不可被覆盖，不论是不是static
    public static final void staticFinalFunc() {
        System.out.println(CFunction.__INFO__());
    }
    public ParentClass(int i) {
        System.out.println("this is parent Class");
    }

    public final void finalFunc() {
        System.out.println("parent finnal function");
    }
}

class SonClass extends ParentClass {
    // 重写静态方法
    public static void staticFunc() {
        System.out.println(CFunction.__INFO__());
    }
    // 重载静态方法
    public static void staticFunc(int i) {
        System.out.println(CFunction.__INFO__());
    }
    private static void privateStaticFunc() {
        System.out.println(CFunction.__INFO__());
    }
    protected static void protectedStaticFunc() {
        System.out.println(CFunction.__INFO__());
    }
    static void packageStaticFunc() {
        System.out.println(CFunction.__INFO__());
    }
    public SonClass(int i) {
        super(i);
        System.out.println("this is son class");
    }
    public int commonFunc() {
        System.out.println("Son class final func");
        privateStaticFunc();
        return 1;
    }
}

class GrandsonClass extends SonClass {
    public GrandsonClass() {
        super(1);
        System.out.println("this is gson class");
    }
    /*
    不能覆盖基类中定义为final的方法
    public void finalFunc(){}
    */
    public int commonFunc() {
        packageStaticFunc();
        protectedStaticFunc();
        finalFunc();
        return super.commonFunc();
    }
}

public class InheritTester {
    public static void test() {
        new GrandsonClass().finalFunc();
        GrandsonClass.staticFunc();
        GrandsonClass.staticFinalFunc();
        GrandsonClass.staticFunc(1);
        // 非继承关系，protected退化为包访问权限
        GrandsonClass.protectedStaticFunc();
        GrandsonClass.packageStaticFunc();
    }
}