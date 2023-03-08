package com.zukxu.design.behavioral.iterator.demo1;

import java.util.NoSuchElementException;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 17:08:48
 */
// 定义一个迭代器接口
interface Iterator<T> {

    boolean hasNext();

    T next();

}

// 定义一个聚合接口
interface Aggregate<T> {

    Iterator<T> createIterator();

}

// 定义一个具体迭代器实现
class ArrayIterator<T> implements Iterator<T> {

    private T[] array;

    private int index = 0;

    public ArrayIterator(T[] array) {
        this.array = array;
    }

    public boolean hasNext() {
        return index < array.length;
    }

    public T next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        return array[index++];
    }

}

// 定义一个具体聚合实现
class ArrayAggregate<T> implements Aggregate<T> {

    private T[] array;

    public ArrayAggregate(T[] array) {
        this.array = array;
    }

    public Iterator<T> createIterator() {
        return new ArrayIterator<T>(array);
    }

}

// 客户端代码
public class Demo1 {

    public static void main(String[] args) {
        String[] words = { "hello", "world", "java", "design", "pattern" };

        Aggregate<String> aggregate = new ArrayAggregate<String>(words);
        Iterator<String> iterator = aggregate.createIterator();

        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
