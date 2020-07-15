package com.dayup.pattern;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface MyImage {
    void display();
}

class RealImage implements MyImage {
    private String imageName;
    public RealImage(String name) {
        imageName = name;
    }
    public RealImage() {
    }
    public void setImageName(String name) {
        imageName = name;
    }
    public String getImageName() {
        return imageName;
    }
    public void display() {
        System.out.println("real image display:" + imageName);
    }
}

class StaticProxyImage implements MyImage {
    RealImage realImage;
    public StaticProxyImage(String name) {
        realImage = new RealImage(name);
    }
    public void display() {
        realImage.display();
    }
}

class JDKProxyFactory {
    private Object object;
    public JDKProxyFactory(Object object) {
        this.object = object;
    }
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(proxy.getClass());
                        System.out.println("开始事务");
                        //执行目标对象方法
                        Object returnValue = method.invoke(object, args);
                        System.out.println("提交事务");
                        return returnValue;
                    }
                });
    }
    public Object getProxyInstance2() {
        class MyInvocationHandler implements InvocationHandler {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(proxy.getClass());
                System.out.println("开始事务");
                //执行目标对象方法
                Object returnValue = method.invoke(object, args);
                System.out.println("提交事务");
                return returnValue;            }
        }
        InvocationHandler inv = new MyInvocationHandler();
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), inv);
    }
}

class CglibProxyFactory implements MethodInterceptor {
    private Object object;
    public CglibProxyFactory(Object object) {
        this.object = object;
    }
    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(o.getClass());
        System.out.println("开始 事务");
        // 执行目标对象方法
        Object returnValue = method.invoke(object, objects);
        System.out.println("提交 事务");
        return returnValue;
    }
}

public class ProxyPattern {
    public static void test() {
        // 静态代理，目标对象和代理对象可以实现接口，也可以不实现
        MyImage img = new StaticProxyImage("abc.jpg");
        img.display();
        // jdk动态代理，目标对象必须实现接口
        RealImage realImage = new RealImage("def.jpg");
        MyImage img2 = (MyImage) new JDKProxyFactory(realImage).getProxyInstance2();
        img2.display();
        for (Class c: img2.getClass().getInterfaces()) {
            // 动态生成的代理对象，实际实现了目标对象的接口，因此这里会打印出MyImage
            System.out.println(c.getName());
        }
        // cglib动态代理，不要求目标对象实现接口，但不能是final类型的，final方法也不会拦截
        MyImage img3 = (MyImage) new CglibProxyFactory(realImage).getProxyInstance();
        img3.display();
        for (Method m: img3.getClass().getMethods()) {
            System.out.println(m.getName());
        }
    }
}
