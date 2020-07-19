package com.dayup.base;

import java.util.*;
import java.util.function.BiFunction;

public class MapTester {
    private class MapKeyObject {
        private int field;

        public MapKeyObject(int field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "key_" + field;
        }
        @Override
        public int hashCode() {
            System.out.println(CFunction.__INFO__() + this);
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println(CFunction.__INFO__());
            return super.equals(obj);
        }
    }

    private class MapValueObject {
        private int field;

        public MapValueObject(int field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "value_" + field;
        }
    }

    public void testHashMap() {
        Map<MapKeyObject, MapValueObject> map = new HashMap<>();
        map.put(new MapKeyObject(1), new MapValueObject(1));
        map.put(new MapKeyObject(2), new MapValueObject(2));
        map.put(new MapKeyObject(3), new MapValueObject(3));
        System.out.println(map);
    }

    public void testLinkedHashMap() {
        Map<MapKeyObject, MapValueObject> map = new LinkedHashMap<>();
        map.put(new MapKeyObject(1), new MapValueObject(1));
        map.put(new MapKeyObject(2), new MapValueObject(2));
        map.put(new MapKeyObject(3), new MapValueObject(3));
        MapKeyObject obj4 =  new MapKeyObject(4);
        map.put(obj4, new MapValueObject(4));
        map.put(obj4, new MapValueObject(4));
        System.out.println(map);
        map.compute(obj4, (mapKeyObject, mapValueObject) -> {
            System.out.println(mapKeyObject);
            System.out.println(mapValueObject);
            if (mapValueObject == null) {
                return new MapValueObject(mapKeyObject.field);
            }
            return mapValueObject;
        });
        System.out.println(map);
    }

    public void testTreeMap() {
        // treeMap不要求元素必须实现hashcode和equals方法
        Map<MapKeyObject, MapValueObject> map = new TreeMap<>(new Comparator<MapKeyObject>() {
            @Override
            public int compare(MapKeyObject o1, MapKeyObject o2) {
                System.out.println(o1 + " vs " + o2);
                if (o1.field == o2.field) {
                    System.out.println(CFunction.__INFO__());
                    return 0;// 返回0，则更新value
                }
                return 1;  // 返回1，则正序添加对象key、value，负数则逆序
            }
        });
        map.put(new MapKeyObject(1), new MapValueObject(1));
        System.out.println(map);
        map.put(new MapKeyObject(2), new MapValueObject(2));
        System.out.println(map);
        map.put(new MapKeyObject(3), new MapValueObject(3));
        System.out.println(map);
        map.put(new MapKeyObject(3), new MapValueObject(33));
        System.out.println(map.get(new MapKeyObject(0)));
        System.out.println(map);
    }

    public void testHashTable() {
        Map<MapKeyObject, Object> table = new Hashtable<>();
        table.put(new MapKeyObject(1), 1);
        table.put(new MapKeyObject(2), 2);
        table.put(new MapKeyObject(3), 3);
        System.out.println(table);
    }

    public static void test() {
        //new MapTester().testHashMap();
        //new MapTester().testLinkedHashMap();
        //new MapTester().testTreeMap();
        new MapTester().testHashTable();
    }
}
