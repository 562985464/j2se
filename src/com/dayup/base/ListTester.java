package com.dayup.base;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ListTester {
    private interface MyConsumer<T> extends Consumer<T> {
        @Override
        void accept(T t);
        void process(T t);
    }
    private class ListObject {
        private int field;
        public ListObject(int field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return "object_" + field;
        }
    }
    public void testArrayList() {
        // 没有指定类型，列表中默认是Object类型
        List list = new ArrayList();
        ListObject listObj1 = new ListObject(1);
        ListObject listObj2 = new ListObject(2);
        ListObject listObj3 = new ListObject(3);
        list.add(listObj1);
        list.add(listObj2); // 尾端追加新元素
        list.add(0, listObj3); // ArrayList 插入性能不高，随机访问效率高
        ListObject obj = (ListObject) list.get(1);
        System.out.println(obj);
        System.out.println(list);
        // 指定了类型
        List<ListObject> list2 = new ArrayList<>();
        list2.add(listObj1);
        list2.add(listObj2);
        list2.add(listObj3);
        ListObject obj2 = list2.get(1);
        System.out.println(obj2);
        System.out.println(list);
        ListObject[] listObjects = list2.toArray(new ListObject[0]);
        for (ListObject listObject:listObjects) {
            System.out.println(listObject);
        }
        ArrayList<ListObject> list3 = new ArrayList<>();
        list3.ensureCapacity(1);
        list3.add(listObj1);
        list3.add(listObj2);
        list3.add(listObj3);
        /*
        这里用到了lambda表达式，如果实现的接口或者抽象类，可以用简写的方式，如果接口有多个未实现的方法，则提示一下错误，不能明确要实现哪个
        Multiple non-overriding abstract methods found in interface com.dayup.base.ListTester.MyConsumer
        MyConsumer<ListObject> consumer = (listObject) -> System.out.println(CFunction.__INFO__() + listObject);
        */
        Consumer<Object> consumer2 = listObject -> System.out.println(CFunction.__INFO__() + listObject);
        /*foreach方法，是将列表中的元素逐一取出，调用接口实现的accept方法来处理一次*/
        list3.forEach(consumer2);
        /**removeIf方法，将列表元素取出，调用接口实现的test方法，如果返回true，则从列表中移除该元素*/
        list3.removeIf(new Predicate<ListObject>() {
            int i = 1;
            @Override
            public boolean test(ListObject listObject) {
                if (i++%2 == 0)
                    return true;
                return false;
            }
        });
        list3.forEach(consumer2);
        /*jdk8新增的可分片迭代器，每次trySplit将会尽可能对半拆分出一个新的分片迭代器*/
        Spliterator<ListObject> spliterators1 = list3.spliterator();
        spliterators1.trySplit().forEachRemaining(o->System.out.println(o));
        spliterators1.forEachRemaining(o->System.out.println(o));
    }
    public void testLinkedList() {
        LinkedList<ListObject> list = new LinkedList<>();
        // 将linkedlist用作队列queue
        Queue<ListObject> queue = list;
        // 向队列中添加元素，add和offer都是从队尾，两个方法的区别是如果失败add会抛异常，offer返回false，成功都返回true
        queue.add(new ListObject(1));
        queue.add(new ListObject(2));
        queue.offer(new ListObject(3));
        // 打印队列元素为1，2，3
        System.out.println(CFunction.__INFO__() + queue);
        // 从队列头返回并移除一个元素，poll和remove的区别是，如果失败，remove会抛异常，poll返回null
        queue.poll();
        queue.remove();
        // 打印队列元素为3
        System.out.println(CFunction.__INFO__() + queue);
        // 将linkedlist用作双端队列,双端队列又可以实现栈的效果，不要使用类库的stack。
        Deque<ListObject> deque = list;
        // add和addLast都是从双端队列队尾添加,offerlast失败不会抛异常，返回false
        deque.add(new ListObject(4));
        deque.addLast(new ListObject(5));
        deque.offerLast(new ListObject(6));
        // 从双端队列头添加，push的字面更适合栈的[压入]，同add一样，失败抛异常, offerFirst失败不会抛异常
        deque.addFirst(new ListObject(3));
        deque.push(new ListObject(2));
        deque.offerFirst(new ListObject(1));
        System.out.println(deque);
        // 从队列尾移除，polllast失败返回null
        deque.removeLast();
        deque.pollLast();
        // 从队列头移除，pollfirst失败返回null
        deque.removeFirst();
        deque.pop(); // = removeFirst
        deque.pollFirst();
        // peek = peekFirst，peekLast 失败不会抛异常
        // element=getFirst，getLast 失败抛异常
        /*以下四个方法功能一样，返回第一个元素*/
        System.out.println(list.getFirst());
        System.out.println(list.element());
        System.out.println(list.peek());
        System.out.println(list.peekFirst());
        /*peekLast 和getLast 功能一样，返回最后一个元素*/
        System.out.println(list.peekLast());
        System.out.println(list.getLast());
    }

    public static void test() {
        new ListTester().testArrayList();
        new ListTester().testLinkedList();
    }
}
