package com.dayup.base;

import java.beans.JavaBean;
import java.lang.annotation.*;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@interface MyAnnotation {
    String value() default "";
}

@MyAnnotation("This-is-class")
class MyClass1 {
    @MyAnnotation("This-is-field")
    private int field;
    public int field2;
    protected int field3;
    int field4;
    @MyAnnotation("This-is-constructor")
    public MyClass1() {

    }
    @MyAnnotation("This-is-constructor2")
    public MyClass1(int field) {
        this.field = field;
    }
    @MyAnnotation("This-is-method")
    public void func1(){}
    public int func2(){return 1;}
    private int func3(){return 1;}
}

public class MyAnnotationTester {
    public static void test() {
        Class<MyClass1> cls = MyClass1.class;
        Annotation[] annotations = cls.getDeclaredAnnotations();
        for (Annotation annotation: annotations) {
            System.out.println(annotation);
            System.out.println(annotation.annotationType());
            System.out.println(annotation.getClass());
            System.out.println(((MyAnnotation)annotation).value());
        }
        Field[] fields = cls.getFields();
        System.out.println("get getFields");
        for (Field field : fields) {
            System.out.println(field.getName());
            System.out.println(field.getAnnotations());
        }
        Field[] fields2 = cls.getDeclaredFields();
        System.out.println("get getDeclaredFields");
        for (Field field : fields2) {
            System.out.println(field.getName());
            System.out.println(field.getAnnotations().length);
        }
        Method[] methods = cls.getMethods();
        System.out.println("get getMethods");
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        Method[] methods2 = cls.getDeclaredMethods();
        System.out.println("get getDeclaredMethods");
        for (Method method : methods2) {
            System.out.println(method.getName());
            AnnotatedType annotatedType = method.getAnnotatedReturnType();
            System.out.println(annotatedType);
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                System.out.println(annotation);
            }
        }
        @SuppressWarnings({"unchecked"})
        Constructor<MyClass1>[] constructors = (Constructor<MyClass1>[]) cls.getConstructors();
        for (Constructor<MyClass1> constructor : constructors) {
            System.out.println(constructor.getName());
            System.out.println(constructor.getClass().getName());
        }
    }
}