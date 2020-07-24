package com.dayup.base;

import java.util.Arrays;
import java.util.List;
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
    public static void testArray() {
        int[][] array = new int[2][7];
        System.out.println(array.length); // 2
        System.out.println(array[0].length); // 7
        array[0] = new int[2];
        System.out.println(array[0].length); // 2
        array[1] = new int[4];
        for (int [] x : array) {
            for (int y: x) {
                //do sth;
            }
        }
        // List泛型可以是基本类型的数组，但不能是基本类型
        List<int[]> list = Arrays.asList(array);
        System.out.println(list.size()); // 2
        Integer[] a = {1,3,4,5};
        List<Integer> list2 = Arrays.asList(a);
        System.out.println(list2.size()); // 4
        int[] b = {1,3,4,5};
        List<int[]> list3 = Arrays.asList(b); // 实际将b作为一个元素放在list中
        System.out.println(list3.get(0).length); // 4
    }
    public static void test() {
        testArray();
    }
}
