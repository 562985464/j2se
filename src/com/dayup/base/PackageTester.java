package com.dayup.base;

import com.dayup.base.sub.PackageTesterSub;

public class PackageTester {
    public static void test() {
        // same package, other java file class, access ok.
        GrandsonClass grandsonClass = new GrandsonClass();
        // 非继承关系访问protected，退化为包访问权限
        GrandsonClass.protectedStaticFunc();
        PackageTesterSub subs = new PackageTesterSub();
        System.out.println(subs.publicFiled);
        /*
         * not same package, access error.
           System.out.println(subs.packageField);
           PackageTesterSub2 subs2 = new Subs2();
         */
    }
}
