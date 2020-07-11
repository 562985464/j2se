package com.dayup.base;

import java.util.Random;

public abstract class ArrayTester {
    public static void testArrayStyle() {
        int[] intArray1, intArray2;  // java-style array
        int intArray3[], intArray4[]; // c-style array
        intArray1 = new int[3];
        intArray2 = intArray1;
        System.out.println(intArray2.length);
    }
    public static void testArrayInit() {
        Integer[] integerArray1 = new Integer[3];
        // for-each init
        for (Integer x : integerArray1) {
            x = new Random().nextInt(10);
            System.out.println(x);
        }
        // another two methods init
        Integer[] integerArray2 = {1, 2, 3};
        Integer[] integerArray3 = new Integer[]{1, 2, 3};
        for (Integer x : integerArray2) {
            System.out.println(x);
        }
        for (Integer x : integerArray3) {
            System.out.println(x);
        }
    }
}
