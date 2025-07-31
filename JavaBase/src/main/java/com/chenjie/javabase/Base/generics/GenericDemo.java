package com.chenjie.javabase.Base.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型示例类，展示泛型在Java中的常见用法
 */
public class GenericDemo<T> {
    // 1. 类级别泛型 - 可用于整个类
    private T value;

    // 构造方法使用泛型
    public GenericDemo(T value) {
        this.value = value;
    }

    // 方法返回泛型类型
    public T getValue() {
        return value;
    }

    // 2. 泛型方法 - 方法级别的泛型
    public <E> void printArray(E[] array) {
        for (E element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    // 3. 有界类型参数 - 限制泛型类型范围
    public <U extends Number> double sum(List<U> numbers) {
        double total = 0.0;
        for (U num : numbers) {
            total += num.doubleValue();
        }
        return total;
    }

    // 4. 通配符示例
    public void processList(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    // 5. 上界通配符
    public double sumOfNumbers(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }

    // 6. 下界通配符
    public void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {
        // 1. 类级别泛型使用示例
        GenericDemo<String> stringDemo = new GenericDemo<>("Hello Generics");
        System.out.println("String Value: " + stringDemo.getValue());

        GenericDemo<Integer> intDemo = new GenericDemo<>(42);
        System.out.println("Integer Value: " + intDemo.getValue());

        // 2. 泛型方法示例
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] strArray = {"A", "B", "C"};

        System.out.print("Integer Array: ");
        stringDemo.printArray(intArray); // 使用String实例调用泛型方法

        System.out.print("String Array: ");
        intDemo.printArray(strArray); // 使用Integer实例调用泛型方法

        // 3. 有界类型参数示例
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        System.out.println("Sum of integers: " + stringDemo.sum(integers));

        List<Double> doubles = List.of(1.1, 2.2, 3.3);
        System.out.println("Sum of doubles: " + stringDemo.sum(doubles));

        // 4. 通配符示例
        List<String> strings = List.of("One", "Two", "Three");
        stringDemo.processList(strings);

        // 5. 上界通配符示例
        List<Number> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2.5);
        numbers.add(3L);
        System.out.println("Sum of numbers: " + stringDemo.sumOfNumbers(numbers));

        // 6. 下界通配符示例
        List<Object> objects = new ArrayList<>();
        stringDemo.addNumbers(objects);
        System.out.println("List after adding numbers: " + objects);
    }
}