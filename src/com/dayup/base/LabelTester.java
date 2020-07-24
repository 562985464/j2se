package com.dayup.base;

public class LabelTester {
    public static void testInnerLabel() {
        //innerLable:
        for (int j = 0; j < 5; j++) {
            if (j == 2) {
                //continue ; /* same as continue innerLable;*/
                break ; /* same as break innerLable;*/
            }
            System.out.println(CFunction.__INFO__() + j);
        }
    }

    public static void testOuterLabel() throws Exception {
        outerLable:
        for (int i = 0; i < 5; i++) {
            System.out.println(CFunction.__INFO__() + "outer " + i);
            //innerLabel:
            for (int j = 0; j < 10; j++) {
                if (j == 4) {
                    break outerLable; /* 跳出外部循环体，即停止多层循环*/
                    //continue outerLable; /* 跳到外部循环，继续下一次循环*/
                }
                System.out.println(CFunction.__INFO__() + "inner " + j);
            }
        }
    }
    public static void test() {

    }
}
