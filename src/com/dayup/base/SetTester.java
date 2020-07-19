package com.dayup.base;

import java.util.*;

public class SetTester {

    private class SetObject{
        private int field;

        public SetObject(int field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "object_" + field;
        }

        @Override
        public boolean equals(Object obj) {
            // 只要重写equals，就必须重写hashCode
            // 因为Set存储的是不重复的对象，依据hashCode和equals进行判断，所以Set存储的对象必须重写这两个方法
            // 如果自定义对象做为Map的键，那么必须重写hashCode和equals
            System.out.println(CFunction.__INFO__() + "current" + toString() + " vs " + obj.toString());
            return field == ((SetObject) obj).field;
        }

        @Override
        public int hashCode() {
            System.out.println(CFunction.__INFO__() + toString());
            System.out.println(CFunction.__INFO__() + super.hashCode());
            return field;
        }
    }

    public void testHashSet() {
        Set<SetObject> set = new HashSet<>();
        set.add(new SetObject(1));
        set.add(new SetObject(2));
        set.add(new SetObject(3));
        // 因为重写了equals和hashCOde方法，所以该对象不能添加到集合中
        // hashSet底层使用hashMap实现，首先比较hashCode是否相同，不相同则add成功
        // 如果hashcode相同，再将所有hashcode一样的对象作为equals入参，依次和本对象比较，如果不同，则add成功
        set.add(new SetObject(1));
        System.out.println(set);
    }

    private class TreeSetObject implements Comparable {

        private int field;

        public TreeSetObject(int field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "object_" + field;
        }

        @Override
        public int compareTo(Object o) {
            return 1;
        }
    }

    public void testTreeSet() {
        // TreeSet集合，没有指定comparator时，add的对象必须实现Comparable接口
        // 如果add对象实现了接口，创建treeset时也指定了comparator，则以comparator为准
        // TreeSet的底层实现依赖了TreeMap
        Set<TreeSetObject> set = new TreeSet<>();
        set.add(new TreeSetObject(1));
        set.add(new TreeSetObject(2));
        set.add(new TreeSetObject(3));
        set.add(new TreeSetObject(1));
        System.out.println(set);
        // SetObject没有实现comparator接口，创建集合时必须指定comparator，否则add抛cast异常
        Set<SetObject> set2 = new TreeSet<>(new Comparator<SetObject>() {
            @Override
            public int compare(SetObject o1, SetObject o2) {
                return 1;
            }
        });
        set2.add(new SetObject(1));
        set2.add(new SetObject(2));
        set2.add(new SetObject(3));
        set2.add(new SetObject(1));
        System.out.println(set2);
    }

    public void testLinkedHashSet() {
        // 与HashSet的差别是，可以保证顺序和插入的一致
        // 底层使用LinkedHashMap实现
        LinkedHashSet<SetObject> set = new LinkedHashSet<>();
        set.add(new SetObject(1));
        set.add(new SetObject(2));
        set.add(new SetObject(3));
        set.add(new SetObject(1));
        System.out.println(set);
    }

    public static void test() {
        //new SetTester().testHashSet();
        //new SetTester().testTreeSet();
        new SetTester().testLinkedHashSet();
    }
}