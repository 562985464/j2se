package com.dayup.base;

class ParentClass {
    private int intField;
    // 静态方法可以被覆盖
    public static void staticFunc() {
        System.out.println(CFunction.__INFO__());
    }
    // final方法不可被覆盖，不论是不是static
    public static final void staticFinalFunc() {
        System.out.println(CFunction.__INFO__());
    }
    public ParentClass(int intField) {
        this.intField = intField;
        System.out.println("this is parent Class:" + intField);
    }

    public final void finalFunc() {
        System.out.println("parent finnal function");
    }

    public void print() {
        System.out.println("intField:" + intField);
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
    public SonClass(int intField) {
        super(intField);
        System.out.println("this is son class");
    }
    public int commonFunc() {
        System.out.println("Son class final func");
        privateStaticFunc();
        return 1;
    }
    static {
        System.out.println("parent class static code");
    }
}
// 初始化一个类对象的过程
// 1、类加载时，从当前类的根基类，顺序执行静态代码块/域
// 2、创建类对象时，先递归基类，再该类中的域和普通代码块，最后构造方法
class GrandsonClass extends SonClass {
    {
        // 代码块在前，为域字段赋值，如果域字段定义后有赋值，该代码块赋值将备覆盖
        //son = new SonClass(1);
        son3 = new SonClass(5);
        System.out.println("code block1.");
    }
    static {
        // 类加载时，执行static代码块及filed
        str2 = "xx";
        System.out.println("static code block1.");
    }
    private SonClass son = new SonClass(2);
    private static SonClass son2 = new SonClass(3);
    private SonClass son3;
    public final String str;// = "init string";
    public final static String str2;// = "yy";

    public GrandsonClass() {
        super(4);
        //str = "init string";
        System.out.println("this is gson class");
        son.print(); // 这里输出 intField:2，可以发现代码块先创建的对象，域声明处后创建的对象，顺序相关
        son3.print();
    }

    static {
        System.out.println("static code block2.");
    }
    {
        str = "init str in code block";
        System.out.println(str);
        System.out.println("code block2.");
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

enum MyEnum1 {
    AAA(1){
        @Override
        public void f() {

        }

        @Override
        public String toString() {
            return super.toString();
        }
    };
    private MyEnum1(int i){}
    public abstract void f();
}

interface Common {
    public void f();
}

//因为编译器会自动为我们继承Enum抽象类而Java只支持单继承，因此枚举类是无法手动实现继承的
enum MyEnum2 implements Common {
    CCC(1) {
        // 枚举值对象，可以重写枚举类的方法
        @Override
        public void f() {
            super.f();
            System.out.println(CFunction.__INFO__());
        }
    },
    DDD(2),EEE(3),FFF; // 枚举变量，编译后实际是枚举类的静态域/枚举类型的对象
    // 默认的构造，枚举FFF调用该构造
    MyEnum2() {
        System.out.println(CFunction.__INFO__());
        System.out.println(this);
        System.out.println(CFunction.__INFO__());
    }
    // 非默认构造, 带参数的CCC、DDD、EEE枚举将会调用该构造
    MyEnum2(int i) {
        System.out.println(CFunction.__INFO__());
    }

    @Override
    public void f() {
        System.out.println(CFunction.__INFO__() + ordinal());
        System.out.println(CFunction.__INFO__() + this.hashCode());
    }

    @Override
    public String toString() {
        System.out.println(CFunction.__INFO__());
        return super.toString();
    }
}


public class InheritTester {
    public static void test() {
        GrandsonClass grandson = new GrandsonClass();
        grandson.print();
        grandson.finalFunc();
        GrandsonClass.staticFunc();
        GrandsonClass.staticFinalFunc();
        GrandsonClass.staticFunc(1);
        // 非继承关系，protected退化为包访问权限
        GrandsonClass.protectedStaticFunc();
        GrandsonClass.packageStaticFunc();
        // 测试枚举类
        MyEnum2 enum2 = MyEnum2.CCC;
        enum2.f();
    }
}