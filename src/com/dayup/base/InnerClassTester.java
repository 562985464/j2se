package com.dayup.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Random;

interface InnerClassInterface {
    int STATIC_INT = 1;

    void printSth();

    default void printSth2(String str) {
    }
}

class OuterClass {
    public InnerClass innerObj = new InnerClass(3);
    private final int privateField = 1;
    private final int privateField2 = 2;

    public void outerFunc() {
        System.out.println(CFunction.__INFO__() + privateField);
        System.out.println(CFunction.__INFO__() + innerObj.privateField);
        System.out.println(CFunction.__INFO__());
        innerObj.innerFunc();
    }

    public void outerFunc2() {
        System.out.println(CFunction.__INFO__());
    }

    public InnerClass getInnerClassObject() {
        return new InnerClass(0);
    }

    public class InnerClass {
        static final int STATIC_INT = 1;  // 定义static的同时，必须是final，否则语法错误
        final int anInt2 = new Random().nextInt(); // 不能修饰为static
        final int anInt3;
        private final int privateField;

        InnerClass(int field) {
            anInt3 = 1;
            privateField = field;
        }

        public void innerFunc() {
            // 内部类可以直接访问外部类的任何域和方法，包括私有域
            // 如果重名，则就近使用内部类的
            System.out.println(CFunction.__INFO__() + privateField);
            System.out.println(CFunction.__INFO__() + privateField2);
            outerFunc();
            outerFunc2();
        }

        public OuterClass getOuterClassObject() {
            return OuterClass.this;
        }

        public void outerFunc() {
            System.out.println(CFunction.__INFO__());
        }
        /*
        内部类不允许static方法
        static void invalid(){}
        */
    }
    static  class InnerClass2 {}
}

abstract class AbstractClass {
    static int STATIC_INT;

    abstract public void printSth();

    public void printSth2(String str) {
    }

}

class OuterClass2 {
    public static InnerClassInterface getInner() {
        return new InnerClassInterface() {
            @Override
            public void printSth() {
                System.out.println(CFunction.__INFO__());
            }
        };
    }

    public static InnerClassInterface getInner2(String input) {
        return new InnerClassInterface() {
            private final String sth;

            // 匿名内部类没有有参构造函数，可以使用如下代码块实现带参数的构造的效果
            {
                sth = input;
            }

            @Override
            public void printSth() {
                System.out.println(CFunction.__INFO__() + sth);
            }

            @Override
            public void printSth2(String str) {
                System.out.println(CFunction.__INFO__() + str);
                System.out.println(CFunction.__INFO__() + sth);
                System.out.println(CFunction.__INFO__() + input);
            }
        };
    }

    public static AbstractClass getInner3() {
        return new AbstractClass() {
            @Override
            public void printSth() {
                System.out.println(CFunction.__INFO__() + AbstractClass.STATIC_INT);
            }
        };
    }
}

class OuterClass3 implements Iterable<Integer>{
    private int[] array;
    public OuterClass3(int size) {
        array = new int[size];
        for (int index = 0; index < size; index++) {
            array[index] = new Random().nextInt(32);
        }
    }
    // 匿名内部类，迭代器实现
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public Integer next() {
                return array[index++];
            }
        };
    }
}

public class InnerClassTester {
    public static void InnerClassTest1() {
        new OuterClass().innerObj.outerFunc();
        new OuterClass().outerFunc();
        OuterClass.InnerClass innerClass = new OuterClass().getInnerClassObject();
        new OuterClass().new InnerClass(1).getOuterClassObject();
        new OuterClass.InnerClass2();
    }

    public static void InnerClassTest2() {
        OuterClass2.getInner().printSth();
        OuterClass2.getInner2("input1").printSth();
        OuterClass2.getInner2("input1").printSth2("input2");
        OuterClass2.getInner3().printSth();
    }

    public static void InnerClassTest3() {
        OuterClass3 outerClass3 = new OuterClass3(6);
        // 两种迭代用法
        for (Iterator<Integer> it = outerClass3.iterator(); it.hasNext(); ) {
            Integer x = it.next();
            System.out.println(x);
        }
        for (Integer x : outerClass3) {
            System.out.println(x);
        }
    }

    public static void test() {
        //InnerClassTest1();
        //InnerClassTest2();
        InnerClassTest3();
    }
}
