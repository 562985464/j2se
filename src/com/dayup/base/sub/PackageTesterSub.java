package com.dayup.base.sub;

public class PackageTesterSub {
    int packageField;
    public int publicFiled;
    protected int protectedFiled;
    public PackageTesterSub() {
        PackageTesterSub2 subs2 = new PackageTesterSub2();
        System.out.println(subs2.packageField);
        System.out.println(subs2.publicFiled);
        System.out.println(subs2.protectedFiled);
    }
}

class PackageTesterSub2 {
    int packageField;
    public int publicFiled;
    protected int protectedFiled;
    public static int staticFiled = 0;
}

