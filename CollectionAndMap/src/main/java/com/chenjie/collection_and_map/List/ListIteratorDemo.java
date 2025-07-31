package com.chenjie.collection_and_map.List;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorDemo {

    public static void main(String[] args) {
        // 1. 创建ArrayList和LinkedList并添加数据
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("Python");
        arrayList.add("C++");
        arrayList.add("JavaScript");
        arrayList.add("Go");

        List<String> linkedList = new LinkedList<>();
        linkedList.add("苹果");
        linkedList.add("香蕉");
        linkedList.add("橙子");
        linkedList.add("葡萄");
        linkedList.add("西瓜");

        System.out.println("初始ArrayList: " + arrayList);
        System.out.println("初始LinkedList: " + linkedList);

        // 2. ArrayList的正向遍历
        System.out.println("\n=== ArrayList正向遍历 ===");
        ListIterator<String> arrayListIterator = arrayList.listIterator();
        while (arrayListIterator.hasNext()) {
            String element = arrayListIterator.next();
            System.out.println("元素: " + element);

            // 在遍历过程中修改元素
            if (element.equals("Python")) {
                arrayListIterator.set("Python 3.9"); // 修改元素
            }

            // 在遍历过程中删除元素
            if (element.equals("C++")) {
                arrayListIterator.remove(); // 删除当前元素
            }
        }
        System.out.println("修改后的ArrayList: " + arrayList);

        // 3. ArrayList的反向遍历
        System.out.println("\n=== ArrayList反向遍历 ===");
        arrayListIterator = arrayList.listIterator(arrayList.size()); // 从末尾开始
        while (arrayListIterator.hasPrevious()) {
            String element = arrayListIterator.previous();
            System.out.println("元素: " + element);
        }

        // 4. LinkedList的正向遍历
        System.out.println("\n=== LinkedList正向遍历 ===");
        ListIterator<String> linkedListIterator = linkedList.listIterator();
        while (linkedListIterator.hasNext()) {
            String element = linkedListIterator.next();
            System.out.println("元素: " + element);

            // 在遍历过程中添加元素
            if (element.equals("橙子")) {
                linkedListIterator.add("芒果"); // 在当前元素后添加
            }
        }
        System.out.println("修改后的LinkedList: " + linkedList);

        // 5. LinkedList的反向遍历
        System.out.println("\n=== LinkedList反向遍历 ===");
        linkedListIterator = linkedList.listIterator(linkedList.size()); // 从末尾开始
        while (linkedListIterator.hasPrevious()) {
            String element = linkedListIterator.previous();
            System.out.println("元素: " + element);

            // 在反向遍历过程中删除元素
            if (element.equals("葡萄")) {
                linkedListIterator.remove(); // 删除当前元素
            }
        }
        System.out.println("最终LinkedList: " + linkedList);

        // 6. 使用forEachRemaining方法（Java 8+）
        System.out.println("\n=== 使用forEachRemaining遍历ArrayList ===");
        arrayListIterator = arrayList.listIterator();
        arrayListIterator.forEachRemaining(element -> {
            System.out.println("元素: " + element);
        });
    }
}