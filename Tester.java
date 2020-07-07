package com.demo;

class ParentClass {
    public ParentClass(int i) {
        System.out.println("this is parent Class");
    }
    final public void finalFunc(){
        System.out.println("parent finnal function");
    }
}

class SonClass extends ParentClass {
    public SonClass(int i) {
        super(i);
        System.out.println("this is son class");
    }

    public int finalFunc2(){
        System.out.println("Son class final func");
        return 1;
    }
}

class GsonClass extends SonClass {
    public GsonClass(){
        super(1);
        System.out.println("this is gson class");
        this.finalFunc2();
    }
    public int finalFunc2(){
        super.finalFunc();
        return super.finalFunc2();
    }
}

public class Tester{
    static void main(String[] argus){
        new GsonClass();
    }
}